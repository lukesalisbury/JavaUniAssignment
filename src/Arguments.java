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

import java.util.*;

/**
 *
 * @author Luke Salisbury
 */
public class Arguments extends SystemCommands
{
	/**
	 * Constructor
	 * @param tutorSystem
	 */
	public Arguments(TutoringSystem tutorSystem)
	{
		setTutorSystem(tutorSystem);
	}

	/**
	 * Method to handle messaging 
	 * @param message Content
	 * @param isError 
	 */
	@Override
	protected void logMessage( String message, boolean isError )
	{
		if ( isError )
		{
			System.out.println(  "Error: " + message );
		}
		else
		{
			System.out.println(  message );
		}
	}

	/* Requests Methods */

	/**
	 * Adds a request.
	 * @param args Array of string starting with: request add [tutor] [subjectCode] [studentID]
	 */
	protected void commandRequestAdd( String[] args )
	{
		// Make sure there is enough argumentm and pass it off to method that handle it.
		if ( args.length > 4 ) 
		{
			this.requestAdd( args[2], args[3], args[4] );
		}
		else
		{
			this.logMessage(  "requestAdd: Too few Arguments" , true );
		}
	}

	/**
	 * removes a request.
	 * @param args Array of string starting with: request remove [tutor] [subjectCode] [studentID]
	 */
	protected void commandRequestRemove( String[] args )
	{
		// Make sure there is enough argumentm and pass it off to method that handle it.
		if ( args.length > 4 )
		{
			this.requestRemove( args[2], args[3], args[4] );
		}
		else
		{
			this.logMessage(  "requestRemove: Too few Arguments" , true );
		}
	}

	/**
	 * Display a list of request.
	 * @param args Array of string starting with: request list
	 */
	protected void commandRequestList( String[] args )
	{
		// request list
		ArrayList<Request> list = this.requestList();

		this.logMessage( "Numbers of matches: " + list.size(), false );
		for ( Request item: list )
		{
			this.logMessage( item.toString(), false );
		}
	}

	/**
	 * Display a list of a student request.
	 * @param args Array of string starting with: request list student [id]
	 */
	protected void commandRequestStudent( String[] args )
	{
		// request list
		ArrayList<Request> list = this.requestList();

		this.logMessage( "Numbers of matches: " + list.size(), false );
		for ( Request item: list )
		{
			Student student = item.getStudent();
			if ( student.getStudentNo().equalsIgnoreCase( args[2] ) )
			{
				this.logMessage( item.toString(), false );
			}
		}
	}

	/**
	 * Display a list of a tutor request.
	 * @param args Array of string starting with: request list tutor [tutor]
	 */
	protected void commandRequestTutor( String[] args )
	{
		// request list
		ArrayList<Request> list = this.requestList();

		this.logMessage( "Numbers of matches: " + list.size(), false );
		for ( Request item: list )
		{
			Tutor tutor = item.getTutor();
			if ( tutor.getFullName().equalsIgnoreCase( args[2] ) )
			{
				this.logMessage( item.toString(), false );
			}
		}
	}

	/* Tutor Methods */

	/**
	 * Print tutor info
	 * @param args Array of string starting with: info [tutorFullName]
	 */
	protected void commandTutorInfo( String[] args )
	{
		if ( args.length > 1 )
		{
			Tutor tutor = this.tutorInfo(args[1] );
			if ( tutor != null )
				this.logMessage( tutor.toString() , false );
		}
		else
		{
			this.logMessage( "tutorSearch: Enter a Course Code." , true );
		}
	}

	/**
	 * Search for Tutor that cover a subject
	 * @param args Array of string starting with: search [classCode]
	 */
	protected void commandTutorSearch( String[] args )
	{
		if ( args.length > 1 )
		{
			ArrayList<Tutor> list = tutorSystem.searchTutors( args[1] );

			this.logMessage( "Numbers of Tutors matches: " + list.size(), false );
			for ( Tutor item: list )
			{
				this.logMessage( item.toString() , false );
			}
		}
		else
		{
			this.logMessage( "tutorSearch: Enter a Course Code." , true );
		}
	}

