package br.com.indtexbr.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsultoriaDTO {

	private Integer id;	
    private String nome;
    private String area;   
}
