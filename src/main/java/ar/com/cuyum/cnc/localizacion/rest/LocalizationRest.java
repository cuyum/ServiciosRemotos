/**
 * 
 */
package ar.com.cuyum.cnc.localizacion.rest;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import ar.com.cuyum.cnc.localizacion.model.Acceso;
import ar.com.cuyum.cnc.localizacion.model.Area;
import ar.com.cuyum.cnc.localizacion.model.Localidad;
import ar.com.cuyum.cnc.localizacion.model.Partido;
import ar.com.cuyum.cnc.localizacion.model.Prestador;
import ar.com.cuyum.cnc.localizacion.model.Provincia;
import ar.com.cuyum.cnc.localizacion.service.LocalizacionService;

/**
 * @author Jorge Morando
 *
 */
@Path("/localizaciones")
@RequestScoped
public class LocalizationRest {

	private Logger log = Logger.getLogger(LocalizationRest.class);

	@Inject
	LocalizacionService localizacionService;
	
	@POST
	@Path("/provincias")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
	public RestResponse provincias() {
		/*devolver un json valido como string*/
		RestResponse response = new RestResponse();
		List<Provincia> listaProv = new ArrayList<Provincia>();
		
		try {
			listaProv = localizacionService.getProvincias();
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			log.error(e.getMessage());
		}		
		response.setResult(listaProv);
		return response;
	}
	
	@POST
	@Path("/partidos")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
	public RestResponse partidos(@FormParam("fkey") String fkey) {
		/*devolver un json valido como string*/
		RestResponse response = new RestResponse();
		List<Partido> listaPart = new ArrayList<Partido>();
		
		try {
			if(fkey==null || fkey.isEmpty()){
				response.setSuccess(false);
				response.setMsg("fkey invalid");
				return response;
			}
			listaPart = localizacionService.getPartidosProvincia(Long.valueOf(fkey));	
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			log.error(e.getMessage());
		}
		response.setResult(listaPart);				
		return response;
	}
	
	@POST
	@Path("/localidades")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")	
	public RestResponse localidades(@FormParam("fkey") String fkey) {
		/*devolver un json valido como string*/
		RestResponse response = new RestResponse();
		List<Localidad> listaLocal = new ArrayList<Localidad>();
		
		try {
			if(fkey==null || fkey.isEmpty()){
				response.setSuccess(false);
				response.setMsg("fkey invalid");
				return response;
			}
			listaLocal = localizacionService.getLocalidadesPartido(Long.valueOf(fkey));
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			log.error(e.getMessage());
		}
		response.setResult(listaLocal);		
		return response;
	}
	
	@POST
	@Path("/areas")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
	public RestResponse areas(@FormParam("fkey") String fkey) {
		/*devolver un json valido como string*/
		RestResponse response = new RestResponse();
		List<Area> listaArea = new ArrayList<Area>();
		try {
			if(fkey==null || fkey.isEmpty()){
				response.setSuccess(false);
				response.setMsg("fkey invalid");
				return response;
			}
			listaArea = localizacionService.getAreasLocalidad(Long.valueOf(fkey));
			response.setSuccess(true);
			
		} catch (Exception e) {
			response.setSuccess(false);
			log.error(e.getMessage());
		}	
		response.setResult(listaArea);
		return response;
	}
	
	@POST
	@Path("/prestadores")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
	public RestResponse prestadores() {		
		RestResponse response = new RestResponse();
		List<Prestador> lstPrestadores = new ArrayList<Prestador>();
		
		try {
			lstPrestadores = localizacionService.buscarPrestadores();		
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			log.error(e.getMessage());
		}		
		response.setResult(lstPrestadores);
		return response;
	}
	
	@POST
	@Path("/accesos")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")	
	public RestResponse accesos(@FormParam("fkey") String fkey) {		
		RestResponse response = new RestResponse();
		List<Acceso> lstAccesos = new ArrayList<Acceso>();
		
		try {
			if(fkey==null || fkey.isEmpty()){
				response.setSuccess(false);
				response.setMsg("fkey invalid");
				return response;
			}
			
			lstAccesos = localizacionService.buscarAccesosPorIdServicio(fkey);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			log.error(e.getMessage());
		}
		response.setResult(lstAccesos);
		return response;
	}
	
}
