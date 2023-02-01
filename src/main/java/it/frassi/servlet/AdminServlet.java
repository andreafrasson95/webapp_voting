package it.frassi.servlet;

import it.frassi.resource.Link;
import it.frassi.resource.Poll;
import it.frassi.resource.Answer;
import it.frassi.database.AnswerDatabase;
import it.frassi.database.LinkDatabase;
import it.frassi.database.PollDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class AdminServlet extends AbstractDatabaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		try {
			String op = req.getParameter("operation");

			if (op.equals("listPolls")) {
				List<Poll> poll_list = new PollDatabase(getDataSource().getConnection()).listPolls();

				JSONObject polls = new JSONObject();
				polls.put("polls", poll_list);
				res.setStatus(HttpServletResponse.SC_OK);
				res.setContentType("application/json");
				res.getWriter().write(polls.toString());
			}

			if (op.equals("listLinks")) {

				int poll = Integer.parseInt(req.getParameter("poll"));

				List<Link> link_list = new LinkDatabase(getDataSource().getConnection()).listLinks(poll);

				JSONObject links = new JSONObject();
				links.put("links", link_list);
				res.setStatus(HttpServletResponse.SC_OK);
				res.setContentType("application/json");
				res.getWriter().write(links.toString());
			}

			if (op.equals("listAnswers")) {

				int poll = Integer.parseInt(req.getParameter("poll"));

				List<Answer> answers_list = new AnswerDatabase(getDataSource().getConnection()).retrieveAnswers(poll);

				JSONObject answers = new JSONObject();
				answers.put("answers", answers_list);
				res.setStatus(HttpServletResponse.SC_OK);
				res.setContentType("application/json");
				res.getWriter().write(answers.toString());
			}
		}

		catch (SQLException ex) {
			// TO do
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		List<Link> lista = null;

		try {
			/* Parsing HTTP Body */
			BufferedReader reader = req.getReader();
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null)
				buffer.append(line);

			// Parsing JSON

			JSONObject json = new JSONObject(buffer.toString());
			String op_json = json.getString("operation");

			if (op_json.equals("createLinks")) {

				int number = json.getInt("number");
				int poll = json.getInt("poll");

				lista = new LinkDatabase(getDataSource().getConnection()).createLinks(number, poll);

				JSONObject links = new JSONObject();
				links.put("links", lista);
				res.setStatus(HttpServletResponse.SC_OK);
				res.setContentType("application/json");
				res.getWriter().write(links.toString());
			}

			if (op_json.equals("updateNotes")) {

				String link = json.getString("link");
				String notes = json.getString("notes");

				int result = new LinkDatabase(getDataSource().getConnection()).updateNotes(link, notes);

				if (result == 0) {
					// Errore
				} else {
					// Modifica corretta
				}
			}

			if (op_json.equals("addPoll")) {
				String name = json.getString("poll");
				String question = json.getString("question");
				JSONArray answers = json.getJSONArray("answers");
				int nr_answers = answers.length();

				//Dates Handling
				if(json.getString("start_date")!=null){
                      System.out.println(LocalDateTime.parse(json.getString("start_date")));




				}

				List<Answer> list = new ArrayList<>();
				for (int i = 0; i < nr_answers; i++) {
					list.add(new Answer(answers.getString(i)));
				}

				Poll poll = new Poll(question, name);
				int j = new PollDatabase(getDataSource().getConnection()).insertPoll(poll);
				System.out.println(j);
				int returned_answers = new AnswerDatabase(getDataSource().getConnection()).insertAnswers(list, j);

				res.setStatus(HttpServletResponse.SC_NO_CONTENT);

			}
		}

		catch (IOException e) {

			// Handling
		}

		catch (JSONException e) {
			System.out.println(e.getMessage());
		}

		catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		catch (NumberFormatException ex) {
		}

	}

}
