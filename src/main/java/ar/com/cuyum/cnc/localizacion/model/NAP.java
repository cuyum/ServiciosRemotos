package ar.com.cuyum.cnc.localizacion.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NAPS")
public class NAP implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idNAP")
	private String id;
		
	@Column(name="NAP")
	private String nombre;
	
	public NAP(){
		
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
