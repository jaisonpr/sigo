package br.com.indtexbr.backend.gestaonormas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.indtexbr.backend.gestaonormas.dto.RequestDTO;
import br.com.indtexbr.backend.gestaonormas.dto.ResponseDTO;
import br.com.indtexbr.backend.gestaonormas.entity.Norma;
import br.com.indtexbr.backend.gestaonormas.repository.NormaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NormaServiceImpl implements NormaService {

	@Autowired
	private NormaRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseDTO criarNorma(RequestDTO dto) {
		log.info("NormaServiceImpl.criarNorma ({})", dto);

		Norma entity = modelMapper.map(dto, Norma.class);

		entity = repository.save(entity);

		return modelMapper.map(entity, ResponseDTO.class);
	}

	@Override
	public List<ResponseDTO> listarNormas() {
		log.info("NormaServiceImpl.listarNormas ({})");

		List<Norma> normas = repository.findAll();

		List<ResponseDTO> responseDTOs = normas.stream()
				.map(standardEntity -> modelMapper.map(standardEntity, ResponseDTO.class))
					.collect(Collectors.toList());

		return responseDTOs;
				//new PageImpl<>(responseDTOs, normas.getPageable(), normas.getTotalElements());
	}

	@Override
	public ResponseDTO alterarNorma(RequestDTO dto) {
		log.info("NormaServiceImpl.alterarNorma ({})", dto);
	
		Norma entity = repository.save( modelMapper.map(dto, Norma.class));
		
		return modelMapper.map(entity, ResponseDTO.class);
	}

	@Override
	public ResponseDTO obterNorma(Integer id) {
		log.info("NormaServiceImpl.obterNorma ({})", id);
		
		Norma entity = repository.findById(id)
	                .orElseThrow(() -> new RuntimeException("'Norma' not found"));
		
		return modelMapper.map(entity, ResponseDTO.class);
	}

	@Override
	public void excluirNorma(Integer id) {
		log.info("NormaServiceImpl.excluirNorma ({})", id);
		
		Norma entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("'Norma' not found"));	
		repository.delete(entity);
	}
}
