package it.be.energy.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.FatturaException;
import it.be.energy.model.Fattura;
import it.be.energy.repository.FatturaRepository;

@Service
public class FatturaService {

	@Autowired
	FatturaRepository fatturaRepository;

	public Page<Fattura> trovaTutte(Pageable pageable) {
		return fatturaRepository.findAll(pageable);

	}

	public Optional<Fattura> trovaById(Long id) {
		Optional<Fattura> fatturaTrovata = fatturaRepository.findById(id);
		if (fatturaTrovata.isPresent()) {
			return fatturaTrovata;
		}

		else {

			throw new FatturaException("Fattura non trovata!");
		}
	}

	public Fattura inserisciFattura(Fattura fattura) {
		return fatturaRepository.save(fattura);
	}

	public Fattura modificaFattura(Fattura fattura, Long id) {
		Optional<Fattura> fatturaDaAggiornare = fatturaRepository.findById(id);
		if (fatturaDaAggiornare.isPresent()) {
			Fattura modifica = fatturaDaAggiornare.get();
			modifica.setNumeroFattura(fattura.getNumeroFattura());
			modifica.setAnno(fattura.getAnno());
			modifica.setData(fattura.getData());
			modifica.setImporto(fattura.getImporto());
			modifica.setCliente(fattura.getCliente());
			modifica.setStatoFattura(fattura.getStatoFattura());
			return fatturaRepository.save(modifica);

		} else {
			throw new FatturaException("Fattura non aggiornata!");
		}
	}

	public void cancellaFattura(Long id) {
		Optional<Fattura> trova = fatturaRepository.findById(id);
		if (trova.isPresent()) {
			
			fatturaRepository.deleteById(id);
		} else {
			throw new FatturaException("Fattura non trovata!");
		}
	}

	public Page<Fattura> trovaByClienteRagioneSocialeContaining(Pageable pageable, String nome) {
		return fatturaRepository.findByClienteRagioneSocialeContaining(pageable, nome);
	}

	public Page<Fattura> trovaByStatoFatturaId(Pageable pageable, Long id) {
		return fatturaRepository.findByStatoFatturaId(pageable, id);
	}

	public Page<Fattura> trovaByData(Pageable pageable, LocalDate data) {
		return fatturaRepository.findByData(pageable, data);
	}

	public Page<Fattura> findByAnno(Pageable pageable, Integer anno) {
		return fatturaRepository.findByAnno(pageable, anno);

	}

	public Page<Fattura> findByImportoBetween(Pageable pageable, BigDecimal importoMin, BigDecimal importoMax) {
		return fatturaRepository.findByImportoBetween(pageable, importoMin, importoMax);
	}
}
