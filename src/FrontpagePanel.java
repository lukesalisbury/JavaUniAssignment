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


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Index Page containing links to other panel section.
 * @author Luke Salisbury
 */
public class FrontpagePanel extends JPanel
{
	private MainWindow windowParent;
	
	private JPanel panelRequest;
	private JPanel panelSessons;
	private JPanel panelTutor;
	private JPanel panelStudent;
	
	private JPanel panelNetworkStatus;
	private JLabel labelNetworkMessage;
	private JToggleButton buttonNetworkStatus;

	private ImageIcon iconNetworkRunning;
	private ImageIcon iconNetworkOffline;
	

	
	/**
	 * Creates a new FrontpagePanel
	 * @param window Parent Window
	 */
	public FrontpagePanel( MainWindow window )
	{
		// Crate Actions
		Action sessionAction = new SwitchPageAction( window, "session" );
		Action requestAction = new SwitchPageAction( window, "request" );
		Action studentAction = new SwitchPageAction( window, "student" );
		Action tutorAction = new SwitchPageAction( window, "tutor" );
	

		this.windowParent = window;

		// Cache Network Status Icons
		try {
			this.iconNetworkRunning = new ImageIcon( getClass().getResource("images/network-idle.png") );
			this.iconNetworkOffline = new ImageIcon( getClass().getResource("images/network-offline.png") );
		} catch ( Exception e ) {
			System.err.println( "Icon Load Error");
		}


		// Create 
		this.panelRequest = this.createPanelButton( "Request a Tutoring Session", "/dialog-question.40.png", requestAction );
		this.panelSessons = this.createPanelButton( "View Tutoring Sessions", "/calendar.40.png", sessionAction );
		this.panelTutor = this.createPanelButton( "New Tutor", "/tutor-new.40.png", tutorAction );
		this.panelStudent = this.createPanelButton( "New Student", "/contact-new.40.png", studentAction );
		this.panelNetworkStatus = this.createNetworkStatusPanel();

		// Add widget
		this.add( panelTutor );
		this.add( panelStudent );
		this.add( panelRequest );
		this.add( panelSessons );
		this.add( panelNetworkStatus );

		// Panel Option
		this.setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setMinimumSize( new Dimension(262, 300) );
		this.setPreferredSize( new Dimension(500, 300) );
	}

	/**
	 * Set text on Network Message Label
	 * @param message
	 */
	public void setNetworkMessage( String message )
	{
		labelNetworkMessage.setText( message );
	}

	/**
	 * Creates the Network Status Panel
	 * @return A JPanel 
	 */
	private JPanel createNetworkStatusPanel()
	{
		JPanel panel = new JPanel();
		Action networkAction = new NetworkModeAction( windowParent, iconNetworkOffline, iconNetworkRunning );
		
		panelNetworkStatus = new JPanel();
		labelNetworkMessage = new JLabel( "", SwingConstants.TRAILING );
		buttonNetworkStatus = new JToggleButton( networkAction );
		
		panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
		
		buttonNetworkStatus.setText("Online Mode");
		buttonNetworkStatus.setIcon(iconNetworkOffline);
		buttonNetworkStatus.setSelected( false );

		panel.add( buttonNetworkStatus );
		panel.add( labelNetworkMessage );

		return panel;
	}

	/**
	 * Creates the Button Panel
	 * @return A JPanel 
	 */
	private JPanel createPanelButton( String message, String iconPath, Action action )
	{
		JPanel panel = new JPanel();
		JButton button = new JButton( action );
		JLabel label = new JLabel( message );

		// Catch any Icon loading issues.
		try {
			ImageIcon icon = new ImageIcon(getClass().getResource("images"+iconPath));
			button.setIcon( icon ); 
		} catch ( Exception e ) {
			System.err.println( "Icon Load Error");
		}

		button.setToolTipText( message );
		
		panel.setLayout( new FlowLayout(FlowLayout.LEFT, 6, 1) );

		panel.add( button );
		panel.add( label );

		return panel;

	}

}
