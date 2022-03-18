package it.be.energy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.ComuneException;
import it.be.energy.model.Comune;
import it.be.energy.repository.ComuneRepository;

@Service
public class ComuneService {

	@Autowired
	ComuneRepository comuneRepository;

	public Page<Comune> trovaTutti(Pageable pageable) {
		return comuneRepository.findAll(pageable);

	}
	//Per thymleaf
	public List<Comune> trovaTutti() {
		return comuneRepository.findAll();
		
	}

	public Optional<Comune> trovaById(Long id) {
		Optional<Comune> comuneTrovato = comuneRepository.findById(id);
		if (comuneTrovato.isPresent()) {
			return comuneTrovato;
		}

		else {

			throw new ComuneException("Comune non trovato!");
		}
	}
	
	public Page<Comune> findByNomeContaining(Pageable pageable, String nome) {
		return comuneRepository.findByNomeContaining(pageable, nome);
	}


}
