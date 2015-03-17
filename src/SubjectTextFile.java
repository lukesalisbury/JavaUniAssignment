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
 * Handles reading of the Subject text file. 
 * @author Luke Salisbury
 */
public class SubjectTextFile extends TextFileDatabase
{
	protected ArrayList<Subject> availableSubjects;

	/** 
	 * 
	 * @param source Path to Database file
	 * @throws FileNotFoundException 
	 */
	public SubjectTextFile(String source) throws FileNotFoundException
	{
		super(source);

		availableSubjects = new ArrayList();

		this.read(); // Read the file

		// Create and add subject to ArrayList
		for ( String record : this.records )
		{
			ArrayList<Subject> tempArray;

			tempArray = this.generateSubjects( record );
			if ( tempArray != null )
			{
				this.availableSubjects.addAll( tempArray );
			}
		}

	}

	/** 
	 * Get list of subject in a record
	 * 
	 * @param record 
	 * @return ArrayList of Subjects
	 */
	public ArrayList<Subject> getList( int record )
	{
		String subjectBuffer;
		ArrayList<Subject> returnArray;

		subjectBuffer = this.get( record );

		returnArray = this.generateSubjects( subjectBuffer );

		return returnArray;
	}

	/** 
	 * Get Subject From a CourseCode
	 * @param courseCode Course code 
	 * @return Subject for the course code
	 * @throws InvalidSubjectException On invalid course code
	 */
	public Subject getSubjectFromCourseCode( String courseCode ) throws InvalidSubjectException
	{

		for ( Subject subject : this.availableSubjects ) 
		{
			if ( subject.getSubjectCode().equalsIgnoreCase( courseCode ) )
			{
				return subject;
			}

		}
		throw new InvalidSubjectException(courseCode);
	}

}
