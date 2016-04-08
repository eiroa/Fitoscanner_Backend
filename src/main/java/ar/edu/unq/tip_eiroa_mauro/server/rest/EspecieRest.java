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

import ar.edu.unq.tip_eiroa_mauro.server.model.Especie;
import ar.edu.unq.tip_eiroa_mauro.server.services.EspecieService;

@Service
@Path("/specie")
public class EspecieRest {
	private EspecieService especieService;

	public void setEspecieService(EspecieService especieService) {
		this.especieService = especieService;
	}

	@GET
	@Path("/all")
	@Produces("application/json")
	public Response getAllSpecies() {
		List<Especie> obs = especieService.findAll();
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.entity(obs).build();
	}
	
	@GET
	@Path("/crops")
	@Produces("application/json")
	public Response getAllCrops() {
		List<Especie> obs = especieService.findCrops();
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.entity(obs).build();
	}
	
	
	
	@GET
	@Path("/plagues")
	@Produces("application/json")
	public Response getAllPlagues() {
		List<Especie> obs = especieService.findPlagues();
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.entity(obs).build();
	}
	
	
	
	@POST
	@Path("/saveNoImages")
	@Consumes("application/x-www-form-urlencoded")
	public Response saveSpecieNoImages(@FormParam("name") String name,
			@FormParam("isCrop") Boolean isCrop,
			@FormParam("isPlague") Boolean isPlague,
			@FormParam("scientificName") String scientificName,
			@FormParam("description") String desc,
			@FormParam("nick") String nick,
			@FormParam("token") String token

	) {

		especieService.saveNoImages(name, isCrop, isPlague, scientificName,
				desc);
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
	public Response saveSpecie(
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
        especieService.saveWithNImages(
    			formParams.getFirst("name"),
    			Boolean.parseBoolean(formParams.getFirst("isCrop")),
    			Boolean.parseBoolean(formParams.getFirst("isPlague")),
    			formParams.getFirst("scientificName"),
    			formParams.getFirst("description"),
    			images
    	);
        
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	

	 @POST
	 @Path("/disableSpecie")
	 @Consumes("application/x-www-form-urlencoded")
	 public Response disableSpecie(
	    @FormParam("id") long id,
		@FormParam("nick") String nick,
		@FormParam("token") String token
	 ){
		 this.especieService.disableSpecie(id);
		 return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	 }
	 
	 @POST
	 @Path("/activateSpecie")
	 @Consumes("application/x-www-form-urlencoded")
	 public Response activateSpecie(
	    @FormParam("id") long id,
		@FormParam("nick") String nick,
		@FormParam("token") String token
	 ){
		 this.especieService.activateSpecie(id);
		 return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	 }
	 
	 
	 @POST
	 @Path("/editNoImages")
	 @Consumes("application/x-www-form-urlencoded")
	 public Response editNoImages(@FormParam("id") long id,
	 @FormParam("name") String name,
	 @FormParam("isCrop") Boolean isCrop,
	 @FormParam("isPlague") Boolean isPlague,
	 @FormParam("scientificName") String scientificName,
	 @FormParam("description") String desc,
		@FormParam("nick") String nick,
		@FormParam("token") String token
	 ){
		 this.especieService.editSpecieNoImages(id, isCrop, isPlague, name, scientificName,desc);
		 return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	 }
	 
	 @POST
	 @Path("/editSpecieImage")
	 @Consumes("application/x-www-form-urlencoded")
	 public Response editSpecieImage(@FormParam("id") long id,
	 @FormParam("idImage") long idImage,
	 @FormParam("imageName") String imageName,
	 @FormParam("base64") String base64,
		@FormParam("nick") String nick,
		@FormParam("token") String token
	 ){
		 this.especieService.editSpecieImage(id, idImage, imageName, base64);
		 return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	 }
	 
	 @POST
	 @Path("/saveSpecieImage")
	 @Consumes("application/x-www-form-urlencoded")
	 public Response editSpecieImage(@FormParam("id") long id,
	 @FormParam("imageName") String imageName,
	 @FormParam("base64") String base64,
		@FormParam("nick") String nick,
		@FormParam("token") String token
	 ){
		 this.especieService.updateSpecieImage(id, imageName, base64);
		 return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	 }

	@POST
	@Path("/findById")
	@Consumes("application/x-www-form-urlencoded")
	public Response getSpecie(@FormParam("id") long id,
			@FormParam("nick") String nick,
			@FormParam("token") String token) {
		Especie result = especieService.findById(id);

		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.entity(result).build();
	}

	@POST
	@Path("/delete")
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteSpecie(@FormParam("specieId") long id,
			@FormParam("nick") String nick,
			@FormParam("token") String token) {
		especieService.deleteById(id);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

}
