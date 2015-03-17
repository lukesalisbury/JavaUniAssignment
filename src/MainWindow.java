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
import java.io.*;
import java.net.*;
import javax.swing.*;
		
/**
 *
 * @author Luke Salisbury
 */
public class MainWindow extends JFrame
{
	// Menubar
	JMenuBar mainMenuBar;
	JMenu fileMenu;
	JMenu studentMenu;
	JMenu tutorMenu;
	JMenu courseMenu;
	CardLayout layoutMain;
	
	// Card Panel
	FrontpagePanel panelFrontpage;
	NewRequestPanel panelRequest;
	ViewSessionPanel panelSessions;
	NewTutorPanel panelTutor;
	NewStudentPanel panelStudent;
	
	// Tutoring System Helper Commands
	LocalTutoringSystemCommands localTutoring;
	
	// Networking code
	int networkStatus = 0;
	DatagramSocket socket = null;
	InetAddress serverAddress;
	
	/**
	 *
	 * @param string Window Title 
	 */
	public MainWindow( String string )
	{
		super(string);

		// Set up Layout
		layoutMain = new CardLayout(6,6);
		this.setLayout( layoutMain );
		
		// Initialise Tutoring System Helper Commands
		localTutoring = new LocalTutoringSystemCommands();
		
		/* Menu Bar Creation */
		mainMenuBar = new JMenuBar();
	
		initialiseApplicationMenu( mainMenuBar );
		initialiseStudentsMenu( mainMenuBar );
		initialiseTutorsMenu( mainMenuBar );
		initialiseCoursesMenu( mainMenuBar );

		this.setJMenuBar(mainMenuBar);
		
		/* Card panels */
		panelFrontpage = new FrontpagePanel( this );
		panelRequest = new NewRequestPanel( this );
		panelSessions = new ViewSessionPanel( this );
		panelTutor = new NewTutorPanel( this );
		panelStudent = new NewStudentPanel( this );
		
		
		this.add( panelFrontpage, "frontpage");
		this.add( panelRequest, "request");
		this.add( panelSessions, "session");
		this.add( panelTutor, "tutor");
		this.add( panelStudent, "student");
		
		/* Window Closing Event */
		this.addWindowListener( new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				if ( JOptionPane.showConfirmDialog( (Component)e.getSource(), "Exit program?", "Do you wish to exit program?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION )
				{
					System.exit(0);
				}
			}
		});
		
		/* Windows Functionality */
		this.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		this.setMinimumSize( new Dimension(400, 340) );
		this.setVisible( true );

		/* Layout */
		layoutMain.first( this.getContentPane() );

	}
	
	/**
	 * Get the currently active TutoringSystemCommands object
	 * @return currently active TutoringSystemCommands object
	 */
	public TutoringSystemCommands getCommandClass()
	{
		// if network connection is active, create a new NetworkTutoringSystemCommands to use.
		if ( getNetworkStatus( ) == 1 )
		{
			return new NetworkTutoringSystemCommands( socket, serverAddress );
		}
		// Otherwsie return Local version.
		return localTutoring;
	}
	
	/**
	 * Get Network Status
	 * @return Network status
	 */
	public int getNetworkStatus( )
	{
		return networkStatus;
	}
	
	/**
	 * Set Network Status
	 * @param mode 1 - active, 0 - inactive
	 */
	public void setNetworkStatus( int mode )
	{
		networkStatus = mode;
	}
	
	/**
	 * Opens network connection
	 * @param ipAddress
	 */
	public void connectNetwork( String ipAddress )
	{
		try {
			//Create socket and set a quick time out
			socket = new DatagramSocket();
			serverAddress = InetAddress.getByName( ipAddress );
			socket.setSoTimeout( 100 );
			panelFrontpage.setNetworkMessage( "Connecting..." );
			
			// Test if server is up
			if ( this.pokeServer() ) // Server up
			{
				panelFrontpage.setNetworkMessage( "Connected" );
				networkStatus = 1;

				socket.setSoTimeout( 2000 ); // change time out
			}
			else  // Server down
			{
				panelFrontpage.setNetworkMessage( "Connection Timeout" );
				networkStatus = 0;
			}
		} catch ( SocketException ex ) {
			panelFrontpage.setNetworkMessage( "Socket Error" );
		} catch ( UnknownHostException ex ) {
			panelFrontpage.setNetworkMessage( "Unknown Host '" + ipAddress + "'" );
		}

	}
	
	/**
	 * Close network connection
	 */
	public void disconnectNetwork()
	{
		if ( socket != null )
		{
			socket.close();
		}
		networkStatus = 0;
	}
	

