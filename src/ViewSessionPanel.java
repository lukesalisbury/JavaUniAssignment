/* 
 * Created: 10/05/2014
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
import java.awt.print.*;
import java.io.*;
import java.util.*;
import javax.swing.*;


/**
 * Display Session of a personm and offer to save or print
 * @author Luke Salisbury
 */
public class ViewSessionPanel extends SubmitFormPanel implements Printable, ActionListener
{
	private JComboBox comboName;
	private JList listDetails;
	private JRadioButton radioStudent;
	private JRadioButton radioTutor;

	private Action radioChangeAction;
	private DefaultListModel modelRequests;

	private String pageContent = ""; // Use for saving

	/**
	 * Creates new form ViewSessionPanel
	 * @param parent
	 */
	public ViewSessionPanel( MainWindow parent )
	{
		super(parent);
		
		/* Create Action for radio change */
		radioChangeAction = new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				updateNameComboBox();
			}
		};

		/* Create Widget */
		initialiseTitlePanel();
		initialiseNamePanel();
		initialiseSessionPanel();
		initialiseButtonPanel();

	}

	/**
	 * Update the Sesstion list base on the name in the combo
	 */
	public void updateSessionList()
	{
		if ( comboName.getSelectedIndex() != -1 )
		{
			String name = (String)comboName.getSelectedItem();

			if ( name != null )
			{
				ArrayList<Request> requestArray;

				// Search the right database.
				if ( radioStudent.isSelected() )
				{
					requestArray = windowParent.getCommandClass().requestStudent( name );
				}
				else
				{
					requestArray = windowParent.getCommandClass().requestTutor( name );
				}

				// Clear and  Update the list
				modelRequests.clear(); 
				if ( requestArray.size() > 0 )
				{
					for ( Request request : requestArray )
					{
						modelRequests.addElement( request.toString() );
					}
				}
				else
				{
					modelRequests.addElement( "-- No Matches Found --" );
				}
			}
		}
	}
	
	/**
	 *
	 */
	public void updateNameComboBox()
	{
		comboName.removeAllItems();
		if ( radioStudent.isSelected() )
		{
			ArrayList<Student> studentArray = windowParent.getCommandClass().studentList();
			for ( Student student : studentArray )
			{
				comboName.addItem( student.getStudentNo() );
			}
		}
		else
		{
			ArrayList<Tutor> tutorArray = windowParent.getCommandClass().tutorList();
			for ( Tutor tutor : tutorArray )
			{
				comboName.addItem( tutor.getFullName() );
			}
		}
	}
	
	/**
	 * Helper Method to create the Page Title panel
	 */
	private void initialiseTitlePanel()
	{
		//Title Panel
		JPanel panelTitle = new JPanel();
		JLabel labelTitle = new JLabel( "Sessions Details", SwingConstants.LEADING );

		panelTitle.setLayout( new BorderLayout() );
		panelTitle.add( labelTitle );

		this.add( panelTitle );

	}

	/**
	 * Helper Method to create the Person Chooser panel
	 */
	private void initialiseNamePanel()
	{
		/* Create Widgets */
		JPanel panelUser = new JPanel( new GridLayout(2, 2) );
		ButtonGroup groupUserType = new ButtonGroup();
		JLabel labelName = new JLabel( "Name/Student ID" );

		comboName = new JComboBox();
		radioTutor = new JRadioButton( radioChangeAction );
		radioStudent = new JRadioButton( radioChangeAction );

		groupUserType.add( radioTutor );
		groupUserType.add( radioStudent );
		radioTutor.setText( "Tutor" );
		radioStudent.setText( "Student" );

		/* Apply actions */
		comboName.addActionListener( this );

		/* Add widgets */ 
		panelUser.add( labelName );
		panelUser.add( comboName );
		panelUser.add( radioTutor );
		panelUser.add( radioStudent );

		this.add( panelUser );

	}

	/**
	 * Helper Method to create the Session List panel
	 */
	private void initialiseSessionPanel()
	{
		/* Create Widgets */
		JPanel panelDetails = new JPanel( new BorderLayout() );
		JScrollPane scrollDetails = new JScrollPane();

		modelRequests = new DefaultListModel();
		listDetails = new JList( modelRequests );

		/* Add widgets */ 
		scrollDetails.setViewportView( listDetails );
		panelDetails.add(scrollDetails, BorderLayout.CENTER);

		this.add(panelDetails);

	}

	/**
	 * Helper Method to create the bottom button panel
	 */
	private void initialiseButtonPanel()
	{
		Action returnAction = new SwitchPageAction( windowParent, "frontpage" );
		Action printSessionAction = new SubmitFormAction( this );

		/* Button Panel */
		JPanel panelButtons = new JPanel();
		JButton buttonReturn = this.createIconButton( "Return", "go-previous.png", returnAction );
		JButton buttonSubmit = this.createIconButton( "Save & Print Sessions", "document-print.png", printSessionAction );

		Box.Filler fillerButtons = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 0) );

		panelButtons.setLayout( new BoxLayout(panelButtons, BoxLayout.LINE_AXIS) );

		panelButtons.add( buttonReturn );
		panelButtons.add( fillerButtons );
		panelButtons.add( buttonSubmit );

		this.add( panelButtons );

	}

	/**
	 * Write the Sessions to a file, choosen by user
	 */
	private void saveResults()
	{
		JFileChooser chooser = new JFileChooser();

		int returnVal = chooser.showSaveDialog( this );
		if ( returnVal == JFileChooser.APPROVE_OPTION )
		{
			File fileTarget = chooser.getSelectedFile();
			System.out.println("Saving: " + fileTarget.getName());
			try
			{
				// Open up file for writing to.
				PrintStream output = new PrintStream( fileTarget );
				output.print( pageContent );
				output.close();
			}	
			catch ( FileNotFoundException ex )
			{
				System.out.println("Error with writing to " + fileTarget.getName() );
			}
		}
		
	}
	

	/**
	 * Method to create a print out
	 * @param grphcs
	 * @param pf
	 * @param i
	 * @return
	 * @throws PrinterException
	 */
	@Override
	public int print(Graphics grphcs, PageFormat pf, int i) throws PrinterException
	{
		if ( i > 0 )
		{
			 return NO_SUCH_PAGE;
		}

		Graphics2D g2d = (Graphics2D)grphcs;
		g2d.translate( pf.getImageableX(), pf.getImageableY() );

		// Render the content of 'pageContent' to image
		int yAxis = 20;
		String[] contentArray = pageContent.split("\n");
		for ( String string : contentArray )
		{
			grphcs.drawString( string, 10, yAxis );
			
			yAxis += 16;
		}

		return PAGE_EXISTS;
	}

	/**
	 * Listen for name's combo box changes.
	 * @param ae
	 */
	@Override
	public void actionPerformed( ActionEvent ae )
	{
		if ( ae.getSource() == comboName )
		{
			this.updateSessionList();
		}
	}

	/**
	 * submit 
	 * Submits current from panel.
	 */
	@Override
	public void submit()
	{
		/* Make sure a name is selected */
		if ( comboName.getSelectedIndex() == -1 )
		{
			return;
		}

		/* Create content */
		pageContent = "Person: " + (String)comboName.getSelectedItem() + System.lineSeparator();
		for ( int i = 0; i < modelRequests.size(); i++ )
		{
			pageContent += modelRequests.getElementAt(i);
			pageContent += System.lineSeparator();
		}

		/* File Version */
		saveResults();
		
		/* Print Version. Confirm if they want a print out. */
		if ( JOptionPane.showConfirmDialog( this, "Print", "Do you wish to print a copy?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION )
		{
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(this);
			if ( job.printDialog() )
			{
				try {
					job.print();
				} catch ( PrinterException ex ) {
					JOptionPane.showMessageDialog( windowParent, "Print Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
				}
			}
		}
	}

	/**
	 * refresh 
	 * Called when the Panel is switched to.
	 */
	@Override
	public void refresh()
	{
		radioStudent.setSelected( false );
	}

}
