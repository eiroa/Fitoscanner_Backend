package ar.edu.unq.tip_eiroa_mauro.server.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

@XmlRootElement(name = "authentication")
public class Authentication {
	
	@JsonProperty
	private Boolean validUser;
	
	@JsonProperty
	private String token;
	
	@JsonProperty
	private String nick;
	
	@JsonProperty
	private String authTime;

	public Boolean getValidUser() {
		return validUser;
	}

	public void setValidUser(Boolean validUser) {
		this.validUser = validUser;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAuthTime() {
		return authTime;
	}

	public void setAuthTime(String authTime) {
		this.authTime = authTime;
	}

	public Authentication(Boolean validUser, String token, String nick) {
		super();
		this.validUser = validUser;
		this.token = token;
		this.nick = nick;
		this.authTime = DateTime.now().toString();
	}

	public Authentication() {
		this.authTime = DateTime.now().toString();
	}
	
	
	
	
	
}
