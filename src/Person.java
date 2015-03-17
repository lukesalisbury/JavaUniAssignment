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
import java.util.Objects;

/**
 *
 * @author luke
 */
public class Person implements Serializable
{
	static int PERSONAL_NAME_FIRST = 1;
	static int FAMILY_NAME_FIRST = 0;

	protected String firstName;
	protected String familyName;
	protected String contactDetails;
	protected int nameOrder = PERSONAL_NAME_FIRST;

	/**
	 * Create a person
	 * @param firstName
	 * @param familyName
	 * @param contactDetails
	 */
	public Person( String firstName, String familyName, String contactDetails )
	{
		this.firstName = firstName;
		this.familyName = familyName;
		this.contactDetails = contactDetails;
	}

	/**
	 * Get the Name order
	 * @return 
	 */
	public int getNameOrder()
	{
		return nameOrder;
	}

	/**
	 *
	 * @param nameOrder
	 */
	public void setNameOrder(int nameOrder)
	{
		this.nameOrder = nameOrder;
	}

	/**
	 *
	 * @return
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 *
	 * @return
	 */
	public String getFamilyName()
	{
		return familyName;
	}

	/**
	 *
	 * @return
	 */
	public String getContactDetails()
	{
		return contactDetails;
	}

	/**
	 *
	 * @return
	 */
	public String getFullName()
	{
		if ( this.nameOrder == FAMILY_NAME_FIRST )
		{
			return this.getFamilyName()  + " " + this.getFirstName(); 
		}
		else
		{
			// Alway Fallback to PERSONAL_NAME_FIRST if nameOrder is incorrectly set
			return this.getFirstName() + " " + this.getFamilyName();
		}
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		String value;
		value = firstName + " " + familyName + 
				" - Contact Details:" + contactDetails;
		return value;
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null )
		{
			return false;
		}
		if ( getClass() != obj.getClass() )
		{
			return false;
		}
		
		Person other = (Person) obj;
		if ( !Objects.equals( this.firstName, other.firstName ) )
		{
			return false;
		}
		if ( !Objects.equals( this.familyName, other.familyName ) )
		{
			return false;
		}
		return true;
	}





}
