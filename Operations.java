
/**
 * This class contains all the operations on polynomials for this program.
 * 
 * @author Cody Moffitt
 * @version 1.0
 */
public class Operations
{
    /**
    * Static method to naively evaluate a polynomial with a certain x(eval)
    * @param eval Complex number to use for x in the polynomial
    * @param poly The polynomial to evaluate
    * @return The result of evaluation as a complex number
    */
    public static Complex naiveEvaluation(Complex eval, Polynomial poly)
    {
        Complex[] coefficients = poly.getCoefficients();
        int degree = poly.getDegree();
        int tempDegree = 0;
        Complex result = new Complex(0.0);
        for(int i = 0; i <= degree; i++)
        {
            result = Complex.add(result, Complex.mult(coefficients[i], pow(eval, tempDegree))); //WRITE POWER
            tempDegree++;
        }
        
        return result;
    }
    
    /**
    * Static method to naively evaluate a polynomial with a certain x(eval). Uses exponentiation by squaring
    * @param eval Complex number to use for x in the polynomial
    * @param poly The polynomial to evaluate
    * @return The result of evaluation as a complex number
    */
    
    public static Complex naiveEvaluationBySquares(Complex eval, Polynomial poly)
    {
        Complex[] coefficients = poly.getCoefficients();
        int degree = poly.getDegree();
        int tempDegree = 0;
        Complex result = new Complex(0.0);
        for(int i = 0; i <= degree; i++)
        {
            result = Complex.add(result, Complex.mult(coefficients[i], powBySquares(eval, tempDegree))); //WRITE POWER
            tempDegree++;
        }
        
        return result;
    }
    
    /**
    * Static method to get the value of a complex number if raised to a power
    * @param a The complex number
    * @param toDegree The degree to raise it to
    * @return a^(toDegree)
    */
    public static Complex pow(Complex a, int toDegree)
    {
        Complex result = a;
        if(toDegree == 0)
           return new Complex(1.0);
        for(int i = 0; i < toDegree-1; i++)
        {
            result = Complex.mult(result, a);
        }
        return result;
   }
   
   /**
    * Static method to get the value of a complex number if raised to a power. Uses exponentiation by squares.
    * @param a The complex number
    * @param toDegree The degree to raise it to
    * @return a^(toDegree)
    */
   public static Complex powBySquares(Complex a, int toDegree)
    {
        Complex result = a;
        if(toDegree == 0)
           return new Complex(1.0);
        else if(toDegree == 1)
           return result;
        else if(toDegree > 1 && toDegree % 2.0 == 0) //Degree is even
        {
            return powBySquares(Complex.mult(result, result), toDegree/2);
        }
        else //Degree is odd
        {
            return Complex.mult(result, powBySquares(Complex.mult(result, result), (toDegree-1)/2));
        }
   }

   /**
    * Static method to evaluate a polynomial with a certain x(eval). Uses Horner's algorithm.
    * @param eval Complex number to use for x in the polynomial
    * @param poly The polynomial to evaluate
    * @return The result of evaluation as a complex number
    */
    public static Complex hornersEvaluation(Complex eval, Polynomial poly)
    {
        Complex[] coefficients = poly.getCoefficients();
        Complex result = new Complex(0);
        for(int i = coefficients.length - 1; i  >= 0; i--)
        {
            result = Complex.add(Complex.mult(result, eval), coefficients[i]);            
        }
        return result;
    }
    
    /**
    * Static method to get the nth roots of unity for a certain n
    * @param n The n to get the nth roots of unity of
    * @return results An array of Complex numbers, with all the nth roots of unity for that n
    */
    public static Complex[] rootsOfUnity(int n)
    {
        Complex[] result = new Complex[n];
        for(int k = 0; k < n; k++)
        {
            double real = Math.cos(2.0*k*Math.PI/n);
            double imag = Math.sin(2.0*k*Math.PI/n);
            result[k] = new Complex(real, imag);
        }
        return result;
    }
    
    /**
    * Static method to evaluate a polynomial at all its nth roots of unity using Fast Fourier Transform. 
    * This one calls the actual method, giving it the right parameters, and error checking.
    * The polynomial must have a number of terms that is a power of 2. 
    * @param poly The polynomial to evaluate
    * @return The result of the evaluations as an array of Complex numbers
    */
    public static Complex[] fftStart(Polynomial poly)
    {
        Complex[] coefficients = poly.getCoefficients();
        int degree = poly.getDegree();
        if((((degree+1) & (degree)) != 0) || degree+1 <= 1) //The most awesome way to check if a number is a power of two, stolen liberally from the internet
        {
            System.out.println("N must be a power of 2");
            return null;
        }
        return fft(degree+1, coefficients);
    }
    
