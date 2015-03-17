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
 * Base class for handling reading of text files
 * @author Luke Salisbury
 */
public class TextFileDatabase
{
	private File fileSource;
	private Scanner fileScanner; 

	/**
	 *
	 */
	protected ArrayList<String> records;

	/**
	 *
	 * @param source
	 */
	public TextFileDatabase( String source ) 
	{
		fileSource = new File( source );
		records = new ArrayList();

		System.out.println( "Database " + fileSource.getAbsolutePath() );
	}

	/**
	 *
	 * @throws FileNotFoundException
	 */
	protected void read() throws FileNotFoundException
	{
		fileScanner = new Scanner( fileSource );
		while ( fileScanner.hasNextLine() )
		{
			String buffer = fileScanner.nextLine().trim();
			if ( buffer.length() > 0 )
			{
				records.add( buffer );
			}
		}
		fileScanner.close();
	}

	/**
	 *
	 * @throws FileNotFoundException
	 */
	public void load() throws FileNotFoundException
	{
		records.clear();
		this.read();
	}

	/**
	 *
	 * @throws IOError
	 */
	public void save() throws IOError
	{
		for ( String record : records )
		{
			System.out.println( record );
		}
	}

	/**
	 *
	 * @return
	 */
	public int recordCount()
	{
		return records.size();
	}

	/**
	 *
	 * @param index
	 * @return
	 */
	public String get( int index )
	{
		String buffer; 
		buffer = records.get( index );
		return buffer;
	}

	/**
	 *
	 * @param subjectsBuffer
	 * @return
	 */
	protected ArrayList<Subject> generateSubjects( String subjectsBuffer )
	{
		ArrayList<Subject> returnArray = new ArrayList();
		Scanner recordsScanner;
		String codeBuffer = "";
		String titleBuffer = "";
		int hoursBuffer = 0;
		int scannerPosition = 0;

		recordsScanner = new Scanner(subjectsBuffer);
		recordsScanner.useDelimiter( ":" );

		while ( recordsScanner.hasNext() )
		{
			switch (scannerPosition)
			{
				default:
				case 0: // code
					codeBuffer = recordsScanner.next();
					break;
				case 1: //Title
					titleBuffer = recordsScanner.next();
					break;
				case 2: // hours
					hoursBuffer = recordsScanner.nextInt();
					break;
			}

			scannerPosition++;

			// When all three fields are read, create subject objects
			if ( scannerPosition == 3 )
			{
				Subject supportedSubject = new Subject( codeBuffer, titleBuffer, hoursBuffer );

				returnArray.add( supportedSubject );

				// Clear buffer
				codeBuffer = "";
				titleBuffer = "";
				hoursBuffer = 0;

				scannerPosition = 0;
			}

		}
		return returnArray;
	}


}