	/**
	 * Adds a tutor.
	 * @param args Array of string starting with: add [FirstName] [FamilyName] [contactDetails] [hours] [subjectOffered,...]
	 */
	protected void commandTutorAdd( String[] args )
	{
		if ( args.length == 6  )
		{
			this.tutorAdd( args[1], args[2], args[3], args[4], args[5] );
		}
		else
		{
			this.logMessage( ( args.length < 7 ? "Too few arguments" : "Too Many arugments"), true );
		}
	}

	/**
	 * Remove a tutor
	 * @param args Array of string starting with: remove [firstName] [familyName]
	 */
	protected void commandTutorRemove( String[] args )
	{
		if ( args.length >= 3 )
		{
			String fullName = args[1] + " " + args[2];
			this.tutorRemove( fullName );
		}
		else
		{
			this.logMessage( "No Tutor Name Given", true );
		}
	}


	/**
	 * List Tutor
	 * @param args Array of string starting with: list
	 */
	protected void commandTutorList( String[] args )
	{
		ArrayList<Tutor> list = this.tutorList();

		this.logMessage("Numbers of tutor: " + list.size(), false );
		for ( Tutor item: list )
		{
			this.logMessage( item.toString() , false );
		}
	}

	/* Subject Methods */

	/**
	 * Subject Info
	 * @param args Array of string starting with: subject [classCode]
	 */
	protected void commandSubjectInfo( String[] args )
	{
		if ( args.length >= 1 )
		{
			Subject info = this.subjectsInfo( args[1] );
			this.logMessage( info.toString() , false );
		}
		else
		{
			this.logMessage( "No Subject Code Name Given", true );
		}
	}

	/**
	 * List Subject
	 * @param args Array of string starting with: subject list
	 */
	protected void commandSubjectList( String[] args )
	{
		ArrayList<Subject> list = this.subjectsList();

		this.logMessage( "Numbers of Subject: " + list.size(), false );
		for ( Subject item: list )
		{
			this.logMessage( item.toString() , false );
		}
	}

	/* Student Methods */

	/**
	 * Print Student Info
	 * @param args Array of string starting with: student [studentID]
	 */
	protected void commandStudentInfo( String[] args )
	{
		if ( args.length >= 2 )
		{
			Student student = this.studentInfo( args[1] );
			this.logMessage( student.toString() , false );
		}
		else
		{
			this.logMessage( "No Student Number Given", true );
		}
	}

	/**
	 * List Student
	 * @param args Array of string starting with: student list
	 */
	protected void commandStudentList( String[] args )
	{
		ArrayList<Student> list = this.studentList();

		this.logMessage("Numbers of student: " + list.size(), false );
		for ( Student item: list )
		{
			this.logMessage( item.toString() , false );
		}
	}

	/**
	 * remove a student
	 * @param args Array of string starting with: student remove [studentID]
	 */
	protected void commandStudentRemove( String[] args )
	{
		if ( args.length >= 2 )
		{
			this.studentRemove( args[2] );
		}
		else
		{
			this.logMessage(  "No Student Number Given" , true );
		}
	}

	/**
	 * Adds a student.
	 * @param args Array of string starting with: student add [FirstName] [FamilyName] [contactDetails] [studentNo] [subjectEnrolled,...]
	 */
	protected void commandStudentAdd( String[] args )
	{
		if ( args.length == 7  )
		{
			Student student = this.studentAdd( args[2], args[3], args[4], args[5], args[6] );
		}
		else
		{
			this.logMessage(  ( args.length < 7 ? "Too few arguments" : "Too Many arugments"), true );
		}
	}


	/* Stats Methods */

	/**
	 * Prints out stats
	 * @param args
	 */

