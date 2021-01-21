package br.com.indtexbr.backend.gestaoprocessos.model;

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
    private String orgao;    
    private String numero;    
    private String link;    
    private Integer versao;    
    private Boolean atualizada; 
    private Boolean ativa;
}
