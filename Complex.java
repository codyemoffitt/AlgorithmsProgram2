import java.text.*;
/**
 * This class is a representation of a complex number as defined in mathematics.
 * 
 * @author Cody Moffitt
 * @version 1.0)
 */
public class Complex
{
    // instance variables
    private double real; //Real part of the complex number
    private double imaginary; //Imaginary part of the complex number

    /**
     * Constructor for objects of class Complex
     * @param real The real part of the complex number
     * @param imaginary The imaginary part of the complex number
     */
    public Complex(double real, double imaginary)
    {
        // initialise instance variables
        this.real = real;
        this.imaginary = imaginary;
    }
    
     /**
     * Constructor for objects of class Complex
     * @param real The real component of the Complex number, imaginary part will be 0i
     */
    public Complex(double real)
    {
        // initialise instance variables
        this.real = real;
        this.imaginary = 0.0;
    }
    
     /**
     * Overrides the toString method in object so we may print the complex number
     * @return Returns the complex number as a String
     */
    public String toString()
    {
        DecimalFormat dF = new DecimalFormat("##.##");
        String imagString = "";
        String realString = dF.format(0);
        if(real < -0.001 || real > 0.001) //Is it not "zero?"
        {
            if(real > 0)
               realString =  dF.format(real);
            else
               realString =  "-" + dF.format(-1 * real);
        }
        if(imaginary < -0.001 || imaginary > 0.001) //Is it not "zero?"
        {
            if(imaginary > 0)
               imagString =  " + " + dF.format(imaginary) + "i";
            else
               imagString =  " - " + dF.format(-1 * imaginary) + "i";
        }
        return realString + imagString; 
    }
    
     /**
     * Gets the real component of the complex number
     * @return real 
     */
    public double getReal()
    {
        return real;
    }
    
    /**
     * Gets the imaginary component of the complex number
     * @return imaginary
     */
    public double getImag()
    {
        return imaginary;
    }
    
    /**
     * Static method to add two complex numbers
     * @param x One of the complex numbers
     * @param y The other complex number
     * @return The sum of two complex numbers 
     */
    public static Complex add(Complex x, Complex y)
    {
        return new Complex((x.getReal()+y.getReal()), (x.getImag() + y.getImag()));
    }
    
    /**
     * Static method to add a complex number and a double
     * @param x The double
     * @param y The complex number
     * @return The sum of two numbers
     */
    public static Complex add(double x, Complex y)
    {
        return add(new Complex(x), y);
    }
    
    /**
     * Static method to add a complex number and a double
     * @param x The complex number
     * @param y The double
     * @return The sum of two numbers
     */
    public static Complex add(Complex x, double y)
    {
        return add(y, x); //Order doesn't matter, calls the method above to add
    }
    
    /**
     * Static method to multiply two complex numbers
     * @param x The complex number
     * @param y The other complex number
     * @return The result of multiplication
     */
    public static Complex mult(Complex x, Complex y)
    {
        //FOIL
        double firstTerms = x.getReal()*y.getReal();
        double secondTerms = x.getReal()*y.getImag();
        double thirdTerms = y.getReal()*x.getImag();
        double fourthTerms = -(x.getImag()*y.getImag()); //i^2 portion, i^2 = -1 
        Globals.mults++; //To count the number of multiplications
        return new Complex((firstTerms+fourthTerms),(secondTerms+thirdTerms));
    }
    
    /**
     * Static method to multiply a complex number and a double
     * @param x The double
     * @param y The complex number
     * @return The result of multiplication
     */
    public static Complex mult(double x, Complex y)
    {
       return mult(new Complex(x), y);    
    }
    
    /**
     * Static method to multiply a complex number and a double
     * @param x The complex number
     * @param y The double
     * @return The result of multiplication
     */
    public static Complex mult(Complex x, double y)
    {
        return mult(y, x); //Call the method just above this one, since order matters not for multiplication
    }
    
    /**
     * Static method to divide complex numbers
     * @param x The complex number to be divided
     * @param y The complex to divide by
     * @return The result of division
     */
    public static Complex div(Complex x, Complex y) //x is divided by y
    {
        Complex conjugate = new Complex(y.getReal(), -(y.getImag()));
        Complex top = mult(x, conjugate);
        Complex bottom = mult(y, conjugate);
        return new Complex((top.getReal()/bottom.getReal()), (top.getImag()/bottom.getReal()));
    }
    
    /**
     * Static method to divide complex numbers and doubles
     * @param x The double to divide
     * @param y The complex divisor
     * @return The result of division
     */
    public static Complex div(double x, Complex y) //x is divided by y
    {
        return div(new Complex(x), y);
    }
    
    /**
     * Static method to divide doubles and complex numbers
     * @param x The complex number to divide
     * @param y The double to divide by
     * @return The result of division
     */
    public static Complex div(Complex x, double y) //x is divided by y
    {
        return div(x, new Complex(y));
    }
    
    /**
     * Static method to subtract complex numbers
     * @param x The complex number subtract from
     * @param y The complex being subtracted from x
     * @return The result of subtraction
     */
    public static Complex sub(Complex x, Complex y) //Subtract y from x
    {
        return add(x, new Complex(-(y.getReal()), -(y.getImag())));
    }
    
    /**
     * Static method to subtract complex numbers from doubles
     * @param x The double to subtract from
     * @param y The complex being subtracted from x
     * @return The result of subtraction
     */
    public static Complex sub(double x, Complex y) //Subtract y from x
    {
        return add(new Complex(x), new Complex(-(y.getReal()), -(y.getImag())));
    }
    
    /**
     * Static method to subtract doubles from complex numbers
     * @param x The complex to subtract from
     * @param y The double being subtracted from x
     * @return The result of subtraction
     */
    public static Complex sub(Complex x, double y) //Subtract y from x
    {
        return add(x, new Complex(-y));
    }
    
    /**
     * Method to see if two complex numbers are equal
     * @param x The complex number we are comparing this to
     * @return True if they are equal, false if they are not
     */
    public boolean equals(Complex x)
    {
        if(this.real == x.getReal() && this.imaginary == x.getImag())
           return true;
        else
           return false;
    }


}
