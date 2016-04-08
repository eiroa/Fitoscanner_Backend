package ar.edu.unq.tip_eiroa_mauro.server.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@XmlRootElement(name = "specie")
@Entity
@Table(name = "specie")
public class Especie {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private Boolean isPlague;
	
	@Column
	private Boolean isCrop;

	@Column
	private String name;

	@Column
	private String scientific_name;
	
	@Column
	private String description;
	
	@Column
	private Boolean active;
	
	@Transient
	@JsonProperty
	private List<String> treatmentNames;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,
			orphanRemoval=true)
	@JoinTable(name="specieximage",
	joinColumns={@JoinColumn(name="id_specie", referencedColumnName="id")},
	inverseJoinColumns={@JoinColumn(name="id_image", referencedColumnName="id")})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Imagen> images;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		    name="treatmentxspecie",
		    joinColumns=
		        @JoinColumn(name="id_specie", referencedColumnName="id"),
		    inverseJoinColumns=
		        @JoinColumn(name="id_treatment", referencedColumnName="id")
		    )
	private Set<Tratamiento> treatments;
	
	public List<Imagen> getImages() {
		return images;
	}

	public void setImages(List<Imagen> imagenes) {
		this.images = imagenes;
	}

	public Boolean getIsPlague() {
		return isPlague;
	}

	public void setIsPlague(Boolean isPlague) {
		this.isPlague = isPlague;
	}

	public Boolean getIsCrop() {
		return isCrop;
	}

	public void setIsCrop(Boolean isCrop) {
		this.isCrop = isCrop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScientific_name() {
		return scientific_name;
	}

	public void setScientific_name(String scientific_name) {
		this.scientific_name = scientific_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	public List<String> getTreatmentNames() {
		return treatmentNames;
	}

	public void setTreatmentNames(List<String> treatmentNames) {
		this.treatmentNames = treatmentNames;
	}

	//Para evitar problemas de circularidad en las relaciones, se omiten los tratamientos para el JSON
	@XmlTransient
	public Set<Tratamiento> getTreatments() {
		return treatments;
	}

	public void setTreatments(Set<Tratamiento> tratamientos) {
		this.treatments = tratamientos;
	}

	@Override
	public String toString() {
		return "Especie [isPlague=" + isPlague + ", isCrop=" + isCrop
				+ ", name=" + name + ", scientific_name=" + scientific_name
				+ ", description=" + description + "]";
	}
	
	public Especie(){
	}
	
	public Especie(String name,Boolean isCrop,
			Boolean isPlague, String scientificName, String description, Boolean active){
		this.name = name;
		this.isCrop = isCrop;
		this.isPlague = isPlague;
		this.scientific_name = scientificName;
		this.description = description;
		this.active = active;
	}
	
	public Especie(String name,Boolean isCrop,
			Boolean isPlague, String scientificName, String description,List<Imagen> images,Boolean active){
		this.name = name;
		this.isCrop = isCrop;
		this.isPlague = isPlague;
		this.scientific_name = scientificName;
		this.description = description;
		this.images = images;
		this.active = active;
	}
	

}