	/**
	 * Check to see if server if active.
	 */
	private boolean pokeServer()
	{
		DataMessage replyMessage;
		DataMessage sendMessage;

		DatagramPacket sendPacket;
		DatagramPacket recievePacket;

		try
		{
			byte[] recieveData;
			byte[] sendData;

			// Create a poke message and send it to the server.
			sendMessage = new DataMessage(DataMessage.POKE, "poke");
			sendData = SerialiseHelper.Serialize( sendMessage );

			sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 13579);
			socket.send(sendPacket);


			// Listen for a valid reply. 
			recieveData = new byte[1028];
			recievePacket = new DatagramPacket(recieveData, recieveData.length);
			socket.receive(recievePacket);

			replyMessage = (DataMessage)SerialiseHelper.Deserialize( recieveData );

			if ( replyMessage != null )
			{
				if ( replyMessage.getCode() == DataMessage.FINE )
				{
					return true;
				}
			}
			else
			{
				// Timed out 
				panelFrontpage.setNetworkMessage( "Server Timeout" );
				networkStatus = 0;
			}
		} catch ( ClassNotFoundException | IOException ex ) {
			panelFrontpage.setNetworkMessage( "Server Error" );
			networkStatus = 0;
		}
	
		return false;
	}
	
	/**
	 * Dialog to connect to Network Server.
	 */
	public void showNetworkRequestDialog()
	{
		String ipAddress;

		ipAddress = (String)JOptionPane.showInputDialog( this, 
				"IP Address of Tutoring System Server",
				"Tutoring System Server",
				JOptionPane.QUESTION_MESSAGE, null, null, "127.0.0.1" );

		if ( ipAddress != null )
		{
			connectNetwork( ipAddress );
		}
		else
		{
			setNetworkStatus(0);
		}
	}
	
	/**
	 * setPage
	 * Change the current panel.
	 * @param pageName
	 */
	public void setPage( String pageName )
	{
		layoutMain.show(this.getContentPane(), pageName);
	}

	/* Menu Methods */
	private void initialiseApplicationMenu( JMenuBar menuBar )
	{
		JMenuItem exitItem;
		JMenuItem aboutItem;
		
		Action openAction = new DialogOpenAction("About", new AboutDialog(this) );
		Action closeAction = new WindowCloseAction("Exit");
		
		closeAction.putValue("window", this );
		
		fileMenu = new JMenu("Application");
		fileMenu.setMnemonic(KeyEvent.VK_A);
		
		aboutItem = new JMenuItem( openAction );

		exitItem = new JMenuItem( closeAction );
		exitItem.setMnemonic(KeyEvent.VK_X);
		
		fileMenu.add(aboutItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
	}

	
	private void initialiseStudentsMenu( JMenuBar menuBar )
	{
		JMenuItem modifyMenuItem;
		Action openAction = new DialogOpenAction("List", new StudentsDialog(this) );
		
		studentMenu = new JMenu("Students");
		studentMenu.setMnemonic(KeyEvent.VK_S);
		
		modifyMenuItem = new JMenuItem("List");
		modifyMenuItem.setAction(openAction);
		modifyMenuItem.setMnemonic(KeyEvent.VK_L);
		
		studentMenu.add(modifyMenuItem);
		
		menuBar.add(studentMenu);
	}
	
	private void initialiseTutorsMenu( JMenuBar menuBar )
	{
		JMenuItem modifyMenuItem;
		Action openAction = new DialogOpenAction("List", new TutorDialog(this) );
		
		tutorMenu = new JMenu("Tutors");
		tutorMenu.setMnemonic(KeyEvent.VK_T);
		
		modifyMenuItem = new JMenuItem("List");
		modifyMenuItem.setAction(openAction);
		modifyMenuItem.setMnemonic(KeyEvent.VK_L);
		
		tutorMenu.add(modifyMenuItem);
		
		menuBar.add(tutorMenu);
	}
	
	private void initialiseCoursesMenu( JMenuBar menuBar )
	{
		JMenuItem modifyMenuItem;
		Action openAction = new DialogOpenAction("List", new CourseDialog(this) );
		courseMenu = new JMenu("Courses");
		courseMenu.setMnemonic(KeyEvent.VK_C);
		
		modifyMenuItem = new JMenuItem("List");
		modifyMenuItem.setAction(openAction);
		modifyMenuItem.setMnemonic(KeyEvent.VK_L);
		
		courseMenu.add(modifyMenuItem);
		
		menuBar.add(courseMenu);
	}
}
