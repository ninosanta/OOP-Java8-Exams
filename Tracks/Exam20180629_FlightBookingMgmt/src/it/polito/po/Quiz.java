package it.polito.po;

public class Quiz {
	final static public String[] questions = {
	"Quali di queste affermazioni sono valide? / Which among the following statements are correct?",
	"Quali di queste affermazioni sono valide per lo sviluppo agile? / Which among the following statements are correct for agile development?",
	"Quali di queste affermazioni sono valide per un comparatore? / Which among the following statements are correct for a comparator?"
	};
	final static public String[][] options = {
	{
		"Il CMM ha l'obiettivo di rivoluzionare il processo di sviluppo del software. / CMM has the goal of revolutionizing software development",
		"Il pair programming pu?? facilitare il training on the job dei neoassunti. / Pair programming may ease training on the job for new employees",
		"Mainline sta a versioni di sistema come codeline sta a versioni di componente. / Mainline stands to system versions as codeline stands to component versions",
		"La disponibilit?? (availability) e l'affidabilit?? (reliability) sono attributi di qualit?? interni. / Availability and reliability are internal quality attributes",
		"Nella programmazione estrema il refactoring si riferisce alla pianificazione incrementale dello sviluppo del software. / In extreme programming, refactoring refers to the incremental planning of software development"	},
	{
		"e' essenzialte pianificare accuratamente il progetto / planning accurately the whole project is essential",
		"e' necessario che i requisiti siano stabili / stable requirements are required",
		"e' importante produrre una documentazione completa / producing a complete documentation is important",
		"viene effettuato periodicamente il code refactoring / code refactoring is applied repeatedly",
		"e' facile adattarsi al cambiamento dei requisiti / it is easy to adapt to changing requirements"	},
	{
		"Un comparatore si pu?? passare al costruttore di un HashSet / A comparator can be passed as an argument to the constructor of HashSet",
		"Un comparatore e' un oggetto che implementa l'interfaccia Comparator<T> / A comparator is an object implementing interface Comparator<T>",
		"Un comparatore implementa il metodo compareTo() / A comparator implements method compareTo()",
		"Un comparatore implementa il metodo compare() / A comparator implements method compare()",
		"Un comparatore contiene dei metodi astratti / A comparator contains abstract methods"	}
	};
	
	/**
	 * Return the index of the right answer(s) for the given question 
	 */
	public static int[] answer(int question){
		// TODO: answer the question
		
		switch(question){
			case 0: return null; // replace with your answers
			case 1: return null; // replace with your answers
			case 2: return null; // replace with your answers
		}
		return null; // means: "No answer"
	}

	/**
	 * When executed will show the answers you selected
	 */
	public static void main(String[] args){
		for(int q=0; q<questions.length; ++q){
			System.out.println("Question: " + questions[q]);
			int[] a = answer(q);
			if(a==null || a.length==0){
				System.out.println("<undefined>");
				continue;
			}
			System.out.println("Answer" + (a.length>1?"s":"") + ":" );
			for(int i=0; i<a.length; ++i){
				System.out.println(a[i] + " - " + options[q][a[i]]);
			}
		}
	}
}

