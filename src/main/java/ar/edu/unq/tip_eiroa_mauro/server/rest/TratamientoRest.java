package ar.edu.unq.tip_eiroa_mauro.server.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import ar.edu.unq.tip_eiroa_mauro.server.model.ResolucionMuestra;
import ar.edu.unq.tip_eiroa_mauro.server.model.Tratamiento;
import ar.edu.unq.tip_eiroa_mauro.server.services.TratamientoService;

@Service
@Path("/treatment")
public class TratamientoRest {
	private TratamientoService tratamientoService;

	public void setTratamientoService(TratamientoService tratamientoService) {
		this.tratamientoService = tratamientoService;
	}

	@GET
	@Path("/all")
	@Produces("application/json")
	public Response getAllTreatments() {
		List<Tratamiento> obs = tratamientoService.findAll();
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.entity(obs).build();
	}
	
	@POST
	 @Path("/saveTreatmentImage")
	 @Consumes("application/x-www-form-urlencoded")
	 public Response saveTreatmentImage(@FormParam("id") long id,
	 @FormParam("imageName") String imageName,
	 @FormParam("base64") String base64,
		@FormParam("nick") String nick,
		@FormParam("token") String token
	 ){
		 this.tratamientoService.saveTreatmentImage(id, imageName, base64);
		 return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	 }

