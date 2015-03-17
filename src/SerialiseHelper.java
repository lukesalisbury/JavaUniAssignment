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

import java.io.*;

/**
 * Serialise Method
 * @author Luke Salisbury
 */
public class SerialiseHelper
{
	/**
	 * Serialize a object 
	 * @param object Object to be serialize
	 * @return object as serialize byte array
	 * @throws IOException
	 */
	public static byte[] Serialize( Object object ) throws IOException
	{
		ByteArrayOutputStream byteStream;
		ObjectOutput output;
		byte[] objectBytes;

		byteStream = new ByteArrayOutputStream();

		output = new ObjectOutputStream(byteStream);
		output.writeObject(object);
		output.close();

		objectBytes = byteStream.toByteArray();

		return objectBytes;
	}

	/**
	 * Deserialize a object 
	 * @param objectBytes Serialize object
	 * @return Deserialized Object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object Deserialize( byte[] objectBytes )  throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream byteStream;
		ObjectInput input;
		Object object;

		byteStream = new ByteArrayInputStream( objectBytes );

		input = new ObjectInputStream( byteStream );
		object = input.readObject();
		input.close();

		return object;
	}


}

