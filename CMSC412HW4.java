// Author: Hayden Migliore
// Date: September 13, 2019
// Class: CMSC 412 6380
// CMSC412HW4 - Implement the Banker's algorithm for deadlock avoidance, 
// that works on a given set of N processes and M resource types (N<10,M<10). 
// Use C/C++/C# or Java for the implementation, with a simple text interface, 
// where the user enters only the name of the input file (text only). The 
// program reads all the necessary input data from that file.
// The input data and result is then displayed on the screen.
// You may use your program to validate the example you gave in the Week 4 
// discussion.

package cmsc412hw4;
import java.io.File; 
import java.util.*;

public class CMSC412HW4 {

    public static void main(String[] args) {
        //Intialize n and m to be read
        int n = 0;
        int m = 0;
        
        //Get fileName from user input
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the input file name.");
        String fileName = in.nextLine();
        
        try{
            //Read file
            File file = new File("C:\\Users\\Hayden\\Documents\\NetBeansProjects\\CMSC412HW4\\" + fileName); 
            Scanner sc = new Scanner(file);
            
            //Set n process and m resources
            n = sc.nextInt();
            m = sc.nextInt();
            
            //Create arrays using n and m
            int[] availableResources = new int[m];
            int[][] maxClaim = new int[n][m];
            int[][] allocation = new int[n][m];
            int[][] need = new int[n][m];
            
            //Fill arrays
            for (int i = 0; i < m; i++){
                availableResources[i] = sc.nextInt();
            }
            for (int a = 0; a < n; a++){
                for (int b = 0; b < m; b++){
                    maxClaim[a][b] = sc.nextInt();
                }
                for (int c = 0; c < m; c++){
                    allocation[a][c] = sc.nextInt();
                }
                for (int d = 0; d < m; d++){
                    need[a][d] = maxClaim[a][d] - allocation[a][d];
                }
            }
            
            //Print the input data
            System.out.println("Number of processes: " + n);
            System.out.println("Number of resources: " + m);
            System.out.println("Available Resources:");
            System.out.println(Arrays.toString(availableResources));
            System.out.println("Max Claim:");
            System.out.println(Arrays.deepToString(maxClaim));
            System.out.println("Allocation:");
            System.out.println(Arrays.deepToString(allocation));
            System.out.println("Need:");
            System.out.println(Arrays.deepToString(need));
            
            //Intialize empty vector and bool array to pass to Bankers 
            Vector<Integer> safeSequence = new Vector<Integer>();
            boolean[] inSequence = new boolean[n];
            
            //Begin Bankers
            System.out.println("Safe sequences are: ");
            Bankers(availableResources, allocation, need, n, m, safeSequence, inSequence);
            
        }
        catch(Exception e){
            System.out.println("Error reading file.");
        }
    }
    
    static void Bankers(int[] availableResources, int[][] allocation, int[][] need, 
            int n, int m, Vector<Integer> safeSequence, boolean[] inSequence){
        
        for(int i = 0; i < n; i++){
            //If not already in use and can be satisfied with available resources
            if (!inSequence[i] && BankersCalc(availableResources, need, m, i)){
                
                //Program i is now in use
                inSequence[i] = true;
                
                //Add allocated resources to available resources
                for (int k = 0; k < m; k++){
                    availableResources[k] += allocation[i][k];
                }
                
                //add program to safe sequence
                safeSequence.add(i);
                
                //Call bankers recursively to find next program in safe sequence
                Bankers(availableResources, allocation, need, n, m, safeSequence, inSequence);
                
                //Reset to move on to next program
                safeSequence.removeElementAt(safeSequence.size() - 1); 
                for (int k = 0; k < m; k++){
                    availableResources[k] -= allocation[i][k];
                }
                inSequence[i] = false;
            }
        }
        //Check if full safe sequence has been found
        if(safeSequence.size() == n){
            //Print results
            System.out.print("[");
            for (int i : safeSequence){
                System.out.print((i + 1) + ", ");
            }
            System.out.print("]\n");
        }
        
    }
    
    static boolean BankersCalc(int[] availableResources, int[][] need, int m, int i){     
        //Check if program can be satisfied by avaiable resources
        boolean answer = true;
        for (int j = 0; j < m; j++){
            if (need[i][j] > availableResources[j]){
                    answer = false;
            }
        }
        return answer;
    }
}


