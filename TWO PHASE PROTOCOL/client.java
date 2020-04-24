import java.io.*;
import java.net.*;
public class client implements Runnable
{
	static Socket clientSocket=null;
	static PrintStream os=null;
	static DataInputStream is=null;
	static BufferedReader inputLine=null; // Buffered Input Character Stream object
	static boolean closed=false;
	
	
	public static void main(String[] args)
	{
		int port_number=1111;
		String host="localhost";
		try
		{
			clientSocket=new Socket(host,port_number);
			
			inputLine=new BufferedReader(new InputStreamReader(System.in));
			
			//InputStreamReader converts BYTES to CHARACTERS
			//console input is done by reading from "System.in"
			//to obtain a character-based stream that is attached to console, we wrap "System.in" in a "BufferedReader" object
			
			os=new PrintStream(clientSocket.getOutputStream());
			
			//PrintStream is an output stream that contains println() and print()
			
			is=new DataInputStream(clientSocket.getInputStream());
			
			//DataInputStream contains methods for reading the java standard datatypes
			
		}
		catch(Exception e)
		{
			System.out.println("Exception occurred :"+e.getMessage());
		}
		if(clientSocket!=null && os!=null && is!=null)
		{
			try
			{
				new Thread(new client()).start();
					
					
					while(!closed)
					{
						os.println(inputLine.readLine());
					}
				os.close();
				is.close();
				clientSocket.close();
			}
			catch(IOException e)
			{
				System.out.println("ioexception:"+e);
			}
	}
}




public void run()
{
	String responseLine;
	try
	{
		while((responseLine=is.readLine())!=null)
			
		//to read a string from keyboard,we use the version of readLine() that is  a member of "BufferedReader" class
		
		{
			System.out.println("\n"+responseLine);
			if(responseLine.equalsIgnoreCase("GLOBAL_COMMIT")==true||responseLine.equalsIgnoreCase("GLOBAL_ABORT")==true)//equalsIgnoreCase(String str) -- ignores case differences
			{
				break;
			}
		}
			closed=true;
		}
		catch(IOException e)         
		{
			System.err.println("IOEXCEPTION:"+e);
		}
	}
}
			
