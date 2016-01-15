
/**
 * Graph creates a graph of times and a graph of multiplications used for each algorithm for this program.
 * 
 * @author Cody Moffitt 
 * @version 1.0
 */
public class Graph
{
    /**
    * Evaluates time and multiplications for each algorithm for random polys of size n at nth roots of unity
    * , and writes a .csv file containing them.
    * @param timeFileName The name you want to use for the time graph
    * @param mulFileName  The name you want to use for the graph of Complex number multiplications
    * 
    * @author Cody Moffitt 
    * @version 1.0
    */
    public static void produceGraphs(String timeFileName, String multFileName)
    {
        FileIO timeFile = new FileIO(timeFileName, FileIO.FOR_WRITING);
        FileIO multFile = new FileIO(multFileName, FileIO.FOR_WRITING);
        timeFile.writeLine("n, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048"); //Headers for each file
        multFile.writeLine("n, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048");
        for(int i = 1; i <= 4; i++)
        {
            String toWriteTime = "";
            String toWriteMult = "";
            
            switch(i) //Write the left most column
            {
                case 1: toWriteTime = "Naive Algorithm,";
                        toWriteMult = "Naive Algorithm,";
                        break;
                case 2: toWriteTime = "Naive By Squares Algorithm,";
                        toWriteMult = "Naive By Squares Algorithm,";
                        break;
                case 3: toWriteTime = "Horner's Algorithm,";
                        toWriteMult = "Horner's Algorithm,";
                        break;
                case 4: toWriteTime = "Fast Fourier Transform,";
                        toWriteMult = "Fast Fourier Transform,";
                        break; 
            }
            for(int j = 4; j <= 2048; j = j*2) //Get the data and write it to files
            {
                toWriteTime += Operations.measureTimeOfAlg(i, j) + ",";
                toWriteMult += Globals.mults + ",";
            }
            timeFile.writeLine(toWriteTime);
            multFile.writeLine(toWriteMult);
        }
        timeFile.close(); //close files
        multFile.close();
    }
}
