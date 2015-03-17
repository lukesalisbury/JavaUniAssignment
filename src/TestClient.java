/* 
 * Created: 11/05/2014
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
import java.util.ArrayList;

/**
 * Test class for Tutoring System Server
 * @author Luke Salisbury
 */
public class TestClient
{
	/**
	 * Sends a packet with a number of retries.
	 * @param socket Socket to use.
	 * @param packet Pack to send.
	 * @param maxRetries Maximum number of retries.
	 * @return true is packet is sent.
	 * @throws SocketTimeoutException 
	 */
	private static boolean send( DatagramSocket socket, DatagramPacket packet, int maxRetries ) throws SocketTimeoutException
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
		throw new SocketTimeoutException( "Can not contact server." );
	}

	/**
	 * Listen for a reply from server 
	 * @param socket  Socket to use.
	 * @param maxRetries Maximum number of retries.s
	 * @return Received DataMessage
	 * @throws SocketTimeoutException 
	 */
	private static DataMessage listen( DatagramSocket socket, int maxRetries ) throws SocketTimeoutException
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
				// Retrieve packet and unserialize it
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
		
		throw new SocketTimeoutException( "Server did not reply." );
	}
	/**
	 * Returns the type of DataMessage as text
	 * @param type type of DataMessage
	 * @return Name of DataMessage type.
	 */
	private static String getDataMessageName( byte type )
	{
		if ( type == DataMessage.STUDENT )
			return "STUDENT";
		if ( type == DataMessage.TUTOR )
			return "TUTOR";
		if ( type == DataMessage.SUBJECT )
			return "SUBJECT";
		if ( type == DataMessage.REQUEST )
			return "REQUEST";
		if ( type == DataMessage.STUDENTLIST )
			return "STUDENTLIST";
		if ( type == DataMessage.TUTORLIST )
			return "TUTORLIST";
		if ( type == DataMessage.SUBJECTLIST )
			return "SUBJECTLIST";
		if ( type == DataMessage.REQUESTLIST )
			return "REQUESTLIST";
		return "Unknown";

	}
	
	/**
	 * Run pre-defined arguments
	 * @param socket Socket to use.
	 * @param ipAddress IP to send to
	 * @param testArray action to run.
	 * @throws IOException 
	 */
	private static void runTests( DatagramSocket socket, InetAddress ipAddress, String[][] testArray ) throws IOException
	{
		DatagramPacket sendPacket;
		DataMessage sentMessage;
		byte[] sendData;

		for ( String[] args : testArray )
		{
			System.out.println( "----------------------------" );
			System.out.println( "Sending " + args[0] );

			sentMessage = new DataMessage( DataMessage.COMMAND, args );
			sendData = SerialiseHelper.Serialize( sentMessage );
			sendPacket = new DatagramPacket( sendData, sendData.length, ipAddress, 13579);
			
			try
			{
				if ( send( socket, sendPacket, 3 ) )
				{
					int listenCount;
					ArrayList<DataMessage> list = new ArrayList();

					// Listen for multiple replies from server
					do
					{
						DataMessage reply = listen( socket, 3 );
						listenCount = reply.getCounter();
						if ( reply.getCode() == DataMessage.FINE )
						{
							System.out.format( "Message: %s \n", reply.getMessage() );
						}
						else if ( reply.getCode() == DataMessage.ERROR )
						{
							System.out.println( reply.getMessage() );
						}
						else
						{
							list.add( reply );
							System.out.format( "Data Reply [%s] \n", getDataMessageName(reply.getCode()) );
						}
					} while ( listenCount != list.size() );
				}
			} catch ( SocketTimeoutException ex ) {
				System.out.println( "Message:" + ex.getMessage() );
			}
		}
	}
	
	/**
	 * Entry point
	 * @param args the command line arguments
	 */
	public static void main( String [] args )
	{
		String[][] argTests = {
			{ "search", "ECB2112" }, // Simple Search
			{ "list" }, // List
			{ "info", "Grace Tan" }, // List
			{ "subject", "list"}, // List Subject
			{ "subject", "info", "ECB2112"}, // Info Subject
			{ "student", "add", "Fake", "Student", "9999 1111", "2345678", "ECB2112,ECB2124" }, // Add Student
			{ "student", "list" }, // Remove Tutor
			{ "request", "add", "Grace Tan", "ECB2112", "2345678" }, // Successful request 
			{ "request", "add", "Nguyen Vo", "ECB2112", "2345678" }, // Failed request
			{ "request", "add", "Fake Tutor", "ECB2112", "2345678" }, // Failed request
			{ "request", "add", "Nguyen Vo", "ECB4112", "2345678" }, // Failed request
			{ "request", "tutor", "Grace Tan" }, // List Requests
			{ "request", "student", "2345678" }, // List Requests
			{ "request", "list" }, // List Requests
			{ "add", "My", "Tutor", "9999 1111", "10", "ECB2123" }, // Add Tutor
			{ "add", "Fake", "Tutor", "9999 1111", "2", "ECB2123" }, // Add Tutor
			{ "remove", "Fake", "Tutor" }, // Remove Tutor
			{ "student", "remove", "2345678" } // Remove Student
		};
		
		
		System.out.println( "Tutoring System Client Test" );
		System.out.println( "Connects to loopback address" );
		
		DatagramSocket socket;
		InetAddress ipAddress;
		try {
			/* Open up socket and get loopback address */
			socket = new DatagramSocket();
			ipAddress = InetAddress.getLoopbackAddress();

			socket.setSoTimeout(5000);
			
			runTests( socket, ipAddress, argTests );
			
			socket.close();
		} catch ( UnknownHostException e ) {
			System.err.println( "Don't know about host 127.0.0.1" );
			System.exit( 1 );
		} catch (IOException e) {
			System.err.println( "Couldn't get I/O for the connection to 127.0.0.1" );
			System.exit( 1 );
		} 
		System.exit( 0 );
		
	}
	
}
