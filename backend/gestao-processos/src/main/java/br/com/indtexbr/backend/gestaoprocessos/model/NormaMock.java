package br.com.indtexbr.backend.gestaoprocessos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NormaMock {
	private String _id;
	private String orgao;
	private String numero;
	private String versao;
	private String[] status;
}
