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


import javax.swing.*;

/**
 * Launcher for TutoringSystemGUI
 * @author Luke Salisbury
 */
public class TutoringSystemGUI
{
	/**
	 * Entry point
	 * @param args the command line arguments
	 */
	public static void main( String[] args )
	{
		// Disabled Native look code.
		/*
		try {
			// Set System L&F
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (UnsupportedLookAndFeelException e) {
			System.err.println( e.getMessage() );
		} catch (ClassNotFoundException e) {
			System.err.println( e.getMessage() );
		} catch (InstantiationException e) {
			System.err.println( e.getMessage() );
		} catch (IllegalAccessException e) {
			System.err.println( e.getMessage() );
		}
		*/

		MainWindow window = new MainWindow( "Tutoring System" );
	}
	
}
