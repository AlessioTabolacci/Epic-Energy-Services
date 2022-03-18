package it.be.energy.util;

import java.io.FileReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

import it.be.energy.model.Cliente;
import it.be.energy.model.Comune;
import it.be.energy.model.Fattura;
import it.be.energy.model.Indirizzo;
import it.be.energy.model.Provincia;
import it.be.energy.model.StatoFattura;
import it.be.energy.model.TipoCliente;
import it.be.energy.repository.ClienteRepository;
import it.be.energy.repository.ComuneRepository;
import it.be.energy.repository.FatturaRepository;
import it.be.energy.repository.IndirizzoRepository;
import it.be.energy.repository.ProvinciaRepository;
import it.be.energy.repository.StatoFatturaRepository;

@Component
public class Runner implements CommandLineRunner {

	@Autowired
	ProvinciaRepository provinciaRepository;
	@Autowired
	ComuneRepository comuneRepository;
	@Autowired
	IndirizzoRepository indirizzoRepository;
	@Autowired
	FatturaRepository fatturaRepository;
	@Autowired
	StatoFatturaRepository statoFatturaRepository;
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public void run(String... args) throws Exception {
		initComune();
		initProvincia();

		//Inserisco idrizzi nel Database
		Indirizzo i1 = new Indirizzo();
		i1.setVia("Via le mani dal naso");
		i1.setCivico("10B");
		i1.setComune(comuneRepository.getById(3981L));
		i1.setLocalita("Città");
		i1.setCap("00175");
		indirizzoRepository.save(i1);

		Indirizzo i2 = new Indirizzo();
		i2.setVia("Piazza San Giovanni Bosco");
		i2.setCivico("5");
		i2.setComune(comuneRepository.getById(5199L));
		i2.setLocalita("Città");
		i2.setCap("00175");
		indirizzoRepository.save(i2);

		Indirizzo i3 = new Indirizzo();
		i3.setVia("Via Roberto De nobili");
		i3.setCivico("5");
		i3.setComune(comuneRepository.getById(5199L));
		i3.setLocalita("Città");
		i3.setCap("00154");
		indirizzoRepository.save(i3);

		Indirizzo i4 = new Indirizzo();
		i4.setVia("Via Garibaldi");
		i4.setCivico("10");
		i4.setComune(comuneRepository.getById(489L));
		i4.setLocalita("Borgo");
		i4.setCap("59874");
		indirizzoRepository.save(i4);

		//Creo gli stati fattura
		StatoFattura sf = new StatoFattura();
		StatoFattura sf1 = new StatoFattura();
		StatoFattura sf2 = new StatoFattura();
		sf.setNome("Pagata");
		sf1.setNome("Non pagata");
		sf2.setNome("In attesa di pagamento");
		statoFatturaRepository.save(sf);
		statoFatturaRepository.save(sf1);
		statoFatturaRepository.save(sf2);



		//Metodo per inserimento dati di tipo Date
		

		//Inserisco le fatture nel Database
		Fattura f = new Fattura();
		LocalDate data = LocalDate.parse("2019-12-23");
		f.setNumeroFattura(20065L);
		f.setAnno(2019);
		f.setData(data);
		f.setImporto(new BigDecimal("20000"));
		f.setStatoFattura(sf1);
		fatturaRepository.save(f);

		Fattura f1 = new Fattura();
		LocalDate data1 = LocalDate.parse("2020-01-10");
		f1.setNumeroFattura(20066L);
		f1.setAnno(2020);
		f1.setData(data1);
		f1.setImporto(new BigDecimal("5000000"));
		f1.setStatoFattura(sf);
		fatturaRepository.save(f1);

		Fattura f2 = new Fattura();
		LocalDate data2 = LocalDate.parse("2021-05-14");
		f2.setNumeroFattura(25698L);
		f2.setAnno(2021);
		f2.setData(data2);
		f2.setImporto(new BigDecimal("1000000"));
		f2.setStatoFattura(sf2);
		fatturaRepository.save(f2);

		//Inserisco i clienti
		Cliente c = new Cliente();
		c.setTipoCliente(TipoCliente.SPA);
		c.setRagioneSociale("A.S.Roma");
		c.setPIva("86334519757");
		c.setEmail("asroma@asroma.it");
		c.setDataInserimento(LocalDate.parse("2022-02-15"));
		c.setDataUltimoContatto(LocalDate.parse("2022-03-15"));
		c.setFatturatoAnnuale(new BigDecimal("150000000"));
		c.setPec("asroma@pec.it");
		c.setTelefono("065896554");
		c.setEmailContatto("Dot.Rossi@asroma.it");
		c.setNomeContatto("Mario");
		c.setCognomeContatto("Rossi");
		c.setNumeroContatto("3332569885");
		c.setSedeLegale(i3);
		c.setSedeOperativa(i2);
		List<Fattura> fatture = new ArrayList<>();
		fatture.add(f2);
		f1.setCliente(c);
		f.setCliente(c);
		clienteRepository.save(c);
		fatturaRepository.save(f);
		fatturaRepository.save(f1);

		Cliente c1 = new Cliente();
		c1.setTipoCliente(TipoCliente.SAS);
		c1.setRagioneSociale("Pagnozzi's Pizza");
		c1.setPIva("8633489639759");
		c1.setEmail("pagnozzi@gmail.com");
		c1.setDataInserimento(LocalDate.parse("2022-01-10"));
		c1.setDataUltimoContatto(LocalDate.parse("2022-03-15"));
		c1.setFatturatoAnnuale(new BigDecimal("300000"));
		c1.setPec("pagnozzi@pec.it");
		c1.setTelefono("06589632");
		c1.setEmailContatto("a.pagnozzi@yahoo.it");
		c1.setNomeContatto("Antonello");
		c1.setCognomeContatto("Pagnozzi");
		c1.setNumeroContatto("3479658965");
		c1.setSedeLegale(i1);
		c1.setSedeOperativa(i4);
		List<Fattura> fatture1 = new ArrayList<>();
		fatture1.add(f1);
		f2.setCliente(c1);
		clienteRepository.save(c1);	
		fatturaRepository.save(f2);

	}

