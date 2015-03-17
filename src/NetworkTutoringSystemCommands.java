/* 
 * Created: 13/05/2014
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



import java.io.*;
import java.net.*;
import java.util.*;


/**
 * 
 * @author Luke Salisbury
 */
public class NetworkTutoringSystemCommands extends TutoringSystemCommands
{
	private DatagramSocket socket;
	private InetAddress ipAddress;

	/**
	 *
	 * @param socket Socket to use
	 * @param ipAddress IP address to send to
	 */
	public NetworkTutoringSystemCommands( DatagramSocket socket, InetAddress ipAddress )
	{
		this.socket = socket;
		this.ipAddress = ipAddress;
	}

	/**
	 * Sends a packet with a number of retries.
	 * @param packet Packet to send.
	 * @param maxRetries Maximum number of retries.
	 * @return true is packet is sent.
	 * @throws SocketTimeoutException 
	 */
	private boolean send( DatagramPacket packet, int maxRetries ) throws SocketTimeoutException
	{
		int counter = 0;
		while ( counter < maxRetries )
		{
		    counter++;
			try {
				socket.send(packet);
				return true;
		    } catch (SocketTimeoutException e) {
				System.out.println(" Timeout Reason: " + e.getMessage() );
			} catch (IOException e) {
				System.out.println(" IOException: " + e.getMessage() );
				return false;
			}
		}

		this.setDisableDialogs(false);
		throw new SocketTimeoutException( "Can not contact server." );
	}
	/**
	 * Listen for a reply from server 
	 * @param maxRetries Maximum number of retries.s
	 * @return Received DataMessage
	 * @throws SocketTimeoutException 
	 */
	private DataMessage listen( int maxRetries )  throws SocketTimeoutException
	{
		int counter = 0;
		byte[] recievedData;
		DataMessage recievedMessage;
		DatagramPacket recievedPacket;

		recievedData = new byte[65535];
		recievedPacket = new DatagramPacket( recievedData, recievedData.length );

		while ( counter < maxRetries )
		{
			counter++;
			try {
				socket.receive( recievedPacket );
				recievedMessage = (DataMessage)SerialiseHelper.Deserialize( recievedData );

				return recievedMessage;
			} catch (SocketTimeoutException e) {
				System.out.println(" Timeout Reason: " + e.getMessage() );
			} catch ( IOException e) {
				System.out.println(" IOException: " + e.getMessage() );
			} catch ( ClassNotFoundException ex) {
				System.out.println(" Deserialize Error:" + ex.getMessage() );
			}
		}

		this.setDisableDialogs(false);
		throw new SocketTimeoutException( "Server did not reply." );
	}


	/**
	 * Sends a packet with a list of command

	 * @param args List of command to sent
	 */
	private boolean sendPacket( String [] args ) throws IOException, SocketTimeoutException
	{
		byte[] sendData;
		DatagramPacket sendPacket;
		DataMessage replyMessage;

		replyMessage = new DataMessage( DataMessage.COMMAND, args );
		sendData = SerialiseHelper.Serialize( replyMessage );
		sendPacket = new DatagramPacket( sendData, sendData.length, ipAddress, 13579 );

		return send( sendPacket, 3 );
	}

	/* Requests Methods */