    /**
    * Static method to naively evaluate a polynomial at all its nth roots of unity using Fast Fourier Transform. 
    * This one calls the actual method, giving it the right parameters, and error checking.
    * The polynomial must have a number of terms that is a power of 2. 
    * This function is recursive
    * @param n The n to get the nth roots of unity of, also the number of coefficient terms
    * @param terms An array of Complex numbers with all of the polynomial to evaulate's terms
    * @return The result of the evaluations as an array of Complex numbers
    */
    public static Complex[] fft(int n, Complex[] terms)
    {
        if( n == 1 )
        {
            return terms; //a sub 0
        }
       Complex[] evens = new Complex[n/2];
       Complex[] odds  = new Complex[n/2];
       //populate evens
       int j = 0;
       for(int i = 0; i <= n-2; i += 2)
       {
           evens[j] = terms[i];
           j++;
       }
       //Populate odds
       j = 0;
       for(int i = 1; i <= n-1; i += 2)
       {
           odds[j] = terms[i];
           j++;
       }
       
       Complex[] e = fft(n/2, evens);
       Complex[] d = fft(n/2, odds);
       Complex[] y = new Complex[n];
       for(int k = 0; k <= ((n/2)-1); k++)
       {   
           Complex wTok = new Complex(Math.cos(2*Math.PI*k/n), Math.sin(2*Math.PI*k/n));
           y[k] = Complex.add(e[k], Complex.mult(wTok, d[k]));
           y[k+n/2] = Complex.sub(e[k], Complex.mult(wTok, d[k]));
       }
       return y;
    }
    
    /**
    * Method to print the amount of time, results, and number of Complex number multiplications for a given algorithm and polynomial.
    * @param alg The algorith to use. 1 is naive, 2 is naive by squaring, 3 is Horner's, 4 is FFT
    * @param poly The polynomial to evaluate
    * @return The amount of time it took the algorithm to execute in nanoseconds
    */
    public static long measureTimeOfAlgPrint(int alg, Polynomial poly) //Prints results as it goes, it is cumbersome.
    {
        int nForRoots = poly.getDegree()+1;
        Complex[] roots = rootsOfUnity(nForRoots);
        Globals.clear();
        long startTime = System.nanoTime(); //Start recording times
        for(int i = 0; i < nForRoots; i++)
        {
            Complex eval = roots[i]; //For evaluation of this polynomial at each root of unity
            switch(alg)
            {
                case 1:
                        System.out.println("Root of unity : " + roots[i]);
                        System.out.println("Result: " + naiveEvaluation(eval, poly));
                        break;
                case 2:
                        System.out.println("Root of unity : " + roots[i]);
                        System.out.println("Result: " + naiveEvaluationBySquares(eval, poly));
                        break;
                case 3:
                        System.out.println("Root of unity : " + roots[i]);
                        System.out.println("Result: " + hornersEvaluation(eval, poly));
                        break;
                case 4:
                        Complex[] results = fftStart(poly);
                        if(results == null) //FFT can't handle Polynomials with less than 2 terms or the number of terms isn't a power of two
                           return 0;
                        String rootsString = "" + roots[0];
                        String resultsString = "";
                        for(int x = 1; x < roots.length; x++)
                           rootsString += ", " + roots[x];
                        System.out.println("Roots of unity : " + rootsString);
                        resultsString = "" + results[0];
                        for(int x = 1; x < results.length; x++)
                           resultsString += ", " + results[x];
                        System.out.println("Results : " + resultsString);
                        i = nForRoots; //fft already computes nth roots of unity
                        break;
            }
        }
        long endTime = System.nanoTime(); //End recording times
        System.out.println("Number of multiplications for this algorithm for all of the roots of unity for n = " + nForRoots + ": " + Globals.mults);
        
        long totalTime = endTime - startTime;
        System.out.println("Total time: " + totalTime + " nanoseconds");
        return totalTime;
    }
    
    /**
    * Method to measure time and number of multiplications for a certain algorithm. 
    * Evaluates random polynomials at all nth roots of unity.
    * Does not print info to screen.
    * @param alg The algorith to use. 1 is naive, 2 is naive by squaring, 3 is Horner's, 4 is FFT
    * @param nForRoots The n to use when getting the nth roots of unity
    * @return The amount of time it took the algorithm to execute in nanoseconds
    */
    public static long measureTimeOfAlg(int alg, int nForRoots) //Does not print results
    {
        Complex[] roots = rootsOfUnity(nForRoots);
        Globals.clear();
        if(nForRoots < 2)
        {
            System.out.println("N must be atleast 2");
            return 0;
        }
        long startTime = System.nanoTime(); //Start recording times
        for(int i = 0; i < nForRoots; i++)
        {
            Complex eval = roots[i]; //For evaluation of this polynomial at each root of unity
            switch(alg)
            {
                case 1:

                        naiveEvaluation(eval, new Polynomial(nForRoots));
                        break;
                case 2:
                        
                        naiveEvaluationBySquares(eval, new Polynomial(nForRoots));
                        break;
                case 3:
                        
                        hornersEvaluation(eval, new Polynomial(nForRoots));
                        break;
                case 4:
                        fftStart(new Polynomial(nForRoots));
                        i = nForRoots; //fft already computes nth roots of unity
                        break;
            }
        }
        long endTime = System.nanoTime(); //End recording times
        long totalTime = endTime - startTime;
        return totalTime;
    }
}