	//popolo il database con i file CSV
	private void initProvincia() throws Exception {
		try (CSVReader csvReader = new CSVReader(new FileReader("province-italiane_1.csv"));) {
			String[] values = null;
			csvReader.readNext(); //
			Optional<Provincia> pr;
			Provincia provincia;
			String nome;
			String[] valore;
			while ((values = csvReader.readNext()) != null) {
				valore = values[0].split(";");
				nome = valore[1];
				pr = provinciaRepository.findByNomeLike("%" + nome + "%");
				if (pr.isPresent()) {

					provincia = pr.get();
					provincia.setSigla(valore[0]);
					provincia.setRegione(valore[2]);
					provinciaRepository.save(provincia);
				} else {

					provinciaRepository.save(new Provincia(valore[0], valore[1], valore[2]));
				}
			}
		}
	}

	private void initComune() throws Exception {
		try (CSVReader csvReader = new CSVReader(new FileReader("comuni-italiani_1.csv"));) {
			String[] values = null;
			csvReader.readNext();
			Optional<Provincia> p;
			String[] valore;
			Provincia provincia;
			while ((values = csvReader.readNext()) != null) {
				valore = values[0].split(";");
				p = provinciaRepository.findByCodProvincia(Long.valueOf(valore[0]));
				if (p.isPresent()) {
					comuneRepository.save(new Comune(cambia(valore[2]), p.get()));
				} else {

					provincia = new Provincia();
					provincia.setCodProvincia(Long.valueOf(valore[0]));
					provincia.setNome(cambia(valore[3]));
					provinciaRepository.save(provincia);
					comuneRepository.save(new Comune(valore[2], provincia));
				}
			}
		}
	}

	private String cambia(String nome) {
		return nome.replace('-', ' ');

	}

}
