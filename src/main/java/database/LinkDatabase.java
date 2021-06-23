package it.frassi.database;

import it.frassi.resource.Link;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkDatabase{
	
	/**
	 * Connection to database 
	 */
	private final Connection con;
	
	/**
	 * SQL query 
	 */
	private static final String query="SELECT * FROM poll.Link WHERE linkid=?";
	
	/**
     * Object for querying/manipulating links in the database.
     *
     * @param con	 
     *	          connection to the database
     */	 
	
	public LinkDatabase(final Connection con){
		this.con=con;
	}
	
	/**
     * Retrive Link in the Database
	 *
	 * @param link
	 *            The link that has to be searched in the database
	 *
	 * @return Link object if found, null otherwise
     *
     * @throws SQLException	 
     *	          if error with the database
	 */	 
	 
	 public Link retrieveLink(String link) throws SQLException{
		 
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 
		 try{
			  pstmt= con.prepareStatement(query);
			  pstmt.setString(1, link);
			  
			  rs=pstmt.executeQuery();
			  
			  if(rs.next()){
			         return new Link(rs.getString(1), rs.getBoolean(2), rs.getInt(3));
			  }
			  else
			  {
				  return null;
			  }
		}
		 
		 finally{
		 if(pstmt !=null){
				pstmt.close();
			}
	     con.close();
		 }
	 }
}
			  