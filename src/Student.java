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
import java.util.ArrayList;

/**
 * Student Class
 * @author Luke Salisbury
 */

public class Student extends Person implements Serializable
{
	private String studentNo;
	private ArrayList<Subject> subjectEnrolled;

	/**
	 *
	 * @param firstName
	 * @param familyName
	 * @param contactDetails
	 * @param studentNo
	 * @param subjectEnrolled
	 */
	public Student( String firstName, String familyName, String contactDetails, String studentNo, ArrayList<Subject> subjectEnrolled )
	{
		super( firstName, familyName, contactDetails );
		this.studentNo = studentNo;
		this.subjectEnrolled = subjectEnrolled;
	}

	/**
	 *
	 * @param searchTerm
	 * @return
	 */
	public boolean hasSubjectByCode( String searchTerm )
	{
		for ( Subject enrolledSubject : this.subjectEnrolled )
		{
			if ( enrolledSubject.getSubjectCode().equals( searchTerm ) ) 
			{
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @return
	 */
	public String toString()
	{
		String value;
		value = "Student " + firstName + " " + familyName + 
				" - Contact Details:" + contactDetails +
				" - Student ID:" + studentNo +
				" - Subject Enrolled:" + subjectEnrolled;
		return value;
	}

	/**
	 *
	 * @return
	 */
	public String getStudentNo()
	{
		return studentNo;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<Subject> getSubjectEnrolled()
	{
		return subjectEnrolled;
	}

	/**
	 *
	 * @param subject
	 */
	public void addSubject( Subject subject )
	{
		this.subjectEnrolled.add(subject);

	}






}
