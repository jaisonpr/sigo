package br.com.indtexbr.backend.gestaonormas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.indtexbr.backend.gestaonormas.entity.Norma;

@Repository
public interface NormaRepository extends JpaRepository<Norma, Integer> {

	public int countByAtualizadaFalse();
	public int countByAtivaFalse();
}
