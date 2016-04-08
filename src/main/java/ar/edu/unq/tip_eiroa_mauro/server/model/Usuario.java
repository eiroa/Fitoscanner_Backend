package ar.edu.unq.tip_eiroa_mauro.server.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "user")
@Entity
@Table(name = "user")
public class Usuario {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String imei;
	
	@Column
	private String name;

	@Column
	private String surname;

	@Column
	private String nick;

	@Column
	private String code;

	@Column
	private Boolean anonymous;
	
	@Column
	private Boolean admin;
	
	@Column
	private String password;
	
	/*
	 * Donde user es el nombre del valor de la var de instancia en la entidad mapeada
	 */
	@OneToMany(mappedBy="user",cascade= CascadeType.ALL)
	private List<Muestra> samples;

	public Long getId() {
		return this.id;
	}
	
	public String getImei(){
		return this.imei;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(Boolean anonymous) {
		this.anonymous = anonymous;
	}
	
	
    @XmlTransient
	public List<Muestra> getSamples() {
		return samples;
	}

	public void setSamples(List<Muestra> samples) {
		this.samples = samples;
	}

	public Usuario() {
	}

	public Usuario(String name) {
		this.name = name;
		this.anonymous = false;
	}

	public Usuario(Boolean isAnon,String imei) {
		this.anonymous = isAnon;
		this.imei = imei;
		this.admin = false;
	}
	
	public Usuario(String name,String surname, String code,String nick, String imei,String passHash ,Boolean admin) {
		this.name = name;
		this.surname= surname;
		this.code = code;
		this.nick = nick;
		this.anonymous = false;
		this.imei = imei;
		this.admin = admin;
		this.password = passHash;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", name=" + name + "]";
	}

	@XmlTransient
	public String getPass() {
		return this.password;
	}

}
