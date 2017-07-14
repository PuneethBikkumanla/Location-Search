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
		
	String location, requirements, firstReq, initialRequest;
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
		requirements=reader.readLine();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		User query = new User();
		
		query.processingKeywords();
		query.getResult();
		
		/*for(String temp: list)
		{
			getList(temp);
		}*/	
	}
	
	public void processingKeywords() {
		String[] listOfKeywords=requirements.split(" ");
	}
	
	
	
	public static void getList(String type)
	{
		List<Place> places = Client.client.getPlacesByQuery(type, GooglePlaces.MAXIMUM_RESULTS);
		for(Place temp: places)
		{
			System.out.println(temp.getName());
		}
	}
	
	public void getResult() {
		try {
		
		mainLocation = Client.client.getPlacesByQuery(initialRequest, GooglePlaces.MAXIMUM_RESULTS, 
				Param.name("opennow").value(true));
		
		//List<Place> places = client.getPlacesByQuery(location, GooglePlaces.MAXIMUM_RESULTS);
		for(Place iter: mainLocation){				
			
			Set<List<Place>> tempSet = new HashSet<List<Place>>();
			
			for(int i=0; i<listOfKeywords.length; i++) {
				String requirementString = listOfKeywords[i] + " near " + iter.getVicinity();
				
				List<Place> something = Client.client.getPlacesByQuery(requirementString, GooglePlaces.MAXIMUM_RESULTS, 
						Param.name("opennow").value(true), Param.name("radius").value(1609.34));
			
				tempSet.add(something);
			}
		}
		
		}catch(GooglePlacesException e) {
			System.out.println("Oops! Nothing found");
		}
		
		
		/*for(Place temp: places)
		{
			String address = temp.getName();
			
		}*/
		
	}
	
	
	

}
