package com.gen.desafio.api.services.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gen.desafio.api.dal.TicketDAO;
import com.gen.desafio.api.domain.exception.ApplicationException;
import com.gen.desafio.api.domain.exception.InvalidRequestException;
import com.gen.desafio.api.domain.exception.RecordNotFoundException;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.domain.model.Ticket;
import com.gen.desafio.api.services.TicketService;


@Service
@Transactional
public class TicketServiceImpl implements TicketService {
	
	private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);
 
	
    @Autowired 
    private TicketDAO ticketRepository;


	@Override
	public boolean createTicket(Ticket ticket) {
		// Business Rules
    	boolean isSaved = false;
    	
    	try {
	    	ticket = ticketRepository.saveAndFlush(ticket);
	    	isSaved = true;
			log.info("Ticket successfully saved");
	    }
	    catch(Exception ex) {
			log.error("Error - " + ex.getMessage());
			throw ex;
		}
		
		return isSaved;
	}
	
	
	@Override
	public boolean addComment(Ticket comment, long ticketId) {
		// Business Rules
    	boolean isSaved = false;
    	Ticket parentTicket = ticketRepository.findOne(ticketId);
    	if(parentTicket != null) {
    		comment.setTicketType(parentTicket.getTicketType());
    		parentTicket.getComments().add(comment);
			comment.setParentTicket(parentTicket);
			ticketRepository.saveAndFlush(parentTicket);
			isSaved = true;
			log.info("Ticket comment successfully saved");
		}
		else{
			throw new RecordNotFoundException("Ticket no encontrado para comentar.");
		} 
		
		return isSaved;
	}


	@Override
	public Ticket find(long id) {
    	Ticket ticket = ticketRepository.findOne(id);
		if (ticket == null) {
			throw new RecordNotFoundException("Ticket no encontrado.");
		}
		
		return ticket; 
	}

	@Override
	public List<Ticket> listTicketsByClient(long clientId, int ticketType) {
		return ticketRepository.findByClientAndType(clientId, ticketType);
	}


	@Override
	public List<Ticket> listTicketsByUser(long userId, int ticketType) {
		return ticketRepository.findByUserAndType(userId, ticketType);
	}
	
	
	@Override
	public List<Ticket> listSupportTickets() {
		return ticketRepository.findByTicketTypeAndIsCommentFalseOrderByPostedDateDesc(2);
	}


	@Override
	public void removeTicket(long id, User owner) {
		// Business Rules
		Ticket validOne = ticketRepository.findOne(id);
		if(validOne==null) {
			log.info("Could not found valid Ticket info for: " + id);
			throw new RecordNotFoundException("Ticket no encontrado.");
		}
		else{
	    	try {
	    		if(validOne.getRelatedUser().equals(owner)){
	    			ticketRepository.delete(validOne);
	    			log.info("Ticket deleted successfully");
	    		}
	    		else{
	    			throw new InvalidRequestException("El Ticket solo puede ser eliminada por el Administrador.");
	    		}
			} catch (ApplicationException ex) {
				log.error("Error - " + ex.getMessage());
				throw ex;
			}
		}		
	}
    
}




