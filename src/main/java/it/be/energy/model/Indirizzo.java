package it.be.energy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class Indirizzo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String via;
	private String civico;
	private String cap;
	private String localita;
	@ManyToOne
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIgnoreProperties( {"provincia", "indirizzi"})
	private Comune comune;


	public String visualizzaIndirizzo() {
		return via + " " + civico + "\n" + cap + "\n" + comune.getNome();
	}


	@Override
	public String toString() {
		return "Indirizzo [id=" + id + ", via=" + via + ", civico=" + civico + ", cap=" + cap + ", localita=" + localita
				+ "]";
	}
	

}
