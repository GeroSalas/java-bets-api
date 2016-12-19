package com.gen.desafio.api.utils.mappers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gen.desafio.api.domain.dto.req.CommentTicketReqDTO;
import com.gen.desafio.api.domain.dto.req.CreateSupportTicketReqDTO;
import com.gen.desafio.api.domain.dto.req.CreateTicketReqDTO;
import com.gen.desafio.api.domain.dto.res.CommentResponseDTO;
import com.gen.desafio.api.domain.dto.res.TicketResponseDTO;
import com.gen.desafio.api.domain.model.Ticket;
import com.gen.desafio.api.domain.model.User;


public class TicketMapper extends ObjectMapper {
	
	
	public static Ticket convertTicketDTOToEntity(CreateTicketReqDTO tReq, User currentUser){
		Ticket entity = new Ticket();
		 entity.setPostedDate(new Date());
		 entity.setTitle(tReq.getTitle());
		 entity.setDescription(tReq.getDescription());
		 entity.setTicketType(tReq.getTicketType());
		 entity.setRelatedUser(currentUser);
		 entity.setComment(false);
		 entity.setParentTicket(null); // Is not a simple comment
		 
		return entity;
	}
	
	public static Ticket convertTicketDTOToEntity(CreateSupportTicketReqDTO tReq, User currentUser){
		Ticket entity = new Ticket();
		 entity.setPostedDate(new Date());
		 entity.setTitle(tReq.getTitle());
		 entity.setDescription(tReq.getDescription());
		 entity.setTicketType(2);
		 entity.setRelatedUser(currentUser);
		 entity.setComment(false);
		 entity.setParentTicket(null); // Is not a simple comment
		 
		return entity;
	}
	
	
	public static Ticket convertTicketCommentDTOToEntity(CommentTicketReqDTO tcReq, User currentUser){
		Ticket entity = new Ticket();
		 entity.setPostedDate(new Date());
		 entity.setTitle(null);
		 entity.setDescription(tcReq.getTextContent());
		 entity.setRelatedUser(currentUser);
		 entity.setComment(true);
		 entity.setParentTicket(null);  // Will be set on Service Layer before saved
		 
		return entity;
	}
	
	
	public static TicketResponseDTO buildCustomTicketDTO(Ticket ticket){
		TicketResponseDTO dto = null;
		  if(!ticket.isComment() && ticket.getParentTicket()==null){
			    dto = new TicketResponseDTO();
			    dto.setId(ticket.getId());
				dto.setPostedDate(ticket.getPostedDate().toInstant().toString());
				dto.setRelatedUser(UserMapper.buildMinifiedUserDTOResponse(ticket.getRelatedUser()));
				dto.setTitle(ticket.getTitle());
				dto.setDescription(ticket.getDescription());
				// Fetch / Add child comments to the related parent Ticket
				for(Ticket c : ticket.getComments()){
					CommentResponseDTO childComment = new CommentResponseDTO();
					 childComment.setCommentedDate(c.getPostedDate().toInstant().toString());
					 childComment.setText(c.getDescription());
					 childComment.setRelatedUser(UserMapper.buildMinifiedUserDTOResponse(c.getRelatedUser()));
					 
					 dto.getComments().add(childComment);
				}
		   }
		
		return dto;
	}
	
	public static List<TicketResponseDTO> buildCustomTicketsDTO(List<Ticket> tickets){
		List<TicketResponseDTO> dtos = new ArrayList<TicketResponseDTO>();
		for(Ticket t : tickets){
			TicketResponseDTO dto = buildCustomTicketDTO(t);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	
	
}
