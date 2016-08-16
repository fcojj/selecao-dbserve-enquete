package com.github.enquete.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.enquete.model.Establishment;
import com.github.enquete.repository.Establishments;

@Service
public class EstablishmentDaoService {

	@Autowired
	private Establishments establishments;
	
	public List<Establishment> getEstablishments(){
		return establishments.findAll();
	}
	
}
