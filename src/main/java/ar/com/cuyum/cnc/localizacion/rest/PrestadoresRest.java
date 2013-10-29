/**
 * 
 */
package ar.com.cuyum.cnc.localizacion.rest;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import ar.com.cuyum.cnc.localizacion.model.Prestador;
import ar.com.cuyum.cnc.localizacion.service.PrestadoresService;
import ar.com.cuyum.cnc.localizacion.vo.ListObject;

/**
 * @author Jorge Morando
 *
 */
@Path("/prestadores")
@RequestScoped
public class PrestadoresRest {

	private Logger log = Logger.getLogger(PrestadoresRest.class);
	
	@Inject
	PrestadoresService prestadoresService;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
	public RestResponse index(
			@QueryParam("term") String term, @QueryParam("limit") Integer limit, @QueryParam("page") Integer page) {		
		RestResponse response = new RestResponse();
		List<Prestador> lstPrestadores;
		List<ListObject> results = new ArrayList<ListObject>();
		try {
			if(limit==null){
				lstPrestadores = prestadoresService.buscarPrestadores(term);		
			}else{
				page=page==null?1:page;
				lstPrestadores = prestadoresService.buscarPrestadores(term,limit,page);	
				response.setTotal(prestadoresService.contarPrestadores(term));
			}
			if(lstPrestadores!=null && lstPrestadores.size()>0){
				for (Prestador prestador : lstPrestadores) {
					results.add(new ListObject(prestador.getId(),prestador.getNombre()));
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
