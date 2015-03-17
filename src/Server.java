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

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Luke Salisbury
 */
public class Server extends Thread
{
	private DatagramSocket serverSocket;
	private TutoringSystem tutoringSystem;

	/**
	 *
	 * @throws FileNotFoundException
	 */
	public Server() throws FileNotFoundException
	{
		tutoringSystem = new TutoringSystem("..");

		/* Create a simple user */
		try 
		{
			ArrayList<Subject> subjectEnrolled = new ArrayList();
			subjectEnrolled.add( tutoringSystem.getSubjectFromCourseCode( "ECB2112" ) );

			Student student = new Student( "Fake", "Student", "1111 9999", "8765432", subjectEnrolled );

			tutoringSystem.addStudent( student );
		} catch ( InvalidSubjectException | InvalidArgumentException ex) {
			// If user isn't add, it's not important if it's fails
		}
	}
	
	/**
	 * Display stats.
	 */
	public void stats()
	{
		Arguments arg = new Arguments( tutoringSystem );
		arg.commandStats(null);
	}
	
	/**
	 * 
	 */
	@Override
	public void run()
	{
		try {
			// Create a socket.
			serverSocket = new DatagramSocket(13579);
			while( true )
			{
				try {
					// Handle requests.
					RequestThread request = new RequestThread();
					request.listenAndReply(serverSocket, tutoringSystem);
				} catch ( ClassNotFoundException e ) {
					System.out.println( "ClassNotFoundException:" + e.getMessage() );	
				} catch ( IOException e ) {
					System.out.println( "IOExceptionError:" + e.getMessage() );	
				}
			}
		} catch ( SocketException ex ) {
			System.out.println( ex.getMessage() );	
		}

	}
	
}
