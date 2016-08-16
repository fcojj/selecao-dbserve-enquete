package com.gihub.enquete.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.enquete.model.Establishment;
import com.github.enquete.model.EstablishmentCalculationVotes;
import com.github.enquete.repository.EstablishmentWinnerDao;
import com.github.enquete.repository.Votes;
import com.github.enquete.service.VoteDaoService;

/**
 * Teste unitário {@link VoteDaoService}.
 *
 * @author Fco Jonas Rodrigues
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class VoteDaoServiceTest {
	
	@Mock
	private EstablishmentWinnerDao establishmentWinnerDao;
	
	@Mock
	private Votes votes;
	
	@InjectMocks 
	VoteDaoService dao;
	
	private List<EstablishmentCalculationVotes> establishmentCalculationVotesList;
	private EstablishmentCalculationVotes subway1508;
	private EstablishmentCalculationVotes panorama1508;
	private EstablishmentCalculationVotes macDonald1708;
	private EstablishmentCalculationVotes bobs1708;
	private EstablishmentCalculationVotes bobs1808;
	private EstablishmentCalculationVotes burgerKing1808;
	
	@Before
	public void setUp(){
		establishmentCalculationVotesList = new ArrayList<EstablishmentCalculationVotes>();
		subway1508 = creeateEstablishmentVotes(1L,"Subway", "(51)4576-9898", new Date("2016/08/15"), 2);
		panorama1508 = creeateEstablishmentVotes(2L,"Panorama", "(51)1212-1212", new Date("2016/08/15"), 3);
		
		macDonald1708 = creeateEstablishmentVotes(3L,"MacDonald", "(51)2222-2222", new Date("2016/08/17"), 5);
		bobs1708 = creeateEstablishmentVotes(4L,"Bob's", "(51)3333-3333", new Date("2016/08/17"), 5);
		
		bobs1808 = creeateEstablishmentVotes(4L,"Bob's", "(51)3333-3333", new Date("2016/08/18"), 5);
		burgerKing1808 = creeateEstablishmentVotes(5L,"Burguer King", "(51)4545-4545", new Date("2016/08/18"), 4);
		
		establishmentCalculationVotesList.add(subway1508);
		establishmentCalculationVotesList.add(panorama1508);
		
		establishmentCalculationVotesList.add(macDonald1708);
		establishmentCalculationVotesList.add(bobs1708);
		
		establishmentCalculationVotesList.add(bobs1808);
		establishmentCalculationVotesList.add(burgerKing1808);
	}

	@Test
    public void shouldFindPanoromaWithWinner(){
		//given
		Date begin = new Date("2016/08/15");
		Date dateSelection = new Date("2016/08/15");
		given(establishmentWinnerDao.resultByDate(begin, dateSelection)).willReturn(establishmentCalculationVotesList);

        //when
		EstablishmentCalculationVotes result = dao.computeVote(dateSelection);
       
        //then
		assertEquals(panorama1508, result);
    }
	
	/*
	 * Deveria encontrar resultado inconclusivo,
	 *  pois aconteceu um empate para esse dia.
	 * */
	@Test
    public void shouldFindResultInconclusiveTie(){
		//given
		Date begin = new Date("2016/08/15");
		Date dateSelection = new Date("2016/08/17");
		EstablishmentCalculationVotes dummy = createEstablishmentWinnerDummy(dateSelection);
		given(establishmentWinnerDao.resultByDate(begin, dateSelection)).willReturn(establishmentCalculationVotesList);

        //when
		EstablishmentCalculationVotes result = dao.computeVote(dateSelection);

        //then
		assertEquals(dummy, result);
       
    }
	
	/*
	 * Deveria encontrar resultado inconclusivo,
	 *  pois esse estabelcimento já venceu nessa semana.
	 * */
	@Test
    public void shouldFindResultInconclusiveRepeated(){
		//given
		Date begin = new Date("2016/08/15");
		Date dateSelection = new Date("2016/08/18");
		EstablishmentCalculationVotes dummy = createEstablishmentWinnerDummy(dateSelection);
		given(establishmentWinnerDao.resultByDate(begin, dateSelection)).willReturn(establishmentCalculationVotesList);

        //when
		EstablishmentCalculationVotes result = dao.computeVote(dateSelection);

        //then
		assertEquals(dummy, result);
       
    }
	
	private EstablishmentCalculationVotes createEstablishmentWinnerDummy(Date currentyDate) {
		EstablishmentCalculationVotes establishmentWinner;
		establishmentWinner = new EstablishmentCalculationVotes();
		Establishment establishment = new Establishment();
		establishment.setName("Resultado inconclusivo");
		establishmentWinner.setEstablishment(establishment);
		establishmentWinner.setDate(currentyDate);
		return establishmentWinner;
	}
	
	private EstablishmentCalculationVotes creeateEstablishmentVotes(Long idEstablishment, String nameEstablishment, 
			String phoneEstablishment, Date date, int amountVoteByDate) {
		
		  EstablishmentCalculationVotes establishmentWinner = new EstablishmentCalculationVotes();  
		  Establishment establishment = new Establishment();
   
		  establishment.setId(idEstablishment);
		  establishment.setName(nameEstablishment);
		  establishment.setPhone(phoneEstablishment);
		  establishmentWinner.setEstablishment(establishment);  
		  establishmentWinner.setDate(date);
		  establishmentWinner.setVotesAmount(amountVoteByDate);
		  
		  return establishmentWinner;
	}
}
