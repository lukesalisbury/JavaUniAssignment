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
 *
 * @author Luke Salisbury
 */
public class InvalidStudentException extends Exception
{
	/**
	 * Blank InvalidSubjectException
	 * 
	 */
	public InvalidStudentException()
	{
		super( "No Student Number Given" );
	}

	/**
	 * InvalidSubjectException with additional detail 
	 * @param studentNumber Student Number that cause the exception to be thrown.
	 */
	public InvalidStudentException( String studentNumber )
	{
		super( "Invalid Student Number: " + studentNumber );
	}
	
}
