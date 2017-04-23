/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decode;

//import static ida.pkgtry.pkg2.encode.gadd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class reconstruct_data 
{
   
   public static void reconstruct(int[][] inv1,int[][] E,int n,int []lab,int u_id,int fid,String outputpath) throws Exception
   {
      
        int j,i;
        int [][]DE1=new int[n][n];
		int sum=0,mul,c,d,k;
		// constructing decoded data matrix using encoded matrix and inverse matrix
        for (c = 0; c < n; c++) 
        {
            for (d = 0; d < n; d++) 
            {
                for (k = 0; k < n; k++) 
                {
                  mul=gmul(inv1[c][k],E[k][d]);
                  sum = gadd(sum,mul);
                }
 
            DE1[c][d] = sum;
            sum = 0;
            }
        }
        System.out.println("decoded_data_matrix");
        for (d = 0; d < n; d++) 
            {
                for (k = 0; k < n; k++) 
                {
					System.out.print(DE1[d][k]);
					System.out.print(" ");
                }
                System.out.println("\n");
            }
                
		filesaved(DE1,u_id,fid,outputpath);
	}
  
	public static void filesaved(int DE1[][],int uid,int fid,String outputpath)
    {
		char chr;
		String s1="";
		String [][] converted = new String[DE1.length][];
		for(int index = 0; index < DE1.length; index++)
		{
        for(int subIndex = 0; subIndex < DE1[index].length; subIndex++)
           {
             chr=(char)DE1[index][subIndex];  
             s1=s1+chr;
           }
		}
		System.out.println("str"+s1);
		try 
		{
		System.out.println("===================================");
        System.out.println("path in filesaved="+outputpath);
		System.out.println("===================================");
	    File file = new File(outputpath+"\\user"+uid+"_"+fid+".txt");
        file.getParentFile().mkdirs();

	     // if file doesnt exists, then create it
	    if (!file.exists()) 
         {
		  file.createNewFile();
	     }

	     FileWriter fw = new FileWriter(file.getAbsoluteFile());
	     BufferedWriter bw = new BufferedWriter(fw);
	     bw.write(s1);
		 bw.close();
		 System.out.println("Done");
         } 
		catch (IOException e) 
		{
			e.printStackTrace();
		}    
	}
   
   public static int gadd(int a, int b) 
	{
		return a ^ b;
	}

	/* Subtract two numbers in a GF(2^8) finite field */
	static int gsub(int a, int b) 
	{
		return a ^ b;
	}
	static int gmul(int a, int b) 
	{
    int p = 0; /* the product of the multiplication */
    while (b!=0) 
		{
            if ((b & 1)!=0) /* if b is odd, then add the corresponding a to p (final product = sum of all a's corresponding to odd b's) */
                p ^= a; /* since we're in GF(2^m), addition is an XOR */

            if ((a & 0x80)!=0) /* GF modulo: if a >= 128, then it will overflow when shifted left, so reduce */
                a = (a << 1) ^ 0x11b; /* XOR with the primitive polynomial x^8 + x^4 + x^3 + x + 1 -- you can change it but it must be irreducible */
            else
                a <<= 1; /* equivalent to a*2 */
            b >>= 1; /* equivalent to b // 2 */
		}
    return p;
	}
}
