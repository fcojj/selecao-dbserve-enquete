package com.github.enquete.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Embeddable
public class VoteKey implements Serializable{
	
	private static final long serialVersionUID = 3702768837501941651L;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "data")
    @NotNull(message = "Data é obrigatório")
    private Date voteDate;
   
	@ManyToOne
	@NotNull(message = "Usuário não pode ser nulo")
	@JoinColumn(name="usuario_id", nullable=false)
	private User user;

	public Date getVoteDate() {
		return voteDate;
	}

	public void setVoteDate(Date voteDate) {
		this.voteDate = voteDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((voteDate == null) ? 0 : voteDate.hashCode());
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
		VoteKey other = (VoteKey) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (voteDate == null) {
			if (other.voteDate != null)
				return false;
		} else if (!voteDate.equals(other.voteDate))
			return false;
		return true;
	}	
}
