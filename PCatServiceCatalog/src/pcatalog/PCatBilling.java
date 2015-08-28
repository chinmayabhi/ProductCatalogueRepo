package pcatalog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/CostSender")
public class PCatBilling {
   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public double sendProfile(@DefaultValue("null")@QueryParam("id") String id) throws SQLException{

	   ConnectionGetter getConnection = new ConnectionGetter();
	   Connection con = getConnection.getConnection();
 	   java.sql.Statement st = con.createStatement();
 	   ResultSet rSet;
 	   
 	   if(id.contains("b")) {
 		   rSet = st.executeQuery("select t1.cost*t2.discount from "
    				 + " (select sum(cost) as cost from products join bundle_products "
    				 + " on (products.product_id = bundle_products.product_id) "
    				 + " where bundle_type_cd = '" + id + "' group by bundle_type_cd) t1, "
    				 + " (select (1-(discount_id/100)) as discount from discount join bundle_types " 
    				 + " on (bundle_types.bundle_type = discount.product_type) "
    				 + " where bundle_type_cd = '" + id + "') t2 ");
    	}
    	
    	else {
    		rSet = st.executeQuery("select (cost*(1-(discount_id/100))) "
    				+ " from products, discount where products.product_type_value = discount.Product_type "
    				+ " and products.product_id = '" + id + "' ");
    	}
    	rSet.next();
    	double cost = rSet.getDouble(1);
    	//System.out.println(cost);
    	
    	con.close();
		return cost;
    }
}