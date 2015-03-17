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
 * Extends Argument class to add print help when running from a terminal
 * @author Luke Salisbury
 */
public class TerminalArguments extends Arguments
{

	/**
	 * TerminalArguments 
	 * @param tutorSystem
	 */
	public TerminalArguments(TutoringSystem tutorSystem)
	{
		super(tutorSystem);
	}

	/**
	 * Prints commain-line help
	 */
	public void printHelp()
	{
		System.out.println( "Arguments:" );
		System.out.println( "\t search [subjectCode]" );
		System.out.println( "\t list [tutorFullName]" );
		System.out.println( "\t info [tutorFullName]" );
		System.out.println( "\t add [tutorFirstName] [tutorFamilyName] [contactDetails] [hours] [subjectOffered,...]" );
		
	
		System.out.println( "\t request add [tutor] [subjectCode] [studentID]" );
		System.out.println( "\t request remove [tutor] [subjectCode] [studentID]" );
		System.out.println( "\t request list" );
		
		System.out.println( "\t subject list" );
		System.out.println( "\t subject [class]" );
		
		
		System.out.println( "\t student add [FirstName] [FamilyName] [contactDetails] [studentNo] [subjectCode,...]" );
		System.out.println( "\t student remove  [id]" );
		System.out.println( "\t student list" );
		System.out.println( "\t student [id]" );
	}
	
	/**
	 * Handles parsing of command arguments and prints help if no commands are executed.
	 * @param args
	 */
	public void handleCommandLine( String[] args )
	{
		boolean hasCommand;
		hasCommand = this.parseCommand( args );
		if ( hasCommand == false )
		{
			printHelp();
		}
	}
	
}
