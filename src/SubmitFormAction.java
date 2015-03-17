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


import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Action to submit a SubmitFormPanel
 * @author Luke Salisbury
 */
public class SubmitFormAction extends AbstractAction 
{
	private SubmitFormPanel parentFormPanel;
	
	/**
	 *
	 * @param parent Panel to submit
	 */
	public SubmitFormAction( SubmitFormPanel parent )
	{
		this.parentFormPanel = parent;
	}

	/**
	 * Action
	 * @param ae
	 */
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		this.parentFormPanel.submit();
	}

}
