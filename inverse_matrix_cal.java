/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class inverse_matrix_cal 
{
    public static int[][] inverse(int n,int []lab)
    {
        int C[][]=new int[n][n];
        int T[][]=new int[n][n];
        int inverse[][]=new int[n][n];
       
		
        
        int [][] c_mat=new int[n+n][n];
        
        //generating C matrix based on labels
        int i,j;
        int index=3,mul=1;
        for(i=0; i<n+n; i++)
		{
			for(j=0; j<n; j++)
			{
                if(j==0)
                {
                    c_mat[i][j]=1;
                }
                if(j==1)
                {
                    c_mat[i][j]=index;
                }
                if(j>1)
                {
                    mul=index;
                    for(int k=1;k<j;k++)
                    {
                      mul=gmul(mul,index);
                    }
                    c_mat[i][j]=mul;
                }
            }
            index++;
        }
        int [][]mat=new int[n][n];
        int q=0,D=0;
        

		// C matrix corresponding to rows selected for retrieving data from shards
        for(i=0;i<n;i++)
        {
            for(j=0;j<n;j++)
            {
                mat[i][j]=c_mat[lab[i]][j];
            }
        }
        
		/*System.out.println("secret matrix for inverse");
        for( i=0; i<n; i++)
		{
			for(j=0; j<n; j++)
			{
				System.out.print(mat[i][j]);
                System.out.print(" ");
            }
            System.out.println("\n");

        } */

         q=determinant(mat,n);
         mul=0;
         System.out.println("val of q="+q);
         for(i=1;i<256;i++)
         {
             mul=gmul(i,q);
         if(mul==1)
             D=i;
          }
         System.out.println("val of D="+D);
         C=cofactor(mat,n);
		 inverse=inverse(C,n,D);
		 for(i=0; i<n; i++)
		 {
			for( j=0; j<n; j++)
			{
				System.out.print(inverse[i][j]);
				System.out.print(" ");
            }
            System.out.print("\n");
          }
		 return inverse;
    }
    
	
	public static int[][] cofactor(int num[][], int f)
	{
		int b[][]=new int [100][100];
		int fac[][]=new int [100][100];
		int p, q, m, n, i, j;
		for (q = 0;q < f; q++)
		{
			for (p = 0;p < f; p++)
				{
					m = 0;
					n = 0;
					for (i = 0;i < f; i++)
					{
						for (j = 0;j < f; j++)
						{
							if (i != q && j != p)
							{
								 b[m][n] = num[i][j];
							if (n < (f - 2))
								n++;
							else
								{
								n = 0;
								m++;
								}
							}
						}
					}
     
				  fac[q][p] =  determinant(b, f - 1);
				}
		}
		return fac;
	}
 
	public static int determinant(int A[][],int N)
	{
		int det=0,u,v,s,t,w;
		if(N == 1)
			{
			det = A[0][0];
			}
		else if (N == 2)
			{
			t=gmul(A[0][0],A[1][1]);
            w=gmul(A[1][0],A[0][1]);
            det = gadd(t,w);
			}
		else
			{
			det=0;
			for(int j1=0;j1<N;j1++)
			{
				int[][] m = new int[N-1][];
				for(int k=0;k<(N-1);k++)
				{
					m[k] = new int[N-1];
				}
				for(int i=1;i<N;i++)
				{	
					int j2=0;
					for(int j=0;j<N;j++)
					{
						if(j == j1)
							continue;
						m[i-1][j2] = A[i][j];
						j2++;
					}
				}
				s=gmul(A[0][j1],determinant(m,N-1));
                det=gadd(det,s);
            }
			return det;
}

public static int [][] transpose(int t[][],int n)
{
    int c,d;
    int transpose[][]=new int[n][n];
    for ( c = 0 ; c < n ; c++ )
      {
         for ( d = 0 ; d < n ; d++ )               
            transpose[d][c] = t[c][d];
      }
 
      System.out.println("Transpose of entered matrix:-");
 
      for ( c = 0 ; c < n ; c++ )
      {
         for ( d = 0 ; d < n; d++ )
               System.out.print(transpose[c][d]+"\t");
 
         System.out.print("\n");
      }
      return transpose;
}

public static int [][] inverse(int i[][],int n,int D)
{
    int c,d;
    int inv[][]=new int[n][n];
    for ( c = 0 ; c < n ; c++ )
      {
         for ( d = 0 ; d < n ; d++ )               
            //inv[d][c] = i[c][d]*D;
             inv[d][c] =gmul(i[c][d],D);
      }
 
      System.out.println("In INVERSE");
      return inv;
}
/* addition  and multiplication of two numbers in a GF(2^8) finite field */
	static int gadd(int a, int b) 
	{
		return a ^ b;
	}


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
            b >>= 1; /* equivalent to b / 2 */
			}
		return p;
	}
    
}
