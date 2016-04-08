package ar.edu.unq.tip_eiroa_mauro.server.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

@XmlRootElement(name = "errorEntity")
public class ErrorEntity {
	
	
	@JsonProperty
	private String errorMessage;
	
	@JsonProperty
	private String errorTime;
	
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(String errorTime) {
		this.errorTime = errorTime;
	}

	public ErrorEntity(String message) {
		super();
		this.errorMessage = message;
		this.errorTime = DateTime.now().toString();
	}

	public ErrorEntity() {
		this.errorTime = DateTime.now().toString();
	}
	
	
	
	
	
}
