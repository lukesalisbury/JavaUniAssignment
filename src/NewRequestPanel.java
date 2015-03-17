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
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Luke Salisbury
 */
public class NewRequestPanel extends SubmitFormPanel implements ActionListener 
{
	private JComboBox comboStudentCourse;
	private JComboBox comboStudentID;
	private JList listTutors;
	private JCheckBox checkSubmit;
	private Student currentStudent = null;

	private String [] defaultStudentCourses = {"-- Enter Student ID --"};
	private String [] defaultTutorsNames = {"-- Enter Student ID --"};

	/** 
	 * Creates new form NewRequestPanel
	 * @param parent 
	 */
	public NewRequestPanel( MainWindow parent )
	{
		super(parent);

		// Other Panels
		initialiseTitlePanel();
		initialiseStudentPanel();
		initialiseTutorPanel();
		initialiseButtonPanel();

	}

	/**
	 * Check to see if combo provide a valid syudent and updates currentStudent.
	 */
	private boolean isValidCurrentStudent()
	{
		String studentID = (String)comboStudentID.getSelectedItem();

		if ( studentID != null )
		{
			//Disable error dialog as we just looking
			windowParent.getCommandClass().setDisableDialogs(true);

			this.currentStudent = windowParent.getCommandClass().studentInfo( studentID );
			//Enable error dialogs
			windowParent.getCommandClass().setDisableDialogs(false);
		}
		else
		{
			this.currentStudent = null;
		}

		return (this.currentStudent != null);
	}

	/**
	 *
	 */
	public void updateTutorList()
	{
		String courseCode = (String)comboStudentCourse.getSelectedItem();

		if ( this.currentStudent != null && courseCode != null )
		{
			int size;
			ArrayList<Tutor> list;
			String [] tutorArray;

			windowParent.getCommandClass().setDisableDialogs( true ); //Disable error dialog as we just looking

			list = windowParent.getCommandClass().tutorSearch( courseCode );

			size = list.size();
			if ( size > 0 )
			{
				tutorArray = new String[size];
				// Add subject names to array.
				for ( int c = 0; c < size; c++ )
				{
					tutorArray[c] = list.get(c).getFullName();
				}
				listTutors.setListData( tutorArray );
			}
		}
		else
		{
			listTutors.setListData( defaultTutorsNames ); //Set a default list
		}


		windowParent.getCommandClass().setDisableDialogs( false ); //re-enable error dialog
	}

	/**
	 * Update subject based on student enrolled course
	 */
	public void updateSubjectComboBox()
	{
		comboStudentCourse.removeAllItems();

		if ( this.currentStudent != null )
		{
			ArrayList<Subject> subjectArray = this.currentStudent.getSubjectEnrolled();
			for (Subject subject : subjectArray)
			{
				comboStudentCourse.addItem( subject.getSubjectCode() );
			}
		}
		else
		{
			comboStudentCourse.addItem( defaultStudentCourses[0] );
		}

	}

	/**
	 * Helper Method to create the bottom button panel
	 */
	private void initialiseTitlePanel()
	{
		//Title Panel
		JPanel panelTitle = new JPanel( new BorderLayout() );
		JLabel labelTitle = new JLabel("Request a Tutoring Session", SwingConstants.LEADING );

		panelTitle.add( labelTitle );

		this.add( panelTitle );

	}

	/**
	 * Helper Method to create the bottom button panel
	 */
	private void initialiseStudentPanel()
	{
		JPanel panelStudent = new JPanel();
		JLabel labelStudentID = new JLabel( "Student ID", SwingConstants.TRAILING );
		JLabel labelStudentCourse = new JLabel( "Course", SwingConstants.TRAILING );

		comboStudentID = new JComboBox();
		comboStudentCourse = new JComboBox( defaultStudentCourses );

		panelStudent.setLayout( new GridLayout(2, 2) );

		panelStudent.add( labelStudentID );
		panelStudent.add( comboStudentID );

		panelStudent.add( labelStudentCourse );
		panelStudent.add( comboStudentCourse );

		comboStudentID.addActionListener( this );
		comboStudentCourse.addActionListener( this );

		this.add(panelStudent);
	}
	/**
	 * Helper Method to create the tutor panel
	 */
	private void initialiseTutorPanel()
	{
		JPanel panelTutors = new JPanel( new BorderLayout() );
		JLabel labelTutors = new JLabel( "Tutors Available" );
		JScrollPane scrollTutors = new JScrollPane();
		listTutors = new JList();

		scrollTutors.setViewportView(listTutors);

		panelTutors.add(labelTutors, BorderLayout.PAGE_START);
		panelTutors.add(scrollTutors, BorderLayout.CENTER);

		this.add(panelTutors);
	}

	/**
	 * Helper Method to create the bottom button panel
	 */
	private void initialiseButtonPanel()
	{
		Action ableAction;
		Action returnAction = new SwitchPageAction( windowParent, "frontpage" );
		Action submitAction = new SubmitFormAction( this );

		// Button Panel
		JPanel panelButtons = new JPanel();
		JButton buttonReturn = this.createIconButton( "Return", "go-previous.png", returnAction );
		JButton buttonSubmit = this.createIconButton( "Make Request", "go-next.png", submitAction );
		checkSubmit = new JCheckBox("Agree");
		Box.Filler fillerButtons = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0) );

		ableAction = new CheckAbleAction( "Agree", buttonSubmit );

		checkSubmit.setAction( ableAction );
		
		buttonSubmit.setEnabled( false );
		panelButtons.setLayout( new BoxLayout(panelButtons, BoxLayout.LINE_AXIS) );

		panelButtons.add( buttonReturn );
		panelButtons.add( fillerButtons );
		panelButtons.add( checkSubmit );
		panelButtons.add( buttonSubmit );

		this.add( panelButtons );

	}

	/**
	 * Submit Action
	 */
	@Override
	public void submit()
	{
		String subjectCode;
		String tutorName;

		subjectCode = (String)comboStudentCourse.getSelectedItem();
		tutorName = (String)listTutors.getSelectedValue();

		if ( this.currentStudent == null )
		{
			JOptionPane.showMessageDialog( windowParent, "No Student Selected", "Error", JOptionPane.ERROR_MESSAGE );
		}
		else if ( subjectCode == null )
		{
			JOptionPane.showMessageDialog( windowParent, "No Subject Selected", "Error", JOptionPane.ERROR_MESSAGE );
		}
		else if ( tutorName == null )
		{
			JOptionPane.showMessageDialog( windowParent, "No Tutor Selected", "Error", JOptionPane.ERROR_MESSAGE );
		}
		windowParent.getCommandClass().requestAdd( tutorName, subjectCode, this.currentStudent.getStudentNo()  );

		checkSubmit.doClick( );
		
	}

	/**
	 * Action
	 * @param ae
	 */
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if ( ae.getSource() == comboStudentID )
		{
			if ( isValidCurrentStudent() )
			{
				updateSubjectComboBox();
			}
		}

		if ( ae.getSource() == comboStudentCourse )
		{
			updateTutorList();
		}
	}

	/**
	 * refresh 
	 * Called when the Panel is switched to.
	 */
	@Override
	public void refresh()
	{
		//Update the Student combo box
		ArrayList<Student> studentArray = windowParent.getCommandClass().studentList();
		for ( Student student : studentArray )
		{
			comboStudentID.addItem( student.getStudentNo() );
		}

		// Only set index if array contain content
		if ( studentArray.size() > 0 )
		{
			comboStudentID.setSelectedIndex(0);
		}
		else
		{
			comboStudentID.setSelectedIndex(-1);
		}
	}
}
