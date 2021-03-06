package br.com.indtexbr.backend.gestaonormas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.indtexbr.backend.gestaonormas.dto.NormaDTO;
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
	public NormaDTO criarNorma(NormaDTO dto) {
		log.info("NormaServiceImpl.criarNorma ({})", dto);

		Norma entity = modelMapper.map(dto, Norma.class);

		entity = repository.save(entity);

		return modelMapper.map(entity, NormaDTO.class);
	}

	@Override
	public List<NormaDTO> listarNormas() {
		log.info("NormaServiceImpl.listarNormas ({})");
		List<NormaDTO> responseDTOs = repository.findAll().stream()
				.map(entity -> modelMapper.map(entity, NormaDTO.class))
					.collect(Collectors.toList());
		return responseDTOs;
	}

	@Override
	public NormaDTO alterarNorma(NormaDTO dto) {
		log.info("NormaServiceImpl.alterarNorma ({})", dto);
	
		Norma entity = repository.save( modelMapper.map(dto, Norma.class));
		
		return modelMapper.map(entity, NormaDTO.class);
	}

	@Override
	public NormaDTO obterNorma(Integer id) {
		log.info("NormaServiceImpl.obterNorma ({})", id);
		
		Norma entity = repository.findById(id)
	                .orElseThrow(() -> new RuntimeException("'Norma' not found"));
		
		return modelMapper.map(entity, NormaDTO.class);
	}

	@Override
	public void excluirNorma(Integer id) {
		log.info("NormaServiceImpl.excluirNorma ({})", id);
		
		Norma entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("'Norma' not found"));	
		
		repository.delete(entity);
	}

	@Override
	public int obterQuantidadeDesatualizadas() {
		log.info("NormaServiceImpl.obterQuantidadeDesatualizadas ");
		return repository.countByAtualizadaFalse();
	}

	@Override
	public int obterQuantidadeInativas() {
		log.info("NormaServiceImpl.obterQuantidadeInativas ");
		return repository.countByAtivaFalse();
	}
}
