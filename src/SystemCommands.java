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
 * Command interface for TutoringSystem class
 * @author Luke Salisbury
 */
abstract public class SystemCommands
{
	protected TutoringSystem tutorSystem;

	/**
	 *
	 * @param tutorSystem
	 */
	public void setTutorSystem(TutoringSystem tutorSystem)
	{
		this.tutorSystem = tutorSystem;
	}

	/**
	 * Log Message
	 * @param message
	 * @param isError
	 */
	protected void logMessage( String message, boolean isError )
	{
		System.out.println( message );
	}

	/**
	 * 
	 * 
	 * 
	 * @return List of Subject
	 */
	private ArrayList<Subject> getSubjectsFromInputCodes( String coursesBuffer, String delimiter )
	{
		ArrayList<Subject> returnArray = new ArrayList();
		String [] courses = coursesBuffer.split( delimiter );

		for ( String course : courses) 
		{
			Subject subject;
			try {
				subject = tutorSystem.getSubjectFromCourseCode( course );
				returnArray.add( subject );
			} catch ( InvalidSubjectException ex ) {
				this.logMessage( "Warning:" + ex.getMessage() + "\n", false );
			}
		}
		return returnArray;
	}

	/* Requests Methods */

	/**
	 * 
	 * 
	 * 
	 * @param tutorFullName
	 * @param subjectCode
	 * @param studentID
	 */
	public void requestAdd( String tutorFullName, String subjectCode, String studentID )
	{
		try {
			Tutor tutor = tutorSystem.getTutor( tutorFullName );
			Student student = tutorSystem.getStudentById( studentID );

			if ( !tutor.hasSubjectByCode( subjectCode ) )
			{
				this.logMessage( "Tutor " + tutor.getFullName() + " does not cover " + subjectCode, true );
			}
			else if ( !student.hasSubjectByCode( subjectCode ) ) 
			{
				this.logMessage( "Student " + student.getFullName() + " does not take " + subjectCode, true );
			}
			else
			{
				Request newRequest = new Request(tutor, student, subjectCode );
				tutorSystem.addRequest(newRequest);

				this.logMessage( "Request has been made.\n" + newRequest, false );
			}
		} catch ( InvalidTutorException ex ) {
			this.logMessage( "Tutor '" + tutorFullName + "' is not in system.\n" + ex.getMessage(), true );
		} catch ( InvalidStudentException ex ) {
			this.logMessage( "No Student with the ID '" + studentID + "' is not in system.\n" + ex.getMessage(), true );
		} catch ( InvalidArgumentException ex ) {
			this.logMessage( ex.getMessage(), true );
		}

	}

	/**
	 *
	 * @return
	 */
	public ArrayList<Request> requestList( )
	{
		return tutorSystem.listRequest();
	}

	/**
	 *
	 * @param studentId
	 * @return
	 */
	public ArrayList<Request> requestStudent( String studentId )
	{
		ArrayList<Request> matches = new ArrayList();
		ArrayList<Request> items = tutorSystem.listRequest();
		for ( Request request : items )
		{
			Student student = request.getStudent();
			if ( student.getStudentNo().equalsIgnoreCase( studentId ) )
			{
				matches.add(request);
			}
		}

		return matches;
	}

	/**
	 *
	 * @param tutorFullName
	 * @return
	 */
	public ArrayList<Request> requestTutor( String tutorFullName )
	{
		ArrayList<Request> matches = new ArrayList();
		ArrayList<Request> items = tutorSystem.listRequest();
		for ( Request request : items )
		{
			Tutor tutor = request.getTutor();
			if ( tutor.getFullName().equalsIgnoreCase( tutorFullName ) )
			{
				matches.add(request);
			}
		}

		return matches;
	}


	/**
	 *
	 * @param tutorFullName
	 * @param subjectCode
	 * @param studentID
	 */
	public void requestRemove( String tutorFullName, String subjectCode, String studentID )
	{
		try
		{
			Request request = tutorSystem.getRequest( tutorFullName, subjectCode, studentID );

			tutorSystem.removeRequest( request );

			this.logMessage( "Tutoring Request removed from system." , false );
		} catch ( InvalidRequestException ex ) {
			this.logMessage( "Tutoring Request is not in the system.", true );
		} catch ( InvalidArgumentException ex ) {
			this.logMessage( ex.getMessage(), true );
		}

	}

	/* Tutor Methods */

