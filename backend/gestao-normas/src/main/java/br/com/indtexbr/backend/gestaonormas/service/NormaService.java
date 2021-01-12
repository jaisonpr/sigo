package br.com.indtexbr.backend.gestaonormas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.indtexbr.backend.gestaonormas.dto.RequestDTO;
import br.com.indtexbr.backend.gestaonormas.dto.ResponseDTO;

public interface NormaService {

	Page<ResponseDTO> listarNormas(Pageable pageable);
	
	ResponseDTO criarNorma(RequestDTO request);
	ResponseDTO alterarNorma(RequestDTO request);
	ResponseDTO obterNorma(Integer id);
	void excluirNorma(Integer id);
}
