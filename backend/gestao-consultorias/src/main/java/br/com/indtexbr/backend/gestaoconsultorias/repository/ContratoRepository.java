package br.com.indtexbr.backend.gestaoconsultorias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.indtexbr.backend.gestaoconsultorias.entity.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
	
	Contrato findByIdContratoExterno(Integer idContratoExterno);
	public int countByIdConsultoriaNull();
}
