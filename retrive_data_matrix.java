/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decode;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import java.util.Arrays;
import java.util.StringTokenizer;

public class retrive_data_matrix 
{
     public static int[][] testShards(int u_id,int fid,int lab[],int n,mongo.connection con) throws Exception 
     { 
        
        final Mongo mongo = new Mongo(new DBAddress(con.getDbaddr(), con.getDbport(), "secretstorage")); 
		final DBCollection shardCollection = mongo.getDB("secretstorage").getCollection("collection"+fid); 
 
        String str[]=new String[100];
        String s1;
        int x=0,j=0,i=0;
        String splitString[]=new String[5];

		// retrieve encoded matrix from shards based on user id and file id

		BasicDBObject whereQuery = new BasicDBObject();
        DBCursor cursor=shardCollection.find();
		BasicDBObject fields = new BasicDBObject("val",1).append("_id",false);
		whereQuery.put("u_id",u_id);

        for(i=0;i<n;i++)
        { 
            whereQuery.put("key",fid+(lab[i]+1)*1000);
            cursor= shardCollection.find(whereQuery,fields);
            while(cursor.hasNext()) 
            {
				s1=cursor.next().toString();
				System.out.println("s1"+s1);
				splitString = s1.split(":",5);
				x=0;
				for(String w:splitString)
				{  
					splitString[x]=w;    
					x++;
				}  
           
				str[j]=splitString[1];
				j++;
              }
        }
      
		int count=n;
		int num[][]=new int[count][count];
		j=0;
		String s2;
		x=0;
		i=0;
		int y = 0;

		// parse string into integer

		for(i=0;i<count;i++)
        {
            x=str[i].length()-3;
            s2=str[i].substring(3,x);
            StringTokenizer st = new StringTokenizer(s2,", ");
            String S3;
            for(j =0;j<(count);j++)
            {
                try
                {
                    S3=st.nextToken().toString();
					num[i][j]=Integer.parseInt(S3);
               
					System.out.print(num[i][j]);
					System.out.print(" ");
                }
                catch(NumberFormatException nfe)
                {
					System.out.println("excp");
            
                }
                
            }
			System.out.println("\n");
        }
		return num;
   }
}