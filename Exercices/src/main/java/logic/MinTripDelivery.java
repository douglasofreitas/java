package main.java.logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 

Your job is to design a system that will calculate the minimum number of trips required to deliver a given list of packages.
You are given the following information:

    The maximum weight ('W') that can be delivered in a single trip
    The weight of each package.

 

This new system must follow these 3 rules:

    A single package cannot be split across multiple trips.
    No more than 2 (two) packages can be delivered per trip.
    The heaviest package weights less or equal than 'W'.

 

You must return the minimum number of trips required to deliver all packages.

 
Sample Input:

The maximum weight ('W') that can be delivered in a single trip: 100

 

The weight of each package: 70, 10, 20

 
Expected Output:

Minimum number of trips required to deliver the given list of packages: 2



 * @author douglas
 *
 */

public class MinTripDelivery {
	
	static int minimumNumberOfTrips(int tripMaxWeight, int[] packagesWeight) {
		
		int numberTrips = 0;
		
		//order package list
		List<Integer> packages = new ArrayList<Integer>();
		
		for(int i = 0; i < packagesWeight.length ; i++) {
			packages.add(packagesWeight[i]);
		}
		Collections.sort(packages);
		
		//count trip
		int weight = 0;
		for(int i = 0; i < packages.size() / 2; i++) {
			int j = packages.size() - 1 - i;
			
			if(i == j) continue;
			
			if(weight == 0) {
				//weight empty
				weight = packages.get(j);
				
				if(weight + packages.get(i) > tripMaxWeight) {
					numberTrips++;
					weight = packages.get(i);
				} else {
					numberTrips++;
					weight = 0; //reset weight to new delivery
				}
			} else {
				if(weight + packages.get(i) > tripMaxWeight) {
					numberTrips++;
					weight = packages.get(i);
					if(weight + packages.get(j) > tripMaxWeight) {
						numberTrips++;
						weight = packages.get(j);
					}else {
						numberTrips++;
						weight = 0;
					}
				} else {
					numberTrips++;
					weight = packages.get(j); //reset weight to new delivery
				}
			}
			
			
		}
		
		//last check (odd array)
		if(packages.size() % 2 == 1) {
			if (weight > 0) {
				if(weight + packages.get(packages.size() / 2) > tripMaxWeight) {
					numberTrips++;
					numberTrips++;
				} else {
					numberTrips++;
				}
			}else {
				numberTrips++;
			}
		}else {
			if(weight > 0)
				numberTrips++;
		}
		
		
		return numberTrips;
	}
	
	
	public static void main(String[] args) {
		
        Scanner in = new Scanner(System.in);
        int tripMaxWeight = in.nextInt();
        int numberOfPackages = in.nextInt();
        int[] packagesWeight = new int[numberOfPackages];
        
        for (int i = 0; i < numberOfPackages; i++) {
            packagesWeight[i] = in.nextInt();
        }
        
        /* Example static code
        int tripMaxWeight = 100;

        int numberOfPackages = 3;

        int[] packagesWeight = new int[numberOfPackages];
        
        packagesWeight[0] = 70;
        packagesWeight[1] = 10;
        packagesWeight[2] = 20;
        */
        
        int minimumNumberOfTrips = minimumNumberOfTrips(tripMaxWeight, packagesWeight);

        System.out.println(minimumNumberOfTrips);
        in.close();
	}

}
