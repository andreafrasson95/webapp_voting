package it.frassi.resource;

/**
 * Represents a Poll
 */

public class Poll{
	
	/**
	 * ID to identify the poll
	 */
	private int id;
	
	/**
	 * Text of the question
	 */
	private String question;
	
	
	/**
	 * Creates a new Poll
	 *
	 * @param id
	 *       ID to identify the poll
     * @param question
     * @param poll
	 *       The text of the question
	 */
 	public Poll(int id, String question){
		this.id=id;
		this.question=question;
	}
	
	
    /**
	 * Return Id of the Poll
	 *
	 * @return Id of the Poll
	 */
	public final int getId(){
		return id;
	}
	
	/**
	 * Return the text of the Question
	 *
	 * @return question
	 */
	public String getQuestion(){
		return question;
	}
	

}