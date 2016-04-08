package ar.edu.unq.tip_eiroa_mauro.server.model;

import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "resolution")
public class ResolucionMuestra {

	@JsonProperty
	private Usuario usuario;

	@JsonProperty
	private Boolean valid;

	@JsonProperty
	private String message;

	@JsonProperty
	private Long idSample;

	@JsonProperty
	private String sampleName;

	@JsonProperty("resolved")
	private Boolean resolved;

	@JsonProperty("treatments")
	private List<Tratamiento> tratamientos;
	
	@JsonProperty
	private Especie specie;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

	public Especie getSpecie() {
		return specie;
	}

	public void setSpecie(Especie specie) {
		this.specie = specie;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	public List<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}

	public Long getIdSample() {
		return idSample;
	}

	public void setIdSample(Long idSample) {
		this.idSample = idSample;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public ResolucionMuestra() {
	};

	public ResolucionMuestra(Usuario usuario, Boolean valid, String message,
			Boolean resolved, List<Tratamiento> tratamientos) {
		super();
		this.usuario = usuario;
		this.valid = valid;
		this.message = message;
		this.resolved = resolved;
		this.tratamientos = tratamientos;
	}

}
