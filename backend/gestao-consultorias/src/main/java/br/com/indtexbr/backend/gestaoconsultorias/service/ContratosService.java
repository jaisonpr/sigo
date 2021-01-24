package br.com.indtexbr.backend.gestaoconsultorias.service;

import java.util.List;

import br.com.indtexbr.backend.gestaoconsultorias.dto.ContratoDTO;

public interface ContratosService {

	List<ContratoDTO> listarContratos();
	ContratoDTO criarContrato(ContratoDTO contratoDTO);
	ContratoDTO alterarContrato(ContratoDTO contratoDTO);
	ContratoDTO obterContrato(Integer id);
	ContratoDTO obterContratoOrigem(Integer id);
	void excluirContrato(Integer id);
	int obterQuantidadeNovos();
}
