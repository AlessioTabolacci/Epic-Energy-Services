package it.be.energy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Provincia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private Long codProvincia;
	@Column(unique = true)
	private String sigla;
	private String nome;
	private String regione;

	@OneToMany(mappedBy = "provincia")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Comune> comuni;

	public Provincia(String sigla, String nome, String regione) {
		this.sigla = sigla;
		this.nome = nome;
		this.regione = regione;
	}

	@Override
	public String toString() {
		return "Provincia [id=" + id + ", codProvincia=" + codProvincia + ", sigla=" + sigla + ", nome=" + nome
				+ ", regione=" + regione + "]";
	}

	
}
