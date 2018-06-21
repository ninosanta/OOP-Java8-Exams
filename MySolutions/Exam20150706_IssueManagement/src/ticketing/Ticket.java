package ticketing;

/**
 * Class representing the ticket linked to an issue or malfunction.
 * 
 * The ticket is characterized by a severity and a state.
 */
public class Ticket {
    
    /**
     * Enumeration of possible severity levels for the tickets.
     * 
     * Note: the natural order corresponds to the order of declaration
     */
    public enum Severity { Blocking, Critical, Major, Minor, Cosmetic };
    
    /**
     * Enumeration of the possible valid states for a ticket
     */
    public static enum State { Open, Assigned, Closed }
    
    private int id;
    private String description;
    private Severity severity;
    private State state;
    private String path;
    private String username;
    
    public Ticket(int id, String username, String path, String description, Severity severity) {
    	state = State.Open;
    	this.id = id;
    	this.path = path;
    	this.description = description;
    	this.severity = severity;
    	this.username = username;
    }
    
    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }
    
    public Severity getSeverity() {
        return severity;
    }

    public String getAuthor(){
        return username;
    }
    
    public String getComponent(){
        return path;
    }
    
    public State getState(){
        return state;
    }
    
    public String getSolutionDescription() throws TicketException {
    	if (this.state != State.Closed) 
    		throw new TicketException();
    	
        return this.description;
    }

	public void setState(State state) {
		this.state = state;		
	}

	public void setAuthor(String username) {
		this.username = username;		
	}

	public void setDescription(String solution) {
		this.description = solution;
	}
}
