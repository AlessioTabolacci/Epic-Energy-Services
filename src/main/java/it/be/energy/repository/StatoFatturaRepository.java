package it.be.energy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.StatoFattura;

public interface StatoFatturaRepository extends JpaRepository<StatoFattura, Long> {
	
	

}
