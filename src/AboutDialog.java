/* 
 * Created: 12/05/2014
 * Author: Luke Salisbury <luke.salisbury@live.vu.edu.au>
 * Student Number: 1510439
 * License: Creative Commons Attribution-NonCommercial-ShareAlike 4.0 
 * License URL: http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Implement a Tutoring System that would register tutors interested in 
 * helping students
 */

import java.awt.*;
import javax.swing.*;

/**
 * Application About Dialog
 * @author Luke Salisbury
 */
public class AboutDialog extends SubmitFormDialog
{
	AboutDialog( MainWindow parent )
	{
		super( "About Application", parent );
		
		// Editor Area
		JPanel panelAbout = new JPanel();
		JScrollPane scrollAbout = new JScrollPane();
		JEditorPane editorAbout = new JEditorPane( "text/html", "<html><body><h1>Tutoring System</h1><p><strong>Goal:</strong> Implement a Tutoring System that would register tutors interested in helping students.</p><p><strong>Author:</strong> Luke Salisbury &lt;<a href=\"mailto:luke.salisbury@live.vu.edu.au\">luke.salisbury@live.vu.edu.au</a>&gt;.</p>	<p><strong>License:</strong> <a href=\"http://creativecommons.org/licenses/by-nc-sa/4.0/\">Creative Commons Attribution-NonCommercial-ShareAlike 4.0</a>.</p><p> Contants Artwork from the <a href=\"http://tango.freedesktop.org/\">Tango Project</a>.</body></html>" );

		editorAbout.setEditable( false );
		scrollAbout.setViewportView( editorAbout );

		panelAbout.setLayout( new BoxLayout(panelAbout, BoxLayout.PAGE_AXIS));

		panelAbout.add( scrollAbout );

		// Button Panel
		Action closeAction = new DialogCloseAction( "Close", this );
		JPanel panelButtons = new JPanel( new FlowLayout(FlowLayout.TRAILING) );		
		JButton buttonClose = new JButton( closeAction );

		panelButtons.add(buttonClose);

		// Add widget to panel
		this.getContentPane().setLayout( new BorderLayout() );
		this.getContentPane().add( panelAbout, BorderLayout.CENTER);
		this.getContentPane().add( panelButtons, BorderLayout.PAGE_END);
	}

	/**
	 * Submit Action
	 */
	@Override
	public void submit()
	{
		
	}

	/**
	 * Method to hide dialog.
	 */
	@Override
	public void hideDialog()
	{
		this.setVisible(false);
	}

	/**
	 * Method to show dialog.
	 */
	@Override
	public void showDialog()
	{
		this.setVisible(true);
	}

}
