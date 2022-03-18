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
import it.be.energy.model.Provincia;
import it.be.energy.service.ProvinciaService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/provincia")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class ProvinciaController {

	@Autowired
	ProvinciaService provinciaService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovatutte")
	@Operation(summary = "Visualizza tutte le province")
	public ResponseEntity<Page<Provincia>> trovaTutte(Pageable pageable) {
		log.info("*** Inizio ricerca Province ***");
		Page<Provincia> trovaTutte = provinciaService.trovaTutte(pageable);
		if (trovaTutte.hasContent()) {
			log.info("*** Province trovate! ***");
			return new ResponseEntity<>(trovaTutte, HttpStatus.OK);

		} else {
			log.info("*** Province non trovate! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovaperid/{id}")
	@Operation(summary = "Visualizza tutte le provincie per id")
	public ResponseEntity<Provincia> trovaById(@PathVariable Long id) {
		log.info("*** Inizio Ricerca per id ***");
		Optional<Provincia> trovaById = provinciaService.trovaById(id);
		log.info("*** Fine ricerca per id ***");
		return new ResponseEntity<>(trovaById.get(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/trovapernome/{nome}")
	@Operation(summary = "Trova le province con parte di nome", description = "")
	public ResponseEntity<Page<Provincia>> trovaPerParteDiNome(Pageable pageable, @PathVariable String nome) {
		log.info("*** Inizio ricerca provincia ***");
		Page<Provincia> trovaTutte = provinciaService.findByNomeContaining(pageable, nome);		
		if (trovaTutte.hasContent()) {
			log.info("*** Provincia trovata! ***");
			return new ResponseEntity<>(trovaTutte, HttpStatus.OK);

		} else {
			log.info("*** Provincia non trovata! ***");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

}
