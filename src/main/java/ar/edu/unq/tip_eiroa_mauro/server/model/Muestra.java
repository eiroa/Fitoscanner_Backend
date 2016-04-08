package ar.edu.unq.tip_eiroa_mauro.server.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@XmlRootElement(name = "sample")
@Entity
@Table(name = "sample")
public class Muestra {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private String lon;

	@Column
	private String lat;

	@Column
	private Boolean resolved;
	
	@Column
	private String images_hash;

	@Column
	private String specie_name_resolved;
	
	@Column
	private String treatment_names_resolved;
	
	@Column	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime date_treatment_obtained;
	

	/**
	 * Luego de numerosas pruebas e intentos, tras chocarse contra referencias
	 * circulares, problemas de sesiones terminadas, y errores de mapeo, se
	 * decide pasar por alto el mapeo de la especie resuelta en la muestra,
	 * cuando se solicite una muestra resuelta, se generara una consulta SQL
	 * nativa adicional que construira la especie correspondiente y la asignará
	 * a la muestra.
	 * 
	 */
	@Transient
	private Especie specie;

	@Column
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime sample_date;

	@ManyToOne
	@JoinColumn(name = "id_user_origin")
	private Usuario user;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sample")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Imagen> images;

	public Long getId() {
		return id;
	}

	public List<Imagen> getImages() {
		return images;
	}

	public void setImages(List<Imagen> imagenes) {
		this.images = imagenes;
	}

	public DateTime getSample_date() {
		return sample_date;
	}

	public void setSample_date(DateTime sample_date) {
		this.sample_date = sample_date;
	}

	public Especie getSpecie() {
		return specie;
	}

	public void setSpecie(Especie specie) {
		this.specie = specie;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getImagesHash() {
		return images_hash;
	}

	public void setImages_hash(String imagesHash) {
		this.images_hash = imagesHash;
	}
	
	
	
	public String getSpecie_name_resolved() {
		return specie_name_resolved;
	}

	public void setSpecie_name_resolved(String specie_name_resolved) {
		this.specie_name_resolved = specie_name_resolved;
	}

	public String getTreatment_names_resolved() {
		return treatment_names_resolved;
	}

	public void setTreatment_names_resolved(String treatment_names_resolved) {
		this.treatment_names_resolved = treatment_names_resolved;
	}

	public DateTime getDate_treatment_obtained() {
		return date_treatment_obtained;
	}

	public void setDate_treatment_obtained(DateTime date_treatment_obtained) {
		this.date_treatment_obtained = date_treatment_obtained;
	}

	public Muestra() {
	}

	public Muestra(String name) {
		this.name = name;
	}
	
	

	public Muestra(String name, String lon, String lat, DateTime sample_date,
			Usuario user, String hash) {
		super();
		this.name = name;
		this.lon = lon;
		this.lat = lat;
		this.sample_date = sample_date;
		this.user = user;
		this.resolved = false;
		this.images_hash = hash;
	}

}
