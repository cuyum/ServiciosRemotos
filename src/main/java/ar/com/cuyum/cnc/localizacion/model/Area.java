package ar.com.cuyum.cnc.localizacion.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="areas") //@Table(name="maestro_areas_locales")
public class Area implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_area_local")
	private Long id;
	
	@Column(name="id_localidad")
	private Long idLocalidad;
		
	@Column(name="descr")
	private String nombre;
	
	public Area(){
		
	}
	
	public Area(Long idParam, String nombreParam){
		id=idParam;
		nombre=nombreParam;
	}
	
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

	public Long getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Long idLocalidad) {
		this.idLocalidad = idLocalidad;
	}
	
}