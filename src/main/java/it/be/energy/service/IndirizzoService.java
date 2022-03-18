package it.be.energy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.IndirizzoException;
import it.be.energy.model.Cliente;
import it.be.energy.model.Indirizzo;
import it.be.energy.repository.ClienteRepository;
import it.be.energy.repository.IndirizzoRepository;

@Service
public class IndirizzoService {

	@Autowired
	IndirizzoRepository indirizzoRepository;
	@Autowired
	ClienteRepository clienteRepository;

	public Indirizzo inserisciIndirizzo(Indirizzo indirizzo) {
		return indirizzoRepository.save(indirizzo);

	}

	public void cancellaIndirizzoById(Long id) {
		Optional<Indirizzo> trova = indirizzoRepository.findById(id);
		List<Cliente> listaClienti = clienteRepository.findAll();
		for (Cliente cliente : listaClienti) {
			if(cliente.getSedeLegale().getId().equals(id)) {
				cliente.setSedeLegale(null);
			}
			else if (cliente.getSedeOperativa().getId().equals(id)) {
				cliente.setSedeOperativa(null);
			}
		}
		if (trova.isPresent()) {
			Indirizzo cancella = trova.get();
			cancella.setComune(null);

			indirizzoRepository.deleteById(id);
		} else {
			throw new IndirizzoException("Indirizzo non trovato");
		}
	}

	public Indirizzo modificaIndirizzo(Indirizzo indirizzo, Long id) {
		Optional<Indirizzo> indirizzoDaAggiornare = indirizzoRepository.findById(id);
		if (indirizzoDaAggiornare.isPresent()) {
			Indirizzo modifica = indirizzoDaAggiornare.get();
			modifica.setVia(indirizzo.getVia());
			modifica.setCivico(indirizzo.getCivico());
			modifica.setCap(indirizzo.getCap());
			modifica.setLocalita(indirizzo.getLocalita());
			modifica.setComune(indirizzo.getComune());
			return indirizzoRepository.save(modifica);
		} else {
			throw new IndirizzoException("Indirizzo non aggiornato!");
		}
	}

	public Page<Indirizzo> trovaTuttiGliIndirizzi(Pageable pageable) {
		return indirizzoRepository.findAll(pageable);

	}

	public Optional<Indirizzo> trovaTuttiTramiteId(Long id) {
		Optional<Indirizzo> indirizzoTrovato = indirizzoRepository.findById(id);
		if (indirizzoTrovato.isPresent()) {
			return indirizzoTrovato;
		}

		else {

			throw new IndirizzoException("Indirizzo non trovato!");
		}
	}
}
