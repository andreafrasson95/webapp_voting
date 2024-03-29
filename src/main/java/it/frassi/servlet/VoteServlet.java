package it.frassi.servlet;

import it.frassi.database.*;

import it.frassi.resource.Link;
import it.frassi.resource.Poll;
import it.frassi.resource.Answer;
import it.frassi.resource.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;
import org.json.JSONObject;
import java.time.LocalDateTime;

public class VoteServlet extends AbstractDatabaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String URL = req.getRequestURI();
		String[] tokens = URL.split("/");
		String[] parts = tokens[3].split(" ");

		String link_url = parts[0];

		// Preparation
		Link link = null;
		Poll poll = null;
		List<Answer> answers = null;
		Message m = null;

		try {
			// Retrive Link from DB
			link = new LinkDatabase(getDataSource().getConnection()).retrieveLink(link_url);

			if (link == null)
				m = new Message("Link non Esiste", "100", "Il link Non Esiste");

			else {

				if (link.getState() == true)
					m = new Message("Link già usato", "101", "Link già usato per la votazione");

				else {
					// Retrieve The Poll
					poll = new PollDatabase(getDataSource().getConnection()).retrievePoll(link.getPoll());

					int time = 0;

					LocalDateTime now = LocalDateTime.now();
					if (poll.getStart() != null) {
						if ((poll.getStart().isBefore(now)) == false) {
							m = new Message("Poll is not started yet", "104", "Poll not Started");
							time = -1;
						}
					}

					if (poll.getEnd() != null) {
						if ((poll.getEnd().isAfter(now)) == false) {
							m = new Message("Poll is over", "105", "Poll finished");
							time = -1;
						}
					}
              
					if (time != -1) {

						// Retrieve the Answers
						answers = new AnswerDatabase(getDataSource().getConnection()).retrieveAnswers(link.getPoll());

						req.setAttribute("poll", poll);
						req.setAttribute("answers", answers);
						req.setAttribute("numero", link);
					}
				}
			}
			req.setAttribute("message", m);
		}

		catch (SQLException ex) {
			System.out.println("Problema");
		}

		req.getRequestDispatcher("/jsp/vote.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String URL = req.getRequestURI();
		String[] tokens = URL.split("/");
		String[] parts = tokens[3].split(" ");

		String link_url = parts[0];

		Link link = null;
		String answer = null;
		int a = 0;

		JSONObject response = new JSONObject();

		try {

			// Retrieve Link from the DB
			link = new LinkDatabase(getDataSource().getConnection()).retrieveLink(link_url);

			if (link == null) {
				response.put("isError", true);
				response.put("message", "Link does not Exist");
			}

			else {

				if (link.getState() == true) {
					response.put("isError", true);
					response.put("message", "Link already used");
				}

				else {

					a = new LinkDatabase(getDataSource().getConnection()).lockLink(link.getId());

					if (a == 0) {
						response.put("isError", true);
						response.put("message", "Error");
					} else {

						answer = req.getParameter(Integer.toString(link.getPoll()));

						a = new AnswerDatabase(getDataSource().getConnection()).voteAnswer(link.getPoll(),
								Integer.parseInt(answer));

						if (a == 0) {
							response.put("isError", true);
							response.put("message", "Error");
						} else {
							response.put("isError", false);
							response.put("message", "Answer Registered Correctly");
						}
					}
				}
			}

			res.setStatus(HttpServletResponse.SC_OK);
			res.setContentType("application/json");
			res.getWriter().write(response.toString());

		}

		catch (SQLException ex) {
			System.out.println("Error");
		}

	}
}
