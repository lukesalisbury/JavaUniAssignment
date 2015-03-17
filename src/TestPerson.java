/* 
 * Created: 01/05/2014
 * Author: Luke Salisbury <luke.salisbury@live.vu.edu.au>
 * Student Number: 1510439
 * Course: Programming for Networks 
 * Subject: ECB2123
 * License: Creative Commons Attribution-NonCommercial-ShareAlike 4.0 
 * License URL: http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Implement a Tutoring System that would register tutors interested in 
 * helping students
 */

/**
 * Test Class for Person
 * @author Luke Salisbury
 */
public class TestPerson
{

	/**
	 * Main
	 * Entry point
	 * @param args the command line arguments
	 */
	public static void main( String[] args )
	{
		Person anne = new Person( "Anne", "Venables", "9919 5209");
		Person grace = new Person( "Grace", "Tan", "9919 4685");

		System.out.println( "1st person is " + anne );
		System.out.println( "2nd person is " + grace );

		// testing all get method
		System.out.println( "1st person's first name is " + anne.getFirstName ( ) );
		System.out.println( "1st person's family name is " + anne.getFamilyName ( ) );
		System.out.println( "1st person's contact details are " + anne.getContactDetails ( ) );

		// testing the equals method
		System.out.println ("Are persons Anne and Grace the same? " + anne.equals ( grace ) ) ;
		System.out.println ("Are these persons the same? " + anne.equals (new Person ("Anne", "Venables", "9919 5209")));
	}

}
