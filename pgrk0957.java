// Sunny Patel   cs 610 0957 prp
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class pgrk0957 {
	
	public static void main(String[] args) {
		int[][] graph = null;
		int iteration = 0, vertices = 0, edges = 0;
		double errorrate = 0, initialvalue = 0;
		double d = 0.85;
		
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
			for (int i = 0; i < edges; i++) {
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
			initialvalue = 1.0/vertices;
			iteration = 0;
			errorrate = (double)Math.pow(10, -5);
		}
		
		//Initialization of PageRank
		double[] pageRank = new double[vertices];
		for(int i = 0; i<vertices; i++) {
			pageRank[i] = initialvalue;
		}
		
		//Cost vector (calculated to avoid excess calculation while iterating to calculate PageRank)
		double[] cost = new double[vertices];
		for(int row = 0; row<vertices; row++) {
			for(int col = 0; col<vertices; col++) {
				if(graph[row][col] == 1)
					cost[row]++;
			}
		}
		
		//Running PageRank with iterations
		if(iteration>0) {
			boolean base = true;
			int iter = 0;
			for(int i=0; i<iteration; i++) {
				double[] pageRankOld  = pageRank.clone();				
				if(base == true) {					
					base = false;
					if(graph.length<=10) {
						System.out.printf("Base  : %2d :",iter);
						for(int j = 0; j<vertices; j++)				
							System.out.printf("P[%2d]=%.7f ",j, pageRank[j]);
						System.out.println();
					}															
				}
				iter++;
				if(graph.length<=10)
					System.out.printf("Iter  : %2d :",iter);
				
				//Calculating PageRank
				for(int row = 0; row<vertices; row++) {
					double temp = 0;
					for(int col = 0; col<vertices; col++) {
						if(graph[col][row]==1) {		// If column node has out-going link to row node 
							temp += (pageRankOld[col] / cost[col]);	// PageRank(column node)/Out-Degree(cost)(column node)
						}
					}
					temp = (1-d) / vertices + (d*temp);		// (1-d)/N + (d*(sum(PR(e)/C(e)))) 
					pageRank[row] = temp;	// Updating PageRank of current node(e)
				}
				if(base == false) {
					if(graph.length<=10) {
						for(int j = 0; j<vertices; j++)				
							System.out.printf("P[%2d]=%.7f ",j, pageRank[j]);
						System.out.println();
					}
				}
			}
			if(graph.length>10) {
				System.out.println("Iter  : "+iter);
				for(int j = 0; j<vertices; j++) {
					System.out.printf("P[%2d]=%.7f",j, pageRank[j]);
					System.out.println();
				}
			}
		}
		
		//Running PageRank with errorrate
		else {			
			boolean base = true;
			double currentErrorRate = 1;		// variable for storing max errorrate.
			boolean flag = true;
			int iter = 0;			
			while(currentErrorRate >= errorrate) {
				double[] pageRankOld  = pageRank.clone();				
				if(base == true) {					
					base = false;
					if(graph.length<=10) {
						System.out.printf("Base  : %2d :",iter);
						for(int j = 0; j<vertices; j++)				
							System.out.printf("P[%2d]=%.7f ",j, pageRank[j]);
						System.out.println();
					}															
				}
				iter++;
				if(graph.length<=10)
					System.out.printf("Iter  : %2d :",iter);
				
				//Calculating PageRank
				for(int row = 0; row<vertices; row++) {
					double temp = 0;
					for(int col = 0; col<vertices; col++) {
						if(graph[col][row]==1) {		// If column node has out-going link to row node 
							temp += (pageRankOld[col] / cost[col]);	// PageRank(column node)/Out-Degree(cost)(column node)
						}
					}
					temp = (1-d) / vertices + (d*temp);		// (1-d)/N + (d*(sum(PR(e)/C(e)))) 
					pageRank[row] = temp;	// Updating PageRank of current node(e)
				}
				if(base == false) {
					if(graph.length<=10) {
						for(int j = 0; j<vertices; j++)				
							System.out.printf("P[%2d]=%.7f ",j, pageRank[j]);
						System.out.println();
					}
				}
				
				for(int j = 0; j<vertices; j++) {
					if(flag == true) {
						flag = false;
						currentErrorRate = Math.abs(pageRank[j] - pageRankOld[j]);
					}					
					// Finding max errorrate and assigning to currentErrorRate
					else {
						if(currentErrorRate < Math.abs(pageRank[j] - pageRankOld[j]))	
							currentErrorRate = Math.abs(pageRank[j] - pageRankOld[j]);
					}
				}
				flag = true;
			}
			if(graph.length>10) {
				System.out.println("Iter  : "+iter);
				for(int j = 0; j<vertices; j++) {
					System.out.printf("P[%2d]=%.7f",j, pageRank[j]);
					System.out.println();
				}
			}
		}
		
	}
}
