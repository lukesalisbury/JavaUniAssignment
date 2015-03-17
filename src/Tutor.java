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
 * Tutor Class
 * @author Luke Salisbury
 */

public class Tutor extends Person implements Serializable
{
	private ArrayList<Subject> subjectOffered;
	private int availableHours;
	
	/**
	 *
	 * @param firstName
	 * @param familyName
	 * @param contactDetails
	 * @param subjectOffered
	 * @param availableHours
	 */
	public Tutor( String firstName, String familyName, String contactDetails, ArrayList<Subject> subjectOffered, int availableHours )
	{
		super( firstName, familyName, contactDetails );
		this.subjectOffered = subjectOffered;
		this.availableHours = availableHours;
	}

	/**
	 *
	 * @return
	 */
	public int getHours()
	{
		return availableHours;
	}

	/**
	 *
	 * @param amount
	 */
	public void subtractHours( int amount )
	{
		if ( this.availableHours - amount >= 0 )
		{
			this.availableHours -= amount;
		}
	}

	/**
	 *
	 * @return
	 */
	public String getSubjectList()
	{
		return this.subjectOffered.toString();
	}

	/**
	 *
	 * @param searchTerm
	 * @return
	 */
	public boolean searchForSubject( Subject searchTerm )
	{
		for ( Subject offeredSubject : this.subjectOffered )
		{
			if ( searchTerm.equals( offeredSubject ) ) 
			{
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param searchTerm
	 * @return
	 */
	public boolean hasSubjectByCode( String searchTerm )
	{
		for ( Subject offeredSubject : this.subjectOffered )
		{
			if ( offeredSubject.getSubjectCode().equals( searchTerm ) ) 
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		String value;
		value = "Tutor " + firstName + " " + familyName + 
				" - Contact Details:" + contactDetails +
				" - Available Hours:" + availableHours +
				" - Subject Offered:" + subjectOffered;
		return value;
	}






}
