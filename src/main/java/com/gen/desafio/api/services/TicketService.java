package com.gen.desafio.api.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.gen.desafio.api.domain.model.Ticket;
import com.gen.desafio.api.domain.model.User;


@Service
public interface TicketService extends BaseService {
	
	 boolean createTicket(Ticket ticket); 
	 boolean addComment(Ticket comment, long ticketId);
	 Ticket find(long id);
     List<Ticket> listTicketsByUser(long userId, int ticketType);
     List<Ticket> listTicketsByClient(long clientId, int ticketType);
     List<Ticket> listSupportTickets();
     void removeTicket(long id, User owner);
     
}
