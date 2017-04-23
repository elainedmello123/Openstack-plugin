/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// package encode;


public class encode 
{
      public static int[][] encode_matrix(int C[][],int D1[][],int n)
	 {
        int j,i;
        int [][]E1=new int [n+n][n];
        int sum=0,mul,c,d,k;
        for (c = 0; c < n+n; c++) 
        {
			for (d = 0; d < n; d++) 
            {
				for (k = 0; k < n; k++) 
                {
                  mul=gmul(C[c][k],D1[k][d]);
                  sum = gadd(sum,mul);
                }
 
            E1[c][d] = sum;
            sum = 0;
            }
        }
            
        return E1;
    }
    
	static int gadd(int a, int b) 
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
