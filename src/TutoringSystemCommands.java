/* 
 * Created: 13/05/2014
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

import javax.swing.*;

/**
 * Extends SystemCommands to provide Dialog Messages
 * @author Luke Salisbury
 */
public class TutoringSystemCommands extends SystemCommands
{
	private boolean disableDialogs = true;

	/**
	 * Disable showing message dialog.
	 * @param disableDialogs
	 */
	public void setDisableDialogs( boolean disableDialogs )
	{
		this.disableDialogs = disableDialogs;
	}
	
	/**
	 * Logs output
	 * @param message Content
	 * @param isError type of message
	 */
	@Override
	protected void logMessage( String message, boolean isError )
	{
		System.out.println( message );
		if ( !this.disableDialogs )
		{
			if ( isError )
			{
				JOptionPane.showMessageDialog( null, message, "Error", JOptionPane.ERROR_MESSAGE );
			}
			else
			{
				JOptionPane.showMessageDialog( null, message, "Information", JOptionPane.INFORMATION_MESSAGE );
			}
		}
	}

}
