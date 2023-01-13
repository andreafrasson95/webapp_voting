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
	 * Note associated with the link
	 */
	private String notes; 
	
	/**
	 * Creates a new Link
	 *
	 * @param id
	 *       ID to identify the link
     * @param used
     *       Bool that represent if link has been used or not
     * @param poll
	 *       The competition which the link belongs
	 */
 	public Link(String id, Boolean used, int poll, String notes){
		this.id=id;
		this.used=used;
		this.poll=poll;
		this.notes=notes;
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
	
	/**
	 * Return the notes associated to that Link
	 *
	 * @return note of the link
	 */
	public String getNotes(){
		return notes;
	}

}