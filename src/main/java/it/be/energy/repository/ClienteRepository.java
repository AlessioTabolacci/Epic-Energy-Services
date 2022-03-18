package it.be.energy.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public Page<Cliente> findAll(Pageable pageable);

	public Page<Cliente> findAllByOrderByRagioneSocialeAsc(Pageable pageable);

	public Page<Cliente> findAllByOrderByFatturatoAnnualeDesc(Pageable pageable);

	public Page<Cliente> findAllByOrderByDataInserimentoDesc(Pageable pageable);

	public Page<Cliente> findAllByOrderByDataUltimoContattoDesc(Pageable pageable);
	
	public Page<Cliente> findAllByOrderBySedeLegaleComuneProvincia(Pageable pageable);
	
	public Page<Cliente> findByFatturatoAnnualeGreaterThanEqual(Pageable pageable, BigDecimal fatturatoAnnuale);
	
	public Page<Cliente> findByFatturatoAnnualeLessThanEqual(Pageable pageable, BigDecimal fatturatoAnnuale);
	
	public Page<Cliente> findByFatturatoAnnualeBetween(Pageable pageable, BigDecimal fatturatoMin, BigDecimal fatturatoMax);
	
	public Page<Cliente> findByDataInserimentoGreaterThanEqual(Pageable pageable, LocalDate dataInserimento);
	
	public Page<Cliente> findByDataInserimentoLessThanEqual(Pageable pageable, LocalDate dataInserimento);
	
	public Page<Cliente> findByDataInserimentoBetween(Pageable pageable, LocalDate dataMin, LocalDate dataMax);
	
	public Page<Cliente> findByDataUltimoContattoGreaterThanEqual(Pageable pageable, LocalDate dataUltimoContatto);
	
	public Page<Cliente> findByDataUltimoContattoLessThanEqual(Pageable pageable, LocalDate dataUltimoContatto);
	
	public Page<Cliente> findByDataUltimoContattoBetween(Pageable pageable, LocalDate dataUltimoContattoMin, LocalDate dataUltimoContattoMax);
	
	public Page<Cliente> findByRagioneSocialeContaining(Pageable pageable, String ragioneSociale);
	

}
