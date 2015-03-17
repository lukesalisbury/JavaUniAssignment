/* 
 * Created: 06/05/2014
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

/**
 * Test Class for TutoringSystem
 * @author Luke Salisbury
 */
public class TestTutoringSystem
{
	/** 
	 * Run a array of arguments.
	 * <p>Example: String[][] argTests = {
	 *		{ "action", "list" },
	 *		{ "run" }
	 * };</p>
	 * 
	 * @param testArray 2d array of arguments
	 */
	private static void runTests( String[][] argsArray )
	{
		try
		{
			/* Create a Tutoring System & Argument parser and then run each argument group.*/
			TutoringSystem tutorSystem = new TutoringSystem( ".." );
			TerminalArguments commands = new TerminalArguments( tutorSystem );

			for (String[] args : argsArray)
			{
				commands.handleCommandLine( args );
			}
		
		}
		catch ( FileNotFoundException e )
		{
			System.out.println( "file error" );
		}
	
	}
	
	/**
	 * Entry point
	 * @param args the command line arguments
	 */
	public static void main( String[] args )
	{
		String[][] argTests = {
			{ "stats" }, // Stats
			{ "search", "ECB2112" }, // Simple Search
			{ "list", "ECB2112" }, // List
			{ "info", "ECB2112" }, // List
			{ "subjects" }, // List
			{ "student", "add", "Fake", "Student", "9999 1111", "2345678", "ECB2112,ECB2124" }, // Add Student
			{ "student", "add", "Another", "Student", "9999 222", "2345679", "ECB2112" }, // Add Student
			{ "student", "list" }, // List Students
			{ "request", "add", "Grace Tan", "ECB2112", "2345678" }, // Successful request 
			{ "request", "add", "Nguyen Vo", "ECB2112", "2345678" }, // Failed request
			{ "request", "add", "Fake Tutor", "ECB2112", "2345678" }, // Failed request
			{ "request", "add", "Nguyen Vo", "ECB4112", "2345678" }, // Failed request
			{ "request", "list" }, // List Requests
			{ "add", "My", "Tutor", "9999 1111", "10", "ECB2123" }, // Add Tutor
			{ "add", "Fake", "Tutor", "9999 1111", "2", "ECB2123" }, // Add Tutor
			{ "remove", "Fake", "Tutor" }, // Remove Tutor
			{ "student", "remove", "2345678" }, // Remove Student
			{ "stats" } // Stats
		};

		System.out.println( "Tutoring System Test " );
		runTests( argTests );

	}
	
}
