package it.be.energy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.be.energy.exception.StatoFatturaException;
import it.be.energy.model.StatoFattura;
import it.be.energy.repository.StatoFatturaRepository;

@Service
public class StatoFatturaService {

	@Autowired
	StatoFatturaRepository statoFatturaRepository;

	public List<StatoFattura> trovaTutte() {
		return statoFatturaRepository.findAll();

	}

	public Optional<StatoFattura> trovaById(Long numeroFattura) {
		Optional<StatoFattura> statoTrovato = statoFatturaRepository.findById(numeroFattura);
		if (statoTrovato.isPresent()) {
			return statoTrovato;
		}

		else {
			throw new StatoFatturaException("Stato Fattura non trovato!");
		}
	}

	public StatoFattura modificaStatoFattura(StatoFattura statoFattura, Long id) {
		Optional<StatoFattura> statoFatturaDaAggiornare = statoFatturaRepository.findById(id);
		if (statoFatturaDaAggiornare.isPresent()) {
			StatoFattura sf = statoFatturaDaAggiornare.get();
			sf.setNome(statoFattura.getNome());
			return statoFatturaRepository.save(sf);

		} else {
			throw new StatoFatturaException("Cliente non aggiornato!");
		}
	}

	public StatoFattura inserisciStatoFattura(StatoFattura statoFattura) {
		return statoFatturaRepository.save(statoFattura);
	}

	public void cancellaStatoFatturaById(Long id) {
		Optional<StatoFattura> trovata = statoFatturaRepository.findById(id);
		if (trovata.isPresent()) {
			statoFatturaRepository.deleteById(id);
		} else {
			throw new StatoFatturaException("Stato fattura non trovato!");
		}
	}

}
