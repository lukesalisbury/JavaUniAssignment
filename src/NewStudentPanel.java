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
import java.util.*;
import javax.swing.*;

/**
 * New Student Form 
 * @author Luke Salisbury
 */
public class NewStudentPanel extends SubmitFormPanel
{

	private JList listSubjects;
	private JTextField textContact;
	private JTextField textFamily;
	private JTextField textFirst;
	private JTextField textID;
	private DefaultListModel modelSubjects;

	/** 
	 * Creates new form.
	 * @param parent Parent Windows of the form.
	 */
	public NewStudentPanel( MainWindow parent )
	{
		super(parent);

		/* Create Widgets */

		/* Title */
		JPanel panelTitle = new JPanel( new BorderLayout() );
		JLabel labelTitle = new JLabel( "Add New Student", SwingConstants.LEADING  );

		panelTitle.add(labelTitle);

		this.add(panelTitle);

		/* Student Details */
		JPanel panelDetails = new JPanel( new GridLayout(4, 2) );
		JLabel labelFirst = new JLabel("First Name", SwingConstants.TRAILING);
		JLabel labelFamily = new JLabel("Family Name", SwingConstants.TRAILING);
		JLabel labelID = new JLabel("Student ID", SwingConstants.TRAILING);
		JLabel labelContact = new JLabel("Contact Number", SwingConstants.TRAILING);

		textFirst = new JTextField();
		textFamily = new JTextField();
		textID = new JTextField();
		textContact = new JTextField();

		panelDetails.add( labelFirst );
		panelDetails.add( textFirst );

		panelDetails.add( labelFamily );
		panelDetails.add( textFamily );

		panelDetails.add( labelID );
		panelDetails.add( textID );

		panelDetails.add( labelContact );
		panelDetails.add( textContact );

		this.add( panelDetails );

		/* Student Details */
		modelSubjects = new DefaultListModel();
		JScrollPane scrollSubjects = new JScrollPane();
		JPanel panelSubjects = new JPanel();
		listSubjects = new JList( modelSubjects );

		listSubjects.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		panelSubjects.setBorder( BorderFactory.createTitledBorder("Subjects") );
		panelSubjects.setLayout( new BoxLayout(panelSubjects, BoxLayout.LINE_AXIS) );

		scrollSubjects.setViewportView(listSubjects);

		panelSubjects.add(scrollSubjects);

		this.add(panelSubjects);

		/* Button Panel */
		Action returnAction = new SwitchPageAction( windowParent, "frontpage" );
		Action submitAction = new SubmitFormAction( this );

		JPanel panelButtons = new JPanel();
		JButton buttonReturn = this.createIconButton( "Return", "go-previous.png", returnAction );
		JButton buttonSubmit = this.createIconButton( "Submit", "go-next.png", submitAction );

		Box.Filler fillerButtons = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0) );

		panelButtons.setLayout( new BoxLayout(panelButtons, BoxLayout.LINE_AXIS) );


		/* Add Widgets to panel */
		panelButtons.add( buttonReturn );
		panelButtons.add( fillerButtons );
		panelButtons.add( buttonSubmit );

		this.add( panelButtons );

	}

	/**
	 * submit 
	 * Submits current from panel.
	 */
	@Override
	public void submit()
	{
		String inputFirstName;
		String inputFamilyName;
		String inputContact;
		String inputStudentID;
		String inputStudentSubject = "";

		inputFirstName = textFirst.getText();
		inputFamilyName = textFamily.getText();
		inputContact = textContact.getText();
		inputStudentID = textID.getText();

		// Check for Incomplete information.
		if ( inputFirstName == null || inputFirstName.length() < 2 )
		{
			JOptionPane.showMessageDialog( windowParent, "No First Name (or too short)", "Error", JOptionPane.ERROR_MESSAGE );
		}
		else if ( inputFamilyName == null || inputFamilyName.length() < 2  )
		{
			JOptionPane.showMessageDialog( windowParent, "No Family Name (or too short)", "Error", JOptionPane.ERROR_MESSAGE );
		}
		else if ( inputContact == null || inputContact.length() < 2 )
		{
			JOptionPane.showMessageDialog( windowParent, "No Contact Details (or too short)", "Error", JOptionPane.ERROR_MESSAGE );
		}
		else if ( inputStudentID == null || inputStudentID.length() < 2 )
		{
			JOptionPane.showMessageDialog( windowParent, "No Student ID (or too short)", "Error", JOptionPane.ERROR_MESSAGE );
		}
		else
		{
			/* Get Selected subjects */
			java.util.List<String> subjects = listSubjects.getSelectedValuesList();
			int subjectsCount = subjects.size();
			for (int i = 0; i < subjectsCount; i++)
			{
				inputStudentSubject += subjects.get(i);
				if ( subjectsCount != i + 1 )
				{
					inputStudentSubject += ",";
				}
			}

			/* Add new Student */
			windowParent.getCommandClass().studentAdd(inputFirstName, inputFamilyName, inputContact, inputStudentID, inputStudentSubject );
		}
	}

	/**
	 * refresh 
	 * Called when the Panel is switched to.
	 */
	@Override
	public void refresh()
	{
		//Update the Subject List
		modelSubjects.clear();

		ArrayList<Subject> subjectArray = windowParent.getCommandClass().subjectsList();
		for ( Subject subject : subjectArray )
		{
			modelSubjects.addElement( subject.getSubjectCode());
		}

	}
}
