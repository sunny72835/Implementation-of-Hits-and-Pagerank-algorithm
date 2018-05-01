// Sunny Patel   cs 610 0957 prp
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class hits0957 {

	public static void main(String[] args) {

		int[][] graph = null;
		int iteration = 0, vertices = 0, edges = 0;
		double errorrate = 0, initialvalue = 0;

		// Getting inputs and creating graph
		int a = Integer.parseInt(args[0]);
		if (a > 0)
			iteration = a;
		else if (a < 0)
			errorrate = (double)Math.pow(10, a);
		else
			errorrate = (double)Math.pow(10, -5);
		String filepath = args[2];
		File f = new File(filepath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String[] line1 = br.readLine().split(" ");
			vertices = Integer.parseInt(line1[0]);
			edges = Integer.parseInt(line1[1]);
			graph = new int[vertices][vertices];
			for(int i = 0; i<edges; i++) {
				String[] line = br.readLine().split(" ");
				graph[Integer.parseInt(line[0])][Integer.parseInt(line[1])] = 1;
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error in reading the file.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error while generating matrix.");
			e.printStackTrace();
		}
		double b = Double.parseDouble(args[1]);
		if (b >= 0)
			initialvalue = b;
		else
			initialvalue = (double)Math.pow(graph.length, 1 / b);
		
		if(vertices > 10) {
			initialvalue = (double)(1.0/vertices);
			iteration = 0;
			errorrate = (double)Math.pow(10, -5);
		}

		// Initialization of Hub and Authority Scores
		double[] authorityScores = new double[vertices];
		double[] hubScores = new double[vertices];
		for (int i = 0; i<vertices; i++) {
			authorityScores[i] = initialvalue;
			hubScores[i] = initialvalue;
		}

		// Running HITS algorithm with the no. of iterations is provided
		if (iteration > 0) {
			boolean base = true;
			int iter = 0;
			for (int i = 0; i < iteration; i++) {
				double normalization = 0;
				if(base == true) {
					base = false;
					if(graph.length<=10) {
						System.out.printf("Base  : %2d :",iter);
						for(int j = 0; j<vertices; j++)
							System.out.printf("A/H[%2d]=%.7f/%.7f ", j, authorityScores[j], hubScores[j]);
						System.out.println();
					}					
				}															
				iter++;			
				if(graph.length<=10) 
					System.out.printf("Iter  : %2d :",iter);					
				
				// Calculating Authority Scores
				for (int row = 0; row<vertices; row++) { // Calculating authority scores of all nodes
					double authority = 0;
					for (int col = 0; col<vertices; col++) {
						// If the node appears in set of edges of the current node then add hub score of current node
						if (graph[col][row] == 1) {
							authority += hubScores[col];
						}
					}
					normalization += authority * authority;
					authorityScores[row] = authority;
				}
				normalization = (double)Math.sqrt(normalization);
				for (int j = 0; j < vertices; j++) { // Normalizing all calculated authority scores
					authorityScores[j] /= normalization; 
				}

				// Calculating Hub scores
				normalization = 0;
				for (int row = 0; row<vertices; row++) { // Calculating hub scores of all nodes
					double hub = 0;
					// If a node has edges, add authority scores of all edges
					for (int col = 0; col<vertices; col++) {
						if(graph[row][col] == 1)
							hub += authorityScores[col];
					}
					normalization += hub * hub;
					hubScores[row] = hub;
				}
				normalization = (double)Math.sqrt(normalization);
				for (int j = 0; j < vertices; j++) { // Normalizing all calculated hub scores
					hubScores[j] /= normalization;
				}
				if(graph.length <= 10) {
					for(int j = 0; j<vertices; j++) {
						System.out.printf("A/H[%2d]=%.7f/%.7f ", j, authorityScores[j], hubScores[j]);
					}
					System.out.println();
				}																	
			}
			if(graph.length>10) {
				System.out.println("Iter  : "+iter);
				for(int i = 0; i<vertices; i++) {
					System.out.printf("A/H[%2d]=%.7f/%.7f ", i, authorityScores[i], hubScores[i]);
					System.out.println();
				}
			}
		}

		// Running HITS algorithm with the errorrate is provided
		else {
			double currentErrorRate = 1;
			int iter = 0;
			boolean flag = true, base = true;
			while (currentErrorRate >= errorrate) {												
				double[] oldAuthorityScores = authorityScores.clone();
				double[] oldHubScores = hubScores.clone();
				
				double normalization = 0;
				if(base == true) {
					base = false;
					if(graph.length<=10) {
						System.out.printf("Base  : %2d :",iter);
						for(int j = 0; j<vertices; j++)
							System.out.printf("A/H[%2d]=%.7f/%.7f ", j, authorityScores[j], hubScores[j]);
						System.out.println();
					}					
				}															
				iter++;			
				if(graph.length<=10) {
					System.out.printf("Iter  : %2d :",iter);					
				}
				
				// Calculating Authority Scores
				for (int row = 0; row<vertices; row++) { // Calculating authority scores of all nodes
					double authority = 0;
					for (int col = 0; col<vertices; col++) {
						// If the node appears in set of edges of the current node then add hub score of current node
						if (graph[col][row] == 1) {
							authority += hubScores[col];
						}
					}
					normalization += authority * authority;
					authorityScores[row] = authority;
				}
				normalization = (double)Math.sqrt(normalization);
				for (int j = 0; j < vertices; j++) { // Normalizing all calculated authority scores
					authorityScores[j] /= normalization; 
				}
																					
				for (int i = 0; i<vertices; i++) { // Finding maximum errorrate
					if(flag==true) { // If it is the first iteration
						flag=false;
						currentErrorRate = Math.abs(authorityScores[i] - oldAuthorityScores[i]); 
					}
					else {
						if(currentErrorRate < (Math.abs(authorityScores[i] - oldAuthorityScores[i])))
							currentErrorRate = Math.abs(authorityScores[i] - oldAuthorityScores[i]);
					}						
				}

				// Calculating Hub scores
				normalization = 0;
				for (int row = 0; row<vertices; row++) { // Calculating hub scores of all nodes
					double hub = 0;
					// If a node has edges, add authority scores of all edges
					for (int col = 0; col<vertices; col++) {
						if(graph[row][col] == 1)
							hub += authorityScores[col];
					}
					normalization += hub * hub;
					hubScores[row] = hub;
				}
				normalization = (double)Math.sqrt(normalization);
				for (int j = 0; j < vertices; j++) { // Normalizing all calculated hub scores
					hubScores[j] /= normalization;
				}
				if(graph.length <= 10) {
					for(int j = 0; j<vertices; j++) {
						System.out.printf("A/H[%2d]=%.7f/%.7f ", j, authorityScores[j], hubScores[j]);
					}
					System.out.println();
				}
				for (int i = 0; i<vertices; i++) { // Finding maximum errorrate
					if(flag==true) { 
						flag=false;
						currentErrorRate = Math.abs(hubScores[i] - oldHubScores[i]); 
					}
					else {
						if(currentErrorRate < (Math.abs(hubScores[i] - oldHubScores[i])))
							currentErrorRate = Math.abs(hubScores[i] - oldHubScores[i]);
					}						
				}
				flag = true;				
			}
			if(graph.length>10) {
				System.out.println("Iter  : "+iter);
				for(int i = 0; i<vertices; i++) {
					System.out.printf("A/H[%2d]=%.7f/%.7f", i, authorityScores[i], hubScores[i]);
					System.out.println();
				}
			}
		}
	}

}
