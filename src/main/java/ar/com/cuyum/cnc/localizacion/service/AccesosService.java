package ar.com.cuyum.cnc.localizacion.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ar.com.cuyum.cnc.localizacion.model.Acceso;
import ar.com.cuyum.cnc.localizacion.model.Servicio;

@Stateless
@Named(value = "accesosService")
public class AccesosService {
	
	@Inject
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Acceso> buscarAccesosPorIdServicio(String idServicio,String term){
		List<Acceso> lstAccesos = null;
		List<Acceso> lstAccesosServicio = new ArrayList<Acceso>();
		StringBuilder query = new StringBuilder();
		query.append("select s from Servicio s ");
		query.append("where s.id=:servicio ");
		List<Servicio> lstServicios = em.createQuery(query.toString())
				.setParameter("servicio", idServicio)
				.getResultList();
		
		if (null != lstServicios && !lstServicios.isEmpty()){
			Servicio selectedService = lstServicios.get(0);
			lstAccesos = selectedService.getAccesos();
		}
		if (null != lstAccesos && !lstAccesos.isEmpty()){
			for (Acceso itemAcceso : lstAccesos) {
				if(null != term){
					if (itemAcceso.getNombre().toLowerCase().contains(term.toLowerCase())) {
						lstAccesosServicio.add(itemAcceso);
					}
				} else {
					lstAccesosServicio.add(itemAcceso);
				}
			}			
		}		
		return lstAccesosServicio;
	}

}