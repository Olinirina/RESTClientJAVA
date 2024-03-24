/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelClient;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="observation")
public class Observation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOb;
	@ManyToOne
    @JoinColumn(name = "numEtudiant", referencedColumnName = "numetudiant")
	@JsonProperty("numEtudiant")
	private Etudiant etudiant;
	private Obs obs;
	public Observation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Observation(Etudiant etudiant, Obs obs) {
		super();
		this.etudiant = etudiant;
		this.obs = obs;
	}
	public Long getIdOb() {
		return idOb;
	}
	public void setIdOb(Long idOb) {
		this.idOb = idOb;
	}
	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	public Obs getObs() {
		return obs;
	}
	public void setObs(Obs obs) {
		this.obs = obs;
	}
	@Override
	public String toString() {
		return "Observation [idOb=" + idOb + ", etudiant=" + etudiant + ", obs=" + obs + "]";
	}
	
	

}

