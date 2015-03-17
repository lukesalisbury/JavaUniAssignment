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

import java.io.Serializable;
import java.util.*;



/**
 * Request
 * @author Luke Salisbury
 */
public class Request implements Serializable
{

	protected Tutor tutor;
	protected Student student;
	protected String courseCode;

	/**
	 * Create a new request.
	 * @param tutor
	 * @param student
	 * @param courseCode
	 */
	public Request(Tutor tutor, Student student, String courseCode)
	{
		this.tutor = tutor;
		this.student = student;
		this.courseCode = courseCode;
	}

	/**
	 *
	 * @return
	 */
	public String toString()
	{
		return "Request by " + student.getFullName() + " for tutoring by " + tutor.getFullName() + " in " + courseCode;
	}

	/**
	 *
	 * @return
	 */
	public Tutor getTutor()
	{
		return tutor;
	}

	/**
	 *
	 * @return
	 */
	public Student getStudent()
	{
		return student;
	}

	/**
	 *
	 * @return
	 */
	public String getCourseCode()
	{
		return courseCode;
	}

	/**
	 *
	 * @param tutorFullName
	 * @param subjectCode
	 * @param studentID
	 * @return
	 */
	public boolean match( String tutorFullName, String subjectCode, String studentID )
	{
		if ( this.tutor.getFullName().equalsIgnoreCase(tutorFullName) == false )
		{
			return false;
		}
		if ( this.courseCode.equalsIgnoreCase(subjectCode) == false )
		{
			return false;
		}
		if ( this.student.getStudentNo().equalsIgnoreCase(studentID) == false )
		{
			return false;
		}
		return true;
	}



}
