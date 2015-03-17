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
 * Test Class for Subject
 * @author Luke Salisbury
 */
public class TestSubject
{

	/**
	 * Main
	 * Entry point
	 * @param args the command line arguments
	 */
	public static void main( String[] args )
	{
		Subject java1 = new Subject( "ECB1121", "Programming Principles", 1 );
		Subject java2 = new Subject( "ECB2123", "Programming for Networks", 2 );
		
		// testing toString method
		System.out.println( "Ist subject is " + java1 );
		System.out.println( "2nd subject is " + java2 );
		
		// testing all get methods
		System.out.println( "1st Subject's code is " + java1.getSubjectCode() );
		System.out.println( "1st Subject's name is " + java1.getSubjectName() );
		System.out.println( "1st Subject's year level is " + java1.getYearLevel() );
		
		// testing the equals method
		System.out.println( "Are 1st subject and 2nd subject the same? " + java1.equals( java2 ) );
		System.out.println( "Are these subjects the same? " + java1.equals( new Subject( "ECB1121", "Programming Principles", 1 ) ) );
	}

}
