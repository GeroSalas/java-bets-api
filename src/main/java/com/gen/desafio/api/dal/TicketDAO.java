package com.gen.desafio.api.dal;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gen.desafio.api.domain.model.Ticket;


  // JPA REPOSITORY

@Repository
public interface TicketDAO extends JpaRepository<Ticket, Long>{ 
	
	@Query(value="SELECT * FROM tickets t WHERE t.user_id=?1 AND t.is_comment=false AND t.ticket_type=?2 GROUP BY t.id ORDER BY t.posted_date DESC", nativeQuery=true)
	List<Ticket> findByUserAndType(long userId, int ticketType);
	
	@Query(value="SELECT t.* FROM tickets t, d_users u WHERE t.user_id=u.id AND u.client_id=?1 AND t.is_comment=false AND t.ticket_type=?2 GROUP BY t.id ORDER BY t.posted_date DESC", nativeQuery=true)
	List<Ticket> findByClientAndType(long clientId, int ticketType);
	
	//@Query(value="SELECT * FROM tickets tWHERE t.is_comment=false AND t.ticket_type=2 GROUP BY t.id ORDER BY t.posted_date DESC", nativeQuery=true)
	List<Ticket> findByTicketTypeAndIsCommentFalseOrderByPostedDateDesc(int supportType);
	
}
