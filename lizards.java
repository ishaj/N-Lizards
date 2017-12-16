import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class lizards {
	private static String algo; 
	private static int n,p;
	private int liz_count = 0;
	private static int [][] nursery;
	private static int [][] solNursery;
	private LinkedHashSet<int [][]> nodeState = new LinkedHashSet<>();
	private final static String in_fl = "input.txt";
	private final static String out_fl = "output.txt";
	
	public static void printNursery(boolean status) {
		String result;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(out_fl));
			if (status) 
				result = "OK";
			else
				result = "FAIL";
			writer.write(result);
			writer.write("\n");
			
			if (status) {
				for(int i=0;i<n;i++) {
					for(int j=0;j<n;j++)
						writer.write(Integer.toString(solNursery[i][j]));
					writer.write("\n");
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean goalTest() {
		if (liz_count==p) {
			return true;
		} else 
			return false;
	}
	
	public boolean goalTestBFS() {
		int count = 0;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++)
				if(isLizard(solNursery,i,j))
					count++;
		}
		
		if(count == p) {
			return true;
		} 
		return false;
	}
	
	public void removeLiz() {
		if (liz_count > 0) {
			liz_count--;
		}
	}
	
	public void placeLiz(int [][] state,int i, int j) {
		state[i][j] = 1;
		liz_count++;
	}
	
	public void unplaceLiz(int i, int j) {
		solNursery[i][j] = 0;
		removeLiz();
	}
	
	public boolean isTree(int currNursery[][],int i, int j) {
		if (currNursery[i][j] == 2) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFree(int [][] currNursery, int i, int j) {
		if (currNursery[i][j] == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isLizard(int [][] currNursery, int i, int j) {
		if (currNursery[i][j] == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean solnUtil(int row, int col) {
		
		if (goalTest()) {
				return true;
		}
		
		if (row > n-1) {
			row=0;
		}
		
		boolean loop = true;
		boolean incrCol = false;
		
		while(loop) {
			for(int i=row;i<n;i++) {
				if (i == n-1 && col == n-1)
					loop = false;
				
				if(incrCol) {
					col++;
					incrCol = false;
				}
				
				if(i==n-1 && col < n-1) {
					row = 0;
					incrCol = true;
				}
				
				if(isTree(solNursery, i, col)) 
					continue;
				else if (safeToPlace(solNursery, i, col)) {
					placeLiz(solNursery, i, col);
					if(col<n && solnUtil(i+1,col)) {
						return true;
					}
					unplaceLiz(i,col);
					
					if(i>=n) {
						removeLiz();
						return false;
					}
				}
			}
		}
		return false;
	}

	private boolean safeToPlace(int currNursery[][], int row, int col) {
		int i,j;
		
		// row check
		for (i = col; i>=0; i--) {
			if (isLizard(currNursery, row, i)) {
				return false;
			}
			else if (isTree(currNursery, row, i)) {
				break;
			}
		}
		
		// upper diagonal left
		for (i=row, j=col; i>=0 && j>=0; i--,j--) {
			if (isLizard(currNursery, i, j)) {
				return false;
			}
			else if (isTree(currNursery, i, j)) {
				break;
			}
		}
		
		// lower diagonal left
		for (i=row, j=col; i<n && j>=0; i++, j--) {
			if (isLizard(currNursery, i, j)) {
				return false;
			}
			else if (isTree(currNursery, i, j)) {
				break;
			}
		}
		
		if (algo.equals("BFS") || algo.equals("SA")) {
			//right row check
			for (i = col; i<n; i++) {
				if (isLizard(currNursery, row, i)) {
					return false;
				}
				else if (isTree(currNursery, row, i)) {
					break;
				}
			}
			
			// upper right diagonal
			for (i=row, j=col; i>=0 && j<n; i--, j++) {
				if (isLizard(currNursery, i, j)) {
					return false;
				}
				else if (isTree(currNursery, i, j)) {
					break;
				}
			}
			
			//lower right diagonal
			for (i=row, j=col; i<n && j<n; i++, j++) {
				if (isLizard(currNursery, i, j)) {
					return false;
				}
				else if (isTree(currNursery, i, j)) {
					break;
				}
			}
		}
		
		// columns -- up
		for (i=row; i>=0; i--) {
			if (isLizard(currNursery, i, col)) {
				return false;
			}
			else if (isTree(currNursery, i, col)) {
				break;
			}
		}
		
		// columns -- down
		for(i=row;i<n;i++) {
			if (isLizard(currNursery, i, col)) {
				return false;
			}
			else if (isTree(currNursery, i, col)) {
				break;
			}
		}
		
		return true;
	}

	public int findStartCol () {
		int col = -1;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if (isFree(solNursery, i,j)) {
					col = i;
					break;
				}
			}
			if (col != -1) {
				break;
			}
		}
		return col;
	}
	
	public boolean solve() {
		solNursery = new int[n][n];
		copyNursery(nursery, solNursery);
		int col = findStartCol();
		if (col == -1)
			return false;
		long start = System.currentTimeMillis();
		long end = start + 285*1000; 
		for(int i=0;i<n;i++) {
			if (System.currentTimeMillis() < end) {
				removeLiz();
				copyNursery(nursery, solNursery);          //reinitialize
				if (! isTree(solNursery, i, col)) {
					if(safeToPlace(solNursery, i,col)) {
						placeLiz(solNursery, i, col);
						if(solnUtil(i+1,0)) {
							return true;
						}
					} else {
						unplaceLiz(i, col);
					}
				}
			} else {
				break;
			}
		}
		return false;
	}
	
	private int[][] solveBFS() {
		nodeState.add(nursery);
		long start = System.currentTimeMillis();
		long end = start + 285*1000;
		Iterator<int [][]> i;
		
		while (nodeState.size() != 0) {
			i = nodeState.iterator();
			if (System.currentTimeMillis() < end) {
				solNursery = i.next();
				i.remove();
				
				if (goalTestBFS()) {
					return solNursery;
				}
				LinkedHashSet<int [][]> children = getChildren(solNursery);
				if (! children.isEmpty()) {
					nodeState.addAll(children);
				}
			} else {
				break;
			}
		}
		
		return null;
	}
	
	private LinkedHashSet<int [][]> getChildren(int [][] state) {
		LinkedHashSet<int [][]> children = new LinkedHashSet<>();
		
		for (int i=0;i<n; i++) {
			for (int j=0;j<n;j++) {
				if(safeToPlace(state, i, j) && !isTree(state, i,j)) {
					int [][] child = new int[n][n];
					copyNursery(state, child);
					placeLiz(child, i, j);
					children.add(child);
				}
			}
		}
		
		return children;
	}
	
	private int [][] solveSA() {
		double T = 18000;
		
		int [][] current = new int [n][n], next = new int[n][n];
		boolean status = getInputNursery(current);
		if(! status)
			return null;

		long start = System.currentTimeMillis();
		long end = start + 290*1000;
		
		for(int t=0;System.currentTimeMillis() < end; t++) {
			double currentConflict = 0, nextConflict = 0, deltaE = 0;
			T = schedule(t);
			
			currentConflict = checkConflict(current);
			if (T <= 0) {
				return null;
			}
			if (Math.abs(currentConflict) == 0) {	
				return current;
			}
			
			copyNursery(current, next);
			moveRandomLizard(next);
			nextConflict = checkConflict(next);
			
			deltaE = nextConflict - currentConflict;
			
			if (deltaE < 0) {
				copyNursery(next, current);
			} else {
				if (acceptState(deltaE, T)) {
					copyNursery(next, current);
				}
			}
		}
		return null;
	}
	
	private boolean getInputNursery(int [][] current) {
		copyNursery(nursery, current);
		int j,k, count = 1, free = 0;
		Random generator = new Random(); 
		
		for (int i=0;i<n;i++)
			for(int m=0;m<n;m++)
				if(isFree(current, i, m))
					free++;
		
		if (p>free)
			return false;
		
		while (count<=p && p<=free) {
			j = generator.nextInt(n);
			k = generator.nextInt(n);
			if (isFree(current, j,k)) {
				placeLiz(current, j, k);
				count++;
			}
		}
		return true;
	}

	private boolean acceptState(double deltaE, double T) {
		double p = Math.exp(-deltaE/T);
		Random generator = new Random();
		double r = generator.nextDouble();
		if (Math.abs(r) < Math.abs(p)) {
			return true;
		}
		return false;
	}

	private int checkConflict(int[][] state) {
		int conflicts = 0;
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(isLizard(state, i,j)) {
					state[i][j] = 0;
					if (! safeToPlace(state, i,j)) {
						conflicts++;
					}
					state[i][j] = 1;
				}
			}
		}
		
		return conflicts;
	}

	private double schedule(double t) {
		Random generator = new Random();
		double c = generator.nextInt(10) + 1;
		double d = generator.nextInt(10) + 1;
		
		double T = c/((Math.log(t+d)));
		return T;
	}

	private void moveRandomLizard(int [][] next) {
		int ii = -1, ji = -1, i = -1,j = -1;

		Random generator = new Random(); 
		
		//select a random lizard
		while (true) {
			ii = generator.nextInt(n);
			ji = generator.nextInt(n);
			if (isLizard(next,ii,ji)) {
				break;
			}
		}
		
		// move this random lizard
		while (true) {
			i = generator.nextInt(n);
			j = generator.nextInt(n);
			if (isFree(next, i,j)) {
				placeLiz(next, i, j);
				next[ii][ji] = 0;
				break;
			}
		}
	}

	private void copyNursery(int [][] from, int [][] to) {
		int i,j;
		for(i=0;i<from.length;i++)
			for(j=0;j<from.length;j++)
				to[i][j] = from[i][j];
	}
	
	public static void main(String[] args) {
		//read the input file
		try {
			List<String> lines = Files.readAllLines(Paths.get(in_fl), StandardCharsets.UTF_8);
			algo = lines.get(0);
			n = Integer.parseInt(lines.get(1));
			p = Integer.parseInt(lines.get(2));
			List<String> subList = lines.subList(3, lines.size());
			nursery = new int[n][n];
			if (subList.size() != n)
				throw new IllegalArgumentException("Size of the matrix does not match n");
			for(int i=0; i<n;i++) {
				char row[] = subList.get(i).toCharArray();
				if (row.length != n)
					throw new IllegalArgumentException("Size of the matrix does not match n");
				for(int j=0; j< n; j++) {
					nursery[i][j] = row[j] - '0';
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lizards soln = new lizards();
		boolean result = false;
		if (algo.equals("DFS")) {
			result = soln.solve();
		}
		else if (algo.equals("BFS")) {
			solNursery = soln.solveBFS();
			if(solNursery != null) {
				result = true;
			}
		} else if (algo.equals("SA")) {
			solNursery = soln.solveSA();
			if(solNursery != null) {
				result = true;
			}
		}
		
		printNursery(result);
	}
}
