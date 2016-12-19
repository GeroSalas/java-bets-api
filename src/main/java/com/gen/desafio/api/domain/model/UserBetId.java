package com.gen.desafio.api.domain.model;

import java.io.Serializable;

public class UserBetId implements Serializable {

		private User relatedUser;
		private Bet relatedBet;
		
		
		public UserBetId() {}

		
		public UserBetId(User user, Bet bet) {
			this.relatedUser = user;
			this.relatedBet = bet;
		}


		public User getRelatedUser() {
			return relatedUser;
		}


		public void setRelatedUser(User relatedUser) {
			this.relatedUser = relatedUser;
		}


		public Bet getRelatedBet() {
			return relatedBet;
		}


		public void setRelatedBet(Bet relatedBet) {
			this.relatedBet = relatedBet;
		}
	
		
}