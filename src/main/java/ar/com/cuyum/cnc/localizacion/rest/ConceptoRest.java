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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import ar.com.cuyum.cnc.localizacion.model.Acceso;
import ar.com.cuyum.cnc.localizacion.model.Concepto;
import ar.com.cuyum.cnc.localizacion.model.Nap;
import ar.com.cuyum.cnc.localizacion.model.Prestador;
import ar.com.cuyum.cnc.localizacion.service.AccesosService;
import ar.com.cuyum.cnc.localizacion.service.ConceptoService;
import ar.com.cuyum.cnc.localizacion.service.NapService;
import ar.com.cuyum.cnc.localizacion.service.PrestadoresService;
import ar.com.cuyum.cnc.localizacion.vo.ListObject;

/**
 * @author Jorge Morando
 *
 */
@Path("/listas")
@RequestScoped
public class ConceptoRest {

	private Logger log = Logger.getLogger(ConceptoRest.class);


	@Inject
	ConceptoService conceptoService;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
	public RestResponse listas(
			@QueryParam("tipo") String tipo, @QueryParam("term") String term, @QueryParam("limit") Integer limit, @QueryParam("page") Integer page) {		
		RestResponse response = new RestResponse();
		List<Concepto> lstConceptos;
		List<ListObject> results = new ArrayList<ListObject>();
		try {
			if(limit==null){
				
				lstConceptos = conceptoService.buscarConceptos(tipo);		
			}else{
				page=page==null?1:page;
				log.error("<<<<<<<<<<<<<Contenido parametro"+ tipo);
				lstConceptos = conceptoService.buscarConceptos(tipo,limit,page);	
				response.setTotal(conceptoService.contarConceptos(tipo));
			}
			if(lstConceptos!=null && lstConceptos.size()>0){
				for (Concepto concepto : lstConceptos) {
					results.add(new ListObject(concepto.getNombre(),concepto.getValor()));
				}
			}
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			log.error(e.getMessage());
		}		
		response.setResult(results);
		return response;
	}	
	
}
