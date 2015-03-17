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
 * Handles reading of the Tutor text file. 
 * @author Luke Salisbury
 */
public class TutorTextFile extends TextFileDatabase
{

	/**
	 * 
	 * @param source File to open
	 * @throws FileNotFoundException
	 */
	public TutorTextFile( String source ) throws FileNotFoundException
	{
		super(source);
		read();
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<Tutor> getList( )
	{
		ArrayList<Tutor> returnArray = new ArrayList();

		for ( int i = 0; i < this.recordCount(); i++ )
		{
			Tutor newTutor = this.getTutor( i );
			if (newTutor != null )
			{
				returnArray.add( newTutor );
			}
		}
		return returnArray;
	}

	/**
	 *
	 * @param record
	 * @return
	 */
	public Tutor getTutor( int record )
	{
		//hours,First,Last,contact,[code,title,year]
		Tutor newTutor = null;

		String tutorsBuffer;
		String [] details;

		tutorsBuffer = this.get( record );

		details = tutorsBuffer.split( ":", 5 );
		if ( details.length == 5 )
		{
			//Record is valid
			ArrayList<Subject> subjects;

			subjects = this.generateSubjects( details[4] );

			int hours = 0;
			try
			{
				hours = Integer.parseInt( details[0], 10 );
			} catch ( NumberFormatException e )	{
				System.out.println( "Undefined Hours, using default");
			}


			newTutor = new Tutor( details[1], details[2], details[3], subjects, hours );

		}
		return newTutor;


	}


}
