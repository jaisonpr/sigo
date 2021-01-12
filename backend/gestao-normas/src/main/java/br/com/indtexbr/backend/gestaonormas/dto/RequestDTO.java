package br.com.indtexbr.backend.gestaonormas.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDTO {

    @NotBlank
    private String titulo;

    private String descricao;
    
    @NotBlank
    private String categoria;

    @NotBlank
    private String codigo;
    
    private String dataInclusao;

    private String dataPublicacao;
    
    private Integer id;
}
