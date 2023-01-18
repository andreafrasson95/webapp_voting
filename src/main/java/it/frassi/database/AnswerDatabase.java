package it.frassi.database;

import it.frassi.resource.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public class AnswerDatabase {

	/**
	 * Connection to database
	 */
	private final Connection con;

	/**
	 * Object for querying/manipulating answers in the database.
	 *
	 * @param con
	 *            connection to the database
	 */

	public AnswerDatabase(final Connection con) {
		this.con = con;
	}

	/**
	 * Retrive all the answer in the Database, given a Poll
	 *
	 * @param id
	 *           The id of the Poll
	 *
	 * @return List of Answers
	 *
	 * @throws SQLException
	 *                      if error with the database
	 */

	public List<Answer> retrieveAnswers(int id) throws SQLException {

		String query = "SELECT * FROM poll.Answers WHERE votingid=?;";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Answer> lista = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lista.add(new Answer(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
			}
		}

		finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}

		return lista;

	}

	/**
	 * Add a vote for an Answer
	 *
	 * @param id
	 *             The id of answer
	 *
	 * @param poll
	 *             The id of the poll who owns the answer
	 *
	 * @return 0 if success, 1 otherwise
	 *
	 * @throws SQLException
	 *                      if error with the database
	 */

	public int voteAnswer(int poll, int id) throws SQLException {

		String query = "UPDATE poll.Answers SET votes_received = votes_received + 1 WHERE answerid=? and votingid=?";

		PreparedStatement pstmt = null;
		int rs;

		try {

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, poll);

			rs = pstmt.executeUpdate();
		}

		finally {
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}

		return rs;
	}

		/**
	 * Add the Answers to the Database
	 *
	 * @param answers
	 *             The list of the answers
	 *
	 * @param poll
	 *             The id of the poll who owns the answer
	 *
	 * @return the number of answers inserted in the database, -1 in case of error
	 *
	 * @throws SQLException
	 *                      if error with the database
	 */

	 public int insertAnswers(List<Answer> answers, int poll) throws SQLException{

		String query= "INSERT INTO poll.Answers VALUES (default, ?, default, ?)";

		PreparedStatement pstmt= null;
		int answers_inserted=0;


		try{
            pstmt=con.prepareStatement(query);
			pstmt.setInt(2, poll);

			for(int i=0; i<answers.size(); i++){
				pstmt.setString(1,answers.get(i).getText());
				pstmt.executeQuery();
				answers_inserted++;
				System.out.println(i);
			}
		}
		catch(SQLException e){

			//DB error
		}

		finally{
			if (pstmt != null) {
				pstmt.close();
			}
			con.close();
		}

		return answers_inserted;

	 }

}
