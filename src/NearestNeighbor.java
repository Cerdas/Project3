import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NearestNeighbor {

	// Scanning file and putting data into
	// the either data or name array
	public static void scanFile(String fileName, double[][] data, String[] name) {
		
		// If file is found then it runs
		// if not then program exits
		try {
			File file = new File(fileName);
			Scanner scan = new Scanner(file);

			int line = 0;
			while (scan.hasNext()) {
				String row = scan.nextLine();
				String[] dataRow = row.split(",");
				
				// The first 4 are numbers and the last one
				// is name of flower
				for (int i = 0; i < dataRow.length; i++) {
					if (i < 4) {
						data[line][i] = Double.parseDouble(dataRow[i]);
					} else {
						name[line] = dataRow[i];
					}
				}
				line++;
			}
			scan.close();
		} catch (FileNotFoundException exp) {
			System.out.println("File not Found, Goodbye");
			System.exit(0);
		}
	}

	// Calculating distance to see which
	// one the testing data matches too
	// from training data
	public static int dist(int num, double[][] data1, double[][] data2) {
		double dist = 0;
		double max = 10;
		int guess = 0;
		for (int i = 0; i < data2.length; i++) {
			for (int j = 0; j < 4; j++) {
				double a = Math.pow(data1[num][j] - data2[i][j], 2);
				dist += a;
			}
			dist = Math.sqrt(dist);
			if (dist < max) {
				max = dist;
				guess = i;
			}
			dist = 0;
		}
		return guess;
	}

	// Returning the "match" between the two
	// data sets and the accuracy
	public static void classify(double[][] data1, String[] name1, double[][] data2, String[] name2) {
		int count = 0;
		System.out.println("EX#: TRUE LABEL, PREDICTED LABEL");
		for (int i = 0; i < data1.length; i++) {
			int guess = dist(i, data1, data2);
			System.out.println((i + 1) + ": " + name1[i] + " " + name2[guess]);
			if (name1[i].equals(name2[guess])) {
				count++;
			}
		}
		double accuracy = count / 75.0;
		System.out.println("ACCURACY: " + accuracy);
	}

	public static void main(String[] args) {

		System.out.println("Programming Fundamentals");
		System.out.println("Name: Alexander Cerdas");
		System.out.println("PROGRAMMING ASSIGNMENT 3" + "\n");

		// Creating arrays to hold data from files
		double[][] trainingData = new double[75][4];
		String[] trainingName = new String[75];
		double[][] testingData = new double[75][4];
		String[] testingName = new String[75];

		// Creating string variable and scanner
		// for file name input
		String name;
		Scanner scan = new Scanner(System.in);
		
		// Entering file name and using method scanFile
		// to put data in appropriate array
		System.out.print("Enter the name of the training file: ");
		name = scan.next();
		scanFile(name, trainingData, trainingName);

		System.out.print("Enter the name of the testing file: ");
		name = scan.next();
		scanFile(name, testingData, testingName);
		System.out.println();

		// Entering arrays into classify method
		// to return the matches and accuracy
		classify(testingData, testingName, trainingData, trainingName);
		scan.close();
	}
}
