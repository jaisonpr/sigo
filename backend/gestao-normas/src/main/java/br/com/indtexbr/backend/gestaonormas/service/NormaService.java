package br.com.indtexbr.backend.gestaonormas.service;

import java.util.List;

import br.com.indtexbr.backend.gestaonormas.dto.RequestDTO;
import br.com.indtexbr.backend.gestaonormas.dto.ResponseDTO;

public interface NormaService {

	//Page<ResponseDTO> listarNormas(Pageable pageable);
	List<ResponseDTO> listarNormas();
	ResponseDTO criarNorma(RequestDTO request);
	ResponseDTO alterarNorma(RequestDTO request);
	ResponseDTO obterNorma(Integer id);
	void excluirNorma(Integer id);
}
