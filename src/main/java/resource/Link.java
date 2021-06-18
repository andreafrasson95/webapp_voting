package it.frassi.resource;


/**
 * Represents a Voting Link
 */

public class Link{
	
	/**
	 * ID to identify the link 
	 */
	private String id;
	
	/**
	 * Bool that represent if link has been used or not
	 */
	private Boolean used;
	
	/**
	 * The competition which the link belongs
	 */
	private int poll;
	
	/**
	 * Creates a new Link
	 *
	 *@param id
	 *       ID to identify the link
     *@param used
     *       Bool that represent if link has been used or not
     *@param poll
	 *       The competition which the link belongs
	 */
 	public Link(String id, Boolean used, int poll){
		this.id=id;
		this.used=used;
		this.poll=poll;
	}
	
	
    /**
	 * Return Id of the Link
	 *
	 * @return Id of the Link
	 */
	public final String getId(){
		return id;
	}
	
	/**
	 * Return the state of the link
	 *
	 * @return state of the link
	 */
	public boolean getState(){
		return used;
	}
	
	/**
	 * Return the poll of the link
	 *
	 * @return poll of the link
	 */
	public int getPoll(){
		return poll;
	}

}