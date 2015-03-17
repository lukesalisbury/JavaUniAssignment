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

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Test Class of Tutor input file.
 * @author Luke Salisbury
 */
public class TutorInputFile
{
	/**
	 * Entry point
	 * @param args the command line arguments
	 */
	public static void main( String[] args )
	{
		TutorTextFile database;
		int recordCount = 0;
		
		try
		{
			/* Open up the Tutor File and print out the records */
			database = new TutorTextFile( "../TutorInputFile.txt" );
			recordCount = database.recordCount();
			for ( int i = 0; i < recordCount; i++ ) 
			{
				Tutor tutor = database.getTutor( i );
			
				System.out.println( i + ": " + tutor.toString() );
			}
		} 
		catch ( FileNotFoundException ex )
		{
			System.out.println( "Database not found" );
		}
	}
}
