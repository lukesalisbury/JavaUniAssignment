/* 
 * Created: 06/05/2014
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
 *
 * @author Luke Salisbury
 */
public class InvalidArgumentException extends Exception
{

	/**
	 * Blank InvalidArgumentException
	 */
	public InvalidArgumentException()
	{
		super( "Invalid Argument" );
	}

	/**
	 * InvalidArgumentException with additional detail 
	 * @param message Message to state.
	 */
	public InvalidArgumentException( String message )
	{
		super( message );
	}
	
}
