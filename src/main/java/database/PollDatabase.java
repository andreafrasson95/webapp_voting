package it.frassi.database;

import it.frassi.resource.Poll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PollDatabase{
	
	/**
	 * Connection to database 
	 */
	private final Connection con;
	
	/**
     * Object for querying/manipulating polls in the database.
     *
     * @param con	 
     *	          connection to the database
     */	 
	
	public PollDatabase(final Connection con){
		this.con=con;
	}
	
	/**
     * Retrive Poll in the Database, if not found return null
	 *
	 * @return Poll object 
     *
     * @throws SQLException	 
     *	          if error with the database
	 */	 
	 
	 public Poll retrievePoll(int id) throws SQLException{
		 
		 String query="SELECT * FROM poll.Voting WHERE votingid=?;";
		 
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 
		 try{
			  pstmt=con.prepareStatement(query);
			  pstmt.setInt(1, id);
			  
			  rs=pstmt.executeQuery();
			  
			  if(rs.next()){
			         return new Poll(rs.getInt(1), rs.getString(2));
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
			  