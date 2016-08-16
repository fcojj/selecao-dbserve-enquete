package com.github.enquete.model;

import java.util.Date;

public class EstablishmentCalculationVotes {
	
	private Establishment establishment;
	private Date date;
	private int votesAmount;
	
	public Establishment getEstablishment() {
		return establishment;
	}
	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getVotesAmount() {
		return votesAmount;
	}
	public void setVotesAmount(int votesAmount) {
		this.votesAmount = votesAmount;
	}
	@Override
	public String toString() {
		return "EstablishmentWinnerInformations [establishment=" + establishment + ", date=" + date + ", votesAmount="
				+ votesAmount + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((establishment == null) ? 0 : establishment.hashCode());
		result = prime * result + votesAmount;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstablishmentCalculationVotes other = (EstablishmentCalculationVotes) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (establishment == null) {
			if (other.establishment != null)
				return false;
		} else if (!establishment.equals(other.establishment))
			return false;
		if (votesAmount != other.votesAmount)
			return false;
		return true;
	}

	
}
