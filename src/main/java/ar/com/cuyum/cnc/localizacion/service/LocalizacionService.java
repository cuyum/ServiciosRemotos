package ar.com.cuyum.cnc.localizacion.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ar.com.cuyum.cnc.localizacion.model.Acceso;
import ar.com.cuyum.cnc.localizacion.model.Area;
import ar.com.cuyum.cnc.localizacion.model.Localidad;
import ar.com.cuyum.cnc.localizacion.model.Partido;
import ar.com.cuyum.cnc.localizacion.model.Prestador;
import ar.com.cuyum.cnc.localizacion.model.Provincia;
import ar.com.cuyum.cnc.localizacion.model.Servicio;

@Stateless
@Named(value = "localizacionService")
public class LocalizacionService {
	public static int PRESTADOR_ACTIVO = 1; 
	public static int PRESTADOR_INACTIVO = 2;
	
	@Inject
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Provincia> getProvincias(){
		List<Provincia> prov = em.createQuery("select a from Provincia a order by a.nombre").getResultList();
		return prov;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Partido> getPartidosProvincia(Long idProvincia){
	List<Partido> lstPartidos = em.createQuery("select a from Partido a where a.idProvincia=:provincia order by a.nombre")
			.setParameter("provincia", idProvincia)
			.getResultList();
	return lstPartidos;
}

	
	@SuppressWarnings("unchecked")
	public List<Localidad> getLocalidadesPartido(Long idPartido){		
		List<Localidad> lstLocalidades = em.createQuery("select l from Localidad l where l.idPartido= :partido order by l.nombre")
				.setParameter("partido", idPartido)
				.getResultList();
		return lstLocalidades;
	}

	
	@SuppressWarnings("unchecked")
	public List<Area> getAreasLocalidad(Long idLocalidad){
		List<Area> prov = em.createQuery("select a from Area a where a.idLocalidad= :localidad order by a.nombre")
				.setParameter("localidad", idLocalidad)
				.getResultList();
		return prov;
	}
	
	@SuppressWarnings("unchecked")
	public List<Acceso> buscarAccesosPorIdServicio(String idServicio){
		List<Acceso> lstAccesos = null;
		List<Acceso> lstAccesosServicio = new ArrayList<Acceso>();
		List<Servicio> lstServicios = em.createQuery("select s from Servicio s where s.id= :servicio")
				.setParameter("servicio", idServicio)
				.getResultList();
		if (null != lstServicios && !lstServicios.isEmpty()){
			Servicio selectedService = lstServicios.get(0);
			lstAccesos = selectedService.getAccesos();
		}
		if (null != lstAccesos && !lstAccesos.isEmpty()){
			for (Acceso itemAcceso : lstAccesos) {
				lstAccesosServicio.add(itemAcceso);
			}			
		}		
		return lstAccesosServicio;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prestador> buscarPrestadores(){
		List<Prestador> lstPrestadores = em.createQuery("select p from Prestador p " +
				"where p.estado = :estado order by p.nombre")
				.setParameter("estado", PRESTADOR_ACTIVO)
				.getResultList();
		
		return lstPrestadores;
	}
}