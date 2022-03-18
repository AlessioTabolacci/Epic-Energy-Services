package it.be.energy.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.be.energy.model.Comune;
import it.be.energy.service.ComuneService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/comune")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class ComuneController {

	@Autowired
	ComuneService comuneService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovatutti")
	@Operation(summary = "Visualizza tutti i comuni", description = "")
	public ResponseEntity<Page<Comune>> trovaTutti(Pageable pageable) {
		log.info("*** Inizio ricerca comune ***");
		Page<Comune> trovaTutti = comuneService.trovaTutti(pageable);
		if (trovaTutti.hasContent()) {
			log.info("*** Comune trovato! ***");
			return new ResponseEntity<>(trovaTutti, HttpStatus.OK);

		} else {
			log.info("*** Comune non trovato! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperid/{id}")
	@Operation(summary = "Visualizza comuni per id", description = "")
	public ResponseEntity<Comune> trovaById(@PathVariable Long id) {
		log.info("*** Inizio Ricerca per id ***");
		Optional<Comune> trovaById = comuneService.trovaById(id);
		log.info("*** Fine ricerca per id ***");
		return new ResponseEntity<>(trovaById.get(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovapernome/{nome}")
	@Operation(summary = "Trova comuni per parte del nome")
	public ResponseEntity<Page<Comune>> trovaPerParteDiNome(Pageable pageable, @PathVariable String nome) {
		log.info("*** Inizio ricerca comune ***");
		Page<Comune> trovaTutti = comuneService.findByNomeContaining(pageable, nome);		
		if (trovaTutti.hasContent()) {
			log.info("*** Comune trovato! ***");
			return new ResponseEntity<>(trovaTutti, HttpStatus.OK);

		} else {
			log.info("*** Comune non trovato! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
}
