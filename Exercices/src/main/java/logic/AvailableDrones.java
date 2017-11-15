package main.java.logic;


import java.util.*;

/**
 *  

Your job is to create a function that selects 'G' drones with the greatest flight range that are available.
For that you are given the following information:

    A list of all drones containing its numerical ID and its flight range in kilometers.
    The required amount of drones ('G')
    A list of drone IDs that are currently in maintenance and thus are not available for shipment operations

 

Your function must return a list of the ids of the 'G' selected drones, sorted by flight range (greater first).

 

Assume that there are enough drones available.

 
Sample Input:

Drone Id
	Flight Range
4 	11
2 	15
5 	16
3 	40
1 	20
9 	60
6 	22

 

The required amount of drones ('G'): 3

 

Drone IDs currently in maintenance:1, 5, 9

 
Expected Output:

The 'G' drones with the greatest flight range will be 3, 6, 2


 * @author douglas
 *
 */

public class AvailableDrones {

	static List<Integer> greatestFlightRangeDrones(
			Integer numberOfRequiredDrones, 
			List<Drone> drones,
			List<Integer> inMaintenanceDrones) {

		List<Integer> avaliable = new ArrayList<>();
		
		//order drones by distance
		for (int i = 0; i < drones.size(); i++) {
		    for (int j = drones.size() - 1; j > i; j--) {
		        if (drones.get(i).getFlightRange() < drones.get(j).getFlightRange()) {
		        	//exchange
		            Drone tmp = drones.get(i);
		            drones.set(i,drones.get(j));
		            drones.set(j,tmp);
		        }
		    }
		}
		
		
		//select drones 
		int index = 0;
		do {
			//check if it's manutence 
			Drone option = drones.get(index);
			
			if(!inMaintenanceDrones.contains(option.getId())) {
				avaliable.add(option.getId());
			}
			
			index++;
			
		} while(avaliable.size() < numberOfRequiredDrones);
		
		
		return avaliable;
	}
	
	class DroneComparator implements Comparator<Drone> {
		@Override
		public int compare(Drone drone1, Drone drone2) {
			int droneRange1 = drone1.getFlightRange();
			int droneRange2 = drone2.getFlightRange();
	 
			if (droneRange1 > droneRange2) {
				return 1;
			} else if (droneRange1 < droneRange2) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	private static class Drone {

		private int id;

		private int flightRange;

		public Drone(int id, int flightRange) {
			this.id = id;
			this.flightRange = flightRange;
		}

		public int getId() {
			return id;
		}

		public int getFlightRange() {
			return flightRange;
		}

	}

	/**
	 * entrada 
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int numberOfDrones = in.nextInt();
		int numberOfRequiredDrones = in.nextInt();
		int numberOfDronesInMaintenance = in.nextInt();
		
		List<Drone> drones = new ArrayList<>();
		List<Integer> inMaintenanceDrones = new ArrayList<>();

		for (int i = 0; i < numberOfDrones; i++) {
			drones.add(new Drone(in.nextInt(), in.nextInt()));
		}

		for (int i = 0; i < numberOfDronesInMaintenance; i++) {
			inMaintenanceDrones.add(in.nextInt());
		}

		List<Integer> greatestFlightRangeDrones = greatestFlightRangeDrones(numberOfRequiredDrones, drones,
				inMaintenanceDrones);

		for (int i = 0; i < greatestFlightRangeDrones.size(); i++) {
			System.out.println(greatestFlightRangeDrones.get(i));
		}
		in.close();
	}
}
