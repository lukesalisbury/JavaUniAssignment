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

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * Dialog to show a list of Student
 * @author Luke Salisbury
 */
public class StudentsDialog extends SubmitFormDialog
{
	private JEditorPane editorDetails;
	private JList listNames;
	private final String detailsFormat = "<html><body><h1>%s</h1><div><b>Name:</b> %s</div><div><b>Student ID:</b> %s</div><div><b>Contact Details:</b> %s</div><div><b>Subjects:</b> %s</div></body></html>";
	private final String errorFormat = "<html><body><h1>%s</h1><div style=\"color:red\">Can not retrieve info on '%s'</div></body></html>";

	StudentsDialog( MainWindow parent )
	{
		super( "Students", parent );

		// Create Name Listing panel 
		Action viewAction = new ViewDetailsAction( this );
		JPanel panelNames = new JPanel( new BorderLayout() );
		JScrollPane scrollNames = new JScrollPane();
		JButton buttonView = new JButton( viewAction );
		listNames = new JList();

		listNames.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

		scrollNames.setViewportView( listNames );

		panelNames.add( scrollNames, BorderLayout.CENTER );
		panelNames.add( buttonView, BorderLayout.PAGE_END );

		// Create the Detail panel
		JPanel panelDetails = new JPanel();
		JScrollPane scrollDetails = new JScrollPane();

		panelDetails.setBorder( BorderFactory.createTitledBorder("Details") );
		panelDetails.setLayout(new BoxLayout(panelDetails, BoxLayout.PAGE_AXIS));
		editorDetails = new JEditorPane( "text/html", "" );
		
		scrollDetails.setViewportView( editorDetails );

		panelDetails.add( scrollDetails );

		// Create Bottom Button Panel
		Action closeAction = new DialogCloseAction( "Close", this );
		JPanel panelButtons = new JPanel( new FlowLayout(FlowLayout.TRAILING) );
		JButton buttonClose = new JButton( closeAction );

		panelButtons.add(buttonClose);

		// Add widget to Frame 
		getContentPane().setLayout( new BorderLayout() );
		getContentPane().add( panelNames, BorderLayout.WEST);
		getContentPane().add( panelDetails, BorderLayout.CENTER);
		getContentPane().add( panelButtons, BorderLayout.PAGE_END);
	}

	/**
	 * Update the Name List.
	 */
	public void updateNameListing()
	{
		int c = 0;
		String [] data;
		ArrayList<Student> nameList = windowParent.getCommandClass().studentList( );

		data = new String[ nameList.size() ]; // Create a array for JList

		// Loop though the ArrayList

		for ( Student item : nameList )
		{
			data[c] = item.getFirstName() + " " + item.getFamilyName() + " ("+ item.getStudentNo() + ")";
			c++;
		}
		
		// Clear and re-add listing
		listNames.removeAll();
		listNames.setListData( data );

	}

	/**
	 * Submit Action
	 */
	@Override
	public void submit()
	{
		String content = "";
		Student person;
		String name;

		/* Make sure a value is selected */
		if ( listNames.getSelectedIndex() != -1 )
		{
			name = (String)listNames.getSelectedValue();
			person = windowParent.getCommandClass().studentInfo( name.substring( name.lastIndexOf("(")+1, name.lastIndexOf( ")") ) );

			// Make sure a person is retieved.
			if ( person != null )
			{
				content = String.format(detailsFormat, 
						"Student Details", 
						person.getFullName(), 
						person.getStudentNo(), 
						person.getContactDetails(), 
						person.getSubjectEnrolled()
				);
			}
			else
			{
				content = String.format(errorFormat, "Error", name ); 
			}
		}

		editorDetails.setText( content );

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
		this.updateNameListing();
		this.setVisible(true);
	}


}