	@POST
	@Path("/saveNoImages")
	@Consumes("application/x-www-form-urlencoded")
	public Response saveTreatmentNoImages(@FormParam("name") String name,
			@FormParam("idTargets") String idTargets,
			@FormParam("description") String desc,
			@FormParam("typeFrequency") String tFreq,
			@FormParam("frequency") String freq,
			@FormParam("typeQuantity") String tQuan,
			@FormParam("quantity") String quan,
			@FormParam("useExplanation") String useEx,
			@FormParam("extraLink1") String extra1,
			@FormParam("extraLink2") String extra2,
			@FormParam("extraLink3") String extra3,
			@FormParam("nick") String nick,
			@FormParam("token") String token
	){
		tratamientoService.saveNoImages(name,desc,idTargets,tFreq,freq,tQuan,quan,useEx,extra1,extra2,extra3);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/save3Images")
	@Consumes("application/x-www-form-urlencoded")
	public Response saveTreatment3Images(
			@FormParam("name") String name,
			@FormParam("description") String desc,
			@FormParam("idTargets") String idTargets,
			@FormParam("im1") String im1,
			@FormParam("base64_1") String base64_1,
			@FormParam("im2") String im2,
			@FormParam("base64_2") String base64_2,
			@FormParam("im3") String im3,
			@FormParam("base64_3") String base64_3,
			@FormParam("nick") String nick,
			@FormParam("token") String token)
	{

		tratamientoService.save3Images(name, 
				desc, idTargets,im1, base64_1, im2, base64_2, im3, base64_3
				);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Recibe en un formulario los datos de la especie, con x cantidad de imagenes
	 * y adicionalmente
	 * 
	 * @param quantity
	 * @param formParams
	 * @param nick
	 * @param token
	 * @return
	 */
	@POST
	@Path("/saveWithNImages")
	@Consumes("application/x-www-form-urlencoded")
	public Response saveTreatment(
			MultivaluedMap<String, String> formParams,
			@FormParam("nick") String nick,
			@FormParam("token") String token) {
        HashedMap images = null;
        if(Boolean.parseBoolean(formParams.getFirst("hasPictures"))){
        	images = new HashedMap();
        	int total = Integer.parseInt(formParams.getFirst("picturesQuantity"));
    		while(total >0){
    			if(!formParams.containsKey("base64_"+total)){
    				//http 417 Expectation failed
    				return Response.status(417)
    						.header("Access-Control-Allow-Origin", "*")
    						.header("There are missing images","*")
    						.build();
    		    }
    			images.put(formParams.getFirst("im"+total), formParams.getFirst("base64_"+total));
    			total--;
    		}
        }
        tratamientoService.saveWithNImages(
    			formParams.getFirst("name"),
    			formParams.getFirst("description"),
    			formParams.getFirst("idTargets"),
    			formParams.getFirst("typeFrequency"),
    			formParams.getFirst("frequency"),
    			formParams.getFirst("typeQuantity"),
    			formParams.getFirst("quantity"),
    			formParams.getFirst("useExplanation"),
    			formParams.getFirst("extraLink1"),
    			formParams.getFirst("extraLink2"),
    			formParams.getFirst("extraLink3"),
    			images
    			
    	);
        
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/saveWith1Image")
	@Consumes("application/x-www-form-urlencoded")
	public Response saveTreatment1Image(
			@FormParam("name") String name,
			@FormParam("description") String desc,
			@FormParam("idTargets") String idTargets,
			@FormParam("typeFrequency") String tFreq,
			@FormParam("frequency") String freq,
			@FormParam("typeQuantity") String tQuan,
			@FormParam("quantity") String quan,
			@FormParam("useExplanation") String useEx,
			@FormParam("im") String im,
			@FormParam("base64") String base64,
			@FormParam("extraLink1") String extra1,
			@FormParam("extraLink2") String extra2,
			@FormParam("extraLink3") String extra3,
			@FormParam("nick") String nick,
			@FormParam("token") String token
			)

	{

		tratamientoService.saveWith1Image(name, 
				desc, idTargets,im, base64,tFreq,freq,tQuan,quan,useEx,extra1,extra2,extra3);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}


	 
	@POST
	@Path("/editNoImages")
	@Consumes("application/x-www-form-urlencoded")
	public Response editTreatmentNoImages(
				@FormParam("id") long idTreatment,
				@FormParam("name") String name,
				@FormParam("idTargets") String idTargets,
				@FormParam("description") String desc,
				@FormParam("typeFrequency") String tFreq,
				@FormParam("frequency") String freq,
				@FormParam("typeQuantity") String tQuan,
				@FormParam("quantity") String quan,
				@FormParam("useExplanation") String useEx,
				@FormParam("extraLink1") String extra1,
				@FormParam("extraLink2") String extra2,
				@FormParam("extraLink3") String extra3,
				@FormParam("nick") String nick,
				@FormParam("token") String token
	){
		tratamientoService.editNoImages(idTreatment,name,desc,idTargets,tFreq,freq,tQuan,quan,useEx,extra1,extra2,extra3);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
		
	@POST
	@Path("/addSpecieTarget")
	@Consumes("application/x-www-form-urlencoded")
	public Response addSpecieTarget(
				@FormParam("id") long idTreatment,
				@FormParam("idTarget") long idTarget,
				@FormParam("nick") String nick,
				@FormParam("token") String token
	){
		tratamientoService.addSpecieTarget(idTreatment,idTarget);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/deleteSpecieTarget")
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteSpecieTarget(
				@FormParam("id") long idTreatment,
				@FormParam("idTarget") long idTarget,
				@FormParam("nick") String nick,
				@FormParam("token") String token
	){
		tratamientoService.deleteSpecieTarget(idTreatment,idTarget);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}	
	 

	@POST
	@Path("/findById")
	@Consumes("application/x-www-form-urlencoded")
	public Response findById(@FormParam("id") long id,
			@FormParam("nick") String nick,
			@FormParam("token") String token) {
		Tratamiento result = tratamientoService.findById(id);

		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.entity(result).build();
	}

	@POST
	@Path("/delete")
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteTreatment(@FormParam("treatmentId") long id,
			@FormParam("nick") String nick,
			@FormParam("token") String token) {
		tratamientoService.deleteById(id);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/requestTreatment")
	@Produces("application/json")
	public Response requestTreatment(
			@FormParam("imei") String imei,
			@FormParam("images_hash") String hash) {
		ResolucionMuestra result = tratamientoService.requestTreatment(imei,hash);
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.entity(result).build();
	}

}
