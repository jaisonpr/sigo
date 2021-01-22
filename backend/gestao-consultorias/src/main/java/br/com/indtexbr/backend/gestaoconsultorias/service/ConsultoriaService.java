package br.com.indtexbr.backend.gestaoconsultorias.service;

import java.util.List;

import br.com.indtexbr.backend.gestaoconsultorias.dto.ConsultoriaDTO;

public interface ConsultoriaService {

	//Page<ResponseDTO> listarConsultorias(Pageable pageable);
	List<ConsultoriaDTO> listarConsultorias();
	ConsultoriaDTO criarConsultoria(ConsultoriaDTO Consultoria);
	ConsultoriaDTO alterarConsultoria(ConsultoriaDTO Consultoria);
	ConsultoriaDTO obterConsultoria(Integer id);
	void excluirConsultoria(Integer id);
}
