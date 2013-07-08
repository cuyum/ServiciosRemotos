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
	public static int PRESTADOR_ACTIVO = 1; 
	public static int PRESTADOR_INACTIVO = 2;
	
	@Inject
	private EntityManager em;
	
	
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
	
	
}