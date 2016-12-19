package com.gen.desafio.api.domain.dto;


import java.io.Serializable;
import com.google.gson.GsonBuilder;


public abstract class BaseDTO  /* extends ResourceSupport */ implements Serializable {

	public String toGson(){
		String gson = new GsonBuilder().setPrettyPrinting().create().toJson(this);
		return gson.toString();
	}
	
}
