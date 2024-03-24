/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionEtudiantREST.net;

import static GestionEtudiantREST.net.EtudiantClient.getWebTaget;
import static GestionEtudiantREST.net.EtudiantClient.testList2;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelClient.Etudiant;
import org.glassfish.jersey.client.ClientConfig;
import modelClient.Observation;
import modelClient.Obs;

/**
 *
 * @author Olinirina
 */
public class ObservationClient {
    private static String baseUri="http://localhost:8081/EtudiantREST/rest/observation";
    public static void main(String[] args) {
		//testList2();
		//testAdd("ET004");
		//Etudiant e= new Etudiant("e4","de",19.00);
		//testAdd(e);
		//testUpdate(e);
		//testDelete("ggg");
               //testGet(1L);
               //testAdd("1e");
		//testList2();
		
	}
   /* static void testList() {
		WebTarget target= getWebTaget();
		String reponse= target.request()
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		System.out.println(reponse);
		
	}*/
   
	static void testList2() {
    WebTarget target = getWebTaget();
    String reponse = target.request()
            .accept(MediaType.APPLICATION_JSON)
            .get(String.class);
    ObjectMapper mapper = new ObjectMapper();
    try {
        // Convert JSON string to array of Observation objects
        Observation[] observations = mapper.readValue(reponse, Observation[].class);

        // Iterate over each Observation object
        for (Observation observation : observations) {
            Long id = observation.getIdOb();
            Etudiant etudiant = observation.getEtudiant();
            String numEtudiant = etudiant.getNumEtudiant();
            String nom = etudiant.getNom();
            double moyenne = etudiant.getMoyenne();
            Obs obs = observation.getObs();

            // Print observation details along with student information
            System.out.println("Id : " + id);
            System.out.println("Numéro étudiant : " + numEtudiant);
            System.out.println("Nom : " + nom);
            System.out.println("Moyenne : " + moyenne);
            System.out.println("Obs : " + obs);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
	static void testAdd(Etudiant etudiant) {
		WebTarget target= getWebTaget();
		Response response=target.
                        request()
                        .post(Entity.entity(etudiant, MediaType.APPLICATION_JSON),
				Response.class);
		System.out.println("Ajout reussi");
	}
	static void testDelete(Long id) {
		WebTarget target= getWebTaget();
		Response response=target.path(String.valueOf(id)).request()
				.delete(Response.class);
	}
	static void testGet(Long id) {
	    WebTarget target = getWebTaget();
	    Response response = target.path(String.valueOf(id))
	            .request()
	            .accept(MediaType.APPLICATION_JSON)
	            .get();

	    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
	        Observation[] observations = response.readEntity(Observation[].class);
	        for (Observation ob : observations) {
	            System.out.println(ob);
	        }
	    } else {
	        System.out.println("Erreur lors de la récupération de l'étudiant avec le code: " + id);
	    }

	    response.close();
	}

	static WebTarget getWebTaget() {
		ClientConfig config= new ClientConfig();
		Client client=ClientBuilder.newClient(config);
		return client.target(baseUri);
	}
        //VRAI CODE
        
         public List<Observation> testGetObservation(Long id) {
    WebTarget target = getWebTaget();
    Response response = target.path(String.valueOf(id))
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get();
    
    // Vérifiez si la réponse est OK
    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        List<Observation> observations = response.readEntity(new GenericType<List<Observation>>() {});
        return observations;
    } else {
        // Gérer les erreurs de requête ici
        return Collections.emptyList(); // Ou lancez une exception appropriée
    }
}
         
         public List<Observation> listerObservations() {
            List<Observation> observations = new ArrayList<Observation>();
            WebTarget target = getWebTaget();
            String reponse = target.request()
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            ObjectMapper mapper = new ObjectMapper();
            try {
                // Convert JSON string to array of Observation objects
                Observation[] observationArray = mapper.readValue(reponse, Observation[].class);

                // Add each observation to the list
                observations.addAll(Arrays.asList(observationArray));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return observations; // Return the list of observations
}
         public void AddObs(Etudiant etudiant) {
		WebTarget target= getWebTaget();
		Response response=target.request().post(
				Entity.entity(etudiant, MediaType.APPLICATION_JSON),
				Response.class);
	}
          public void DeleteOb(Long id) {
		WebTarget target= getWebTaget();
		Response response=target.path(String.valueOf(id)).request()
				.delete(Response.class);
	}


        
    
}
