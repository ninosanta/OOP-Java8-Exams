package it.polito.oop.po;

public class Quiz {
	final static public String[] questions = {
	"Quali di queste affermazioni sono valide per un'interfaccia Java? / Which among the following statements are correct for a Java interface?",
	"Che metodo usa SVN per risolvere conflitti? / What methods does SVN adopts to resolve conflics?",
	"Cosa contiene la sezione centrale di una classe UML? / What is present in the middle section of a UML class?"
	};
	final static public String[][] options = {
	{
		/**
		 * Every method declaration in the body of an interface is implicitly abstract, so its body is always represented by a semicolon, not a block.
		 *
		 * Every method declaration in the body of an interface is implicitly public.
		 */
//		public interface Pippo {
//			String foo = "bar";  // no problem
//			public abstract void hello();  // 'abstract' unnecessary, but legit...idem for 'public'
//		}
		"Un'interfaccia puo' contenere attributi / An interface can contain attributes",   // 0
		"Un'interfaccia puo' contenere dei metodi astratti / An interface can contain abstract methods",  // 1
		"Un'interfaccia puo' contenere dei metodi statici / An interface can contain static methods",  // 2
		"Un'interfaccia puo' essere vuota / An interface can be empty",  // 3
		"Un'interfaccia deve contenere almeno un metodo astratto / An interface must contain at least one abstract method"  // 4
		},
	{
		"Lock-Unlock-Modify",  // 0
		"Lock-Modify-Unlock",  // 1
		"Check-out/Check-in",  // 2
		"Check-out/Commit",  // 3
		"Copy-Modify-Merge"	  // 4
		},
	{
		"Nome della classe / Name of the class",  // 0
		"Implementazione / Implementation",  // 1
		"Interfacce / Interfaces",  // 2
		"Metodi / Methods",  // 3
		"Attributi / Attributes"  //4
		}
	};
	
	/**
	 * Return the index of the right answer(s) for the given question 
	 */
	public static int[] answer(int question){
		// TODO: answer the question
		
		switch(question){
			case 0: return new int[] {0,1,2,3}; // replace with your answers
			case 1: return new int[] {4}; // replace with your answers
			case 2: return new int[] {4}; // replace with your answers
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