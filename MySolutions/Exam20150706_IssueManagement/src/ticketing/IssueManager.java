package ticketing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.*;

import ticketing.Ticket.State;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;


public class IssueManager {
	
	/**
     * Eumeration of valid user classes
     */
    public static enum UserClass {
        /** user able to report an issue and create a corresponding ticket **/
        Reporter, 
        /** user that can be assigned to handle a ticket **/
        Maintainer 
    }
    
    private Map<String,User> users = new HashMap<>();
    private Map<String,Component> components = new HashMap<>();
    private Map<Integer, Ticket> tickets = new HashMap<>();
    private int id = 1;
    
    /**
     * Creates a new user
     * 
     * @param username name of the user
     * @param classes user classes
     * @throws TicketException if the username has already been created or if no user class has been specified
     */
    public void createUser(String username, UserClass... classes) throws TicketException {
    	if (users.containsKey(username) || classes.length == 0)
        	throw new TicketException();
    	
    	Set<UserClass> classSet = Stream.of(classes).collect(toSet());
    	users.put(username, new User(username, classSet));
    }

    /**
     * Creates a new user
     * 
     * @param username name of the user
     * @param classes user classes
     * @throws TicketException if the username has already been created or if no user class has been specified
     */
    public void createUser(String username, Set<UserClass> classes) throws TicketException {
        if (users.containsKey(username) || classes.isEmpty())
        	throw new TicketException();
        
        users.put(username, new User(username, classes));
    }
   
    /**
     * Retrieves the user classes for a given user
     * 
     * @param username name of the user
     * @return the set of user classes the user belongs to
     */
    public Set<UserClass> getUserClasses(String username){
        return users.get(username).getClasses();
    }
    
    /**
     * Creates a new component
     * 
     * @param name unique name of the new component
     * @throws TicketException if a component with the same name already exists
     */
    public void defineComponent(String name) throws TicketException {
        if (components.containsKey(name))
        	throw new TicketException();
        components.put("/" + name, new Component(name));
    }
    
    /**
     * Creates a new sub-component as a child of an existing parent component
     * 
     * @param name unique name of the new component
     * @param parentPath path of the parent component
     * @throws TicketException if the the parent component does not exist or 
     *                          if a sub-component of the same parent exists with the same name
     */
    public void defineSubComponent(String name, String parentPath) throws TicketException {
        Component parent = components.get(parentPath);
        if (parent == null)
        	throw new TicketException();
        
        Component c = new Component(name);
        components.put(parentPath + "/"  + name, c);
        parent.addChild(c);        
    }
    
    /**
     * Retrieves the sub-components of an existing component
     * 
     * @param path the path of the parent
     * @return set of children sub-components
     */
    public Set<String> getSubComponents(String path){
    	Component c = components.get(path);
    	return c.getSubComponents();
    }

    /**
     * Retrieves the parent component
     * 
     * @param path the path of the parent
     * @return name of the parent
     */
    public String getParentComponent(String path) {
    	String[] components = path.split("/");
    	int len = components.length;
    	
    	// components[0] = "" 
    	// len-1 -> component, len-2 -> parent
    	return (len == 2) ? null : components[len-2];
    	
    }

    /**
     * Opens a new ticket to report an issue/malfunction
     * 
     * @param username name of the reporting user
     * @param componentPath path of the component or sub-component
     * @param description description of the malfunction
     * @param severity severity level
     * 
     * @return unique id of the new ticket
     * 
     * @throws TicketException if the user name is not valid, the path does not correspond to a defined component, 
     *                          or the user does not belong to the Reporter {@link IssueManager.UserClass}.
     */
    public int openTicket(String username, String componentPath, 
    		String description, Ticket.Severity severity) throws TicketException {
        if (!users.containsKey(username))
        	throw new TicketException();
        tickets.put(id, 
        		    new Ticket(id, username, componentPath, description, severity));
    	return id++;
    }
    
    /**
     * Returns a ticket object given its id
     * 
     * @param ticketId id of the tickets
     * @return the corresponding ticket object
     */
    public Ticket getTicket(int ticketId) {
    	return (ticketId > 0 && ticketId < id) ? tickets.get(ticketId) : null;
    }
    
    /**
     * Returns all the existing tickets sorted by severity
     * 
     * @return list of ticket objects
     */
    public List<Ticket> getAllTickets() {
        return tickets.values().stream().sorted().collect(toList());
    }
    
    /**
     * Assign a maintainer to an open ticket
     * 
     * @param ticketId  id of the ticket
     * @param username  name of the maintainer
     * @throws TicketException if the ticket is in state <i>Closed</i>, the ticket id or the username
     *                          are not valid, or the user does not belong to the <i>Maintainer</i> user class
     */
    public void assingTicket(int ticketId, String username) throws TicketException {
        if (ticketId < 1 || ticketId >= id || 
            !users.containsKey(username) || 
            !users.get(username).getClasses().contains(UserClass.Maintainer))
        	throw new TicketException();
        tickets.get(ticketId).setState(State.Assigned);
        tickets.get(ticketId).setAuthor(username);
        
    }

    /**
     * Closes a ticket
     * 
     * @param ticketId id of the ticket
     * @param description description of how the issue was handled and solved
     * @throws TicketException if the ticket is not in state <i>Assigned</i>
     */
    public void closeTicket(int ticketId, String solution) throws TicketException {
        if (tickets.get(ticketId).getState() != State.Assigned)
        	throw new TicketException();
        tickets.get(ticketId).setState(State.Closed);
        tickets.get(ticketId).setDescription(solution);
    }

    /**
     * returns a sorted map (keys sorted in natural order) with the number of  
     * tickets per Severity, considering only the tickets with the specific state.
     *  
     * @param state state of the tickets to be counted, all tickets are counted if <i>null</i>
     * @return a map with the severity and the corresponding count 
     */
    public SortedMap<Ticket.Severity, Long> countBySeverityOfState(Ticket.State state) {
    	return (state == null) ? 
    			
    			tickets.values().stream()
    			.collect(groupingBy(Ticket::getSeverity, 
    					() -> new TreeMap<Ticket.Severity, Long>(),
    					counting()
    					))
    			
    			:
    				
    			tickets.values().stream()
    			.filter(t -> t.getState() == state)
    			.collect(groupingBy(Ticket::getSeverity, 
    					() -> new TreeMap<Ticket.Severity, Long>(),
    					counting()
    					));
    }

    /**
     * Find the top maintainers in terms of closed tickets.
     * 
     * The elements are strings formatted as <code>"username:###"</code> where <code>username</code> 
     * is the user name and <code>###</code> is the number of closed tickets. 
     * The list is sorter by descending number of closed tickets and then by username.

     * @return A list of strings with the top maintainers.
     */
    public List<String> topMaintainers() {
    	return tickets.values().stream().filter(t -> t.getState() == State.Closed)
    			.collect(groupingBy(Ticket::getAuthor,
    					counting()))
    			.entrySet().stream()
    			.sorted(comparing(Map.Entry<String, Long>::getValue, reverseOrder())
    					.thenComparing(Map.Entry::getKey))
    			.map(Map.Entry::getKey)
    			.collect(toList());
    }

}
