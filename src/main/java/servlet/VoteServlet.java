package it.frassi.servlet;

import it.frassi.database.*;

import it.frassi.resource.Link;
import it.frassi.resource.Poll;
import it.frassi.resource.Answer;
import it.frassi.resource.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;


public class VoteServlet extends AbstractDatabaseServlet{


    public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String URL= req.getRequestURI();
		String[] tokens=URL.split("/");
		String[] parts=tokens[3].split(" ");
	
	    String link_url=parts[0];
		
		//Preparation
		Link link=null;
        Poll poll=null;
        List<Answer> answers=null;
		
		try{
			//Retrive Link from DB
			link=new LinkDatabase(getDataSource().getConnection()).retrieveLink(link_url);
			
	        //TODO: Case Link==null or already used
			
			//Retrieve The Poll
			poll=new PollDatabase(getDataSource().getConnection()).retrievePoll(link.getPoll());
			
			//Retrieve the Answers
			answers=new AnswerDatabase(getDataSource().getConnection()).retrieveAnswers(link.getPoll());
			
			req.setAttribute("poll",poll);
			req.setAttribute("answers",answers);
            req.setAttribute("numero",link);			
			
		}
		
		catch (SQLException ex){ System.out.println("Problema");
		}
			
	    req.getRequestDispatcher("/jsp/vote.jsp").forward(req,res);
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        String URL= req.getRequestURI();
		String[] tokens=URL.split("/");
		String[] parts=tokens[3].split(" ");
	
	    String link_url=parts[0];
		
		Link link=null;
		Message m=null;
		String answer=null;
		
		try{
			
			//Retrieve Link from the DB
			link=new LinkDatabase(getDataSource().getConnection()).retrieveLink(link_url);
			
			if(link == null){
				m=new Message("Link non Esiste","100","Il link Non Esist");
			}
			
			else{
				
				if(link.getState() == true){
					m=new Message("Link già usato","101","Mona");
				}
				
				else{
					
					new LinkDatabase(getDataSource().getConnection()).lockLink(link.getId());
					
					answer= req.getParameter(Integer.toString(link.getPoll()));
					
					
					//TODO Finish
					
					
				}
				
			}
			
		}
			
 
        catch (SQLException ex){ System.out.println("Problema");}



		/*
        //Stores as request attribute
        req.setAttribute("quiz", quiz);
        req.setAttribute("message", m);

        req.getRequestDispatcher("/jsp/simulation.jsp").forward(req,res);*/
    }
}