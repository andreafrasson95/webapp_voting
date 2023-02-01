package it.frassi.database;

import it.frassi.resource.Poll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

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
	 * @return the id of the poll if ok, -1 otherwise 
     *
     * @throws SQLException	 
     *	          if error with the database
	 */	 


	 public int insertPoll(Poll poll) throws SQLException{

		String poll_query="INSERT INTO poll.Voting VALUES (default, ?, ?, ?, ?) RETURNING votingid";

		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//int rs=0;

		try{
			pstmt=con.prepareStatement(poll_query);
			pstmt.setString(1, poll.getQuestion());
			pstmt.setString(2,poll.getName());

			pstmt.setTimestamp(3, Timestamp.valueOf(poll.getStart()));
			pstmt.setTimestamp(4, Timestamp.valueOf(poll.getEnd()));

			rs=pstmt.executeQuery();

			if(rs.next()){
				return rs.getInt(1);
			}
			else return -1;
		}

		finally{
			
			if(pstmt !=null){
			  pstmt.close();
			}
			con.close();
	   }

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
				     LocalDateTime start=null;
					 LocalDateTime end=null;

					 Timestamp tmp=null;

                     tmp=rs.getTimestamp(4);
					 if(tmp != null) start=tmp.toLocalDateTime();
					 tmp=rs.getTimestamp(5);
					 if(tmp != null) end=tmp.toLocalDateTime();
		
			         ret=new Poll(rs.getInt(1), rs.getString(2), rs.getString(3), start, end);
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
				LocalDateTime start=null;
				LocalDateTime end=null;

				Timestamp tmp=null;

				tmp=rs.getTimestamp(4);
				if(tmp != null) start=tmp.toLocalDateTime();
				tmp=rs.getTimestamp(5);
			    if(tmp != null) end=tmp.toLocalDateTime();
				    
                lista.add(new Poll(rs.getInt(1), rs.getString(2), rs.getString(3), start, end));
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
			  