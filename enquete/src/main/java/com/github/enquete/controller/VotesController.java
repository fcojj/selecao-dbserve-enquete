package com.github.enquete.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.enquete.model.EstablishmentCalculationVotes;
import com.github.enquete.model.Vote;
import com.github.enquete.service.EstablishmentDaoService;
import com.github.enquete.service.VoteDaoService;

@Controller
@RequestMapping("/votes")
public class VotesController {
	
	@Autowired
	private EstablishmentDaoService establishmentDaoService ;
	
	@Autowired
	private VoteDaoService voteDaoService;
	
	@RequestMapping
	public ModelAndView listingVote(EstablishmentCalculationVotes establishmentWinnerInformations) {
		ModelAndView mv = new ModelAndView("/vote/ListingVotes");

		mv.addObject("votes", voteDaoService.selectAll());
		mv.addObject("establishmentWinner", voteDaoService.computeVote(establishmentWinnerInformations.getDate()));
		
		return mv;
	}
	
	@RequestMapping("/new")
	public ModelAndView newVote(Vote vote) {
		ModelAndView mv = new ModelAndView("/vote/RegisterVote");
		mv.addObject("establishments", establishmentDaoService.getEstablishments());
		
		return mv;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView save(@Valid Vote vote, BindingResult result, RedirectAttributes attributes) {
		
		voteDaoService.save(vote, result);
		
		if (result.hasErrors()) {
			return newVote(vote);
		}
		
		attributes.addFlashAttribute("mensagem", "Voto registrado!");
		return new ModelAndView("redirect:/votes/new");
	}
}
