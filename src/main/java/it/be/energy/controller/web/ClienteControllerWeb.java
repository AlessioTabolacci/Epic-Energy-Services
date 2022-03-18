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
import it.be.energy.model.Indirizzo;
import it.be.energy.service.ClienteService;
import it.be.energy.service.ComuneService;
import it.be.energy.service.IndirizzoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/web")
public class ClienteControllerWeb {

	@Autowired
	ClienteService clienteService;
	@Autowired
	ComuneService comuneService;
	@Autowired
	IndirizzoService indirizzoService;

	@GetMapping("/mostraelencoclienti")
	public ModelAndView findAllClienti(Pageable pageable) {
		log.info("*** findAllClienti ***");
		ModelAndView view = new ModelAndView("visualizzaClienti");
		view.addObject("clienti", clienteService.findAll(pageable));
		return view;
	}


	@GetMapping("/formaggiungi")
	public ModelAndView mostraForm() {
		List<Comune> comuni = comuneService.trovaTutti();
		log.info("Inizio visualizzazione form");
		ModelAndView mav = new ModelAndView("inserisciCliente", "cliente", new Cliente());
		mav.addObject("comuni", comuni);
		return mav;
	}

	@PostMapping("/aggiungicliente")
	public ModelAndView inserisciCliente(@ModelAttribute("cliente") Cliente cliente, Model model, BindingResult bindingResult , Pageable pageable) {
		log.info("inizio inserimento cliente");
		if(bindingResult.hasErrors()) {
			return new ModelAndView("inserisciCliente", "cliente", new Cliente()); 
		}

		else {
			Indirizzo indirizzoSedeLegale = new Indirizzo();
			List<Comune> comuni = comuneService.trovaTutti();
			Long comuneSedeLegale = cliente.getSedeLegale().getComune().getId();
			indirizzoSedeLegale.setVia(cliente.getSedeLegale().getVia());
			indirizzoSedeLegale.setCivico(cliente.getSedeLegale().getCivico());
			indirizzoSedeLegale.setCap(cliente.getSedeLegale().getCap());
			for (Comune comune : comuni) {
				if(comune.getId().equals(comuneSedeLegale)) {
					indirizzoSedeLegale.setComune(comune);
				}

			}
			indirizzoService.inserisciIndirizzo(indirizzoSedeLegale);
			Optional<Indirizzo> indt = indirizzoService.trovaTuttiTramiteId(indirizzoSedeLegale.getId());
			cliente.setSedeLegale(indt.get());

			Indirizzo indirizzoSedeOperativa = new Indirizzo();
			Long comuneSedeOperativa = cliente.getSedeOperativa().getComune().getId();
			log.info("***ARRIVI QUI? ***");
			indirizzoSedeOperativa.setVia(cliente.getSedeOperativa().getVia());
			indirizzoSedeOperativa.setCivico(cliente.getSedeOperativa().getCivico());
			indirizzoSedeOperativa.setCap(cliente.getSedeOperativa().getCap());
			for (Comune comune : comuni) {
				if(comune.getId().equals(comuneSedeOperativa)) {
					indirizzoSedeOperativa.setComune(comune);
				}

			}
			indirizzoService.inserisciIndirizzo(indirizzoSedeOperativa);
			Optional<Indirizzo> indt2 = indirizzoService.trovaTuttiTramiteId(indirizzoSedeOperativa.getId());
			cliente.setSedeOperativa(indt2.get());
			clienteService.inserisciCliente(cliente);
			return findAllClienti(pageable);
		}
	}

	@GetMapping("/formaggiorna/{id}")
	public ModelAndView mostraFormAggiornaCliente(@PathVariable Long id, Model model) {
		log.info("***Inizio mostra form aggiorna***");
		List<Comune> comuni = comuneService.trovaTutti();
		Optional<Cliente> c = clienteService.findAllById(id);
		Cliente cliente = c.get();
		ModelAndView mav = new ModelAndView("aggiornaCliente", "cliente", new Cliente());
		mav.addObject("cliente", cliente);
		mav.addObject("comuni", comuni);
		return mav;
	}

