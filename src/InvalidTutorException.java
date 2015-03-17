/* 
 * Created: 08/05/2014
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
 * Invalid Tutor Exception
 * @author Luke Salisbury
 */
public class InvalidTutorException extends Exception
{
	/**
	 * Blank InvalidTutorException
	 */
	public InvalidTutorException()
	{
		super( "No tutor name Given" );
	}

	/**
	 * InvalidTutorException with additional detail 
	 * @param tutorName Tutor Name that cause the exception to be thrown.
	 */
	public InvalidTutorException( String tutorName )
	{
		super( "No tutor named : " + tutorName );
	}
	
}
