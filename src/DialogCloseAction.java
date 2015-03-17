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

import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Action to close a dialog 
 * @author Luke Salisbury
 */
public class DialogCloseAction extends AbstractAction 
{
	SubmitFormDialog dialog;

	/**
	 *
	 * @param title Title of Action
	 * @param dialog dialog to close
	 */
	public DialogCloseAction( String title, SubmitFormDialog dialog )
	{
		super( title );
		this.dialog = dialog;
	}

	/**
	 *
	 * @param ae
	 */
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		this.dialog.hideDialog();
	}
	
}
