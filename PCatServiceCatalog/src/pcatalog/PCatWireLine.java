package pcatalog;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.GsonBuilder;

@Path("/Consumer")
public class PCatWireLine {

   @GET
   @Path("/{Location}&{Package1}")
   @Produces(MediaType.TEXT_PLAIN)
   public String getWireLineDetails(@PathParam("Location")String Location, @PathParam("Package1")String Package1) throws SQLException {
		
	   //System.out.println(Location + Package1);
	   ConnectionGetter getConnection = new ConnectionGetter();
	   Connection con = getConnection.getConnection();
	   java.sql.Statement st = con.createStatement();
	   JsonObjectBuilder productDetails = Json.createObjectBuilder();
	   JsonArrayBuilder list = Json.createArrayBuilder();
	   JsonObjectBuilder x1;
	   ResultSet rSet = st.executeQuery("SELECT P.Product_Id, P.PRODUCT_NAME, P.Product_Desc, D.DISCOUNT_ID, P.COST FROM products P JOIN DISCOUNT D 	"
	   		+ "ON P.product_type_value=D.product_type WHERE PRODUCT_ID IN"
	   		+ "(SELECT PRODUCT_ID FROM package_filters WHERE FILTER_TYPE='notstate'"
	   		+ "AND filter_value1 not like '%" +  Location + "%' AND package_id=("
	   		+ "SELECT PACKAGE_ID FROM PACKAGE WHERE PACKAGE_NAME='" + Package1 + "'))");
	   while(rSet.next())
	   {
		   x1 =Json.createObjectBuilder();
		   x1.add("Product_ID",rSet.getString(1));
		   x1.add("Product_Name",rSet.getString(2));
		   x1.add("Product_Description",rSet.getString(3));
		   x1.add("Discount", rSet.getString(4));
		   x1.add("Cost", rSet.getString(5));
		   list.add(x1);
	   }
	   productDetails.add("Product_Details", list);
	   con.close();
	   return new GsonBuilder().setPrettyPrinting().create().toJson(productDetails.build());
	   //return new GsonBuilder().create().toJson(productDetails.build());
   }
   
   @GET
   @Path("/{Location}&{Package1}&{Package2}")
   @Produces(MediaType.TEXT_PLAIN)
   public String getWireLineDetails(@PathParam("Location")String Location, @PathParam("Package1")String Package1, @PathParam("Package2")String Package2) throws SQLException {
		
	   //System.out.println(Location + Package1 + Package2);
	   ConnectionGetter getConnection = new ConnectionGetter();
	   Connection con=getConnection.getConnection();
	   java.sql.Statement st1 = con.createStatement();
	   java.sql.Statement st2 = con.createStatement();
	   java.sql.Statement st4 = con.createStatement();
	   
	   JsonObjectBuilder bundleDetails=Json.createObjectBuilder();
	   JsonArrayBuilder list = Json.createArrayBuilder();
	   JsonObjectBuilder x1;
	   
	   ResultSet var1 = st1.executeQuery("select Package_Id from Package where Package_name= '" + Package1 + "'");
	   ResultSet var2 = st2.executeQuery("select Package_Id from Package where Package_name='" + Package2 + "'");
	   var1.next();
	   //System.out.println(var1.getString(1));
	   var2.next();
	   //System.out.println(var2.getString(1));
	   CallableStatement cs = null;
	   cs = con.prepareCall("{call WireLine_Get_Double_Bundle(?,?,?)}");
	   cs.setString(1, Location);
	   cs.setString(2, var1.getString(1));
	   cs.setString(3, var2.getString(1));
	   cs.executeQuery();
	   
	   ResultSet rs=st4.executeQuery("select * from temp");
	   while(rs.next()) 
	   {
		   x1=Json.createObjectBuilder();
		   x1.add("Bundle_Id",rs.getString(1) );
		   x1.add("Bundle_Desc", rs.getString(2));
		   x1.add("Bundle_Discount", rs.getString(3));
		   x1.add("Bundle_Cost", rs.getString(4));
		   list.add(x1);		
	   }
	   bundleDetails.add("Bundle_Details",list);	
	   con.close();
	   
	   return new GsonBuilder().setPrettyPrinting().create().toJson(bundleDetails.build());
   }
   
   @GET
   @Path("/{Location}&{Package1}&{Package2}&{Package3}")
   @Produces(MediaType.TEXT_PLAIN)
   public String getWireLineDetails(@PathParam("Location")String Location, @PathParam("Package1")String Package1, @PathParam("Package2")String Package2, @PathParam("Package1")String Package3) throws SQLException {
		
	   //System.out.println(Location + Package1 + Package2);
	   ConnectionGetter getConnection = new ConnectionGetter();
	   Connection con=getConnection.getConnection();
	   java.sql.Statement st1 = con.createStatement();
	   java.sql.Statement st2 = con.createStatement();
	   java.sql.Statement st3 = con.createStatement();
	   java.sql.Statement st4 = con.createStatement();
	   
	   JsonObjectBuilder bundleDetails=Json.createObjectBuilder();
	   JsonArrayBuilder list = Json.createArrayBuilder();
	   JsonObjectBuilder x1;
	   
	   ResultSet var1 = st1.executeQuery("select Package_Id from Package where Package_name= '" + Package1 + "'");
	   ResultSet var2 = st2.executeQuery("select Package_Id from Package where Package_name='" + Package2 + "'");
	   ResultSet var3 = st3.executeQuery("select Package_Id from Package where Package_name='" + Package3 + "'");
	   var1.next();
	   //System.out.println(var1.getString(1));
	   var2.next();
	   //System.out.println(var2.getString(1));
	   var3.next();
	   //System.out.println(var3.getString(1));
	   
	   CallableStatement cs = null;
	   cs = con.prepareCall("{call WireLine_Get_Triple_Bundle(?,?,?,?)}");
	   cs.setString(1, Location);
	   cs.setString(2, var1.getString(1));
	   cs.setString(3, var2.getString(1));
	   cs.setString(4, var2.getString(1));
	   cs.executeQuery();
	   
	   ResultSet rs=st4.executeQuery("select * from temp");
	   while(rs.next()) 
	   {
		   x1=Json.createObjectBuilder();
		   x1.add("Bundle_Id",rs.getString(1) );
		   x1.add("Bundle_Desc", rs.getString(2));
		   x1.add("Bundle_Discount", rs.getString(3));
		   x1.add("Bundle_Cost", rs.getString(4));
		   list.add(x1);		
	   }
	   bundleDetails.add("Bundle_Details",list);
	   con.close();
	   
	   return new GsonBuilder().setPrettyPrinting().create().toJson(bundleDetails.build());
   }
   
   @GET
   @Path("/VESTest")
   @Produces(MediaType.TEXT_PLAIN)  
   public String abc() {
	
	   return "Hello";
   }
}