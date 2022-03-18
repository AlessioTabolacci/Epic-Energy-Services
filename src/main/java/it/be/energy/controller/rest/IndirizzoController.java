package it.be.energy.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import it.be.energy.exception.IndirizzoException;
import it.be.energy.model.Indirizzo;
import it.be.energy.service.IndirizzoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/indirizzo")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class IndirizzoController {

	@Autowired
	IndirizzoService indirizzoService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovatutti")
	@Operation(summary = "Trova tutti gli indirizzi")
	public ResponseEntity<Page<Indirizzo>> trovaTuttiGliIndirizzi(Pageable pageable) {
		log.info("*** Inizio ricerca indirizzi ***");
		Page<Indirizzo> trovaTutti = indirizzoService.trovaTuttiGliIndirizzi(pageable);
		if (trovaTutti.hasContent()) {
			log.info("*** Indirizzi trovati! ***");
			return new ResponseEntity<>(trovaTutti, HttpStatus.OK);

		} else {
			log.info("*** Indirizzi non trovati! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovatuttiperid/{id}")
	@Operation(summary = "Trova indirizzi tramite id")
	public ResponseEntity<Indirizzo> trovaById(@PathVariable Long id) {
		log.info("*** Inizio Ricerca indirizzo per id ***");
		Optional<Indirizzo> trovaById = indirizzoService.trovaTuttiTramiteId(id);
		log.info("*** Fine ricerca indirizzo per id ***");
		return new ResponseEntity<>(trovaById.get(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/inserisci")
	@Operation(summary = "Inserisce un indirizzo")
	public ResponseEntity<Indirizzo> inserisciIndirizzo(@RequestBody Indirizzo indirizzo) {
		log.info("*** Inizio inserimento indirizzo ***");
		Indirizzo i = indirizzoService.inserisciIndirizzo(indirizzo);
		log.info("*** Indirizzo salvato ***");
		return new ResponseEntity<>(i, HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "aggiorna/{id}")
	@Operation(summary = "Aggiorna un indirizzo tramite il suo id")
	public ResponseEntity<Indirizzo> aggiornaIndirizzo(@RequestBody Indirizzo indirizzo, @PathVariable Long id) throws IndirizzoException {
		log.info("*** Inizio aggornamento indirizzo ***");
		Indirizzo i = indirizzoService.modificaIndirizzo(indirizzo, id);
		log.info("*** Fine aggiornamento indirizzo ***");
		return new ResponseEntity<>(i, HttpStatus.ACCEPTED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/cancella/{id}")
	@Operation(summary = "Cancella un indirizzo tramite il suo id")
	public ResponseEntity<String> cancellaIndirizzo(@PathVariable Long id) {
		log.info("*** Inizio cancellazione indirizzo ***");
		indirizzoService.cancellaIndirizzoById(id);
		log.info("*** Fine cancellazione indirizzo ***");
		return new ResponseEntity<>("Indirizzo cancellato correttamente!", HttpStatus.OK);
	}

}
