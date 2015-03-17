/* 
 * Created: 09/05/2014
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
import javax.swing.*;

/**
 * Extends TutoringSystemCommands to add Directory choose if TutoringSystem does not load
 * @author Luke Salisbury
 */
public class LocalTutoringSystemCommands extends TutoringSystemCommands
{
	/**
	 * 
	 */
	public LocalTutoringSystemCommands()
	{
		String path = "..";
		boolean loaded;

		loaded = initialiseTutoringSystem(path);

		if ( loaded == false )
		{
			while ( !loaded )
			{
				/* Ask user to point to database directory */
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

				if ( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION )
				{
					path = chooser.getSelectedFile().getAbsolutePath();
					loaded = initialiseTutoringSystem(path);
				}
				else
				{
					System.exit(1);
				}
			}
		}

		this.studentAdd( "Fake", "Student", "9999 1111", "2345678", "ECB2112,ECB2124" );

		this.setDisableDialogs( false );
	}

	
	/**
	 * Initialise a TutoringSystem and return true is successful.
	 * @param path Directory path
	 * @return true if TutoringSystem is able to load.
	 */
	private boolean initialiseTutoringSystem( String path )
	{
		try {
			this.tutorSystem = new TutoringSystem(path);
			this.setTutorSystem(tutorSystem);
			return true;
		} catch ( FileNotFoundException ex ) {
			JOptionPane.showMessageDialog( null, 
				"Invaild Path: " + path + "\nError: " + ex.getMessage(),
				"Tutoring System",
				JOptionPane.ERROR_MESSAGE );
		}
		return false;
	}

}
