package br.com.indtexbr.backend.gestaoconsultorias.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.indtexbr.backend.gestaoconsultorias.dto.ConsultoriaDTO;
import br.com.indtexbr.backend.gestaoconsultorias.entity.Consultoria;
import br.com.indtexbr.backend.gestaoconsultorias.repository.ConsultoriaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsultoriaServiceImpl implements ConsultoriaService {

	@Autowired
	private ConsultoriaRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ConsultoriaDTO criarConsultoria(ConsultoriaDTO dto) {
		log.info("ConsultoriaServiceImpl.criarConsultoria ({})", dto);

		Consultoria entity = modelMapper.map(dto, Consultoria.class);

		entity = repository.save(entity);

		return modelMapper.map(entity, ConsultoriaDTO.class);
	}

	@Override
	public List<ConsultoriaDTO> listarConsultorias() {
		log.info("ConsultoriaServiceImpl.listarConsultorias ({})");

		List<ConsultoriaDTO> responseDTOs = repository.findAll().stream()
				.map(entity -> modelMapper.map(entity, ConsultoriaDTO.class))
					.collect(Collectors.toList());

		return responseDTOs;
	}

	@Override
	public ConsultoriaDTO alterarConsultoria(ConsultoriaDTO dto) {
		log.info("ConsultoriaServiceImpl.alterarConsultoria ({})", dto);
	
		Consultoria entity = repository.save( modelMapper.map(dto, Consultoria.class));
		
		return modelMapper.map(entity, ConsultoriaDTO.class);
	}

	@Override
	public ConsultoriaDTO obterConsultoria(Integer id) {
		log.info("ConsultoriaServiceImpl.obterConsultoria ({})", id);
		
		Consultoria entity = repository.findById(id)
	                .orElseThrow(() -> new RuntimeException("'Consultoria' not found"));
		
		return modelMapper.map(entity, ConsultoriaDTO.class);
	}

	@Override
	public void excluirConsultoria(Integer id) {
		log.info("ConsultoriaServiceImpl.excluirConsultoria ({})", id);
		
		Consultoria entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("'Consultoria' not found"));	
		
		repository.delete(entity);
	}
}
