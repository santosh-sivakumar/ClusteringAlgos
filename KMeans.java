import java.io.*;
import java.util.*;
import java.lang.Math; 

public class KMeans {

	private Vector midpoint (List<Vector> listofvectors) { //computes + returns a midpoint Vector from a given list of Vectors
		
		List<Double> mp_coordinates = new ArrayList<Double>();
		double number_of_vectors = listofvectors.size();
		double length_of_vector = listofvectors.get(0).numbers.size();	
			
		for (int dimension = 0; dimension < length_of_vector; dimension ++) {
			
			double coordinate = 0;

			for (int curr = 0; curr < number_of_vectors; curr ++) {
				
				coordinate += listofvectors.get(curr).numbers.get(dimension);
			}
				
			coordinate /= number_of_vectors;
			mp_coordinates.add(coordinate);
				
		}
		return new Vector(mp_coordinates);
		
	}
	
	private int closestCenter (Vector start_vector, List<Vector> listofcenters) { // given a starting vector and list of cluster centers, returns closest center's index
		
		int closest_index = 0;
		double distance_to_closest = start_vector.distance(listofcenters.get(0));
		
		
		for (int curr = 1; curr < listofcenters.size(); curr++) {
			double distance = start_vector.distance(listofcenters.get(curr));
			
			if (distance < distance_to_closest) {
				closest_index = curr;
				distance_to_closest = distance;
			}
		}
		
		return closest_index;
	}
	
	private boolean is_clustering_finished (List<Vector> old_centers, List<Vector> current_centers) { // computes normalized difference between cluster centers of 2 subsequent iterations [for all clusters], returns whether clustering is finished
	
		for (int index = 0; index < current_centers.size(); index++) {
			double distance_between_centers = old_centers.get(index).distance(current_centers.get(index));
			double magnitude = current_centers.get(index).magnitude();
			double normalized_distance = distance_between_centers / magnitude;

			
			if (normalized_distance > 0.00000001) {
				return false;
			}
		}
		return true;
	}
	
	private List<List<Vector>> empty_clusters (int quantity) { // initializes List of desired number of clusters (each cluster is a list of vectors)
	
		List<List<Vector>> cluster_list = new ArrayList<List<Vector>>();
		
		for (int count = 0; count < quantity; count++) {
			List<Vector> cluster = new ArrayList<Vector>();
			cluster_list.add(cluster);
		}
		
		return cluster_list;
	}
			
	public List<List<Vector>> cluster (List<Vector> unclustered_vectors, int number_of_clusters) { // full cluster algorithm
	
		List<List<Vector>> returnlist = new ArrayList<List<Vector>>(); // initializes cluster list to be returned
		int number_of_vectors = unclustered_vectors.size();
		
		List<Vector> current_centers = new ArrayList<Vector>();
		for (int count = 0; count < number_of_clusters; count++) { // chooses random centers to begin, adds to list of centers
			int random_index = (int)(Math.random() * number_of_vectors);
			current_centers.add(new Vector(unclustered_vectors.get(random_index).numbers));
		}

		List<Vector> old_centers = new ArrayList<Vector>(); // initializes old list of centers for isClusteringFinished method

		do { // main loop, iterates through all vectors, finds closest center, adds vector to cluster with index corresponding to center's index in center list
			
			List<List<Vector>> current_clusters = empty_clusters(number_of_clusters);
			

			for (int index = 0; index < number_of_vectors; index++) {
				
				int closest_center = closestCenter(unclustered_vectors.get(index), current_centers);
				(current_clusters.get(closest_center)).add(unclustered_vectors.get(index));
					
			}
			
			number_of_clusters = current_centers.size(); // if cluster is empty, catches exception case by recomputing number of clusters
			
			old_centers.clear(); // update old centers
			for (int index = 0; index < number_of_clusters; index ++) {
				old_centers.add(current_centers.get(index));
			}
			
			current_centers.clear(); // clear current centers, compute midpoints of each cluster. These midpoints become new centers
			for (int index = 0; index < number_of_clusters; index ++) {
				if ((current_clusters.get(index)).size() > 0) {
				current_centers.add(midpoint(current_clusters.get(index)));
				}
			}

			if (is_clustering_finished(old_centers, current_centers)) { // check if clustering is complete
				for (int index = 0; index < current_clusters.size(); index ++) {
					returnlist.add(current_clusters.get(index));
				}
			}
			
		} while (! is_clustering_finished(old_centers, current_centers));

		
	return returnlist;
	}
}
		