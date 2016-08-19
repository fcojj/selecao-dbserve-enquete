package com.github.enquete.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.github.enquete.model.Establishment;
import com.github.enquete.model.EstablishmentCalculationVotes;
import com.github.enquete.model.Vote;
import com.github.enquete.repository.EstablishmentWinnerDao;
import com.github.enquete.repository.Votes;

@Service
public class VoteDaoService {
	
	private static final Logger LOGGER = Logger.getLogger(VoteDaoService.class.getName());

	@Autowired
	private Votes votes;
	
	@Autowired
	EstablishmentWinnerDao establishmentWinnerDao;
	
	public void save(Vote vote, BindingResult result) {
		try{
			if(!result.hasErrors() && !votes.exists(vote.getVoteKey())){
				votes.save(vote);
			}else if(votes.exists(vote.getVoteKey())){
				result.addError(new ObjectError("pk_user_date", "Você já votou nessa data, por favor escolha outra data"));
			}
		}catch (RuntimeException e) {
			 LOGGER.log(Level.SEVERE, "Exception occur", e);
		}
	}
	
	
	public List<Vote> selectAll(){
		return votes.findAll();
	}


	public EstablishmentCalculationVotes computeVote(Date dateSelection) {
		EstablishmentCalculationVotes establishmentsWinner = getEstablishmentsWinner(dateSelection);
		
		return establishmentsWinner;
	}

	private Date formatedDate(Date date){
		if(date == null){
			date = new Date();
		}
		LocalDate localDate = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(date));
		
		return java.sql.Date.valueOf(localDate);
		
	}

	private EstablishmentCalculationVotes getEstablishmentsWinner(
			Date dateSelection) {
		
		Date currentyDate = formatedDate(dateSelection);
		Date firstDayWeek = getFirstDayWeek(currentyDate);
		List<EstablishmentCalculationVotes> establishmentCalculationVotesList = establishmentWinnerDao.resultByDate(firstDayWeek, currentyDate);
		EstablishmentCalculationVotes establishmentWinner = createEstablishmentWinnerDummy(currentyDate); 
		
		//seleciona o estabelecimento com mais votos no dia especificado no filtro
		for (EstablishmentCalculationVotes establishment: establishmentCalculationVotesList) {
			if(isEstablishmentWinner(currentyDate, establishmentWinner, establishment)){
				if(establishmentWinner.getVotesAmount() == establishment.getVotesAmount()){//empate
					establishmentWinner = createEstablishmentWinnerDummy(currentyDate);//resultado inconclusivo
				}else{
					establishmentWinner = establishment;
				}
			}
		}
		
		
		/**
		 * verifica se esse estabelecimento já foi escolhido essa semana,
		 * caso sim o resultado da votação é inconclusivo, pois o mesmo estabelcimento não pode ser repetido duas vezes na semana.
		 */
		establishmentCalculationVotesList.remove(establishmentWinner);
		for (EstablishmentCalculationVotes establishment: establishmentCalculationVotesList) {
			if(isEstablishmentWinnerRepeated(establishmentWinner, establishment)){
				establishmentWinner = createEstablishmentWinnerDummy(currentyDate);
			}
		}
		
		return establishmentWinner;
	}


	private boolean isEstablishmentWinner(Date currentyDate, EstablishmentCalculationVotes establishmentWinner,
			EstablishmentCalculationVotes establishment) {
		return establishment.getVotesAmount() >= establishmentWinner.getVotesAmount() 
				&& establishment.getDate().equals(currentyDate);
	}


	private boolean isEstablishmentWinnerRepeated(EstablishmentCalculationVotes establishmentWinner,
			EstablishmentCalculationVotes establishment) {
		return establishment.getVotesAmount() >= establishmentWinner.getVotesAmount() 
				&& establishmentWinner.getEstablishment()!= null 
				&& establishmentWinner.getEstablishment().getName().equals(establishment.getEstablishment().getName());
	}

	private EstablishmentCalculationVotes createEstablishmentWinnerDummy(Date currentyDate) {
		EstablishmentCalculationVotes establishmentWinner;
		establishmentWinner = new EstablishmentCalculationVotes();
		Establishment establishment = new Establishment();
		establishment.setName("resultado inconclusivo");
		establishmentWinner.setEstablishment(establishment);
		establishmentWinner.setDate(currentyDate);
		return establishmentWinner;
	}
	
	private Date getFirstDayWeek(Date beginDateWeek){
		LocalDate localDate = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(beginDateWeek) );
		int FIRST_WEEK_DAY = 1;
		LocalDate localDateEnd = localDate;
		
		for(int day = localDate.getDayOfWeek().getValue() ; day > FIRST_WEEK_DAY; day--) {
			localDateEnd = localDateEnd.minusDays(1);
		}
		Date endDate = java.sql.Date.valueOf(localDateEnd);
		
		return endDate;
	}
}
