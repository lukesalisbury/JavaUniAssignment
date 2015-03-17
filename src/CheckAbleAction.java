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
 * Set status of a component based on a check box
 * @author Luke Salisbury
 */
public class CheckAbleAction extends AbstractAction 
{
	private JComponent componentTarget;
	
	/**
	 *
	 * @param name Name of Action
	 * @param target Target JComponent
	 */
	public CheckAbleAction( String name, JComponent target )
	{
		super(name);
		this.componentTarget = target;
	}

	/**
	 * @param ae
	 */
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		JCheckBox checkItem = (JCheckBox)ae.getSource();
		
		this.componentTarget.setEnabled( checkItem.isSelected() );
	}
}
