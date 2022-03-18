package it.be.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.ProvinciaException;
import it.be.energy.model.Provincia;
import it.be.energy.repository.ProvinciaRepository;

@Service
public class ProvinciaService {

	@Autowired
	ProvinciaRepository provinciaRepository;

	public Page<Provincia> trovaTutte(Pageable pageable) {
		return provinciaRepository.findAll(pageable);

	}

	public Optional<Provincia> trovaById(Long id) {
		Optional<Provincia> provinciaTrovata = provinciaRepository.findById(id);
		if (provinciaTrovata.isPresent()) {
			return provinciaTrovata;
		}

		else {

			throw new ProvinciaException("Provincia non trovata!");
		}
	}

	public Page<Provincia> findByNomeContaining(Pageable pageable, String nome) {
		return provinciaRepository.findByNomeContaining(pageable, nome);
	}

}
