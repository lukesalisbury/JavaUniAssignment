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



import java.awt.Dimension;
import javax.swing.*;

/**
 * abstract class for SubmitFormDialog
 * @author Luke Salisbury
 */
public abstract class SubmitFormDialog extends JDialog
{
	protected final MainWindow windowParent;

	/**
	 *
	 * @param Title Title of Action
	 * @param parent Parent Window
	 */
	public SubmitFormDialog( String Title, MainWindow parent )
	{
		windowParent = parent;

		this.setModal(true);
		this.setTitle( Title );
		this.setMinimumSize( new Dimension(500, 300) );
	}

	/**
	 * Submit Action
	 */
	abstract public void submit();

	/**
	 * Method to hide dialog.
	 */
	abstract public void hideDialog();

	/**
	 * Method to show dialog.
	 */
	abstract public void showDialog();

}
