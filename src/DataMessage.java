/* 
 * Created: 12/05/2014
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

import java.io.Serializable;


/**
 *
 * @author Luke Salisbury
 */
public class DataMessage implements Serializable
{
	/* Message Type */
	static byte ERROR = 0x00;
	static byte FINE = 0x01;
	static byte COMMAND = 0x02;
	static byte POKE = 0x03;

	static byte STUDENT = 0x10;
	static byte TUTOR = 0x11;
	static byte SUBJECT = 0x12;
	static byte REQUEST = 0x13;
	
	static byte STUDENTLIST = 0x20;
	static byte TUTORLIST = 0x21;
	static byte SUBJECTLIST = 0x22;
	static byte REQUESTLIST = 0x23;
	
	/* Internal Varible */
	private byte code;
	private String message;
	private String [] array;
	
	private int counter;
	private Object data;
	
	private boolean hasArray = false;
	private boolean hasMessage = false;
	private boolean hasObject = false;
	
	/**
	 *
	 * @param code Type of Message
	 * @param message Message to send back
	 */
	public DataMessage(byte code, String message )
	{
		this.code = code;
		this.message = message;
		this.hasMessage = true;
	}

	/**
	 *
	 * @param code Type of Message
	 * @param array Command Array
	 */
	public DataMessage(byte code, String [] array )
	{
		this.code = code;
		this.array = array;
		this.hasArray = true;
	}

	/**
	 *
	 * @param code Type of Message
	 * @param data Object to send
	 * @param counter Count of object to be sending.
	 */
	public DataMessage(byte code, Object data, int counter )
	{
		this.code = code;
		this.data = data;
		this.counter = counter;
		this.hasObject = true;
	}

	/**
	 *
	  * @return true if message have object
	 */
	public boolean hasData()
	{
		return hasObject;
	}

	/**
	 *
	 * @return object
	 */
	public Object getData()
	{
		return data;
	}

	/**
	 *
	 * @return Count of object to be expecting.
	 */
	public int getCounter()
	{
		return counter;
	}

	/**
	 *
	 * @return true if message have command array
	 */
	public boolean hasArray()
	{
		return hasArray;
	}
	
	/**
	 *
	 * @return command array
	 */
	public String[] getArray()
	{
		return array;
	}

	/**
	 *
	 * @param array
	 */
	public void setArray(String[] array)
	{
		this.array = array;
		this.hasArray = true;
	}
	
	/**
	 *
	 * @return message type
	 */
	public byte getCode()
	{
		return code;
	}

	/**
	 *
	 * @param code
	 */
	public void setCode(byte code)
	{
		this.code = code;
	}

	/**
	 *
	 * @return mssage
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 *
	 * @param message
	 */
	public void setMessage(String message)
	{
		this.message = message;
		this.hasMessage = true;
	}

	/**
	 *
	 * @return format string 
	 */
	@Override
	public String toString()
	{
		return "DataMessage{" + "code=" + code + ", message=" + message + '}';
	}

	
	
}
