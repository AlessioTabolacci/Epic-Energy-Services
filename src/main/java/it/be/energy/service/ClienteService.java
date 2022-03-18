package it.be.energy.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.be.energy.exception.ClienteException;
import it.be.energy.model.Cliente;
import it.be.energy.model.Fattura;
import it.be.energy.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	/*
	 * Metodo che permette di inserire un cliente nel database
	 */
	public Cliente inserisciCliente(Cliente cliente) {
		return clienteRepository.save(cliente);

	}

	/*
	 * Metodo che permette di cancellare un cliente presente nel database
	 */
	public void cancellaClienteById(Long id) {
		Optional<Cliente> trova = clienteRepository.findById(id);
		if (trova.isPresent()) {
			Cliente cancellato = trova.get();
			for (Fattura fattura : cancellato.getFatture()) {
				fattura.setCliente(null);
			}
			cancellato.setFatture(null);
			cancellato.setSedeLegale(null);
			cancellato.setSedeOperativa(null);
			clienteRepository.deleteById(id);
		} else {
			throw new ClienteException("Cliente non trovato");
		}
	}

	/*
	 * Metodo che permette di modificare un cliente presente nel database
	 */
	public Cliente modificaCliente(Cliente cliente, Long id) {
		Optional<Cliente> clienteDaAggiornare = clienteRepository.findById(id);
		if (clienteDaAggiornare.isPresent()) {
			Cliente modifica = clienteDaAggiornare.get();
			modifica.setNomeContatto(cliente.getNomeContatto());
			modifica.setCognomeContatto(cliente.getCognomeContatto());
			modifica.setRagioneSociale(cliente.getRagioneSociale());
			modifica.setPIva(cliente.getPIva());
			modifica.setPec(cliente.getPec());
			modifica.setEmail(cliente.getEmail());
			modifica.setTelefono(cliente.getTelefono());
			modifica.setTipoCliente(cliente.getTipoCliente());
			modifica.setDataInserimento(cliente.getDataInserimento());
			modifica.setDataUltimoContatto(cliente.getDataUltimoContatto());
			modifica.setEmailContatto(cliente.getEmailContatto());
			modifica.setSedeLegale(cliente.getSedeLegale());
			modifica.setSedeOperativa(cliente.getSedeOperativa());
			modifica.setFatturatoAnnuale(cliente.getFatturatoAnnuale());
			modifica.setFatture(cliente.getFatture());
			modifica.setNumeroContatto(cliente.getNumeroContatto());
			return clienteRepository.save(modifica);

		} else {
			throw new ClienteException("Cliente non aggiornato!");
		}
	}

	/*
	 * Metodo che restituisce tutti i clienti presenti nel database
	 */
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteRepository.findAll(pageable);

	}

	/*
	 * Metodo che restituisce un cliente dato il su id
	 */
	public Optional<Cliente> findAllById(Long id) {
		Optional<Cliente> clienteTrovato = clienteRepository.findById(id);
		if (clienteTrovato.isPresent()) {
			return clienteTrovato;
		}

		else {

			throw new ClienteException("Cliente non trovato!");
		}
	}

	/*
	 * Metodo che restituisce tutti i clienti ordinati per Ragione Sociale
	 */
	public Page<Cliente> findAllByOrderByRagioneSocialeAsc(Pageable pageable) {
		return clienteRepository.findAllByOrderByRagioneSocialeAsc(pageable);
	}

	/*
	 * Metodo che restituisce tutti i clenti ordinati per Fatturato Annuale
	 */
	public Page<Cliente> findAllByOrderByFatturatoAnnualeDesc(Pageable pageable) {
		return clienteRepository.findAllByOrderByFatturatoAnnualeDesc(pageable);

	}

	/*
	 * Metodo che resttuisce tutti i clienti ordinati per Data di inserimento
	 */
	public Page<Cliente> findByOrderByDataInserimentoDesc(Pageable pageable) {
		return clienteRepository.findAllByOrderByDataInserimentoDesc(pageable);
	}

	/*
	 * Metodo che restituisce tutti i clienti ordinati per Data ultimo contatto
	 */
	public Page<Cliente> findByOrderByDataUltimoContattoDesc(Pageable pageable) {
		return clienteRepository.findAllByOrderByDataUltimoContattoDesc(pageable);
	}

	/*
	 * Metodo che restituisce tutti i clienti ordinati per Sede legale, comune e
	 * provincia
	 */
	public Page<Cliente> findAllByOrderBySedeLegaleComuneProvincia(Pageable pageable) {
		return clienteRepository.findAllByOrderBySedeLegaleComuneProvincia(pageable);
	}

	/*
	 * Metodo che restituisce tutti i clienti con fatturato annuale maggiore o
	 * uguale all'importo inserito
	 */
	public Page<Cliente> findByFatturatoAnnualeGreaterThanEqual(Pageable pageable, BigDecimal fatturatoAnnuale) {
		return clienteRepository.findByFatturatoAnnualeGreaterThanEqual(pageable, fatturatoAnnuale);
	}

	/*
	 * Metodo che restituisce tutti i clienti con fatturato annuale minore o uguale
	 * all'importo inserito
	 */
	public Page<Cliente> findByFatturatoAnnualeLessThanEqual(Pageable pageable, BigDecimal fatturatoAnnuale) {
		return clienteRepository.findByFatturatoAnnualeLessThanEqual(pageable, fatturatoAnnuale);
	}

	/*
	 * Metodo che restituisce tutti i clienti con fatturato annuale compreso tra i
	 * due importi inseriti
	 */
	public Page<Cliente> findByFatturatoAnnualeBetween(Pageable pageable, BigDecimal fatturatoMin,
			BigDecimal fatturatoMax) {
		return clienteRepository.findByFatturatoAnnualeBetween(pageable, fatturatoMin, fatturatoMax);
	}

	/*
	 * Metodo che restituisce tutti i clienti con data inserimento posticipata
	 * rispetto alla data inserita
	 */
	public Page<Cliente> findByDataInserimentoGreaterThanEqual(Pageable pageable, LocalDate dataInserimento) {
		return clienteRepository.findByDataInserimentoGreaterThanEqual(pageable, dataInserimento);

	}

	/*
	 * Metodo che restituisce tutti i clienti con data di inserimento antecedente
	 * alla data inserita
	 */
	public Page<Cliente> findByDataInserimentoLessThanEqual(Pageable pageable, LocalDate dataInserimento) {
		return clienteRepository.findByDataInserimentoLessThanEqual(pageable, dataInserimento);
	}

	/*
	 * Metodo che restituisce tutti i clienti con data di inserimento compresa tra
	 * le due date inserite
	 */
	public Page<Cliente> findByDataInserimentoBetween(Pageable pageable, LocalDate dataMin, LocalDate dataMax) {
		return clienteRepository.findByDataInserimentoBetween(pageable, dataMin, dataMax);
	}

	/*
	 * Metodo che restituisce tutti i clienti con data ultimo contatto posticipata
	 * rispetto alla data inserita
	 */
	public Page<Cliente> findByDataUltimoContattoGreaterThanEqual(Pageable pageable, LocalDate dataUltimoContatto) {
		return clienteRepository.findByDataUltimoContattoGreaterThanEqual(pageable, dataUltimoContatto);

	}

	/*
	 * Metodo che restituisce tutti i clienti con data ultimo contatto antecedente
	 * rispetto alla data inserita
	 */
	public Page<Cliente> findByDataUltimoContattoLessThanEqual(Pageable pageable, LocalDate dataUltimoContatto) {
		return clienteRepository.findByDataUltimoContattoLessThanEqual(pageable, dataUltimoContatto);
	}

	/*
	 * Metodo che restituisce tutti i clienti con data ultimo contatto compreso tra
	 * le due date inserite
	 */
	public Page<Cliente> findByDataUltimoContattoBetween(Pageable pageable, LocalDate dataUltimoContattoMin,
			LocalDate dataUltimoContattoMax) {
		return clienteRepository.findByDataUltimoContattoBetween(pageable, dataUltimoContattoMin,
				dataUltimoContattoMax);
	}

	/*
	 * Metodo che restituisce tutti i clienti per parte della ragione sociale
	 */
	public Page<Cliente> findByRagioneSocialeContaining(Pageable pageable, String ragioneSociale) {
		return clienteRepository.findByRagioneSocialeContaining(pageable, ragioneSociale);
	}

}