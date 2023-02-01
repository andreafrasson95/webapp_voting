package it.frassi.resource;
import java.time.LocalDateTime;


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
	 * Name of the Poll
	 */
	private String name; 

	/**
	 * Start time of the poll
	 */
	private LocalDateTime start; 

	/**
	 * End time of the Poll
	 */
	private LocalDateTime end; 


	
	
	/**
	 * Creates a new Poll
	 *
	 * @param id
	 *       ID to identify the poll
     * @param question
	 *       The text of the question
	 */
 	public Poll(int id, String question, String name){
		this.id=id;
		this.question=question;
		this.name=name;
	}

	public Poll(String question, String name){
		this.question=question;
		this.name=name;
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
	
	/**
	 * Return the name of the Poll
	 *
	 * @return name of poll
	 */
	public String getName(){
		return name;
	}

	/**
	 * Return the start time of the Poll
	 *
	 * @return Start time of the Poll
	 */
	public LocalDateTime getStart(){
		return start;
	}

	/**
	 * Return the end time of the Poll
	 *
	 * @return End time of the Poll
	 */
	public LocalDateTime getEnd(){
		return end;
	}

	

}