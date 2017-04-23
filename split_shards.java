/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;

//import com.mongodb.DBAddress;
import com.mongodb.DB; 
import com.mongodb.Mongo; 
import com.mongodb.DBCollection; 
import com.mongodb.DBAddress; 
import com.mongodb.ServerAddress; 
import com.mongodb.DBObject; 
import com.mongodb.BasicDBObject; 
import com.mongodb.CommandResult; 
import com.mongodb.DBCursor;
import java.util.Arrays;
import java.util.StringTokenizer;
import mongo.connection;


public class split_shards
{
    public static void testShards(int E[][],int n,int u_id,int fid,mongo.connection con) throws Exception 
		{ 
			//connect mongodb database and collection 
			final Mongo mongo = new Mongo(new DBAddress(con.getDbaddr(), con.getDbport(), "secretstorage")); 
			final DBCollection shardCollection = mongo.getDB("secretstorage").getCollection("collection"+fid); 
			CommandResult result = mongo.getDB("admin").command(new BasicDBObject("enablesharding", "secretstorage"));
			System.out.println(result);
			
			//define shardkey of collection which is "key" attribute 

			final BasicDBObject shardKey = new BasicDBObject("key", 1);
			final BasicDBObject cmd = new BasicDBObject("shardcollection", "secretstorage.collection"+fid);
			cmd.put("key", shardKey);
			result = mongo.getDB("admin").command(cmd);
			
			//split based on middle value of threshold value
			final BasicDBObject re = new BasicDBObject("split", "secretstorage.collection"+fid);
			final BasicDBObject mid = new BasicDBObject("key", n*1000+fid);
			re.put("middle", mid);
			CommandResult result3 = mongo.getDB("admin").command(re);
			System.out.println(result3);
			// Write some data 
			int index=1,i;
			String[] stringArray;
			int m=E[0].length;
			stringArray = new String[m+m];
			
			//converting matrix to string array
			for( i = 0; i < m+m; i++)
			{
				stringArray[i] = Arrays.toString(E[i]);
                System.out.println(stringArray[i]);
			}
		
			//insert values(user id,file id, threshold  value,encoded matrix rows) into database collection 
	        for(int idx=1;idx<=m+m;idx++)
            {
	             final BasicDBObject entry = new BasicDBObject("u_id",u_id);
		         entry.put("n_value",n);
				 entry.put("key",fid+idx*1000);
				 entry.put("val",stringArray[idx-1]);
				 shardCollection.insert(entry);
            }
            System.out.print("data splited safely");
      
    
        }
    
    }
