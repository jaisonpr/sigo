package br.com.indtexbr.backend.gestaonormas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NormaDTO {

	private Integer id;	
    private String titulo;
    private String descricao;    
    private String categoria;
    private String codigo;    
    private String dataInclusao;
    private String dataPublicacao;
    private Boolean atualizada; 
}