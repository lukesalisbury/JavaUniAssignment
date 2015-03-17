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

import java.awt.event.*;
import javax.swing.*;

/**
 * Action to call submit on a SubmitFormDialog.
 * @author Luke Salisbury
 */
public class ViewDetailsAction extends AbstractAction
{
	SubmitFormDialog parentFormPanel;

	/**
	 *
	 * @param parent SubmitFormDialog to apply the submit action to
	 */
	public ViewDetailsAction( SubmitFormDialog parent )
	{
		super( "View" );
		this.parentFormPanel = parent;
	}
	
	/**
	 * Action Listiner
	 * @param ae
	 */
	@Override
	public void actionPerformed( ActionEvent ae )
	{
		this.parentFormPanel.submit();
	}
	
}
