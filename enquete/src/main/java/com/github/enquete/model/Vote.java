package com.github.enquete.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "votacao")
public class Vote implements Serializable{

	private static final long serialVersionUID = -2151544302728756349L;

	@EmbeddedId
	@Valid
	private VoteKey voteKey;
	
	@ManyToOne
	@NotNull(message = "Escolha um estabelecimento")
	@JoinColumn(name="estabelecimento_id", nullable=false)
    private Establishment establishment;


	public VoteKey getVoteKey() {
		return voteKey;
	}


	public void setVoteKey(VoteKey voteKey) {
		this.voteKey = voteKey;
	}


	public Establishment getEstablishment() {
		return establishment;
	}


	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((establishment == null) ? 0 : establishment.hashCode());
		result = prime * result + ((voteKey == null) ? 0 : voteKey.hashCode());
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
		Vote other = (Vote) obj;
		if (establishment == null) {
			if (other.establishment != null)
				return false;
		} else if (!establishment.equals(other.establishment))
			return false;
		if (voteKey == null) {
			if (other.voteKey != null)
				return false;
		} else if (!voteKey.equals(other.voteKey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vote [voteKey=" + voteKey + ", establishment=" + establishment + "]";
	}
}
