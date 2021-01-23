package br.com.indtexbr.backend.gestaoprocessos.model;

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
}
