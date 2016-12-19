package com.gen.desafio.api.domain.dto.req;


import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.gen.desafio.api.domain.dto.BaseDTO;


public class CreateTicketReqDTO extends BaseDTO {
	
	
	@NotNull(message = "Por favor ingrese tipo de Ticket a abrir (1 o 2).")
	private Integer ticketType;
	
	@NotEmpty(message = "Por favor ingrese un Título.")
	@Size(max=60)
	private String title;
	
	@NotEmpty(message = "Por favor ingrese un texto de contenido como descripción del Ticket.")
	@Size(max=255)
	private String description;
	
	
	@AssertTrue(message="DesafioApp no soporta ese codigo de Ticket.")
    private boolean isValidTicket() {
        if(ticketType.intValue()==1 || ticketType.intValue()==2) {
            return true;
        } else {
            return false;
        }
    }
	
	
	public CreateTicketReqDTO() {}


	public Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(Integer ticketType) {
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
	
	
}
