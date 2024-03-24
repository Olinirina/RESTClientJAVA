package modelClient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name="etudiant")
public class Etudiant {
	@Id
	@Column(name = "numetudiant", length = 50, nullable = false)
	private String numEtudiant;
	private String nom;
	private double moyenne;
	public Etudiant() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Etudiant(String numEtudiant, String nom, double moyenne) {
		super();
		this.numEtudiant = numEtudiant;
		this.nom = nom;
		this.moyenne = moyenne;
	}
	public String getNumEtudiant() {
		return numEtudiant;
	}
	public void setNumEtudiant(String numEtudiant) {
		this.numEtudiant = numEtudiant;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getMoyenne() {
		return moyenne;
	}
	public void setMoyenne(double moyenne) {
		this.moyenne = moyenne;
	}
	@Override
	public String toString() {
		return "Etudiant [numEtudiant=" + numEtudiant + ", nom=" + nom + ", moyenne=" + moyenne + "]";
	}
	

}
