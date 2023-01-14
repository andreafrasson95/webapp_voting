package it.frassi.database;

import it.frassi.resource.Link;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;


public class LinkDatabase{
	
	/**
	 * Connection to database 
	 */
	private final Connection con;
	
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
		 
		 String query="SELECT * FROM poll.Link WHERE linkid=?";
		 
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 Link found=null;
		 
		 try{
			  pstmt= con.prepareStatement(query);
			  pstmt.setString(1, link);
			  
			  rs=pstmt.executeQuery();
			  
			  if(rs.next()){
			       found=new Link(rs.getString(1), rs.getBoolean(2), rs.getInt(3), rs.getString(4));
			  }
			  else{
				   found=null;
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
		 
		 return found;
	 }
	 
	 /**
     * Flag the Link as "used" so it cannot be used anymore
	 *
	 * @param link
	 *            The link that has to be flagged "used"
	 *
	 * @return 1 if success, 0 otherwise
     *
     * @throws SQLException	 
     *	          if error with the database
	 */	 
	 
	 public int lockLink(String link) throws SQLException{
		 
		 String query="UPDATE poll.Link SET used=True WHERE linkid=?";
		 
		 PreparedStatement pstmt=null;
		 int rs=0;
		 
		 try{
		     pstmt=con.prepareStatement(query);
			 pstmt.setString(1,link);
			 
			 rs=pstmt.executeUpdate();
		 }
		 
		 finally{
			 
			 if(pstmt != null){
				 pstmt.close();
			 }
			 con.close();
		 }
		 //Todo Check the return value
		 return rs;
	 }


     /**
     * Creates s given number of links for a poll
	 *
	 * @param link_number
	 *            The number of links to be created
	 *
	 * @param poll_number
	 *            The poll who links belong
     *	 
	 * @return the list of created links
     *
     * @throws SQLException	 
     *	          if error with the database
	 */	 

     public List<Link> createLinks(int number, int poll) throws SQLException {
		 
		 String query= "INSERT INTO poll.Link VALUES (array_to_string(ARRAY(SELECT chr((97 + round(random() * 25)) :: integer) FROM generate_series(1,15)), ''),default, ?) RETURNING linkid;";
		 
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 int i;
         List<Link> lista=new ArrayList<>();
		 
		 try{
		     pstmt=con.prepareStatement(query);
			 pstmt.setInt(1,poll);
		     for(i=0;i<number;i++){
			   rs=pstmt.executeQuery();
			   if(rs.next()){
				   lista.add(new Link(rs.getString(1), false, poll, ""));
			   }
		     }
		 }
		 
		 catch(SQLException ex){System.out.println(ex.getMessage());}
		 
		 finally{
			 
			 if(rs != null){
				 rs.close();
			 }
			 if(pstmt != null){
				 pstmt.close();
			 }
			 con.close();
		 }
		 
		 return lista;
	 }
	 
	 /**
     * Retrive all Links in the Database given a Poll
	 *
	 * @param poll
	 *            The poll owning the links
	 *
	 * @return List of all links
     *
     * @throws SQLException	 
     *	          if error with the database
	 */	 
	 
	 public List<Link> listLinks(int poll) throws SQLException{
		 
		 String query="SELECT * FROM poll.Link WHERE votingid=?";
		 
		 PreparedStatement pstmt=null;
		 ResultSet rs=null;
		 List<Link> lista=new ArrayList<>();
		 
		 try{
			  pstmt= con.prepareStatement(query);
			  pstmt.setInt(1, poll);
			  
			  rs=pstmt.executeQuery();
			  
			  while(rs.next()){
			       lista.add(new Link(rs.getString(1), rs.getBoolean(2), rs.getInt(3), rs.getString(4)));
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
		 
		 return lista;
	 }
	 
	 
	 /**
	 * Update the notes for a given link
	 *
	 * @param linkid
	 *              The id of the link to be edited
	 * @param notes
	 *              New Notes to be inserted
	 *
	 * @return 1 if success, 0 otherwise
	 *
	 * @throws SQLException
	 *              if error with the database
	 */
	 
	 public int updateNotes(String link, String notes) throws SQLException{
		 
		 String query="UPDATE poll.Link SET notes=? WHERE linkid=?";
		 
		 PreparedStatement pstmt=null;
		 int rs;
		 
		 try{
			 pstmt=con.prepareStatement(query);
			 pstmt.setString(1, notes);
			 pstmt.setString(2, link);
			 
			 rs=pstmt.executeUpdate();
		 }
		 
		 finally{
			  if(pstmt !=null){
				 pstmt.close();
			  }
	          con.close();
		 }
		 
		 return rs;
	 }
					
}

