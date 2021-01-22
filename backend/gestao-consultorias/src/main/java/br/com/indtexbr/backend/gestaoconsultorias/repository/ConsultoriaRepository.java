package br.com.indtexbr.backend.gestaoconsultorias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.indtexbr.backend.gestaoconsultorias.entity.Consultoria;

@Repository
public interface ConsultoriaRepository extends JpaRepository<Consultoria, Integer> {

}
