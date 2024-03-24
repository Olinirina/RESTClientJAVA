package GestionEtudiantREST.net;

import com.fasterxml.jackson.core.type.TypeReference;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.core.GenericType;
import modelClient.Etudiant;
import modelClient.Observation;

public class EtudiantClient {
	private static String baseUri="http://localhost:8081/EtudiantREST/rest/etudiant";

	public static void main(String[] args) {
		//testList2();
		//testGet("e1");
		//Etudiant e= new Etudiant("e5","uuu",1.00);
		//testAdd(e);
		//testUpdate(e);
		//testDelete("e5");
		//testList2();
                //testListCode();
                //MaxMoy();
                        
		
	}
	static void testList2() {
		WebTarget target= getWebTaget();
		String reponse= target.request()
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		ObjectMapper mapper = new ObjectMapper();
        try {
            // Convert JSON string to JsonNode
            JsonNode jsonNode = mapper.readTree(reponse);

            // Assuming the JSON is an array, iterate over its elements
            if (jsonNode.isArray()) {
                for (JsonNode node : jsonNode) {
                    // Access each element and do whatever you need
                	String numEtudiant = node.get("numEtudiant").asText();
                	String nom = node.get("nom").asText();
                	double moyenne = node.get("moyenne").asDouble();
                	System.out.println("Numéro étudiant : " + numEtudiant);
                	System.out.println("Nom : " + nom);
                	System.out.println("Moyenne : " + moyenne);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	 static void testListCode() {
		ClientConfig config= new ClientConfig();
		Client client=ClientBuilder.newClient(config);
		WebTarget target= client.target("http://localhost:8081/EtudiantREST/rest/etudiant/code");
		String reponse= target.request()
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		System.out.println(reponse);
		
	}
	static void testAdd(Etudiant etudiant) {
		WebTarget target= getWebTaget();
		Response response=target.request().post(
				Entity.entity(etudiant, MediaType.APPLICATION_JSON),
				Response.class);
		System.out.println(response);
	}
	static void testUpdate(Etudiant etudiant) {
		WebTarget target= getWebTaget();
		Response response=target.request().put(
				Entity.entity(etudiant, MediaType.APPLICATION_JSON),
				Response.class);
		System.out.println(response);
		
	}
	static void testDelete(String numEt) {
		WebTarget target= getWebTaget();
		Response response=target.path(numEt).request()
				.delete(Response.class);
	}
	static void testGet(String numEt) {
	    WebTarget target = getWebTaget();
	    Response response = target.path(numEt)
	            .request()
	            .accept(MediaType.APPLICATION_JSON)
	            .get();

	    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
	        Etudiant[] etudiants = response.readEntity(Etudiant[].class);
	        for (Etudiant etudiant : etudiants) {
	            System.out.println(etudiant);
	        }
	    } else {
	        System.out.println("Erreur lors de la récupération de l'étudiant avec le code: " + numEt);
	    }

	    response.close();
	}
        

	static WebTarget getWebTaget() {
		ClientConfig config= new ClientConfig();
		Client client=ClientBuilder.newClient(config);
		return client.target(baseUri);
	}
        
      public List<Etudiant> testGetEtudiant(String numEt) {
    WebTarget target = getWebTaget();
    Response response = target.path(numEt)
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get();
    
    // Vérifiez si la réponse est OK
    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        List<Etudiant> etudiants = response.readEntity(new GenericType<List<Etudiant>>() {});
        return etudiants;
    } else {
        // Gérer les erreurs de requête ici
        return Collections.emptyList(); // Ou lancez une exception appropriée
    }
}
      
      public Etudiant Get(String numEt) {
	    WebTarget target = getWebTaget();
	    Response response = target.path(numEt)
	            .request()
	            .accept(MediaType.APPLICATION_JSON)
	            .get();
	        Etudiant etudiants = response.readEntity(Etudiant.class);
	        return etudiants;
	        
	    } 

        
       public List<Etudiant> listerEtudiants() {
        List<Etudiant> listeEtudiants = new ArrayList<Etudiant>();

        // Récupérer les données depuis le service web
        WebTarget target = getWebTaget();
        String reponse = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        // Mapper la réponse JSON en objets Java
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(reponse);
            if (jsonNode.isArray()) {
                for (JsonNode node : jsonNode) {
                    // Créer un objet Etudiant pour chaque élément JSON
                    String numEtudiant = node.get("numEtudiant").asText();
                    String nom = node.get("nom").asText();
                    double moyenne = node.get("moyenne").asDouble();
                    Etudiant etudiant = new Etudiant(numEtudiant, nom, moyenne);
                    // Ajouter l'étudiant à la liste
                    listeEtudiants.add(etudiant);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listeEtudiants;
    }
       
      public void AddEtudiant(Etudiant etudiant) {
		WebTarget target= getWebTaget();
		Response response=target.request().post(
				Entity.entity(etudiant, MediaType.APPLICATION_JSON),
				Response.class);
	}
      public void UpdateEtudiant(Etudiant etudiant) {
		WebTarget target= getWebTaget();
		Response response=target.request().put(
				Entity.entity(etudiant, MediaType.APPLICATION_JSON),
				Response.class);
		
	}
      public void DeleteEtudiant(String numEt) {
		WebTarget target= getWebTaget();
		Response response=target.path(numEt).request()
				.delete(Response.class);
	}
      
       public List<String> listerCodeEtudiants() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/EtudiantREST/rest/etudiant/code");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String reponse = response.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            try {
                // Désérialiser la réponse JSON en une liste de chaînes
                List<String> codesEtudiants = mapper.readValue(reponse, new TypeReference<List<String>>(){});
                return codesEtudiants;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erreur lors de la récupération des codes d'étudiants.");
        }

        return null; // Retourner null si quelque chose ne va pas
    }
       
        public double MaxMoy() {
		ClientConfig config= new ClientConfig();
		Client client=ClientBuilder.newClient(config);
		WebTarget target= client.target("http://localhost:8081/EtudiantREST/rest/etudiant/max");
		Double reponse= target.request()
				.accept(MediaType.APPLICATION_JSON)
				.get(Double.class);
		return reponse;
		
	}
         public double MinMoy() {
		ClientConfig config= new ClientConfig();
		Client client=ClientBuilder.newClient(config);
		WebTarget target= client.target("http://localhost:8081/EtudiantREST/rest/etudiant/min");
		Double reponse= target.request()
				.accept(MediaType.APPLICATION_JSON)
				.get(Double.class);
		return reponse;
		
	}
         public double Moy() {
		ClientConfig config= new ClientConfig();
		Client client=ClientBuilder.newClient(config);
		WebTarget target= client.target("http://localhost:8081/EtudiantREST/rest/etudiant/moy");
		Double reponse= target.request()
				.accept(MediaType.APPLICATION_JSON)
				.get(Double.class);
		return reponse;
		
	}
}
