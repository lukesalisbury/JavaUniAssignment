/* 
 * Created: 12/05/2014
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
 * Handles the Network Toggle Button 
 * @author Luke Salisbury
 */
public class NetworkModeAction extends AbstractAction 
{
	private MainWindow windowParent;
	private Icon iconOffline;
	private Icon iconOnline;
	
	/**
	 *
	 * @param parent Parent window
	 * @param offline Icon used for offline mode
	 * @param online Icon used for online mode
	 */
	public NetworkModeAction( MainWindow parent, Icon offline, Icon online )
	{
		this.windowParent = parent;
		this.iconOffline = offline;
		this.iconOnline = online;
	}
	
	/**
	 * Action
	 * @param ae
	 */
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		JToggleButton button = (JToggleButton)ae.getSource();

		// Get the network status
		if ( this.windowParent.getNetworkStatus() == 0 )
		{
			// Connect
			this.windowParent.showNetworkRequestDialog();
		}
		else
		{
			// Confirm disconnection
			int answer = JOptionPane.showConfirmDialog( this.windowParent, "Disconnect from Server?", "Tutoring System Server", JOptionPane.OK_CANCEL_OPTION );
			if ( answer == JOptionPane.OK_OPTION )
			{
				this.windowParent.setNetworkStatus(0);
			}
		}

		// Update icon and select mode on button
		if ( this.windowParent.getNetworkStatus() == 1 )
		{
			button.setIcon( this.iconOnline );
			button.setSelected( true );
		}
		else
		{
			button.setIcon( this.iconOffline );
			button.setSelected( false );
		}
	}
}
