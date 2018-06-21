package ticketing;

import java.util.Set;

import ticketing.IssueManager.UserClass;

public class User {

	private String username;
	private Set<UserClass> classes;
	
	public User(String username, Set<UserClass> classes) {
		this.setUsername(username);
		this.setClasses(classes);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<UserClass> getClasses() {
		return classes;
	}

	public void setClasses(Set<UserClass> classes) {
		this.classes = classes;
	}
	
	
}
