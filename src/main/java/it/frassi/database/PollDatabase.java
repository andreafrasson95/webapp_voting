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
     * Insert new Poll in the Database
	 *
	 * @param Poll 
	 *            Poll to the inserted
	 *
	 * @return 0 if ok, -1 otherwise 
     *
     * @throws SQLException	 
     *	          if error with the database
	 */	 


	 public int insertPoll(Poll poll) throws SQLException{

		String poll_query="INSERT INTO poll.Voting VALUES (default, ?, ?)";

		PreparedStatement pstmt=null;
		int rs=0;

		try{
			pstmt=con.prepareStatement(poll_query);
			pstmt.setString(1, poll.getQuestion());
			pstmt.setString(2,poll.getName());

			rs=pstmt.executeUpdate();

			if(rs<1) return -1;
		}

		finally{
			
			if(pstmt !=null){
			  pstmt.close();
			}
			con.close();
	   }

	   return 0;
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
			  