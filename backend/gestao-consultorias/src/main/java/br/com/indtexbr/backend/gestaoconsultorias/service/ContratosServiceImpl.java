package br.com.indtexbr.backend.gestaoconsultorias.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.indtexbr.backend.gestaoconsultorias.dto.ContratoDTO;
import br.com.indtexbr.backend.gestaoconsultorias.entity.Contrato;
import br.com.indtexbr.backend.gestaoconsultorias.repository.ContratoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContratosServiceImpl implements ContratosService {

	@Autowired
	private ContratoRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ContratoDTO criarContrato(ContratoDTO dto) {
		log.info("ContratoServiceImpl.criarContrato ({})", dto);

		Contrato entity = modelMapper.map(dto, Contrato.class);

		entity = repository.save(entity);

		return modelMapper.map(entity, ContratoDTO.class);
	}

	@Override
	public List<ContratoDTO> listarContratos() {
		log.info("ContratoServiceImpl.listarContratos ({})");

		List<Contrato> normas = repository.findAll();

		List<ContratoDTO> responseDTOs = normas.stream()
				.map(standardEntity -> modelMapper.map(standardEntity, ContratoDTO.class))
					.collect(Collectors.toList());

		return responseDTOs;
	}

	@Override
	public ContratoDTO alterarContrato(ContratoDTO dto) {
		log.info("ContratoServiceImpl.alterarContrato ({})", dto);
	
		Contrato entity = repository.save( modelMapper.map(dto, Contrato.class));
		
		return modelMapper.map(entity, ContratoDTO.class);
	}

	@Override
	public ContratoDTO obterContrato(Integer id) {
		log.info("ContratoServiceImpl.obterContrato ({})", id);
		
		Contrato entity = repository.findById(id)
	                .orElseThrow(() -> new RuntimeException("'Contrato' not found"));
		
		return modelMapper.map(entity, ContratoDTO.class);
	}

	@Override
	public void excluirContrato(Integer id) {
		log.info("ContratoServiceImpl.excluirContrato ({})", id);
		
		Contrato entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("'Contrato' not found"));	
		
		repository.delete(entity);
	}

	@Override
	public ContratoDTO obterContratoOrigem(Integer idContratoExterno) {
		log.info("ContratoServiceImpl.obterContratoOrigem ({})", idContratoExterno);
		
		Contrato entity = repository.findByIdContratoExterno(idContratoExterno);	
		
		log.info("ContratoServiceImpl.obterContratoOrigem ({})", entity);
		
		return modelMapper.map(entity, ContratoDTO.class);
	}
}
