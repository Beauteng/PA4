import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//*********************************************************
//Code created on: 6/1/2019
//Code last edited on: 6/6/2019
//Code created by: Spencer Raymond
//Associated with: MSU CSCI-232
//Description: Dijkstra's graph algorithm. This code takes
//an input file that has the first line be the dimensions of
//the graph. By definition, this would be a square but just
//for ease of readability I wanted to include both sides
//So I could reuse the code on another project if needed.
//This uses a nested for loop to check values instead of
//using a priority queue. I think a priority queue would
//have been a little simpler and cleaner but since I have
//already implemented that type of data structure before,
//I went for a different route. In this, we check every node
//except the last since by definition we do not need to check
//the final node. We find the shortest path to the next location
//and then we map the distance to every node it can see. Then
//we move onto the next shortest edge to a new node and continue
//until all nodes are found with the shortest path possible.
//*********************************************************

public class Dijkstra {
	public int MAX_VAL = Integer.MAX_VALUE;
	public int shortestEdge(int finalList[], boolean nodeDone[], int numNodes) {
		//take in the nodes and find the shortest edge
		int smallestVal = MAX_VAL;
		int index = 0;
		for(int l = 0; l < numNodes ; l++) {
			if(finalList[l] <= smallestVal && nodeDone[l] == false) {
				smallestVal = finalList[l];
				index = l;
			}
		}
		return index;
	}
	public void alg(int graph[][], int first, int numNodes) throws IOException {
		//set up table, we need to keep track of finished nodes
		//and distance values
		int[] finalList = new int[numNodes];
		boolean[] nodeDone = new boolean[numNodes];
		for(int i = 0; i < numNodes ; i++) {
			finalList[i] = MAX_VAL;
			nodeDone[i] = false;
		}
		//Initialize the "root" to zero and leave the rest to max
		finalList[0] = 0;
		//we need to step through all nodes except the last
		for(int j = 0 ; j < numNodes-1 ; j++) {
			//locate shortest edge
			int k = shortestEdge(finalList, nodeDone,numNodes);
			//mark node connected by shortest edge as complete
			nodeDone[k] = true;
			int currVal = finalList[k];
			for(int m = 0; m < numNodes ; m++) {
				//make sure location is accessible by current node and
				//the node being looked at has not been finalized.
				if(graph[k][m] != 0 && !nodeDone[m]) {
					//If the new path is shorter than previous let's
					//set it as that new smaller value
					if(currVal + graph[k][m] < finalList[m]) {
						finalList[m] = currVal + graph[k][m];
					}
				}
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("outputChart.txt", true));
	    for(int g = 0 ; g < numNodes ; g++) {
	    	writer.append(g + " --> " + finalList[g] + "\n");
	    }
	    writer.close();
	}
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(new File("inputMatrix.txt")).useDelimiter("\\s*,\\s*|\\n");
		int x = 0;
		int y = 0;
		if(scan.hasNextInt()) {
			x = scan.nextInt();
		}
		if(scan.hasNextInt()) {
			y = scan.nextInt();
		}
		int graph[][] = new int[x][y];
		for(int a = 0; a<x ; a++) {
			for(int b = 0; b<y ; b++) {
				if(scan.hasNextInt()) {
					graph[a][b] = scan.nextInt();
				}
			}
		}
		scan.close();
		Dijkstra t = new Dijkstra(); 
		t.alg(graph, 0, 8); 
	}
}
