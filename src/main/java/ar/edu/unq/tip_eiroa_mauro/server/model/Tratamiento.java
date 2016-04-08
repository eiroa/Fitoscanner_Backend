package ar.edu.unq.tip_eiroa_mauro.server.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@XmlRootElement(name = "treatment")
@Entity
@Table(name = "treatment")
public class Tratamiento {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;
	
	@Column
	private String quantity;
	
	@Column
	private String typeQuantity;
	
	@Column
	private String typeFrequency;
	
	@Column
	private String frequency;
	
	@Column
	private String useExplanation;
	
	@Column
	private String description;
	
	@Column
	private String extraLink1;
	
	@Column
	private String extraLink2;
	
	@Column
	private String extraLink3;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval=true)
	@JoinTable(name="treatmentximage",joinColumns={@JoinColumn(name="id_treatment", referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="id_image", referencedColumnName="id")})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Imagen> images;
	
	@ManyToMany
	@JoinTable(name="treatmentxspecie",
	joinColumns={@JoinColumn(name="id_treatment", referencedColumnName="id")},
	inverseJoinColumns={@JoinColumn(name="id_specie", referencedColumnName="id")})
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Especie> targets;
	
	
	public Set<Especie> getTargets() {
		return targets;
	}

	public void setTargets(Set<Especie> objetivos) {
		this.targets = objetivos;
	}

	public List<Imagen> getImages() {
		return images;
	}

	public void setImages(List<Imagen> imagenes) {
		this.images = imagenes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTypeQuantity() {
		return typeQuantity;
	}

	public void setTypeQuantity(String typeQuantity) {
		this.typeQuantity = typeQuantity;
	}

	public String getTypeFrequency() {
		return typeFrequency;
	}

	public void setTypeFrequency(String typeFrequency) {
		this.typeFrequency = typeFrequency;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getUseExplanation() {
		return useExplanation;
	}

	public void setUseExplanation(String useExplanation) {
		this.useExplanation = useExplanation;
	}
	
	public String getExtraLink1() {
		return extraLink1;
	}

	public void setExtraLink1(String extraLink1) {
		this.extraLink1 = extraLink1;
	}

	public String getExtraLink2() {
		return extraLink2;
	}

	public void setExtraLink2(String extraLink2) {
		this.extraLink2 = extraLink2;
	}

	public String getExtraLink3() {
		return extraLink3;
	}

	public void setExtraLink3(String extraLink3) {
		this.extraLink3 = extraLink3;
	}

	public Long getId() {
		return id;
	}

	public Tratamiento(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public Tratamiento(String name, String description, Set<Especie> objetivos) {
		super();
		this.name = name;
		this.description = description;
		this.targets = objetivos;
	}

	public Tratamiento(String name, String description, List<Imagen> imagenes,
			Set<Especie> objetivos) {
		super();
		this.name = name;
		this.description = description;
		this.images = imagenes;
		this.targets = objetivos;
	}
	

	public Tratamiento(String name, String quantity, String typeQuantity,
			String typeFrequency, String frequency, String useExplanation,
			String description, List<Imagen> images, Set<Especie> targets) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.typeQuantity = typeQuantity;
		this.typeFrequency = typeFrequency;
		this.frequency = frequency;
		this.useExplanation = useExplanation;
		this.description = description;
		this.images = images;
		this.targets = targets;
	}
	
	

	public Tratamiento(String name, String quantity, String typeQuantity,
			String typeFrequency, String frequency, String useExplanation,
			String description, String extraLink1, String extraLink2,
			String extraLink3, List<Imagen> images, Set<Especie> targets) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.typeQuantity = typeQuantity;
		this.typeFrequency = typeFrequency;
		this.frequency = frequency;
		this.useExplanation = useExplanation;
		this.description = description;
		this.extraLink1 = extraLink1;
		this.extraLink2 = extraLink2;
		this.extraLink3 = extraLink3;
		this.images = images;
		this.targets = targets;
	}

	public Tratamiento() {
	}
	
	
}
