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

/**
 * Subject Class
 * @author Luke Salisbury
 */
public class Subject implements Serializable
{
	private String subjectCode;
	private String subjectName;
	private int yearLevel;

	/**
	 *
	 * @param subjectCode
	 * @param subjectName
	 * @param yearLevel
	 */
	public Subject( String subjectCode, String subjectName, int yearLevel )
	{
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.yearLevel = yearLevel;
	}

	/**
	 *
	 * @return
	 */
	public String getSubjectCode()
	{
		return subjectCode;
	}

	/**
	 *
	 * @return
	 */
	public String getSubjectName()
	{
		return subjectName;
	}

	/**
	 *
	 * @return
	 */
	public int getYearLevel()
	{
		return yearLevel;
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	public boolean equals( Subject obj )
	{
		if ( obj == null ) {
			return false;
		}
		return this.subjectCode.equals( obj.subjectCode );
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		return subjectName + " [" + subjectCode + "] Year Level: " + yearLevel;
	}




}