	/**
	 * 
	 * 
	 * 
	 * @param tutorFullName
	 * @param subjectCode
	 * @param studentID
	 */
	@Override
	public void requestAdd( String tutorFullName, String subjectCode, String studentID )
	{
		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			String [] networkArguments = { "request", "add", tutorFullName, subjectCode, studentID };
			if ( this.sendPacket( networkArguments ) )
			{
				DataMessage reply = listen( 3 );
				this.setDisableDialogs( false ); // enable dialog
				if ( reply.getCode() == DataMessage.FINE )
				{
					this.logMessage( reply.getMessage(), false );
				}
				else
				{
					this.logMessage( reply.getMessage(), true );
				}
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	public ArrayList<Request> requestList( )
	{
		ArrayList<Request> list = new ArrayList();

		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			int listenCount = 0;
			String [] networkArguments = { "request", "list" };

			if ( this.sendPacket( networkArguments ) )
			{
				// Listen for multiple replies from server
				do
				{
					DataMessage reply = listen( 3 );
					if ( reply.getCode() == DataMessage.REQUESTLIST )
					{
						listenCount = reply.getCounter();
						if ( reply.hasData() )
						{
							list.add( (Request)reply.getData() );
						}
					}
					else if ( reply.getCode() == DataMessage.ERROR )
					{
						this.logMessage( reply.getMessage(), true );
					}
				} while ( listenCount != list.size() );
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return list;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * @param tutorFullName
	 * @param subjectCode
	 * @param studentID
	 */
	public void requestRemove( String tutorFullName, String subjectCode, String studentID )
	{
		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			String [] networkArguments = { "request", "remove", tutorFullName, subjectCode, studentID };
			this.sendPacket( networkArguments );
			if ( this.sendPacket( networkArguments ) )
			{
				DataMessage reply = listen( 3 );
				this.setDisableDialogs( false ); // enable dialog
				if ( reply.getCode() == DataMessage.FINE )
				{
					this.logMessage( reply.getMessage(), false );
				}
				else
				{
					this.logMessage( reply.getMessage(), true );
				}
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
	}

	/**
	 *
	 * @param studentId
	 * @return
	 */
	public ArrayList<Request> requestStudent( String studentId )
	{
		ArrayList<Request> matches = new ArrayList();

		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			int listenCount = 0;
			String [] networkArguments = { "request", "student", studentId };

			if ( this.sendPacket( networkArguments ) )
			{
				// Listen for multiple replies from server
				do
				{
					DataMessage reply = listen( 3 );
					if ( reply.getCode() == DataMessage.REQUESTLIST )
					{
						listenCount = reply.getCounter();
						if ( reply.hasData() )
						{
							matches.add( (Request)reply.getData() );
						}
					}
					else if ( reply.getCode() == DataMessage.ERROR )
					{
						this.logMessage( reply.getMessage(), true );
					}
				} while ( listenCount != matches.size() );
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return matches;

	}

	/**
	 *
	 * @param tutorFullName
	 * @return
	 */
	public ArrayList<Request> requestTutor( String tutorFullName )
	{
		ArrayList<Request> matches = new ArrayList();

		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			int listenCount = 0;
			String [] networkArguments = { "request", "tutor", tutorFullName };

			if ( this.sendPacket( networkArguments ) )
			{
				// Listen for multiple replies from server
				do
				{
					DataMessage reply = listen( 3 );
					if ( reply.getCode() == DataMessage.REQUESTLIST )
					{
						listenCount = reply.getCounter();
						if ( reply.hasData() )
						{
							matches.add( (Request)reply.getData() );
						}
					}
					else if ( reply.getCode() == DataMessage.ERROR )
					{
						this.logMessage( reply.getMessage(), true );
					}
				} while ( listenCount != matches.size() );
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return matches;
	}


	/* Tutor Methods */

	/**
	 *
	 * @param tutorFullName
	 */
	public void tutorRemove( String tutorFullName )
	{
		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			String [] networkArguments = { "remove", tutorFullName };
			if ( this.sendPacket( networkArguments ) )
			{
				// Listen for reply
				DataMessage reply = listen( 3 );
				this.setDisableDialogs( false ); // enable dialog
				if ( reply.getCode() == DataMessage.FINE )
				{
					this.logMessage( reply.getMessage(), false );
				}
				else
				{
					this.logMessage( reply.getMessage(), true );
				}
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}

	}

	/**
	 * 
	 * 
	 * 
	 * @param tutorFirstName
	 * @param tutorFamilyName
	 * @param tutorContactDetails
	 * @param tutorHoursText
	 * @param tutorsubjectOffered
	 * @return 
	 */
	public Tutor tutorAdd( String tutorFirstName, String tutorFamilyName, String tutorContactDetails, String tutorHoursText, String tutorsubjectOffered )
	{
		Tutor tutor = null;

		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			String [] networkArguments = { "add", tutorFirstName, tutorFamilyName, tutorContactDetails, tutorHoursText, tutorsubjectOffered };
			if ( this.sendPacket( networkArguments ) )
			{
				// Listen for a reply from server
				DataMessage reply = listen( 3 );
				this.setDisableDialogs( false ); // enable dialog
				if ( reply.getCode() == DataMessage.FINE )
				{
					this.logMessage( reply.getMessage(), false );
				}
				else
				{
					this.logMessage( reply.getMessage(), true );
				}
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return tutor;
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	public ArrayList<Tutor> tutorList( )
	{
		ArrayList<Tutor> list = new ArrayList();

		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			int listenCount = 0;

			String [] networkArguments = { "list" };
			if ( this.sendPacket( networkArguments ) )
			{
				// Listen for multiple replies from server
				do
				{
					DataMessage reply = listen( 3 );
					if ( reply.getCode() == DataMessage.TUTORLIST )
					{
						listenCount = reply.getCounter();
						if ( reply.hasData() )
						{
							list.add( (Tutor)reply.getData() );
						}
					}
					else if ( reply.getCode() == DataMessage.ERROR )
					{
						this.logMessage( reply.getMessage(), true );
					}
				} while ( listenCount != list.size() );
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return list;
	}

	/**
	 * 
	 * 
	 * 
	 * @param classCode
	 * @return 
	 */
	public ArrayList<Tutor> tutorSearch( String  classCode )
	{
		ArrayList<Tutor> list = new ArrayList();

		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			int listenCount = 0;
			String [] networkArguments = { "search", classCode };
			this.sendPacket( networkArguments );

			// Listen for multiple replies from server
			do
			{
				DataMessage reply = listen( 3 );
				if ( reply.getCode() == DataMessage.TUTORLIST )
				{
					listenCount = reply.getCounter();
					if ( reply.hasData() )
					{
						list.add( (Tutor)reply.getData() );
					}
				}
				else if ( reply.getCode() == DataMessage.ERROR )
				{
					this.logMessage( reply.getMessage(), true );
				}
			} while ( listenCount != list.size() );

		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return list;
	}

	/**
	 * 
	 * 
	 * 
	 * @param tutorFullName
	 * @return 
	 */
	public Tutor tutorInfo( String tutorFullName )
	{
		Tutor tutor = null;

		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			String [] networkArguments = { "info", tutorFullName };
			if ( this.sendPacket( networkArguments ) )
			{
				// Listen for a reply from server
				DataMessage reply = listen( 3 );
				if ( reply.getCode() == DataMessage.TUTOR )
				{
					if ( reply.hasData() )
					{
						tutor = (Tutor)reply.getData();
					}
				}
				else if ( reply.getCode() == DataMessage.ERROR )
				{
					this.logMessage( reply.getMessage(), true );
				}
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return tutor;
	}

	/* Subjects Methods */

	/**
	 * 
	 * 
	 * 
	 * @param subjectCode
	 * @return 
	 */

	public Subject subjectsInfo( String subjectCode )
	{
		Subject course = null;

		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			String [] networkArguments = { "subject", subjectCode };
			if ( this.sendPacket( networkArguments ) )
			{
				// Listen for a reply from server
				DataMessage reply = listen( 3 );
				if ( reply.getCode() == DataMessage.SUBJECT )
				{
					if ( reply.hasData() )
					{
						course = (Subject)reply.getData();
					}
				}
				else if ( reply.getCode() == DataMessage.ERROR )
				{
					this.logMessage( reply.getMessage(), true );
				}
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return course;
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	@Override
	public ArrayList<Subject> subjectsList( )
	{
		ArrayList<Subject> list = new ArrayList();

		this.setDisableDialogs(true); // disable dialog unless we get a SocketTimeoutException

		try 
		{
			int listenCount = 0;
			String [] networkArguments = { "subject", "list" };

			this.sendPacket( networkArguments );

			// Listen for multiple replies from server
			do
			{
				DataMessage reply = listen( 3 );
				if ( reply.getCode() == DataMessage.SUBJECTLIST )
				{
					listenCount = reply.getCounter();
					if ( reply.hasData() )
					{
						list.add( (Subject)reply.getData() );
					}
				}
				else if ( reply.getCode() == DataMessage.ERROR )
				{
					this.logMessage( reply.getMessage(), true );
				}
			} while ( listenCount != list.size() );
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return list;
	}

	/* Student Methods */

	/**
	 * 
	 * 
	 * 
	 * @param studentID
	 * @return 
	 */
	@Override
	public Student studentInfo( String studentID )
	{
		Student student = null;
		try 
		{
			String [] networkArguments = { "student", studentID };
			if ( this.sendPacket( networkArguments ) )
			{
				// Listen for a reply from server
				DataMessage reply = listen( 3 );
				if ( reply.getCode() == DataMessage.STUDENT )
				{
					if ( reply.hasData() )
					{
						student = (Student)reply.getData();
					}
				}
				else if ( reply.getCode() == DataMessage.ERROR )
				{
					this.logMessage( reply.getMessage(), true );
				}
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return student;
	}

	/**
	 * 
	 * 
	 * 
	 * @return 
	 */
	@Override
	public ArrayList<Student> studentList( )
	{
		ArrayList<Student> list = new ArrayList();
		try 
		{
			int listenCount = 0;
			String [] networkArguments = { "student", "list" };

			this.sendPacket( networkArguments );

			// Listen for multiple replies from server
			do
			{
				DataMessage reply = listen( 3 );
				if ( reply.getCode() == DataMessage.STUDENTLIST )
				{
					listenCount = reply.getCounter();
					if ( reply.hasData() )
					{
						list.add( (Student)reply.getData() );
					}
				}
				else if ( reply.getCode() == DataMessage.ERROR )
				{
					this.logMessage( reply.getMessage(), true );
				}
			} while ( listenCount != list.size() );
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return list;
	}

	/**
	 * 
	 * 
	 * 
	 * @param studentID
	 */
	@Override
	public void studentRemove( String studentID )
	{
		try 
		{
			String [] networkArguments = { "student", "remove", studentID };
			if ( this.sendPacket( networkArguments ) )
			{
				DataMessage reply = listen( 3 );
				this.setDisableDialogs( false ); // enable dialog
				if ( reply.getCode() == DataMessage.FINE )
				{
					this.logMessage( reply.getMessage(), false );
				}
				else
				{
					this.logMessage( reply.getMessage(), true );
				}
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param studentFirstName
	 * @param studentFamilyName
	 * @param studentContactDetails
	 * @param studentID
	 * @param studentSubjectEnrolled
	 * @return 
	 */

	@Override
	public Student studentAdd( String studentFirstName, String studentFamilyName, String studentContactDetails, String studentID, String studentSubjectEnrolled )
	{
		Student student = null;
		try 
		{
			String [] networkArguments = { "student", "add", studentFirstName, studentFamilyName, studentContactDetails, studentID, studentSubjectEnrolled };
			if ( this.sendPacket( networkArguments ) )
			{
				// Listen for a reply from server
				DataMessage reply = listen( 3 );
				this.setDisableDialogs( false ); // enable dialog
				if ( reply.getCode() == DataMessage.FINE )
				{
					this.logMessage( reply.getMessage(), false );
				}
				else
				{
					this.logMessage( reply.getMessage(), true );
				}
			}
		} catch ( SocketTimeoutException ex ) {
			this.logMessage( ex.getMessage(), true );
		} catch ( IOException ex ) {
			this.logMessage( ex.getMessage(), true );
		}
		return student;
	}


}
