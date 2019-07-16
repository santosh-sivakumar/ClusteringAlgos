import java.io.*;
import java.util.*;
import java.lang.Math; 

public class Vector {
	
	List<Double> numbers;
		
	Vector (List<Double> numbers) {
			
		numbers = numbers;
			
	}
		
	public double distance(Vector vector2) { // returns distance between two vectors
		
		double squared_distance = 0;
			
		for (int index = 0; index < numbers.size(); index++) {
			double difference = numbers.get(index) - vector2.numbers.get(index);
			squared_distance += (difference * difference);
		}
			
		return Math.sqrt(squared_distance);	
					
	}
	
	public double magnitude() { // computes vector magnitude
			
		List<Double> origin_coordinates = new ArrayList<Double>();
		for (int index = 0; index < numbers.size(); index++) {
			origin_coordinates.add(0.0);
		}
			
		Vector origin = new Vector(origin_coordinates);
			
		return distance(origin);
			
	}
}
	