package data;

import housing.Config;
import housing.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**************************************************************************************************
 * Class to read the matrix of distances between the regions.
 *
 * @author Haoyu Ye, Adrian Carro
 *
 *************************************************************************************************/

public class Distance {

    //------------------//
    //----- Fields -----//
    //------------------//

    private static Config config = Model.config; // Passes the Model's configuration parameters object to a private field

    // Reads and stores the matrix of distances between regions as a static 2D ArrayList of doubles
	private static ArrayList<ArrayList<Double>> distanceMatrix = readDistanceMatrix(config.DISTANCE_BETWEEN_REGIONS);

    //-------------------//
    //----- Methods -----//
    //-------------------//

	/**
	 * Method to read the matrix of distances between every pair of regions from a data file.
	 *
	 * @param fileName String with name of file (address inside source folder)
	 * @return distanceMatrix 2D ArrayList of doubles with the distances between each pair of regions
	 */
	private static ArrayList<ArrayList<Double>> readDistanceMatrix(String fileName) {
		ArrayList<ArrayList<Double>> distanceMatrix = new ArrayList<>();
		// Try-with-resources statement
		try (BufferedReader buffReader = new BufferedReader(new FileReader(fileName))) {
			String line = buffReader.readLine();
			while (line != null) {
				if (line.charAt(0) != '#') {
					ArrayList<Double> distanceLine = new ArrayList<>();
					for (String str: line.split(",")) {
						try {
							distanceLine.add(Double.parseDouble(str.trim()));
						} catch (NumberFormatException nfe) {
							System.out.println("Exception " + nfe + " while trying to parse " +
                                    line.split(",")[1] + " for an double");
							nfe.printStackTrace();
						}
					}
					distanceMatrix.add(distanceLine);
				}
				line = buffReader.readLine();
			}
		} catch (IOException ioe) {
			System.out.println("Exception " + ioe + " while trying to read file '" + fileName + "'");
			ioe.printStackTrace();
		}
		// Check that the matrix is squared
        if (distanceMatrix.size() != distanceMatrix.get(0).size()) {
            System.out.println("Distance matrix is not squared");
            System.exit(0);
        }
		return distanceMatrix;
	}

    //----- Getter/setter methods -----//

	public static ArrayList<ArrayList<Double>> getDistanceMatrix(int numberOfRegions) {
	    // First check if the number of regions passed as input (derived from reading the population per region file) is
        // the same as the number of regions read from the distances file
	    if (numberOfRegions != distanceMatrix.size()) {
            System.out.println("Number of regions at population file, " + numberOfRegions +
                    ", incoherent with the number of regions at the distances file, " + distanceMatrix.size());
            System.exit(0);
        }
        return distanceMatrix;
    }
}