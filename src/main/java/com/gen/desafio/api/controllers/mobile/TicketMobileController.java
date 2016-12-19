package com.gen.desafio.api.controllers.mobile;
 

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gen.desafio.api.domain.dto.req.CommentTicketReqDTO;
import com.gen.desafio.api.domain.dto.req.CreateTicketReqDTO;
import com.gen.desafio.api.domain.dto.res.BooleanResultDTO;
import com.gen.desafio.api.domain.dto.res.TicketResponseDTO;
import com.gen.desafio.api.domain.exception.InvalidRequestException;
import com.gen.desafio.api.domain.model.Ticket;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.TicketService;
import com.gen.desafio.api.utils.EmailSender;
import com.gen.desafio.api.utils.mappers.TicketMapper;
import com.gen.desafio.api.utils.security.AuthoritiesConstants;


 
@RestController
@RequestMapping("/api/v1m")
@Secured(AuthoritiesConstants.USER)
public class TicketMobileController {
	
	private final Logger log = LoggerFactory.getLogger(TicketMobileController.class);
	
	
	@Autowired
    TicketService ticketService;
	
	
	//-------------------Retrieve User Tickets (Types: Contact / Support)-----------------------------------
	
	@RequestMapping(value = "/tickets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TicketResponseDTO>> retrieveTickets(@RequestParam(value="tt", required=true) int ticketType, 
																   @AuthenticationPrincipal User authUser) {
		log.debug("Retrieving Tickets for Mobile User...");

		// tt = 1 --> Contact Tickets
		// tt = 2 --> Support Tickets
		
		List<Ticket> tickets = ticketService.listTicketsByUser(authUser.getId(), ticketType);
		
		if(tickets==null || tickets.isEmpty()){
            return new ResponseEntity<List<TicketResponseDTO>>(HttpStatus.NO_CONTENT);
        }
        else{
        	List<TicketResponseDTO> response = TicketMapper.buildCustomTicketsDTO(tickets);
        	return new ResponseEntity<List<TicketResponseDTO>>(response, HttpStatus.OK);	
        }
		
	}
	
	
	//------------------- Create Ticket (Support / Contact) -----------------------------------
	
	@RequestMapping(value = "/tickets", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> createTicket(@RequestBody @Validated CreateTicketReqDTO tReq, BindingResult bindingResult, 
    		                                             @AuthenticationPrincipal User authUser) {
    	log.debug("Creating new Ticket for Mobile User...");
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        Ticket ticket = TicketMapper.convertTicketDTOToEntity(tReq, authUser);
        boolean saved = ticketService.createTicket(ticket);
        
        BooleanResultDTO response  = new BooleanResultDTO(saved);
        
        if(saved){
        	EmailSender.notifyTicketsInCopy(authUser, ticket);
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CREATED);
        }
        else{
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
        }
        
    }
	
	
	
	//------------------- Comment Ticket (Support / Contact) -----------------------------------
	
	@RequestMapping(value = "/tickets/{id}/comments", method = RequestMethod.POST)
    public ResponseEntity<BooleanResultDTO> commentTicket(@PathVariable("id") long ticketId, 
    		                                              @RequestBody @Validated CommentTicketReqDTO tcReq, BindingResult bindingResult, 
    		                                              @AuthenticationPrincipal User authUser) {
    	log.debug("Adding comment to existing mobile Ticket...");
 
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Algunos campos tienen errores", bindingResult);
        }
        
        Ticket comment = TicketMapper.convertTicketCommentDTOToEntity(tcReq, authUser);
        boolean saved = ticketService.addComment(comment, ticketId);
        
        BooleanResultDTO response  = new BooleanResultDTO(saved);     
        
        if(saved)
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.OK);
        else
        	return new ResponseEntity<BooleanResultDTO>(response, HttpStatus.CONFLICT);
    }


 
}