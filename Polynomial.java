  import java.util.Random;
  import java.lang.Math;
  
/**
 * This is a class representing a polynomial with complex coefficients
 * 
 * @author Cody Moffitt
 * @version 1.0
 */
public class Polynomial
{
    // instance variables 
    private Complex[] coefficients; //Array of coefficients of the polynomial
    private int degree; //The degree of the polynomial

    /**
     * Constructor for objects of class Polynomial
     */
    public Polynomial(int degree)
    {
        // initialise instance variables
        this.degree = degree-1; //Degree of n-1
       if(degree+1 <= 0)
       {
           System.out.println("N needs to be greater than 0");
           return;
       }
        coefficients = new Complex[this.degree+1];
        //Random number generation, filling in coefficients
        Random generator = new Random();
        for(int i = 0; i < this.degree+1; i++)
        {
            coefficients[i] = new Complex((generator.nextInt(21)-10)); //Get a Complex number with 0i and a real part from 0 to 10
        }
    }
    
     /**
     * Constructor for objects of class Polynomial, loads the polynomial from a file
     * @throws FileIOException
     */
    public Polynomial(String fileName) throws FileIOException
    {
       try
       {
           FileIO file = new FileIO(fileName, FileIO.FOR_READING);
           this.degree = Integer.parseInt(file.readLine()) - 1;
           coefficients = new Complex[this.degree+1];
           if(degree+1 <= 0)
           {
               throw new FileIOException("N needs to be greater than 0");
               
           }

           for(int i = 0; i <= degree && !(file.EOF()); i++)
           {
               String line = file.readLine();
               System.out.println(line);
               String[] realImag = line.split(",");
               coefficients[i] = new Complex(Double.parseDouble(realImag[0]), Double.parseDouble(realImag[1]));
           }
           file.close();
           
       }
       catch(FileIOException e)
       {
           throw e;
       }
       catch(RuntimeException e)
       {
           throw new FileIOException("File not found");
       }
    }
    
     /**
     * Writes a polynomial to a file
     * @param fileName The name of the file to create/write to
     * @throws FileIOException
     */
    public void writeFile(String fileName) throws FileIOException
    {
       try
       {
           FileIO file = new FileIO(fileName, FileIO.FOR_WRITING);
           int n = degree+1;
           file.writeLine(n + "");
           for(int i = 0; i <= degree; i++)
           {
               file.writeLine(coefficients[i].getReal() + "," + coefficients[i].getImag()); 
               //If I wanted to write them as whole numbers(ex. 5 instead of 5.0), like in the files given, I would cast real and imaginary as int, but lose accuracy
           }
           file.close();
           
       }
       catch(FileIOException e)
       {
           throw e;
       }
       catch(RuntimeException e)
       {
           throw new FileIOException("File IO error");
       }
    }
    
    /**
     * The toString override for polynomials
     * @return A string describing the polynomial
     * 
     */
    public String toString()
    {
        String returnString = "";
        int tempDegree = 0; //Increase degrees as we go through coefficents from 0 to degree+1
        for(int i = 0; i <= degree; i++)
        {
            if(tempDegree > 1 && tempDegree == degree)
            {
                returnString += "(" + coefficients[i].toString() + ")" + "(" + "x" + ")^" + tempDegree; //If its the last term, don't add the plus
            }
            else if(tempDegree > 1 && tempDegree != degree)
            {
                returnString += "(" + coefficients[i].toString() + ")" + "(" + "x" + ")^" + tempDegree + " + ";
            }
            else if(tempDegree == 1 && degree > 1) //x^1 just looks dumb
            {
                returnString += "(" + coefficients[i].toString() + ")" + "x" + " + ";
            }
            else if(tempDegree == 1 && degree == 1) //x^1 just looks dumb
            {
                returnString += "(" + coefficients[i].toString() + ")" + "x"; //If its the last term, don't add the plus
            }
            else if(tempDegree == 0 && degree == 0)//if tempDegree == 0, x^0 is always one except when x = 0, so we don't need x
            {
                   returnString += "(" + coefficients[i].toString() + ")"; //If its the only term, don't add a + sign
            }
            else if(tempDegree == 0 && degree > 0 )//if tempDegree == 0, x^0 is always one except when x = 0, so we don't need x
            {
                   returnString += "(" + coefficients[i].toString() + ") + "; //If there are more terms, add the plus sign
            }
            tempDegree++;
        }
        return returnString;
    }
    
    /**
    * Gets the coefficients of the polynomial as an array of complex numbers
    * @return An array of Complex numbers
    * 
    */
    public Complex[] getCoefficients()
    {
        return coefficients;
    }
   
    /**
    * Gets the degree of this polynomial
    * @return degree
    * 
    */
    public int getDegree()
    {
        return degree;
    }
    
    
    
    
}
