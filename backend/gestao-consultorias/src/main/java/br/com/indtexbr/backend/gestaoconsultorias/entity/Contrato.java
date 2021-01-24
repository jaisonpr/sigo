package br.com.indtexbr.backend.gestaoconsultorias.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contratos")
public class Contrato {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_contrato")
    private Integer idContrato;
	
	@Column(name = "id_consultoria")
    private Integer idConsultoria;

	@Column(name = "id_contrato_externo")
    private Integer idContratoExterno;
	
	@ElementCollection
	@Column(name = "contrato_normas")
    private Set<Integer> normas;
	
	@Lob
	@Column(name = "texto", length = 100000 )
    private String texto;
	
	@Column(name = "area")
    private String area;
}
