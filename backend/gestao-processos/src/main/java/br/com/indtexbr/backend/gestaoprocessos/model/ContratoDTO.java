package br.com.indtexbr.backend.gestaoprocessos.model;

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
