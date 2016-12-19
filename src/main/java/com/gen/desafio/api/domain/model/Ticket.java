package com.gen.desafio.api.domain.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.gson.GsonBuilder;



/**
 * DATA OBJECT - ENTITY MODEL BEAN POJO
 */
@Entity
@Table(name="tickets")
public class Ticket implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(name = "ticket_type", nullable = false)
    private int ticketType;
	
	@Column(name = "title", nullable = true, length = 60)
    private String title;
	
	@NotEmpty
	@Column(name = "description", nullable = false, length = 200)
    private String description;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.NONE)
	@Column(name = "posted_date", nullable = false)  // columnDefinition="DATETIME" -> @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate;
	
	@Column(name = "is_comment", nullable = false)
    private boolean isComment;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
 	private User relatedUser;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parentTicket", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Ticket> comments = new ArrayList<Ticket>();
	
	@ManyToOne
    @JoinColumn(name = "parent_ticket_id", nullable=true)
 	private Ticket parentTicket;

    

	/* Default Constructor */
	public Ticket(){ }
	
	
	public Ticket(String title, String description, String notes){
		this.title = title;
		this.description = description;
	}
	
	
	/* Getters & Setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	public int getTicketType() {
		return ticketType;
	}

	public void setTicketType(int ticketType) {
		this.ticketType = ticketType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	
	public User getRelatedUser() {
		return relatedUser;
	}

	public void setRelatedUser(User relatedUser) {
		this.relatedUser = relatedUser;
	}
	
	public boolean isComment() {
		return isComment;
	}

	public void setComment(boolean isComment) {
		this.isComment = isComment;
	}

	public List<Ticket> getComments() {
		return comments;
	}

	public void setComments(List<Ticket> comments) {
		this.comments = comments;
	}

	public Ticket getParentTicket() {
		return parentTicket;
	}

	public void setParentTicket(Ticket parentTicket) {
		this.parentTicket = parentTicket;
	}

	
	/*******************
     *  BEAN METHODS   *
     *******************/
			
	public String toGson(){
		String gson = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		return gson.toString();
	}
	
	
}
