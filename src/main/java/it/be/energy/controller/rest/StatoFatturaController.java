package it.be.energy.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.be.energy.model.StatoFattura;
import it.be.energy.service.StatoFatturaService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/statofattura")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class StatoFatturaController {

	@Autowired
	StatoFatturaService statoFatturaService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovatutti")
	@Operation(summary = "Visualizza tutti gli stati fattura")
	public ResponseEntity<List<StatoFattura>> trovaTutti() {
		log.info("*** Inizio ricerca stato fattura ***");
		List<StatoFattura> trovaTutti = statoFatturaService.trovaTutte();
		if (trovaTutti.isEmpty()) {
			log.info("*** Stato fattura non trovato! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		} else {
			log.info("*** Stato fattura trovato! ***");
			return new ResponseEntity<>(trovaTutti, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperid/{id}")
	@Operation(summary = "Trova gli stati fattura per id")
	public ResponseEntity<StatoFattura> trovaById(@PathVariable Long id) {
		log.info("*** Inizio Ricerca per id ***");
		Optional<StatoFattura> trovaById = statoFatturaService.trovaById(id);
		log.info("*** Fine ricerca per id ***");
		return new ResponseEntity<>(trovaById.get(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/inserisci")
	@Operation(summary = "Inserisce uno stato fattura")
	public ResponseEntity<StatoFattura> inserisciStatoFattura(@RequestBody StatoFattura statoFattura) {
		log.info("*** Inizio inserimento stato fattura ***");
		StatoFattura sf = statoFatturaService.inserisciStatoFattura(statoFattura);
		log.info("*** Stato fattura salvato ***");
		return new ResponseEntity<>(sf, HttpStatus.CREATED);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/aggiorna/{id}")
	@Operation(summary = "Aggiorna uno stato fattura tramite il suo id")
	public ResponseEntity<StatoFattura> aggiornaStatoFattura(@RequestBody StatoFattura statofattura, @PathVariable Long id) throws ClienteException {
		log.info("*** Inizio aggornamento stato fattura ***");
		StatoFattura sf = statoFatturaService.modificaStatoFattura(statofattura, id);
		log.info("*** Fine aggiornamento stato fattura ***");
		return new ResponseEntity<>(sf, HttpStatus.ACCEPTED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/cancella/{id}")
	@Operation(summary = "Cancella uno stato fattura tramite il suo id")
	public ResponseEntity<String> cancellaStatoFattura(@PathVariable Long id) {
		log.info("*** Inizio cancellazione Stato fattura ***");
		statoFatturaService.cancellaStatoFatturaById(id);
		log.info("*** Fine cancellazione stato fattura ***");
		return new ResponseEntity<>("Stato fattura cancellato correttamente!", HttpStatus.OK);
	}

}
