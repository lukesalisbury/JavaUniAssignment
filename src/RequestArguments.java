/* 
 * Created: 10/05/2014
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

import java.util.ArrayList;

/**
 * Extends Arguments clas to send DataPacket back to client.
 * @author luke
 */
public class RequestArguments extends Arguments
{
	private boolean hasError = false;
	private String messageBuffer;
	private ArrayList<DataMessage> messages;
	
	/**
	 *
	 * @param tutorSystem
	 */
	public RequestArguments(TutoringSystem tutorSystem)
	{
		super(tutorSystem);
		this.messageBuffer = "";
		this.messages = new ArrayList();
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<DataMessage> getMessages()
	{
		return this.messages;
	}
	
	/**
	 *
	 * @return
	 */
	public boolean hasError()
	{
		return hasError;
	}

	/**
	 *
	 * @param hasError
	 */
	public void setHasError(boolean hasError)
	{
		this.hasError = hasError;
	}

	/**
	 *
	 * @return
	 */
	public String getMessageBuffer()
	{
		return messageBuffer;
	}

	/**
	 *
	 * @param messageBuffer
	 */
	public void appendMessageBuffer(String messageBuffer)
	{
		this.messageBuffer += "\n" + messageBuffer;
	}

	/**
	 *
	 * @param messageBuffer
	 */
	public void clearMessageBuffer(String messageBuffer)
	{
		this.messageBuffer = "";
	}
	
	@Override
	protected void logMessage( String message, boolean isError )
	{
		if ( isError )
		{
			this.setHasError( true );
			this.appendMessageBuffer( "Error: " + message );
			System.out.println( "Error: " + message );
		}
		else
		{
			System.out.println( message );
			this.appendMessageBuffer( message );
		}
	}
	
	/* Requests Methods */

	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandRequestList( String[] args )
	{
		// request list
		ArrayList<Request> list = this.requestList();

		for ( Request item: list )
		{
			this.messages.add( new DataMessage( DataMessage.REQUEST, item, list.size() ) );
		}
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandRequestStudent( String[] args )
	{
		// request list
		ArrayList<Request> list = this.requestList();
		ArrayList<Request> matches = new ArrayList();

		for ( Request item: list )
		{
			Student student = item.getStudent();
			if ( student.getStudentNo().equalsIgnoreCase( args[2] ) )
			{
				matches.add( item );
			}
		}
		
		if ( matches.size() > 0 )
		{
			this.logMessage( matches.size() + " Matches." , false );
			for ( Request item: matches )
			{
				this.messages.add( new DataMessage( DataMessage.REQUESTLIST, item, matches.size() ) );
			}
		}
		else
		{
			this.logMessage( "No Matches." , true );
		}
	}	
	
	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandRequestTutor( String[] args )
	{
		// request list
		ArrayList<Request> list = this.requestList();
		ArrayList<Request> matches = new ArrayList();

		for ( Request item: list )
		{
			Tutor tutor = item.getTutor();
			if ( tutor.getFullName().equalsIgnoreCase( args[2] ) )
			{
				matches.add( item );
			}
		}
		
		if ( matches.size() > 0 )
		{
			this.logMessage( matches.size() + " Matches." , false );
			for ( Request item: matches )
			{
				this.messages.add( new DataMessage( DataMessage.REQUESTLIST, item, matches.size() ) );
			}
		}
		else
		{
			this.logMessage( "No Matches." , true );
		}
	}
	

	/* Tutor Methods */

	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandTutorInfo( String[] args )
	{
		//	student [studentID]
		if ( args.length >= 2 )
		{
			Tutor tutor = this.tutorInfo( args[1] );
			this.messages.add( new DataMessage( DataMessage.TUTOR, tutor, 1 ) );
		}
		else
		{
			this.logMessage( "No Student Number Given", true );
		}
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandTutorSearch( String[] args )
	{
		//	search [classCode]
		if ( args.length > 1 )
		{
			ArrayList<Tutor> list = tutorSystem.searchTutors( args[1] );

			for ( Tutor item: list )
			{
				this.messages.add( new DataMessage( DataMessage.TUTORLIST, item, list.size() ) );
			}
		}
		else
		{
			this.logMessage( "tutorSearch: Enter a Course Code." , true );
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandTutorAdd( String[] args )
	{
		// add [FirstName] [FamilyName] [contactDetails] [hours] [subjectOffered,...]
		if ( args.length == 6  )
		{
			Tutor tutor = this.tutorAdd( args[1], args[2], args[3], args[4], args[5] );

		}
		else
		{
			this.logMessage( ( args.length < 7 ? "Too few arguments" : "Too Many arugments"), true );
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandTutorRemove( String[] args )
	{
		//	remove [firstName] [familyName]
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
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandTutorList( String[] args )
	{
		ArrayList<Tutor> list = this.tutorList();
		for ( Tutor item: list )
		{
			this.messages.add( new DataMessage( DataMessage.TUTORLIST, item, list.size() ) );
		}
	}
	
	/* Subject Methods */

	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandSubjectInfo( String[] args )
	{
		//	subject [classCode]
		if ( args.length >= 1 )
		{
			Subject info = this.subjectsInfo( args[1] );
			this.messages.add( new DataMessage( DataMessage.SUBJECT, info, 1 ) );
		}
		else
		{
			this.logMessage( "No Subject Code Name Given", true );
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandSubjectList( String[] args )
	{
		//	subject list
		ArrayList<Subject> list = this.subjectsList();
		for ( Subject item: list )
		{
			this.messages.add( new DataMessage( DataMessage.SUBJECTLIST, item, list.size() ) );
		}
	}
	
	/* Student Methods */

	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandStudentInfo( String[] args )
	{
		//	student [studentID]
		if ( args.length >= 2 )
		{
			Student student = this.studentInfo( args[1] );
			this.messages.add( new DataMessage( DataMessage.STUDENT, student, 1 ) );
		}
		else
		{
			this.logMessage( "No Student Number Given", true );
		}
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandStudentList( String[] args )
	{
		//	student list
		ArrayList<Student> list = this.studentList();
		for ( Student item: list )
		{
			this.messages.add( new DataMessage( DataMessage.STUDENTLIST, item, list.size() ) );
		}
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandStudentRemove( String[] args )
	{
		//	student remove [studentID]
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
	 * 
	 * 
	 * 
	 * @param args
	 */
	protected void commandStudentAdd( String[] args )
	{
		//student add [FirstName] [FamilyName] [contactDetails] [studentNo] [subjectEnrolled]
		if ( args.length == 7  )
		{
			Student student = this.studentAdd( args[2], args[3], args[4], args[5], args[6] );
		}
		else
		{
			this.logMessage(  ( args.length < 7 ? "Too few arguments" : "Too Many arugments"), true );
		}
	}
	
	
}
