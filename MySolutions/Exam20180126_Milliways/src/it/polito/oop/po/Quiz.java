package it.polito.oop.po;

public class Quiz {
	final static public String[] questions = {
	"Quali di queste affermazioni sono valide per un'interfaccia Java? / Which among the following statements are correct for a Java interface?",
	"Che metodo usa SVN per risolvere conflitti? / What methods does SVN adopts to resolve conflics?",
	"Cosa contiene la sezione centrale di una classe UML? / What is present in the middle section of a UML class?"
	};
	final static public String[][] options = {
	{
		"Un'interfaccia puo' contenere attributi / An interface can contain attributes",
		"Un'interfaccia puo' contenere dei metodi astratti / An interface can contain abstract methods",
		"Un'interfaccia puo' contenere dei metodi statici / An interface can contain static methods",
		"Un'interfaccia puo' essere vuota / An interface can be empty",
		"Un'interfaccia deve contenere almeno un metodo astratto / An interface must contain at least one abstract method"	},
	{
		"Lock-Unlock-Modify",
		"Lock-Modify-Unlock",
		"Check-out/Check-in",
		"Check-out/Commit",
		"Copy-Modify-Merge"	},
	{
		"Nome della classe / Name of the class",
		"Implementazione / Implementation",
		"Interfacce / Interfaces",
		"Metodi / Methods",
		"Attributi / Attributes"	}
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