
/**
 * This is the main driver of the program. Allows users to create, load, write, and evaluate polynomials with different algorithms
 * 
 * @author Cody Moffitt
 * @version 1.0
 */
public class Program2Driver
{
   /**
   * This is the main method
   * @param args[] Not used in this program
   */
   public static void main(String args[])
   {
       System.out.println("Welcome to Program 2 by Cody Moffitt!");
       int choice = 0;
       Polynomial poly = null;
       while(choice != 10)
       {
           choice = printMenu();
           switch(choice)
             {
                 case 1: 
                           poly = genPoly();
                           break;
                 case 2:
                           showPoly(poly);
                           break;
                 case 3:
                           poly = readPolyFile(poly);
                           break;        
                 case 4:
                           writePolyFile(poly);
                           break;       
                 case 5:
                           runNaive(poly);
                           break;     
                 case 6:
                           runHorners(poly);
                           break;      
                 case 7:
                           runNaiveSquares(poly);
                           break;  
                 case 8:  
                           runFFT(poly);
                           break;
                 case 9:
                           genCSV();
                           break;
                 case 10:  
                           System.out.println("Goodbye!");
                           return;
             }
         Keyboard.getKeyboard().readString("Please press enter to continue");
       }
       
   }
   
   /**
   * Prints the menu and gets the user's choice
   * @return The user's choice as an int
   */
   public static int printMenu()
   {
       int choice = 0;
       System.out.println("\nPlease choose your task");
       System.out.println("1.  Generate Polynomial ");
       System.out.println("2.  Display current Polynomial ");
       System.out.println("3.  Read Polynomial from file ");
       System.out.println("4.  Write Polynomial to file");
       System.out.println("5.  Run naive algorithm to evaluate Polynomial");
       System.out.println("6.  Run Horner's algorithm to evaluate Polynomial");
       System.out.println("7.  Run naive algorithm with exponentiation by squaring to evaluate Polynomial");
       System.out.println("8.  Run Fast Fourier Transform to evaluate Polynomial");
       System.out.println("9.  Generate comprehensive csv files of running times and complex multiplications");
       System.out.println("10. Quit the program. ");
       while(choice < 1 || choice > 10)
       {
          choice = Keyboard.getKeyboard().readInt("\nPlease enter the number of your choice: ");
       }   
       return choice;
   }
   
   /**
   * Generates a random polynomial
   * @return The new polynomial
   */
   public static Polynomial genPoly()
   {
       int n = 0;
       while(n <= 0)
       {
           n = Keyboard.getKeyboard().readInt("\nPlease enter the degree of the polynomial you'd like to generate: ");
           n++; //When the Polynomial is generated it will be degree of n-1, with n terms, so we add 1 to get what the user wants
       }
       
       return new Polynomial(n);
   }
   
   /**
   * Prints the current polynomial
   * 
   */
   public static void showPoly(Polynomial poly)
   {
       if(poly == null)
          System.out.println("No Polynomial generated yet. Please load or generate one.");
       else   
       {
           System.out.println(poly + "\n");
       }
   }
   
   /**
   * Reads a Polynomial from a file
   * @return The Polynomial it read from the file
   */
   public static Polynomial readPolyFile(Polynomial poly)
   {
        String fileName = Keyboard.getKeyboard().readString("\nPlease enter the name of a Polynomial file to load: ");
        Polynomial X = null;
        try 
        {
           X = new Polynomial(fileName);
           if( X != null)
              poly = X;
           System.out.println("\nFile loaded.");
           return poly;
        }
        catch (FileIOException  e)
        {
            System.out.println("\n" + e.getMessage());
            return poly;
        }
   }
   
   
   /**
   * Writes a Polynomial to a file
   * @param poly The Polynomial
   */
   public static void writePolyFile(Polynomial poly)
   {
        
       if(poly == null)
           System.out.println("No Polynomial generated yet. Please load or generate one.");
       else
       {      
           try 
           {
              String fileName = Keyboard.getKeyboard().readString("\nPlease enter the file name you wish to write to: ");
              poly.writeFile(fileName);
              System.out.println("\nFile written.");
           }
           catch (FileIOException  e)
           {
               System.out.println("\n" + e.getMessage());
           }
       }
   }
   
   /**
   * Runs the naive algorithm on a Polyonomial, prints its results to screen
   * @param poly The Polynomial
   */
   public static void runNaive(Polynomial poly)
   {
       if(poly == null)
          System.out.println("No Polynomial generated yet. Please load or generate one.");
       else
       {
           System.out.println("Running Naive Algorithm for all nth roots of unity for n: " + (poly.getDegree()+1));
           Operations.measureTimeOfAlgPrint(1, poly);
       }
   }
   
   /**
   * Runs the Horner's algorithm on a Polyonomial, prints its results to screen
   * @param poly The Polynomial
   */
   public static void runHorners(Polynomial poly)
   {
       if(poly == null)
          System.out.println("No Polynomial generated yet. Please load or generate one.");
       else
       {
           System.out.println("Running Horner's Algorithm for all nth roots of unity for n: " + (poly.getDegree()+1));
           Operations.measureTimeOfAlgPrint(3, poly);
       }
   }
   
   /**
   * Runs the Naive algorithm with exponentiation by squares on a Polyonomial, prints its results to screen
   * @param poly The Polynomial
   */
   public static void runNaiveSquares(Polynomial poly)
   {
       if(poly == null)
          System.out.println("No Polynomial generated yet. Please load or generate one.");
       else
       {
           System.out.println("Running Naive Algorithm with exponentiation by squares for all nth roots of unity for n: " + (poly.getDegree()+1));
           Operations.measureTimeOfAlgPrint(2, poly);
       }
   }
   
   
   /**
   * Runs the FFT algorithm on a Polyonomial, prints its results to screen
   * @param poly The Polynomial
   */
   public static void runFFT(Polynomial poly)
   {
       if(poly == null)
          System.out.println("No Polynomial generated yet. Please load or generate one.");
       else
       {
           System.out.println("Running FFT with exponentiation by squares for all nth roots of unity for n: " + (poly.getDegree()+1));
           Operations.measureTimeOfAlgPrint(4, poly);
       }
   }
   
   /**
   * Generates two csv files with details on each algorithm, like running times, multiplications...
   * 
   */
   public static void genCSV()
   {
           try 
           {
              String timeFileName = Keyboard.getKeyboard().readString("\nPlease enter the file name you wish to write the times to: ");
              String multFileName = Keyboard.getKeyboard().readString("\nPlease enter the file name you wish to write the multiplication counts to: ");
              System.out.println("This could take a few minutes...");
              Graph.produceGraphs(timeFileName, multFileName);
              System.out.println("\nFiles written.");
           }
           catch (FileIOException  e)
           {
               System.out.println("\n" + e.getMessage());
           }
   }
}
