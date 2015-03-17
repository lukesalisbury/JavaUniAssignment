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



import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Luke Salisbury
 */
public abstract class SubmitFormPanel extends JPanel implements ComponentListener
{
	protected final MainWindow windowParent;

	/**
	 * submit 
	 * Submits current from panel.
	 * @param parent
	 */
	public SubmitFormPanel( MainWindow parent )
	{
		windowParent = parent;
		this.addComponentListener( this );
		this.setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS) );
	}


	/**
	 * Submits current from panel.
	 */
	abstract public void submit();

	/**
	 * Called when the Panel is switched to.
	 */
	abstract public void refresh();

	/** 
	 * Create a Icon button such as return and submit buttons.
	 * @param title
	 * @param iconPath
	 * @param action
	 * @return 
	 */
	public JButton createIconButton( String title, String iconPath, Action action )
	{
		ImageIcon icon;
		JButton button;
		icon = new ImageIcon( getClass().getResource( "images/" + iconPath ) );
		button = new JButton( );

		if ( action != null )
		{
			button.setAction(action);
		}
		button.setText(title);
		button.setIcon(icon);

		return button;
	}

	/**
	 * Unused 
	 * @param e
	 */
	public void componentHidden(ComponentEvent e)
	{

	}

	/**
	 * Unused 
	 * @param e
	 */
	public void componentMoved(ComponentEvent e)
	{
	}

	/**
	 * Unused
	 * @param e
	 */
	public void componentResized(ComponentEvent e)
	{

	}

	/**
	 * Calls refresh when conponent is showned.
	 * @param e
	 */
	public void componentShown( ComponentEvent e )
	{
		this.refresh();
	}

}
