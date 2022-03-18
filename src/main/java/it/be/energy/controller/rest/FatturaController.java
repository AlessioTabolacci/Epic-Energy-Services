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
import it.be.energy.model.Fattura;
import it.be.energy.service.FatturaService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fattura")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class FatturaController {

	@Autowired
	FatturaService fatturaService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovatutte")
	@Operation(summary = "visualizza tutte le fatture")
	public ResponseEntity<Page<Fattura>> trovaTutteLeFatture(Pageable pageable) {
		log.info("*** Inizio ricerca fatture ***");
		Page<Fattura> trovaTutte = fatturaService.trovaTutte(pageable);
		if (trovaTutte.hasContent()) {
			log.info("*** Fattura trovata! ***");
			return new ResponseEntity<>(trovaTutte, HttpStatus.OK);

		} else {
			log.info("*** Fattura non trovata! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperid/{id}")
	@Operation(summary = "trova fatture tramite l'id")
	public ResponseEntity<Fattura> trovaById(@PathVariable Long id) {
		log.info("*** Inizio Ricerca fattura per id ***");
		Optional<Fattura> trovaById = fatturaService.trovaById(id);
		log.info("*** Fine ricerca fattura per id ***");
		return new ResponseEntity<>(trovaById.get(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/inserisci")
	@Operation(summary = "Inserisci fattura")
	public ResponseEntity<Fattura> inserisciFattura(@RequestBody Fattura fattura) {
		log.info("*** Inizio inserimento fattura ***");
		Fattura f = fatturaService.inserisciFattura(fattura);
		log.info("*** Fattura salvata ***");
		return new ResponseEntity<>(f, HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/aggiorna/{id}")
	@Operation(summary = "Aggiorna fattura tramite il suo id")
	public ResponseEntity<Fattura> aggiornaFattura(@RequestBody Fattura fattura, @PathVariable Long id) {
		log.info("*** Inizio aggornamento fattura ***");
		Fattura f = fatturaService.modificaFattura(fattura, id);
		log.info("*** Fine aggiornamento fattura ***");
		return new ResponseEntity<>(f, HttpStatus.ACCEPTED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/cancella/{id}")
	@Operation(summary = "Cancella una fattura tramite il suo id")
	public ResponseEntity<String> cancellaFattura(@PathVariable Long id) {
		log.info("*** Inizio cancellazione fattura ***");
		fatturaService.cancellaFattura(id);
		log.info("*** Fine cancellazione fattura ***");
		return new ResponseEntity<>("Fattura cancellata correttamente!", HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperragionesocialecliente/{nome}")
	@Operation(summary = "Trova fatture per parte del nome del cliente")
	public ResponseEntity<Page<Fattura>> trovaByClienteRagioneSocialeContaining(Pageable pageable,
			@PathVariable String nome) {
		Page<Fattura> trovate = fatturaService.trovaByClienteRagioneSocialeContaining(pageable, nome);
		if (trovate.isEmpty()) {
			return new ResponseEntity<>(trovate, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovate, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperidstatofattura/{id}")
	@Operation(summary = "Trova fatture per il loro stato")
	public ResponseEntity<Page<Fattura>> trovaByStatoFatturaId(Pageable pageable, @PathVariable Long id) {
		Page<Fattura> trovate = fatturaService.trovaByStatoFatturaId(pageable, id);
		if (trovate.hasContent()) {
			return new ResponseEntity<>(trovate, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(trovate, HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperdata/{data}")
	@Operation(summary = "Trova fatture per data")
	public ResponseEntity<Page<Fattura>> trovaByData(Pageable pageable, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data) {
		Page<Fattura> trovate = fatturaService.trovaByData(pageable, data);
		if (trovate.isEmpty()) {
			return new ResponseEntity<>(trovate, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovate, HttpStatus.OK);
		}

	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperanno/{anno}")
	@Operation(summary = "Trova fatture per anno")
	public ResponseEntity<Page<Fattura>> findByAnno(Pageable pageable, @PathVariable Integer anno) {
		Page<Fattura> trovate = fatturaService.findByAnno(pageable, anno);
		if (trovate.isEmpty()) {
			return new ResponseEntity<>(trovate, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovate, HttpStatus.OK);
		}

	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperimportocompreso/{importoMin}/{importoMax}")
	@Operation(summary = "Trova fatture in un range di importi inserito")
	public ResponseEntity<Page<Fattura>> trovaByImportoCompreso(Pageable pageable, @PathVariable BigDecimal importoMin, @PathVariable BigDecimal importoMax) {
		Page<Fattura> trovate = fatturaService.findByImportoBetween(pageable, importoMin, importoMax);
		if (trovate.isEmpty()) {
			return new ResponseEntity<>(trovate, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(trovate, HttpStatus.OK);
		}

	}

}
