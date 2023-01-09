package it.frassi.servlet;


import it.frassi.resource.Link;
import it.frassi.resource.Poll;

import it.frassi.database.LinkDatabase;
import it.frassi.database.PollDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;

public class AdminServlet extends AbstractDatabaseServlet{
	
  
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
	  
	  try{
	     String op=req.getParameter("operation");
		 
		 if(op.equals("listPolls")){
			 List<Poll> poll_list=new PollDatabase(getDataSource().getConnection()).listPolls();
			 
			 JSONObject polls=new JSONObject();
			 polls.put("polls",poll_list);
			 res.setStatus(HttpServletResponse.SC_OK);
			 res.setContentType("application/json");	
             res.getWriter().write(polls.toString());
		 }
		 
		 if(op.equals("listLinks")){
			 
			 int poll=Integer.parseInt(req.getParameter("poll"));
			 
			 List<Link> link_list=new LinkDatabase(getDataSource().getConnection()).listLinks(poll);
			 
			 JSONObject links=new JSONObject();
			 links.put("links",link_list);
			 res.setStatus(HttpServletResponse.SC_OK);
			 res.setContentType("application/json");	
             res.getWriter().write(links.toString());
		 }
	  }
	  
	  catch(SQLException ex){
		  //TO do
	  }
  }
  
  
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
	  
	  List<Link> lista=null;
	  
	  try{
		  String op=req.getParameter("operation");
		  
		  if(op.equals("createLinks")){
			  
			  int number=Integer.parseInt(req.getParameter("number"));
			  int poll=Integer.parseInt(req.getParameter("poll"));
		      
			  lista=new LinkDatabase(getDataSource().getConnection()).createLinks(number,poll);
			  
			  JSONObject links=new JSONObject();
			  links.put("links",lista);
			  res.setStatus(HttpServletResponse.SC_OK);
			  res.setContentType("application/json");	
              res.getWriter().write(links.toString());
		 }
		 
		 if(op.equals("updateNotes")){
			 
			 String link=req.getParameter("link");
			 String notes=req.getParameter("notes");
			 
			 int result=new LinkDatabase(getDataSource().getConnection()).updateNotes(link, notes);
			 
			 if( result==0){
				 //Errore
			 }
			 else{
				 //Modifica corretta
			 }
		 }
		  
	  }
	  
	  catch (SQLException ex){ System.out.println("Problema");}
	  
	  catch (NumberFormatException ex) {}
		    
  }  




}
