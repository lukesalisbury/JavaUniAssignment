/* 
 * Created: 08/05/2014
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

import java.awt.event.*;
import javax.swing.*;

/**
 * Closes the Main Program with a confirm dialog.
 * @author Luke Salisbury
 */
public class WindowCloseAction extends AbstractAction 
{

	/**
	 * 
	 * @param title Title of Action
	 */
	public WindowCloseAction( String title )
	{
		super(title);
	}

	/**
	 *
	 * @param ae
	 */
	@Override
	public void actionPerformed( ActionEvent ae )
	{
		MainWindow window = (MainWindow)this.getValue( "window" );
		if ( JOptionPane.showConfirmDialog( window, "Exit program?", "Do you wish to exit program?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION )
		{
			System.exit(0);
		}
	
	}
	
}
