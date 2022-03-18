package it.be.energy.controller.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.be.energy.model.Cliente;
import it.be.energy.model.Comune;
import it.be.energy.model.Fattura;
import it.be.energy.model.Indirizzo;
import it.be.energy.model.StatoFattura;
import it.be.energy.service.ClienteService;
import it.be.energy.service.FatturaService;
import it.be.energy.service.StatoFatturaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/web/fattura")
public class FatturaControllerWeb {

	@Autowired
	FatturaService fatturaService;

	@Autowired
	StatoFatturaService statoFatturaService;

	@Autowired
	ClienteService clienteService;

	@GetMapping("/mostraelencofatture")
	public ModelAndView findAllFatture(Pageable pageable) {
		log.info("*** findAllClienti ***");
		ModelAndView view = new ModelAndView("visualizzaFatture");
		view.addObject("fatture", fatturaService.trovaTutte(pageable));
		return view;
	}

	@GetMapping("/formaggiungifattura")
	public ModelAndView mostraFormFatture(Pageable pageable) {
		log.info("Inizio visualizzazione form");
		List<StatoFattura>statiFattura = statoFatturaService.trovaTutte();
		Page<Cliente> clienti =clienteService.findAll(pageable);
		ModelAndView mav = new ModelAndView("inserisciFattura", "fattura", new Fattura());
		mav.addObject("statiFattura", statiFattura);
		mav.addObject("clienti", clienti);
		return mav;
	}

	@PostMapping("/aggiungifattura")
	public ModelAndView inserisciFattura(@ModelAttribute("fattura") Fattura fattura, Model model, BindingResult bindingResult , Pageable pageable) {
		log.info("inizio inserimento fattura");
		if(bindingResult.hasErrors()) {
			return new ModelAndView("inserisciFattura", "fattura", new Fattura()); 
		}

		else {
			List<StatoFattura> statiFattura = statoFatturaService.trovaTutte();
			Long idStatoFatturaTrovato = fattura.getStatoFattura().getId();
			for (StatoFattura statoFattura : statiFattura) {
				if(statoFattura.getId().equals(idStatoFatturaTrovato)) {
					fattura.setStatoFattura(statoFattura);
				}
			}


			Page<Cliente> clienti = clienteService.findAll(pageable);
			Long idClienteTrovato = fattura.getCliente().getId();

			for (Cliente cliente : clienti) {
				if(cliente.getId().equals(idClienteTrovato)) {
					fattura.setCliente(cliente);
				}

			}
			log.info("*** Fattura salvata correttamente! ***");
			fatturaService.inserisciFattura(fattura);
			return findAllFatture(pageable);
		}
	}

	@GetMapping("/formaggiornafattura/{id}")
	public ModelAndView mostraFormAggiornaFatture(@PathVariable Long id, Pageable pageable) {
		log.info("Inizio visualizzazione form aggiorna fattura");
		Optional<Fattura> fattura = fatturaService.trovaById(id);
		List<StatoFattura>statiFattura = statoFatturaService.trovaTutte();
		Page<Cliente> clienti =clienteService.findAll(pageable);
		ModelAndView mav = new ModelAndView("aggiornaFattura", "fattura", new Fattura());
		mav.addObject("fattura", fattura.get());
		mav.addObject("statiFattura", statiFattura);
		mav.addObject("clienti", clienti);
		return mav;
	}

	@PostMapping("/aggiornafattura/{id}")
	// valid significa fa un controllo sul back end per verificare che il campo non sia vuoto
	public ModelAndView aggiornaFattura(@ModelAttribute("fattura") Fattura fattura, Model model, BindingResult bindingResult , Pageable pageable, @PathVariable Long id) {
		log.info("*** inizio aggiornamento fattura ***");
		if(bindingResult.hasErrors()) {
			log.info("*** Fattura non aggiornata ***");
			return new ModelAndView("aggiornaFattura", "fattura", new Fattura()); 
		}
		else {
			List<StatoFattura> statiFattura = statoFatturaService.trovaTutte();
			Long idStatoFatturaTrovato = fattura.getStatoFattura().getId();
			for (StatoFattura statoFattura : statiFattura) {
				if(statoFattura.getId().equals(idStatoFatturaTrovato)) {
					fattura.setStatoFattura(statoFattura);
				}
			}


			Page<Cliente> clienti = clienteService.findAll(pageable);
			Long idClienteTrovato = fattura.getCliente().getId();

			for (Cliente cliente : clienti) {
				if(cliente.getId().equals(idClienteTrovato)) {
					fattura.setCliente(cliente);
				}

			}
			log.info("*** Fattura aggiornata correttamente! ***");
			fatturaService.inserisciFattura(fattura);
			return findAllFatture(pageable);
		}

	}
	@GetMapping("/eliminafattura/{id}")
	public ModelAndView eliminaFattura(@PathVariable ("id") Long id, Model model, Pageable pageable) {
		fatturaService.cancellaFattura(id);
		return findAllFatture(pageable);
	}
}