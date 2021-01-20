package br.com.indtexbr.backend.gestaonormas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "normas")
public class Norma {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "orgao")
    private String orgao;
    
    @Column(name = "numero")
    private String numero;
    
    @Column(name = "versao")
    private Integer versao;
    
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "categoria")
    private String categoria;
 
    @Column(name = "dataInclusao")    
    private String dataInclusao;
    
    @Column(name = "dataPublicacao")    
    private String dataPublicacao;
    
    @Column(name = "atualizada")    
    private Boolean atualizada;
    
    @Column(name = "ativa")    
    private Boolean ativa;
}
