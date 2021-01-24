package br.com.indtexbr.backend.gestaonormas.service;

import java.util.List;

import br.com.indtexbr.backend.gestaonormas.dto.NormaDTO;

public interface NormaService {

	//Page<ResponseDTO> listarNormas(Pageable pageable);
	List<NormaDTO> listarNormas();
	NormaDTO criarNorma(NormaDTO norma);
	NormaDTO alterarNorma(NormaDTO norma);
	NormaDTO obterNorma(Integer id);
	void excluirNorma(Integer id);
	int obterQuantidadeDesatualizadas();
	int obterQuantidadeInativas();
}
