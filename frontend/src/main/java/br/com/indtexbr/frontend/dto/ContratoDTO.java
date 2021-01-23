package br.com.indtexbr.frontend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContratoDTO {

	private Integer id;
    private Integer idContrato;
	private String texto;
	private String area;
	private String consultoria;
	private String normasFormatado;
	//
	private List<Consultoria> consultorias;
	private List<String> normas;
}

class Consultoria {
	Integer id;
	String nome;
}