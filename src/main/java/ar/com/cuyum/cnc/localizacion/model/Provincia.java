package ar.com.cuyum.cnc.localizacion.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="provincias") //@Table(name="maestro_provincias")
public class Provincia implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_prov")
	private Long id;
		
	@Column(name="descr") //@Column(name="descripcion")
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