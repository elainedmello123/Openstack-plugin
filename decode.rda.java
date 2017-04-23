/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decode;

import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import java.util.Arrays;
import java.util.StringTokenizer;

public class decode 
{
     public static int decode_data(int u_id,int fid,mongo.connection con) throws Exception 
	 { 
		//System.out.println("From getter decode: dbAddr:"+con.getDbaddr()+"\\dbport:"+con.getDbport());
            
        final Mongo mongo = new Mongo(new DBAddress(con.getDbaddr(), con.getDbport(), "secretstorage")); 
		int index=1,i,j=0;
        final BasicDBObject ret = new BasicDBObject();
        DB db = mongo.getDB( "secretstorage" );
        DBCollection collection = db.getCollection("collection"+fid);
    
  
        String str[]=new String[10];
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("u_id",u_id);
       
        BasicDBObject field = new BasicDBObject("n_value",1).append("_id",false);
       
        DBCursor cursor1 = collection.find(whereQuery,field);
        String s1,s2;
        int x=0,y=0,n1=0,z;
        String splitString[]=new String[4];
        String splitString1[]=new String[5];
      
    //retrieve threshold value n corresponding to user id and file id    
        while(cursor1.hasNext())
        { 
          
            s2=cursor1.next().toString();
			y=0;
            for(String w:s2.split(":"))
			{  
             splitString1[y]=w;    
             y++;
            }
          
            s2=splitString1[1];
            System.out.println("str"+s2);
            s2=s2.trim();
            z=s2.length()-1;
            s2=s2.substring(0,z);
			n1=(int) Double.parseDouble(s2);
			System.out.print("n="+n1);
        }
        return n1;
	}
}
