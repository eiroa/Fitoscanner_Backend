package ar.edu.unq.tip_eiroa_mauro.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "image")
@Entity
@Table(name = "image")
public class Imagen {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;
	
	@Column
	private String base64;
	
	@ManyToOne
	@JoinColumn(name = "id_sample")
	private Muestra sample;
	
	
	//mapeo que surge ante la necesidad de joiner dos tablas, ya que existe tabla intermedia
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(
		    name="specieximage",
		    joinColumns=
		        @JoinColumn(name="id_image", referencedColumnName="id"),
		    inverseJoinColumns=
		        @JoinColumn(name="id_specie", referencedColumnName="id")
		    )
	private Especie specie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(
		    name="treatmentximage",
		    joinColumns=
		        @JoinColumn(name="id_image", referencedColumnName="id"),
		    inverseJoinColumns=
		        @JoinColumn(name="id_treatment", referencedColumnName="id")
		    )
	Tratamiento tratamiento;

	public Imagen(String name, String base64, Muestra sample) {
		super();
		this.name = name;
		this.base64 = base64;
		this.sample = sample;
	}
	
	public Imagen(String name, String base64) {
		super();
		this.name = name;
		this.base64 = base64;
	}
	
	public Imagen(String name, String base64,Especie especie) {
		super();
		this.name = name;
		this.base64 = base64;
		this.specie = especie;
	}
	
	
	public Long getId() {
		return id;
	}
	
	


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@XmlTransient
	public String getBase64() {
		return base64;
	}



	public void setBase64(String base64) {
		this.base64 = base64;
	}

	@XmlTransient
	public Muestra getSample() {
		return sample;
	}


	public void setSample(Muestra sample) {
		this.sample = sample;
	}
	
	@XmlTransient
	public Especie getSpecie() {
		return specie;
	}


	public void setSpecie(Especie specie) {
		this.specie = specie;
	}
	
	

	@XmlTransient
	public Tratamiento getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}

	public Imagen(){}
	
	
}
