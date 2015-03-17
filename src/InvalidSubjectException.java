/* 
 * Created: 08/05/2014
 * Author: Luke Salisbury <luke.salisbury@live.vu.edu.au>
 * Student Number: 1510439
 * License: Creative Commons Attribution-NonCommercial-ShareAlike 4.0 
 * License URL: http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Implement a Tutoring System that would register tutors 
 */


/**
 * Invalid Subject Exception
 * @author Luke Salisbury
 */
public class InvalidSubjectException extends Exception
{
	/**
	 * Blank InvalidSubjectException
	 */
	public InvalidSubjectException()
	{
		super( "No Course Code Given" );
	}

	/**
	 * InvalidSubjectException with course code additional detail 
	 * @param courseCode Course code that cause the exception to be thrown.
	 */
	public InvalidSubjectException( String courseCode )
	{
		super( "Invalid Course Code: " + courseCode );
	}
	
}