	@PostMapping("/aggiornacliente/{id}")
	// valid significa fa un controllo sul back end per verificare che il campo non sia vuoto
	public ModelAndView aggiornaCliente(@ModelAttribute("cliente") Cliente cliente, Model model, BindingResult bindingResult , Pageable pageable, @PathVariable Long id) {
		log.info("inizio aggiornamento cliente");
		if(bindingResult.hasErrors()) {
			log.info("*** Cliente non aggiornato ***");
			return new ModelAndView("aggiornaCliente", "cliente", new Cliente()); 
		}

		else {
			cliente.setId(id);
			Indirizzo indirizzoSedeLegale = new Indirizzo();
			List<Comune> comuni = comuneService.trovaTutti();
			Long comuneSedeLegale = cliente.getSedeLegale().getComune().getId();
			indirizzoSedeLegale.setVia(cliente.getSedeLegale().getVia());
			indirizzoSedeLegale.setCivico(cliente.getSedeLegale().getCivico());
			indirizzoSedeLegale.setCap(cliente.getSedeLegale().getCap());
			for (Comune comune : comuni) {
				if(comune.getId().equals(comuneSedeLegale)) {
					indirizzoSedeLegale.setComune(comune);
				}

			}
			indirizzoService.inserisciIndirizzo(indirizzoSedeLegale);
			Optional<Indirizzo> indt = indirizzoService.trovaTuttiTramiteId(indirizzoSedeLegale.getId());
			cliente.setSedeLegale(indt.get());

			Indirizzo indirizzoSedeOperativa = new Indirizzo();
			Long comuneSedeOperativa = cliente.getSedeOperativa().getComune().getId();
			indirizzoSedeOperativa.setVia(cliente.getSedeOperativa().getVia());
			indirizzoSedeOperativa.setCivico(cliente.getSedeOperativa().getCivico());
			indirizzoSedeOperativa.setCap(cliente.getSedeOperativa().getCap());
			for (Comune comune : comuni) {
				if(comune.getId().equals(comuneSedeOperativa)) {
					indirizzoSedeOperativa.setComune(comune);
				}

			}
			indirizzoService.inserisciIndirizzo(indirizzoSedeOperativa);
			Optional<Indirizzo> indt2 = indirizzoService.trovaTuttiTramiteId(indirizzoSedeOperativa.getId());
			cliente.setSedeOperativa(indt2.get());
			clienteService.modificaCliente(cliente, id);
			log.info("*** Cliente Aggiornato correttamente ***");
			return findAllClienti(pageable);
		}
	}

	@GetMapping("/eliminacliente/{id}")
	public ModelAndView eliminaCliente(@PathVariable ("id") Long id, Model model, Pageable pageable) {
		clienteService.cancellaClienteById(id);
		return findAllClienti(pageable);
	}
	
	@GetMapping("/findallbyorderbyragionesociale")
	public ModelAndView ordinaPerRagioneSociale(Pageable pageable, Model model) {
		Page<Cliente> trova = clienteService.findAllByOrderByRagioneSocialeAsc(pageable);
		ModelAndView mav = new ModelAndView("visualizzaClienti");
		mav.addObject("clienti", trova);
		return mav;
		
	}
	
	@GetMapping("/findallbyorderbyfatturatoannuale")
	public ModelAndView findAllByOrderByFatturatoAnnuale(Pageable pageable, Model model) {
		Page<Cliente> trova = clienteService.findAllByOrderByFatturatoAnnualeDesc(pageable);
		ModelAndView mav = new ModelAndView("visualizzaClienti");
		mav.addObject("clienti", trova);
		return mav;
	}
	
	@GetMapping("/findallbyorderbydatainserimento")
	public ModelAndView findAllByOrderByDataInserimento(Pageable pageable, Model model) {
		Page<Cliente> trova = clienteService.findByOrderByDataInserimentoDesc(pageable);
		ModelAndView mav = new ModelAndView("visualizzaClienti");
		mav.addObject("clienti", trova);
		return mav;
	}
	
	@GetMapping("/findallbyorderbydataultimocontatto")
	public ModelAndView findAllByOrderByDataUltimoContatto(Pageable pageable, Model model) {
		Page<Cliente> trova = clienteService.findByOrderByDataUltimoContattoDesc(pageable);
		ModelAndView mav = new ModelAndView("visualizzaClienti");
		mav.addObject("clienti", trova);
		return mav;
	}
	
	
}
