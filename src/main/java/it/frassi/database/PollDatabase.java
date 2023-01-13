package it.frassi.database;

import it.frassi.resource.Poll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

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
	 * @param id 
	 *            The id of the Poll 
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
		 Poll ret=null;
		 
		 try{
			  pstmt=con.prepareStatement(query);
			  pstmt.setInt(1, id);
			  
			  rs=pstmt.executeQuery();
			  
			  if(rs.next()){
			         ret=new Poll(rs.getInt(1), rs.getString(2), rs.getString(3));
			  }
			  else
			  {
				  ret=null;
			  }
		 }
		 
		 finally{
		      if(rs != null){
				 rs.close();
			  }
			  if(pstmt !=null){
				pstmt.close();
			  }
	          con.close();
		 }
	   
 	     return ret;
	 
	 }
	 
	 /**
     * Retrive all the Polls in the Database
	 *
	 * @return List of Poll Object
     *
     * @throws SQLException	 
     *	          if error with the database
	 */	 

     public List<Poll> listPolls() throws SQLException{

         String query="SELECT * FROM poll.Voting;";

         PreparedStatement pstmt=null;
         ResultSet rs=null;
         List<Poll> lista=new ArrayList<>();

		 System.out.println("Qua sonoXDXDD");

         try{
              pstmt=con.prepareStatement(query);

              rs=pstmt.executeQuery();

              while(rs.next()){
                	 lista.add(new Poll(rs.getInt(1), rs.getString(2), rs.getString(3)));
			  }
		 }
		 
		 catch (SQLException e){
			System.out.println(e.getSQLState());
		 }

         finally{
			  if(rs != null){
				 rs.close();
			  }				 
			  if(pstmt !=null){
				 pstmt.close();
			  }				 
              con.close();
		 }
 
         return lista;
		 
	 }
}
			  