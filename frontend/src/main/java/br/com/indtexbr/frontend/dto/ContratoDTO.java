package br.com.indtexbr.frontend.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContratoDTO {

	private Integer id;
    private Integer idConsultoria;
    private Integer idContratoExterno;
    private Set<Integer> normas;
    private String texto;
    private String area;
	//
    private String consultoria;
    private String normasFormatado;
}
