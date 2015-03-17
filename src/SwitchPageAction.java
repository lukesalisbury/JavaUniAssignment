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



import java.awt.event.*;
import javax.swing.*;


/**
 * Action to switch the CardLayout on MainWindow
 * @author Luke Salisbury
 */
public class SwitchPageAction extends AbstractAction 
{
	private MainWindow windowParent;
	private String page;
	
	/**
	 * 
	 * @param parent 
	 * @param newPage name of page to switch to.
	 */
	public SwitchPageAction( MainWindow parent, String newPage )
	{
		this.windowParent = parent;
		this.page = newPage;

	}
	
	/**
	 * Action Listener
	 * @param ae
	 */
	@Override
	public void actionPerformed( ActionEvent ae )
	{
		this.windowParent.setPage( page );
	}
}