	/**
	 *
	 * @param tutorFullName
	 */
	public void tutorRemove( String tutorFullName )
	{
		try {
			Tutor tutor = tutorSystem.getTutor( tutorFullName );

			tutorSystem.removeTutor( tutor );

			this.logMessage( tutor.getFullName() + " removed from system." , false );
		} catch ( InvalidTutorException ex ) {
			this.logMessage( "Tutor '" + tutorFullName + "' is not in system.\n" + ex.getMessage(), true );
		} catch ( InvalidArgumentException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param tutorFirstName
	 * @param tutorFamilyName
	 * @param tutorContactDetails
	 * @param tutorHoursText
	 * @param tutorsubjectOffered
	 * @return 
	 */
	public Tutor tutorAdd( String tutorFirstName, String tutorFamilyName, String tutorContactDetails, String tutorHoursText, String tutorsubjectOffered )
	{
		try {
			ArrayList<Subject> subjectOffered;
			int contactHours;

			contactHours = Integer.parseInt( tutorHoursText );
			subjectOffered = this.getSubjectsFromInputCodes( tutorsubjectOffered, ",");

			Tutor tutor = new Tutor( tutorFirstName, tutorFamilyName, tutorContactDetails, subjectOffered, contactHours );

			tutorSystem.addTutor(tutor);

			this.logMessage( "Tutor " + tutor.getFullName() + "  added to system." , false );
			return tutor;
		} catch (NumberFormatException ex) {
			this.logMessage( "Invalid Contact Hours", true );
		} catch (InvalidArgumentException ex) {
			this.logMessage( ex.getMessage(), true );
		}

		return null;
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	public ArrayList<Tutor> tutorList( )
	{
		return tutorSystem.listTutors();
	}

	/**
	 * 
	 * 
	 * 
	 * @param classCode
	 * @return 
	 */
	public ArrayList<Tutor> tutorSearch( String  classCode )
	{
		return tutorSystem.searchTutors( classCode );
	}

	/**
	 * 
	 * 
	 * 
	 * @param tutorFullName
	 * @return 
	 */
	public Tutor tutorInfo( String tutorFullName )
	{
		Tutor tutor = null;
		try {
			tutor = tutorSystem.getTutor( tutorFullName );
		} catch ( InvalidTutorException ex ) {
			this.logMessage( "Tutor '" + tutorFullName + "' is not in system.\n" + ex.getMessage(), true );
		}
		return tutor;
	}

	/* Subjects Methods */

	/**
	 * 
	 * 
	 * 
	 * @param subjectCode
	 * @return 
	 */

	public Subject subjectsInfo( String subjectCode )
	{
		Subject course = null;
		try {
			course = tutorSystem.getSubjectFromCourseCode( subjectCode );
		} catch ( InvalidSubjectException ex ) {
			this.logMessage( "Subject '" + subjectCode + "' is not in system.\n" + ex.getMessage(), true );
		}
		return course;
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	public ArrayList<Subject> subjectsList(  )
	{
		return tutorSystem.getSubjects();
	}

	/* Student Methods */

	/**
	 * 
	 * 
	 * 
	 * @param studentID
	 * @return 
	 */
	public Student studentInfo( String studentID )
	{
		Student student = null;
		try {
			student = tutorSystem.getStudentById( studentID );
		} catch ( InvalidStudentException ex ) {
			this.logMessage( "No Student with the ID '" + studentID + "' is not in system.\n" + ex.getMessage(), true );
		}
		return student;
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	public ArrayList<Student> studentList( )
	{
		return tutorSystem.listStudents();
	}

	/**
	 * 
	 * 
	 * 
	 * @param studentID
	 */
	public void studentRemove( String studentID )
	{
		try {
			Student student = tutorSystem.getStudentById( studentID );

			tutorSystem.removeStudent( student );

			this.logMessage( student.getFullName() + " removed from system." , false );
		} catch ( InvalidStudentException ex ) {
			this.logMessage( "No Student with the ID '" + studentID + "' is not in system.\n" + ex.getMessage(), true );
		} catch ( InvalidArgumentException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param studentFirstName
	 * @param studentFamilyName
	 * @param studentContactDetails
	 * @param studentID
	 * @param studentSubjectEnrolled
	 * @return 
	 */

	public Student studentAdd( String studentFirstName, String studentFamilyName, String studentContactDetails, String studentID, String studentSubjectEnrolled )
	{
		Student student = null;
		ArrayList<Subject> subjectEnrolled;

		subjectEnrolled = this.getSubjectsFromInputCodes( studentSubjectEnrolled, ",");

		try {
			if ( studentFirstName.isEmpty() )
			{
				throw new InvalidArgumentException("First Name not given.");
			}
			else if ( studentFamilyName.isEmpty() )
			{
				throw new InvalidArgumentException("First Name not given.");
			}
			else if ( studentContactDetails.isEmpty() )
			{
				throw new InvalidArgumentException("Contact Details not given.");
			}
			else if ( studentID.isEmpty() )
			{
				throw new InvalidArgumentException("Student ID not given.");
			}
			student = new Student( studentFirstName, studentFamilyName, studentContactDetails, studentID, subjectEnrolled );
			tutorSystem.addStudent( student );

			this.logMessage( student.getFullName() + " add to system." , false );
		} catch ( InvalidArgumentException ex ) {
			this.logMessage( ex.getMessage(), true );
		}

		return student;
	}




}
