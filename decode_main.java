/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mongo.connection;


public class decode_main extends HttpServlet 
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
        response.setContentType("text/html;charset=UTF-8");
        try
        {   
			int i,j;
			System.out.println("******************"); 
            ServletContext context = getServletContext();
            String dbaddress =context.getInitParameter("dbaddress");
            int dbport =Integer.parseInt(context.getInitParameter("dbport"));
            mongo.connection con=new connection();
            con.setDbaddr(dbaddress);
            con.setDbport(dbport);
            System.out.println("******************");    
            String path = getServletContext().getRealPath("/");
            System.out.println("pp="+path);     
            String outputpath=path+"Output";
            File f = new File (path +"Output");
            f.mkdir();
            System.out.println("Check1 download_java");
            HttpSession session = request.getSession();
            int uid=(int) session.getAttribute("uid");
            //String s=request.getParameter("fid");
            int fid=Integer.parseInt(request.getParameter("fid"));
            System.out.println("uid="+uid);
            System.out.println("fid="+fid);
            int n=decode.decode_data(uid,fid,con);
            System.out.println("n in decode_main:"+n);

			// generating n random key values(=threshold value) to retrive perticular row of encoded matrix 
            int label[]=new int[n];
            label=randomlabelgenerator(n);
      
        
			//generating inverse matrix correspoding to rows of encoded matrix which we are retrieving from shards
            int inv[][]=inverse_matrix_cal.inverse(n,label);
			System.out.println("inverse matrix");
			for(i=0;i<n;i++)
			{ 
				for(j=0;j<n;j++)
				{
					System.out.print(inv[i][j]);
					System.out.print(" ");
				}
				System.out.println("\n");
			}


			//retrieving encoded matrix from shards
			int encoded_data[][]=retrive_data_matrix.testShards(uid,fid,label,n,con);
			System.out.println("encoded matrix");
			for(i=0;i<n;i++)
			{ 
				for(j=0;j<n;j++)
				{
                System.out.print(encoded_data[i][j]);
                System.out.print(" ");
				}
				System.out.println("\n");
			}
			
			// reconstruction of original data 
			reconstruct_data.reconstruct(inv,encoded_data,n,label,uid,fid,outputpath);
			response.sendRedirect("ServletDownloadDemo?fid="+fid);
		 }
         catch(Exception e)
         {
         
         } 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
		{
        processRequest(request, response);
		}

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
		{
        processRequest(request, response);
		}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
   public int[] randomlabelgenerator(int n)
   {
    final Random random = new Random();
	final Set<Integer> intSet = new HashSet<>();
    while (intSet.size() < n) 
	{
      intSet.add(random.nextInt(n+2) + 1);
    }
    final int[] ints = new int[intSet.size()];
    final Iterator<Integer> iter = intSet.iterator();
    for (int i = 0; iter.hasNext(); ++i) 
	{
        ints[i] = iter.next();
    }
    Arrays.sort(ints);
    System.out.println(Arrays.toString(ints));
    return ints;
    }
    
	@Override
    public String getServletInfo() 
		{
        return "Short description";
		}// </editor-fold>
}
