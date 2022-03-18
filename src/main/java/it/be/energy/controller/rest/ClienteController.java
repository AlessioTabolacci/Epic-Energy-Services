package it.be.energy.controller.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.be.energy.exception.ClienteException;
import it.be.energy.model.Cliente;
import it.be.energy.service.ClienteService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cliente")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovatutti")
	@Operation(summary = "Visualizza tutti i clienti")
	public ResponseEntity<Page<Cliente>> trovaTutti(Pageable pageable) {
		log.info("*** Inizio ricerca clienti ***");
		Page<Cliente> trovaTutti = clienteService.findAll(pageable);
		if (trovaTutti.hasContent()) {
			log.info("*** Cliente trovato! ***");
			return new ResponseEntity<>(trovaTutti, HttpStatus.OK);

		} else {
			log.info("*** Cliente non trovato! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperid/{id}")
	@Operation(summary = "Visualizza tutti i clienti per id")
	public ResponseEntity<Cliente> trovaById(@PathVariable Long id) {
		log.info("*** Inizio Ricerca per id ***");
		Optional<Cliente> trovaById = clienteService.findAllById(id);
		log.info("*** Fine ricerca per id ***");
		return new ResponseEntity<>(trovaById.get(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/inserisci")
	@Operation(summary = "Inserisce un cliente")
	public ResponseEntity<Cliente> inserisciCliente(@RequestBody Cliente cliente) {
		log.info("*** Inizio inserimento cliente ***");
		Cliente c = clienteService.inserisciCliente(cliente);
		log.info("*** Cliente salvato ***");
		return new ResponseEntity<>(c, HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/aggiorna/{id}")
	@Operation(summary = "Aggiorna un cliente tramite il suo id")
	public ResponseEntity<Cliente> aggiornaCliente(@RequestBody Cliente cliente, @PathVariable Long id)
			throws ClienteException {
		log.info("*** Inizio aggornamento cliente ***");
		Cliente c = clienteService.modificaCliente(cliente, id);
		log.info("*** Fine aggiornamento cliente ***");
		return new ResponseEntity<>(c, HttpStatus.ACCEPTED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/cancella/{id}")
	@Operation(summary = "Cancella un cliente tramite il suo id")
	public ResponseEntity<String> cancellaCliente(@PathVariable Long id) {
		log.info("*** Inizio cancellazione cliente ***");
		clienteService.cancellaClienteById(id);
		log.info("*** Fine cancellazione cliente ***");
		return new ResponseEntity<>("Cliente cancellato correttamente!", HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperragionesociale")
	@Operation(summary = "Ordina i clienti per nome")
	public ResponseEntity<Page<Cliente>> ordinaByNome(Pageable pageable) {
		log.info("*** Inizio ricerca clienti per nome ***");
		Page<Cliente> trovaTutti = clienteService.findAllByOrderByRagioneSocialeAsc(pageable);
		if (trovaTutti.hasContent()) {
			log.info("*** Cliente trovato! ***");
			return new ResponseEntity<>(trovaTutti, HttpStatus.OK);

		} else {
			log.info("*** Cliente non trovato! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/ordinaperfatturato")
	@Operation(summary = "Ordina i clienti per fatturato annuale")
	public ResponseEntity<Page<Cliente>> ordinaByFatturatoAnnualeDesc(Pageable pageable) {
		log.info("*** Inizio ricerca clienti per fatturato annuale ***");
		Page<Cliente> trovaTutti = clienteService.findAllByOrderByFatturatoAnnualeDesc(pageable);
		if (trovaTutti.hasContent()) {
			log.info("*** Cliente trovato! ***");
			return new ResponseEntity<>(trovaTutti, HttpStatus.OK);

		} else {
			log.info("*** Cliente non trovato! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/ordinaperdatainserimento")
	@Operation(summary = "Ordina i clienti per data inserimento")
	public ResponseEntity<Page<Cliente>> ordinaByDataInserimentoDesc(Pageable pageable) {
		log.info("*** Inizio ricerca clienti per data inserimento ***");
		Page<Cliente> trovaTutti = clienteService.findByOrderByDataInserimentoDesc(pageable);
		if (trovaTutti.hasContent()) {
			log.info("*** Cliente trovato! ***");
			return new ResponseEntity<>(trovaTutti, HttpStatus.OK);

		} else {
			log.info("*** Cliente non trovato! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/ordinaperdataultimocontatto")
	@Operation(summary = "Ordina i clienti per data ultimo contatto")
	public ResponseEntity<Page<Cliente>> ordinaByDataUltimoContattoDesc(Pageable pageable) {
		log.info("*** Inizio ricerca clienti per data ultimo contatto ***");
		Page<Cliente> trovaTutti = clienteService.findByOrderByDataUltimoContattoDesc(pageable);
		if (trovaTutti.hasContent()) {
			log.info("*** Cliente trovato! ***");
			return new ResponseEntity<>(trovaTutti, HttpStatus.OK);

		} else {
			log.info("*** Cliente non trovato! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/ordinapersedelegale")
	@Operation(summary = "Ordina clienti per sede legale")
	public ResponseEntity<Page<Cliente>> ordinaBySedeLegaleComuneProvincia(Pageable pageable) {
		log.info("*** Inizio ricerca clienti per sede legale ***");
		Page<Cliente> trovaTutti = clienteService.findAllByOrderBySedeLegaleComuneProvincia(pageable);
		if (trovaTutti.hasContent()) {
			log.info("*** Cliente trovato! ***");
			return new ResponseEntity<>(trovaTutti, HttpStatus.OK);

		} else {
			log.info("*** Cliente non trovato! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperfatturatomaggioredi/{fatturatoAnnuale}")
	@Operation(summary = "Trova cliente per fatturato annuale maggiore o uguale all'importo inserito")
	public ResponseEntity<Page<Cliente>> trovaPerFatturatoAnnualeMaggioreUguale(Pageable pageable,
			@PathVariable BigDecimal fatturatoAnnuale) {
		Page<Cliente> trovati = clienteService.findByFatturatoAnnualeGreaterThanEqual(pageable, fatturatoAnnuale);
		if (trovati.isEmpty()) {
			return new ResponseEntity<>(trovati, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovati, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperfatturatominoredi/{fatturatoAnnuale}")
	@Operation(summary = "Trova cliente per fatturato annuale minore o uguale all'importo inserito")
	public ResponseEntity<Page<Cliente>> trovaPerFatturatoAnnualeMinoreUguale(Pageable pageable,
			@PathVariable BigDecimal fatturatoAnnuale) {
		Page<Cliente> trovati = clienteService.findByFatturatoAnnualeLessThanEqual(pageable, fatturatoAnnuale);
		if (trovati.isEmpty()) {
			return new ResponseEntity<>(trovati, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovati, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperfatturatocompresotra/{fatturatoMin}/{fatturatoMax}")
	@Operation(summary = "Trova cliente per fatturato annuale compreso tra un range di importi")
	public ResponseEntity<Page<Cliente>> trovaByFatturatoAnnualeCompresoTra(Pageable pageable,
			@PathVariable BigDecimal fatturatoMin, @PathVariable BigDecimal fatturatoMax) {
		Page<Cliente> trovati = clienteService.findByFatturatoAnnualeBetween(pageable, fatturatoMin, fatturatoMax);
		if (trovati.isEmpty()) {
			return new ResponseEntity<>(trovati, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovati, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperinserimentomaggioredi/{dataInserimento}")
	@Operation(summary = "Trova un cliente con la data inserimento maggiore o uguale alla data inserita")
	public ResponseEntity<Page<Cliente>> trovaByDataInserimentoMaggioreUguale(Pageable pageable,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInserimento) {
		Page<Cliente> trovati = clienteService.findByDataInserimentoGreaterThanEqual(pageable, dataInserimento);
		if (trovati.isEmpty()) {
			return new ResponseEntity<>(trovati, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovati, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperinserimentominoredi/{dataInserimento}")
	@Operation(summary = "Trova un cliente con la data inserimento minore o uguale alla data inserita")
	public ResponseEntity<Page<Cliente>> trovaByDataInserimentoMinoreUguale(Pageable pageable,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInserimento) {
		Page<Cliente> trovati = clienteService.findByDataInserimentoLessThanEqual(pageable, dataInserimento);
		if (trovati.isEmpty()) {
			return new ResponseEntity<>(trovati, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovati, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperinserimentocompresotra/{dataMin}/{dataMax}")
	@Operation(summary = "Trova un cliente in un range di date inserimento")
	public ResponseEntity<Page<Cliente>> trovaByDataInserimentoCompresaTra(Pageable pageable,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataMin,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataMax) {

		Page<Cliente> trovati = clienteService.findByDataInserimentoBetween(pageable, dataMin, dataMax);
		if (trovati.isEmpty()) {
			return new ResponseEntity<>(trovati, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovati, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperultimocontattomaggioredi/{dataUltimoContatto}")
	@Operation(summary = "Trova un cliente con la data dell'ultimo contatto maggiore o uguale alla data inserita")
	public ResponseEntity<Page<Cliente>> trovaByDataUltimoContattoMaggioreUguale(Pageable pageable,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataUltimoContatto) {
		Page<Cliente> trovati = clienteService.findByDataUltimoContattoGreaterThanEqual(pageable, dataUltimoContatto);
		if (trovati.isEmpty()) {
			return new ResponseEntity<>(trovati, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovati, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperultimocontattominoredi/{dataUltimoContatto}")
	@Operation(summary = "Trova un cliente con la data dell'ultimo contatto minore o uguale alla data inserita")
	public ResponseEntity<Page<Cliente>> trovaByDataUltimoContattoMinoreUguale(Pageable pageable,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataUltimoContatto) {
		Page<Cliente> trovati = clienteService.findByDataUltimoContattoLessThanEqual(pageable, dataUltimoContatto);
		if (trovati.isEmpty()) {
			return new ResponseEntity<>(trovati, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovati, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperultimocontattocompresotra/{dataUltimoContattoMin}/{dataUltimoContattoMax}")
	@Operation(summary = "Trova un cliente in un range di date dell'ultimo contatto")
	public ResponseEntity<Page<Cliente>> trovaByDataUltimoContattoCompresaTra(Pageable pageable,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataUltimoContattoMin,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataUltimoContattoMax) {
		Page<Cliente> trovati = clienteService.findByDataUltimoContattoBetween(pageable, dataUltimoContattoMin,
				dataUltimoContattoMax);
		if (trovati.isEmpty()) {
			return new ResponseEntity<>(trovati, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovati, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovapernomecontaining/{ragioneSociale}")
	@Operation(summary = "Cerca cliente tramite parte del nome")
	public ResponseEntity<Page<Cliente>> findByRagioneSocialeContaining(Pageable pageable,
			@PathVariable String ragioneSociale) {
		Page<Cliente> trovati = clienteService.findByRagioneSocialeContaining(pageable, ragioneSociale);
		if (trovati.isEmpty()) {
			return new ResponseEntity<>(trovati, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovati, HttpStatus.OK);
		}

	}

}
