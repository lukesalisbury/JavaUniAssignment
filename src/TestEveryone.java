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

import java.util.*;


/**
 * Tests for Tutor, Student, Person and Subject Classes.
 * @author luke S1510439
 */
public class TestEveryone
{

	/**
	 * Entry point
	 * @param args the command line arguments
	 */
	public static void main( String[] args )
	{
		Subject java1 = new Subject( "ECB1121", "Programming Principles", 1 );
		Subject java2 = new Subject( "ECB2123", "Programming for Networks", 2 );
		Subject java3 = new Subject( "ECB2128", "Fake Programming", 2 );

		ArrayList<Subject> anneSubjects = new ArrayList();
		anneSubjects.add(java1);
		anneSubjects.add(java2);

		ArrayList<Subject> graceSubjects = new ArrayList();
		graceSubjects.add(java2);
		graceSubjects.add(java3);

		Tutor anne = new Tutor( "Anne", "Venables", "9919 5209", anneSubjects, 5 );
		Student grace = new Student( "Grace", "Tan", "9919 4685", "111", graceSubjects );

		System.out.println( "1st person is " + anne );
		System.out.println( "2nd person is " + grace );

		// testing all get method
		System.out.println( "1st person's first name is " + anne.getFirstName( ) );
		System.out.println( "1st person's family name is " + anne.getFamilyName( ) );
		System.out.println( "1st person's contact details are " + anne.getContactDetails( ) );
		System.out.println( "1st person's hours available is " + anne.getHours() );

		System.out.println( "2nd person's first name is " + grace.getFirstName( ) );
		System.out.println( "2nd person's family name is " + grace.getFamilyName( ) );
		System.out.println( "2nd person's contact details are " + grace.getContactDetails( ) );

		System.out.println( "2nd person's subjects are " + grace.getSubjectEnrolled() );
		System.out.println( "2nd person's student number is " + grace.getStudentNo() );

		/* Test to see tutor can teach student subject */
		ArrayList<Subject> studentSubject = grace.getSubjectEnrolled();
		for ( Subject subject : studentSubject)
		{
			if ( anne.searchForSubject( subject ) )
			{
				System.out.println( anne.getFirstName() + " can tutor " + grace.getFirstName( ) + " in " + subject.getSubjectName() );
			}
			else
			{
				System.out.println( anne.getFirstName() + " can not tutor " + grace.getFirstName( ) + " in " + subject.getSubjectName() );
			}
		}

		// testing the equals method
		System.out.println ("Are persons Anne and Grace the same? " + anne.equals ( grace ) ) ;
		System.out.println ("Are these persons the same? " + anne.equals (new Person ("Anne", "Venables", "9919 5209")));
	}
	
}
