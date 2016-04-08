package ar.edu.unq.tip_eiroa_mauro.server.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.map.HashedMap;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ar.edu.unq.tip_eiroa_mauro.server.helpers.DateHelper;
import ar.edu.unq.tip_eiroa_mauro.server.model.Especie;
import ar.edu.unq.tip_eiroa_mauro.server.model.Muestra;
import ar.edu.unq.tip_eiroa_mauro.server.model.ResolucionMuestra;
import ar.edu.unq.tip_eiroa_mauro.server.services.MuestraService;


@Service
@Path("/sample")
public class MuestraRest {
	private MuestraService muestraService;

	public void setMuestraService(MuestraService muestraService) {
		this.muestraService = muestraService;
	}
	
	@POST
	@Path("/resolve")
	@Consumes("application/x-www-form-urlencoded")
	public Response resolveSample(
			@FormParam("sampleId") long sampleId,
			@FormParam("specieResolvedId") long specieResolved,
			@FormParam("nick") String nick,
			@FormParam("token") String token
			) {
		muestraService.resolveSample(sampleId,specieResolved);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/delete")
    @Consumes("application/x-www-form-urlencoded")
	public Response deleteSample(
			@FormParam("sampleId") long sampleId,
			@FormParam("nick") String nick,
			@FormParam("token") String token
			){
		muestraService.deleteSample(sampleId);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	
	@POST
	@Path("/findSamples")
	@Produces("application/json")
	public Response findSamplesOfUser(
			@FormParam("userId") long userId,
			@FormParam("getResolved") Boolean getResolved,
			@FormParam("nick") String nick,
			@FormParam("token") String token
			) {
		List<Muestra>muestras = muestraService.findSamplesOfUserSQLStyle(userId,getResolved);
		return Response.ok().header("Access-Control-Allow-Origin", "*").entity(muestras).build();
	}
	
	@POST
	@Path("/latestUnresolved")
	@Produces("application/json")
	public Response findLatestUnresolvedSamplesOfUser(
			@FormParam("limit") Integer limit,
			@FormParam("nick") String nick,
			@FormParam("token") String token
			) {
		Integer limitToSet= limit;
		//Por motivo de performance, como maximo devolvemos 100
		if(limit >100){
			limitToSet = 100;
		}
		List<Muestra>muestras = muestraService.findLatestUnresolvedSamples(limitToSet);
		return Response.ok().header("Access-Control-Allow-Origin", "*").entity(muestras).build();
	}
	

	@POST
	@Path("/save/{quant}")
	@Consumes("application/x-www-form-urlencoded")
	public Response saveSample(@PathParam("quant")Integer quantity  ,
			MultivaluedMap<String, String> FormValues,
			@FormParam("date") String date,
			@FormParam("isAnon")Boolean isAnon,
			@FormParam("imei")String imei,
			@FormParam("lat")String lat,
			@FormParam("lon")String lon,
			@FormParam("images_hash")String hash) {
		DateTime parsedDate = DateHelper.formatStringDate(date);
		System.out.println("Se intentara guardar muestra de "+quantity+ " imagenes");
		
		HashedMap imagenes = new HashedMap();
		int total = quantity;
		while(total >0){
			if(!FormValues.containsKey("sample_image_"+total+"_base64")){
				//http 417 Expectation failed
				return Response.status(417)
						.header("Access-Control-Allow-Origin", "*")
						.header("There are sample images missing","*")
						.build();
			}
			
			//Armar mapa titulo =>base64
			imagenes.put(FormValues.getFirst("sample_image_"+total+"_title"), FormValues.getFirst("sample_image_"+total+"_base64"));
			total--;
		}
		System.out.println("Imagenes obtenidas: "+ imagenes);
		
		muestraService.saveSample(parsedDate,isAnon,imei,lat,lon,
				FormValues.getFirst("sample_name"), imagenes,hash);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	
}
