


	import java.io.*;
	import java.net.*;
	import java.util.*;
	public class Server
	{	
		boolean closed=false,inputFromAll=false;
		List<clientThread>t;// t is the object of List class of type clientThread
		List<String>data;// data is the object of the List class of type String
		Server()
		{
			t = new ArrayList<clientThread>();// now t(object of List class) is a arraylist of type clientThread class
			data = new ArrayList<String>();// data(object of List class) is an arraylist of type string
		}
		public static void main(String args[])
		{
		
		Socket clientSocket = null; 
		ServerSocket serverSocket = null;
		int port_number = 1111;
		Server ser = new Server(); //intializing constructor Server() of class Server by creating object "ser"
		
		
		try
		{
			serverSocket = new ServerSocket(port_number); //ServerSocket is the set with the port number of 1111
		}
		
		
		catch(IOException e) // exceptions in serverSocket can be caught
		{
		System.out.println(e);
		}
		
		
    while(!ser.closed) //ser is the object of Server class,closed is a boolean variable
   {
      try
      {
         clientSocket = serverSocket.accept();
         clientThread th = new clientThread(ser,clientSocket); 
		 (ser.t).add(th);//"ser.t" List class object.... "th" is the clientThread object
          System.out.println("\n Now total are:"+(ser.t).size());
		  (ser.data).add("NOT_SENT");
       th.start();//thread starts
     }
     catch(IOException e)
     {
     }
  }
     try
     {
       serverSocket.close();//closing serverSocket
     }
     catch(Exception e1)
     {
     }
  }
}


class clientThread extends Thread
{
    DataInputStream is= null;
    String line;
    String destClient="";
    String name;
    PrintStream os=null;
    Socket clientSocket=null;
    String clientIdentity;
    Server ser;
	
 public clientThread(Server ser,Socket clientSocket)
 {
    this.clientSocket=clientSocket;
    this.ser=ser;
 }
 
 public void run()
{
  try
 {
    is = new DataInputStream(clientSocket.getInputStream());
    os = new PrintStream(clientSocket.getOutputStream());
    os.println("Enter your name");
   name = is.readLine();
   clientIdentity = name;// String clientIdentity
   os.println("Welcome"+name+"to this 2 Phase Application.\n You will receive a vote Request now....");
   os.println("VOTE REQUEST\nPlease enter COMMIT or ABORT to proceed:");
    
	for(int i=0;i<(ser.t).size();i++)
	{
		if((ser.t).get(i)!=this)
		{
			((ser.t).get(i)).os.println("--a new user"+name+"entered the application--");
		}
	}

	while(true)
	{
		line=is.readLine();//String line
		if(line.equalsIgnoreCase("ABORT"))
		{
			System.out.println("\n from '"+clientIdentity+"':ABORT\n\n since aborted we will not wait for inputs from other clients");
			System.out.println("\nAborted..");
			for(int i=0;i<(ser.t).size();i++)
			{
				((ser.t).get(i)).os.println("GLOBAL_ABORT");
				((ser.t).get(i)).os.close();
				((ser.t).get(i)).is.close();
			}
    break;
  }
  if(line.equalsIgnoreCase("COMMIT"))
  {
    System.out.println("\n From '"+clientIdentity+"':COMMIT");
    if((ser.t).contains(this))
    {
     (ser.data).set((ser.t).indexOf(this),"COMMIT");
     for(int j=0;j<(ser.data).size();j++)
     {
      if(!(((ser.data).get(j)).equalsIgnoreCase("NOT_SENT")))
      {
        ser.inputFromAll=true;
        continue;
      }
      else
      {
        ser.inputFromAll=false;
        System.out.println("\n waiting for inputs from other clients");
        break;
      }
    }
    if(ser.inputFromAll)
    {
      System.out.println("\n\n commited..");
       for(int i=0;i<(ser.t).size();i++)
       {
         ((ser.t).get(i)).os.println("GLOBAL_COMMIT");
         ((ser.t).get(i)).is.close();
       }
       break;
     }
  }
 }
}
     ser.closed=true;
     clientSocket.close();
   }
     catch(IOException e)
     {
     };
  } 
}
