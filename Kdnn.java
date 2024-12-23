//Amina Kanamatova, Data Structures Final Project
//Collab Statement: I worked on the project alone but I referred to sources such as https://www.geeksforgeeks.org/java-program-to-construct-k-d-tree/

import java.io.*;
import java.util.*;

class Kdnn{
	public static void main(String[] args){
		try{
			//read data into an ArrayList
			ArrayList<Word> words = readData("dat.1");
			//build the k-d tree 
			KDTree tree = new KDTree(words); 

			//read the test data from standard input 
			Scanner scanner = new Scanner(System.in);
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String[] data = line.split(" ");
				String test = data[0];
				float[] vec = new float[data.length - 1];

				for(int i = 1; i < data.length; i++){
					vec[i - 1] = Float.parseFloat(data[i]);
				}

				Word tar = new Word(test, vec);

				Word nearest = tree.findNearestNeighbor(tar); 

				double dist = tree.dist(tar.vector, nearest.vector); 

				System.out.println(test + "nearest" + nearest.word + "distance" + String.format("%.6f", dist));
			}
			scanner.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	//Need to write a method that will read data from file 
	//And return it as an ArrayList
	public static ArrayList<Word> readData(String file) throws IOException{
		ArrayList<Word> words = new ArrayList<>(); 
		BufferedReader reader = new BufferedReader(new FileReader(file));

		//for reading the first line to get vector size and number of words 
		String line = reader.readLine(); 
		String[] dim = line.split(" ");
		int num = Integer.parseInt(dim[0]);
		int size = Integer.parseInt(dim[1]); 

		//read the word vector
		while((line = reader.readLine()) != null){
			String[] wordData = line.split(" "); 
			String word = wordData[0];
			float[] vector = new float[size]; 
			for(int i = 0; i < size; i++){
				vector[i] = Float.parseFloat(wordData[i + 1]);
			}
			words.add(new Word(word, vector));
		}
		reader.close(); 
		return words; 
	}
}