package ar.com.cuyum.cnc.localizacion.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="localidades") //@Table(name="localidades_poblacion")
public class Localidad implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_localidad")
	private Long id;
	
	@Column(name="id_partido")
	private Long idPartido;
	
//	@Column(name="id_area_local")
//	private Long idArea;
	
	public Long getIdPartido() {
		return idPartido;
	}

//	public Long getIdArea() {
//		return idArea;
//	}
//
//	public void setIdArea(Long idArea) {
//		this.idArea = idArea;
//	}

	public void setIdPartido(Long idPartido) {
		this.idPartido = idPartido;
	}

	@Column(name="descr")
	private String nombre;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}