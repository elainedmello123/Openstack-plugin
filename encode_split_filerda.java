/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;

import java.io.File;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class encode_split_file
{
	public  static void readFile(String fileContents,int u_id,int fid,mongo.connection con) throws FileNotFoundException, IOException, Exception
     
    {
    
		int count=fileContents.length();
		int D[][]=data_mat(fileContents);
		int n=D[0].length,m;
        int c_mat[][]=c_matrix_gen.c_mat(n);
        m=n;
        int i=0;
        for( i=0;i<n+m;i++)
        {
            for(int j=0;j<n;j++)
            {
                System.out.print(c_mat[i][j]);
                System.out.print(" ");
            }
            System.out.println("\n");
        }
        int E[][]=encode.encode_matrix(c_mat,D,n);
        for( i=0;i<n+m;i++)
        {
            for(int j=0;j<n;j++)
            {
                System.out.print(E[i][j]);
                System.out.print(" ");
            }
            System.out.println("\n");
        }
        split_shards.testShards(E, n,u_id,fid,con);
        
  //reconstruct_data.filesaved(data);
 
 }
 //constructing square data matrix
	public static int[][] data_mat(String s)
	{
		int j=0,i,count=0;
		int asciInt[]=new int[s.length()];
        for (i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);
				asciInt[j] = (int)c; 
				j++;
				count++;
			}
        int c=1;
        //deciding square matrix size
        while(c*c<j)
        {
            c++;
            
        }
        System.out.println("c val"+c);
        int k=0;
        int D[][]=new int[c][c];
        for(i=0;i<c ;i++)
        {
            for(j=0;j<c;j++)
            {
                if(k<count)
                D[i][j]=asciInt[k];
                else
                    D[i][j]=0;
                k++;
            }
        }
        for(i=0;i<c ;i++)
        {
            for(j=0;j<c;j++)
            {
            System.out.print(D[i][j]);
            System.out.print(" ");
            }
            System.out.println("\n");
        }
        return D;
 }   
 }
