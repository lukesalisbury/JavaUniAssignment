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

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Tutoring System Server
 * @author Luke Salisbury
 */
public class TutoringSystemServer
{
	/**
	 * Main Entry point
	 * @param args the command line arguments
	 */
	
	public static void main(String[] args)
	{
		try {
			// Create the server that will listen for UDP packets
			Server server = new Server();
			server.start();

			// Listen for commands 
			System.out.println("Type quit to exit");
			try {
				Scanner scan = new Scanner( System.in );
				while ( scan.hasNextLine() )
				{
					String response = scan.nextLine();
					if ( response.equalsIgnoreCase( "quit") )
					{
						break;
					}
					if ( response.equalsIgnoreCase( "stats") )
					{
						server.stats();
					}
					if ( !server.isAlive() )
					{
						break;
					}
				}
				scan.close();
			} catch( IllegalStateException  e ) {

			}
		} catch( FileNotFoundException ex ) {
			System.out.println("Could not load database files.");
		}
		System.exit(0);
	}
	
}
