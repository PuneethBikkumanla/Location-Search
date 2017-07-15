import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Place;
import se.walkercrou.places.exception.GooglePlacesException;
import se.walkercrou.places.exception.NoResultsFoundException;

public class User {
		
	String location, requirements = " ", firstReq, initialRequest;
	String[] listOfKeywords;
	HashMap<String, Set<List<Place>>> map = new HashMap<String, Set<List<Place>>>(); 
	List<Place> mainLocation;
	
	
	public User() {
		getRequirements();
	}
	
	public void getRequirements()
	{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
		System.out.println("Please enter the general location");
		location = reader.readLine();
		
		System.out.println("Please enter the first place you would like to visit");
		firstReq = reader.readLine();
		
		initialRequest = firstReq + " near " + location;
		
		System.out.println("Please enter other keywords with a space in between them");
		requirements+=reader.readLine();
		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		User query = new User();
		
		query.processingKeywords();
		query.processInput();
	
	}
	
	public void processingKeywords() {
		listOfKeywords=requirements.split(" ");
	}
	
	
	public void processInput() {
		try {
		
		mainLocation = Client.client.getPlacesByQuery(initialRequest, GooglePlaces.MAXIMUM_RESULTS, 
				Param.name("opennow").value(true));
		
		//List<Place> places = client.getPlacesByQuery(location, GooglePlaces.MAXIMUM_RESULTS);
		
		
		
		for(Place iter: mainLocation) {  //Maybe for initializing the set
			 map.put(iter.getName(), new HashSet<List<Place>>());
		}
		
		
		for(Place iter: mainLocation){				
			
			for(String word : listOfKeywords) {
					String requirementString = word + " near " + iter.getName();
					
					try {
					
					List<Place> something = Client.client.getPlacesByQuery(requirementString, GooglePlaces.MAXIMUM_RESULTS, 
							Param.name("opennow").value(true), Param.name("radius").value(1609.34));
					
					for(Place temp: something) {
						System.out.println(temp.getName());
					}
					System.out.println();
					
					map.get(iter.getName()).add(something);
					
					}
					catch(GooglePlacesException e) {
						map.remove(iter.getName());
						break;
					}
				}
			
		}
		
		}catch(GooglePlacesException e) {
			System.out.println("Oops! Nothing found");
		}
		
	}
	
	
	public void printResult() {
		System.out.println(map.keySet().size());
		
		for(String temp: map.keySet()) {
			System.out.println(temp);
		}
	}
	
	
	

}
