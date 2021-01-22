package br.com.indtexbr.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContratoDTO {

	private Integer id;
	private String texto;
	private String area;
}
