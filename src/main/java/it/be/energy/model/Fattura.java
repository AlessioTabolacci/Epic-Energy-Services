package it.be.energy.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Fattura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long numeroFattura;
	private Integer anno;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate data;
	private BigDecimal importo;
	@ManyToOne
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIgnoreProperties({"dataInserimento", "dataUltimoContatto", "fatturatoAnnuale", "fatture", "sedeLegale", "sedeOperativa"})
	private Cliente cliente;
	@ManyToOne
	private StatoFattura statoFattura;
	
	@Override
	public String toString() {
		return "Fattura [id="+ id + ", numeroFattura=" + numeroFattura + ", anno=" + anno + ", data=" + data
				+ ", importo=" + importo + "]";
	}
	
	

}
