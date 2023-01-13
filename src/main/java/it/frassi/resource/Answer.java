package it.frassi.resource;

/**
 * Represents a Poll
 */

public class Answer{
	
	/**
	 * ID to identify the Answer
	 */
	private int id;
	
	/**
	 * Text of the answer
	 */
	private String text;
	
	/**
	 * Votes Received
	 */
	private int votes;

    /**
     * The poll who belongs
     */
    private int poll;
    	 
	
	/**
	 * Creates a new Answer
	 *
	 * @param id
	 *       ID to identify the answer
     * @param text
	 *       The text of the answer
     * @param votes
	 *       The votes that the answer has received
	 * @param poll
	         The poll who belongs this answer
	 */
 	public Answer(int id, String text, int votes, int poll){
		this.id=id;
		this.text=text;
		this.votes=votes;
		this.poll=poll;
	}
	
	
    /**
	 * Return Id of the Answer
	 *
	 * @return Id of the Answer
	 */
	public final int getId(){
		return id;
	}
	
	/**
	 * Return the text of the Answer
	 *
	 * @return Answer
	 */
	public String getText(){
		return text;
	}
	
    /**
	 * Return the number of votes receives
	 *
	 * @return Votes Received
	 */
	public int getVotes(){
		return votes;
	}
	
    /**
	 * Return the Poll who own this answer
	 *
	 * @return Poll of the Answer
	 */
	public int getPoll(){
		return poll;
	}

}