	public void commandStats( String[] args )
	{
		boolean showTutors;
		boolean showRequests;
		boolean showStudents;
		boolean showSubjects;

		if ( args.length > 1 )
		{
			showTutors = args[1].equalsIgnoreCase("tutors");
			showRequests = args[1].equalsIgnoreCase("requests");
			showStudents = args[1].equalsIgnoreCase("students");
			showSubjects = args[1].equalsIgnoreCase("subjects");
		}
		else
		{
			showTutors = true;
			showRequests = true;
			showStudents = true;
			showSubjects = true;
		}

		if ( showRequests )
		{
			this.logMessage( "--------------------------------------" , false );
			this.logMessage( "Requests:" , false );
			this.commandRequestList(args);
			this.logMessage( "--------------------------------------" , false );
		}

		if ( showTutors )
		{
			this.logMessage( "--------------------------------------" , false );
			this.logMessage( "Tutors:" , false );
			this.commandTutorList(args);
			this.logMessage( "--------------------------------------" , false );
		}

		if ( showStudents )
		{
			this.logMessage( "--------------------------------------" , false );
			this.logMessage( "Students:" , false );
			this.commandStudentList(args);
			this.logMessage( "--------------------------------------" , false );
		}

		if ( showSubjects )
		{
			this.logMessage( "--------------------------------------" , false );
			this.logMessage( "Subjects:" , false );
			this.commandSubjectList(args);
			this.logMessage( "--------------------------------------" , false );
		}
	}

	/* Sub-parser Methods */

	/**
	 * Adds a request.
	 * @param args Array of string starting with: request add [tutor] [subjectCode] [studentID]
	 */
	protected void commandStudent( String[] args )
	{
		if ( args.length > 1 )
		{
			System.out.println("Subcommand> " + args[1] );
			switch ( args[1] )
			{
				case "add":
					this.commandStudentAdd( args );
					break;
				case "remove":
					this.commandStudentRemove( args );
					break;
				case "list":
					this.commandStudentList( args );
					break;
				default:
					this.commandStudentInfo( args );
					break;
			}
		}
	}

	/**
	 * Handle request subcommand.
	 * @param args Array of string starting with: request
	 */
	protected void commandRequest( String[] args )
	{
		if ( args.length > 1 )
		{
			System.out.println(  "Subcommand> " + args[1] );
			switch ( args[1] )
			{
				case "add":
					this.commandRequestAdd( args );
					break;
				case "remove":
					this.commandRequestRemove( args );
					break;
				case "student":
					this.commandRequestStudent( args );
					break;
				case "tutor":
					this.commandRequestTutor( args );
					break;
				default:
					this.commandRequestList( args );
					break;

			}
		}
	}

	/**
	 * Handle subject subcommand.
	 * @param args Array of string starting with: request
	 */
	protected void commandSubjects( String[] args )
	{

		if ( args.length > 1 )
		{
			System.out.println( "Subcommand> " + args[1] );
			switch ( args[1] )
			{
				case "list":
					this.commandSubjectList(args);
					break;
				default:
					this.commandSubjectInfo(args);
					break;
			}
		}

	}



	private String stringJoin( String[] array )
	{
		String value = "";

		for ( int i = 0; i < array.length; i++ )
		{
			value += array[i];
			if ( (i + 1) != array.length )
			{
				value += ",";
			}
		}

		return value;
	}

	/**
	 * 
	 * 
	 * @param args
	 * @return boolean if a command if found
	 */
	public boolean parseCommand( String[] args )
	{
		boolean hasCommand = false;

		if ( args.length > 0 )
		{
			System.out.println( "-------------------------------" );
			System.out.println( "Command> " + args[0] );

			hasCommand = true;

			switch ( args[0] )
			{
				case "student":
					this.commandStudent( args );
					break;
				case "search":
					this.commandTutorSearch( args );
					break;
				case "list":
					this.commandTutorList( args );
					break;
				case "add":
					this.commandTutorAdd( args );
					break;
				case "remove":
					this.commandTutorRemove( args );
					break;
				case "info":
					this.commandTutorInfo( args );
					break;
				case "request":
					this.commandRequest( args );
					break;
				case "subject":
					this.commandSubjects( args );
					break;
				case "stats":
					this.commandStats( args );
					break;
				default:
					hasCommand = true;
					break;
			}

			System.out.println(  "-------------------------------\n" );
		}

		return hasCommand;
	}

}
