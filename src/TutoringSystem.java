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
import java.io.*;
import java.util.*;

/**
 * Tutoring System
 * @author Luke Salisbury
 */
public class TutoringSystem
{
	private SubjectTextFile subjectsDatabase;
	private ArrayList<Tutor> tutors;
	private ArrayList<Student> students;
	private ArrayList<Request> requests;

	/**
	 * 
	 * 
	 * 
	 * @param path
	 * @throws java.io.FileNotFoundException
	 */
	public TutoringSystem( String path ) throws FileNotFoundException
	{
		TutorTextFile tutorDatabase = new TutorTextFile( path + "/TutorInputFile.txt" );

		subjectsDatabase = new SubjectTextFile( path + "/SubjectInputFile.txt" );

		this.tutors = tutorDatabase.getList();
		this.students = new ArrayList();
		this.requests = new ArrayList();
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	public ArrayList<Subject> getSubjects()
	{
		return subjectsDatabase.availableSubjects;
	}

	/**
	 * 
	 * 
	 * 
	 * @param courseCode
	 * @return 
	 * @throws InvalidSubjectException
	 */
	public Subject getSubjectFromCourseCode( String courseCode ) throws InvalidSubjectException
	{
		return subjectsDatabase.getSubjectFromCourseCode( courseCode );
	}

	/**
	 * 
	 * 
	 * 
	 * @param newRequest
	 * @throws InvalidArgumentException
	 */
	public void addRequest( Request newRequest ) throws InvalidArgumentException
	{
		if ( newRequest == null )
		{
			throw new InvalidArgumentException( "addRequest: Invalid Arugment 'Request'" );
		}
		else
		{
			// Only accept request if tutor has hours
			Tutor tutor = newRequest.getTutor();
			if ( tutor.getHours() > 0 )
			{
				tutor.subtractHours( 1 );
				this.requests.add( newRequest );
			}
			else
			{
				throw new InvalidArgumentException( "Tutor hasn't got the time." );
			}
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	public ArrayList<Request> listRequest()
	{
		return this.requests;
	}


	/**
	 * 
	 * 
	 * 
	 * @param tutorFullName
	 * @param subjectCode
	 * @param studentID
	 * @return 
	 * @throws InvalidRequestException 
	 */
	public Request getRequest( String tutorFullName, String subjectCode, String studentID ) throws InvalidRequestException
	{

		for ( Request request : this.requests )
		{
			if ( request.match(tutorFullName,  subjectCode, studentID ) )
			{
				return request;
			}
		}
		throw new InvalidRequestException();
	}

	/**
	 * 
	 * 
	 * 
	 * @param selectedRequest
	 * @throws InvalidArgumentException
	 */
	public void removeRequest( Request selectedRequest ) throws InvalidArgumentException
	{
		if ( selectedRequest == null )
		{
			throw new InvalidArgumentException( "removeRequest: Invalid Arugment 'Request'" );
		}
		else
		{
			// If a request is removed, readd the hour
			if ( this.requests.remove( selectedRequest ) )
			{
				selectedRequest.getTutor().subtractHours( -1 );
			}
			else
			{
				throw new InvalidArgumentException( "removeRequest: Invalid Arugment 'Request'" );
			}
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param name
	 * @return 
	 * @throws InvalidTutorException 
	 */
	public Tutor getTutor( String name ) throws InvalidTutorException
	{
		for ( Tutor tutor : this.tutors )
		{
			if ( tutor.getFullName().equalsIgnoreCase(name) )
			{
				return tutor;
			}
		}
		throw new InvalidTutorException(name);
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	public ArrayList<Tutor> listTutors()
	{
		return this.tutors;
	}

	/**
	 * 
	 * 
	 * @param classCode
	 * @return 
	 */
	public ArrayList<Tutor> searchTutors( String classCode )
	{
		ArrayList<Tutor> matches = new ArrayList();
		for ( Tutor tutor : this.tutors )
		{
			if ( tutor.hasSubjectByCode( classCode ) )
			{
				matches.add(tutor);
			}
		}
		return matches;
	}

	/**
	 * 
	 * 
	 * 
	 * @param newTutor
	 * @throws InvalidArgumentException
	 */
	public void addTutor( Tutor newTutor ) throws InvalidArgumentException
	{
		if ( newTutor == null )
		{
			throw new InvalidArgumentException( "addTutor: Invalid Arugment 'Tutor'" );
		}
		else
		{
			this.tutors.add(  newTutor );
		}

	}

	/**
	 * 
	 * 
	 * 
	 * @param selectedTutor
	 * @throws InvalidArgumentException
	 */
	public void removeTutor( Tutor selectedTutor ) throws InvalidArgumentException
	{
		if ( selectedTutor == null )
		{
			throw new InvalidArgumentException( "removeTutor: Invalid Arugment 'Tutor'" );
		}
		else
		{
			this.tutors.remove( selectedTutor );
		}

	}

	/**
	 * 
	 * 
	 * 
	 * @param studentNo
	 * @return
	 * @throws InvalidStudentException
	 */
	public Student getStudentById( String studentNo ) throws InvalidStudentException
	{
		for ( Student student : this.students )
		{
			if ( student.getStudentNo().equals(studentNo) )
			{
				return student;
			}
		}
		throw new InvalidStudentException(studentNo);
	}

	/**
	 * 
	 * 
	 * 
	 * @return String A String with the list of Students
	 */
	public ArrayList<Student> listStudents()
	{
		return this.students;
	}

	/**
	 * 
	 * 
	 * 
	 * @param newStudent
	 * @throws InvalidArgumentException
	 */
	public void addStudent( Student newStudent ) throws InvalidArgumentException
	{
		if ( newStudent == null )
		{
			throw new InvalidArgumentException( "addStudent: Invalid Arugment 'Student'" );
		}
		else
		{
			this.students.add(  newStudent );
		}

	}

	/**
	 * 
	 * 
	 * 
	 * @param selectedStudent
	 * @throws InvalidArgumentException
	 */
	public void removeStudent( Student selectedStudent ) throws InvalidArgumentException
	{
		if ( selectedStudent == null )
		{
			throw new InvalidArgumentException( "removeStudent: Invalid Arugment 'Student'" );
		}
		else
		{
			this.students.remove( selectedStudent );
		}

	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	public int noOfTutors()
	{
		return this.tutors.size();
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	public int noOfStudent()
	{
		return this.students.size();
	}

}
