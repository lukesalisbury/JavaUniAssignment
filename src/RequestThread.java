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
import java.util.*;

/**
 *
 * @author Luke Salisbury
 */
public class RequestThread
{
	private byte[] data = new byte[1024];
	private DatagramPacket packet;

	public RequestThread( )
	{
		packet = new DatagramPacket( data, data.length );
	}

	/**
	 * Listen for a reply from server 
	 * @param server Socket to user
	 * @param tutoringSystem The system use to apply command to.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void listenAndReply( DatagramSocket server, TutoringSystem tutoringSystem ) throws IOException, ClassNotFoundException
	{
		DataMessage recievedMessage;
		RequestArguments arg = new RequestArguments( tutoringSystem );

		server.receive( packet );

		recievedMessage = (DataMessage)SerialiseHelper.Deserialize( data );

		System.out.format("Request from %s\n", packet.getAddress().getHostAddress() );
		if ( recievedMessage.getCode() == DataMessage.COMMAND ) //Argument array sent
		{
			if ( recievedMessage.hasArray() )
			{
				ArrayList<DataMessage> messages;

				// Handle the sent command and get objects if any.
				arg.parseCommand( recievedMessage.getArray() );
				messages = arg.getMessages();

				if ( arg.hasError() ) // Send Error
				{
					this.reply( server, DataMessage.ERROR, arg.getMessageBuffer() );
				}
				else if ( messages.size() > 0 )  // Send by a list of Objects.
				{
					for ( DataMessage msg: messages )
					{
						this.replyMessage( server, msg );
					}
				}
				else // Sent a plain message
				{
					this.reply( server, DataMessage.FINE, arg.getMessageBuffer() );
				}
			}
		}
		else if ( recievedMessage.getCode() == DataMessage.POKE ) //Server has been poked
		{
			//Replying to a poke.
			this.reply( server, DataMessage.FINE, "We heard you" );
		}
	}

	/**
	 * Send a response
	 * @param server Socket to user.
	 * @param replyMessage DataMessage to send.
	 * @throws IOException
	 */
	public void replyMessage( DatagramSocket server, DataMessage replyMessage ) throws IOException
	{
		byte[] replyData;
		DatagramPacket sendPacket;
		InetAddress ipAddress = packet.getAddress();
		int port = packet.getPort();

		replyData = SerialiseHelper.Serialize( replyMessage );
		sendPacket = new DatagramPacket( replyData, replyData.length, ipAddress, port);

		server.send( sendPacket );
	}
	
	/**
	 * Send a response
	 * @param server Socket to user.
	 * @param code Message type
	 * @param message Message to send
	 * @throws IOException
	 */
	public void reply( DatagramSocket server, byte code, String message ) throws IOException
	{
		byte[] replyData;
		DataMessage replyMessage;
		DatagramPacket sendPacket;
		InetAddress ipAddress = packet.getAddress();
		int port = packet.getPort();

		replyMessage = new DataMessage( code, message );
		replyData = SerialiseHelper.Serialize( replyMessage );
		sendPacket = new DatagramPacket( replyData, replyData.length, ipAddress, port );

		server.send( sendPacket );
	}
	
}
