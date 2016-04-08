package ar.edu.unq.tip_eiroa_mauro.server.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import static org.imgscalr.Scalr.*;

import org.springframework.stereotype.Service;

import ar.edu.unq.tip_eiroa_mauro.server.model.Imagen;
import ar.edu.unq.tip_eiroa_mauro.server.services.ImagenService;

@Service
@Path("/image")
public class ImagenRest {
	private ImagenService imagenService;
	
	public void setImagenService(ImagenService imagenService) {
		this.imagenService = imagenService;
	}
	
	
	 @GET
	 @Path("/getImage/{id}")
	 @Produces("image/png")
	 public Response getImageRepresentation(@PathParam("id") long id) {
	     //get base64 data from service
	    byte[] valueDecoded=Base64.decodeBase64(this.imagenService.getImageBase64(id));
	    System.out.println("size of img is: "+ valueDecoded.length +"bytes");
	    return Response.ok(valueDecoded, "image/png").header("Access-Control-Allow-Origin", "*").build();
	  }
	 
	 @GET
	 @Path("/getImageTest")
	 @Produces("image/jpg")
	 public Response getUserImage() {
		 
		 Imagen target = this.imagenService.getImage(new Long("78"));
		 
	     final byte[] image = Base64.decodeBase64(target.getBase64());
	     // say your image is png?

	     return Response.ok(image, "image/jpg").header("Access-Control-Allow-Origin", "*").build();
	 }
	 
	 @GET
	 @Path("/getImageTest2")
	 @Produces("application/json")
	 public Response getUserImage2() {
		 Imagen target = this.imagenService.getImage(new Long("78"));
	     return Response.ok().entity(target).header("Access-Control-Allow-Origin", "*").build();
	 }
	 
	 
	 /**
	  * Genera en el momento una version reducida de una imagen en base. Pensada para guardarse en los 
	  * dispositivos moviles al momento de guardar una resolucion de muestra
	  * 
	  * La funcion no es eficiente, pero en principio solo sera invocada ante cada imagen
	  * correspondiente a un tratamiento.
	  * 
	  * TODO: Guardar al mismo tiempo que se guarda la imagen, una version thumbnail y levantarla directamente.
	  * @param id
	  * @return
	  */
	 @GET
	 @Path("/getImageThumbnail/{id}")
	 @Produces("image/png")
	 public Response getImageThumbnailRepresentation(@PathParam("id") long id) {
	     //get base64 data from service
	    byte[] valueDecoded=Base64.decodeBase64(this.imagenService.getImageBase64(id));
	    BufferedImage img;
	    byte[] newBytes;
	    
	    try {
	    	ByteArrayInputStream bais = new ByteArrayInputStream(valueDecoded);
		    img = ImageIO.read(bais);
		    
		    System.out.println("size of img is: "+ valueDecoded.length +"bytes");
		    resize(img, Method.SPEED, 125, OP_ANTIALIAS, OP_BRIGHTER);
		    
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	ImageIO.write(img, "jpg", baos);
		    newBytes = baos.toByteArray();
	    } catch (IOException e) {
	        return Response.serverError().header("Access-Control-Allow-Origin", "*").build();
	    }
	    return Response.ok(newBytes, "image/png").header("Access-Control-Allow-Origin", "*").build();
	  }
	 
	 @POST
	 @Path("/delete")
	 @Consumes("application/x-www-form-urlencoded")
	 public Response deleteImage(@FormParam("idImage") long id,
				@FormParam("nick") String nick,
				@FormParam("token") String token) {
		 this.imagenService.deleteImage(id);
		 return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	  }
	 
	 @POST
	 @Path("/edit")
	 @Consumes("application/x-www-form-urlencoded")
	 public Response editImage(
	 @FormParam("idImage") long idImage,
	 @FormParam("imageName") String imageName,
	 @FormParam("base64") String base64,
	 @FormParam("altersBase64") Boolean flagAlter,
		@FormParam("nick") String nick,
		@FormParam("token") String token
	 ){
		 this.imagenService.editImage(idImage, imageName, base64,flagAlter);
		 return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	 }
}
