import java.net.*;
import java.io.*;
import java.util.*;

public class ftp
{
	static Socket DataSocket;
	
	static DataInputStream ipstream;
	static PrintStream outstream;
	
	public static void main(String args[])throws IOException
	{
		if(connect())
		{
			while(true)
			{
				System.out.println("Enter your choice:\n");
				System.out.println("1. Read a file:\n");
				System.out.println("2. Store a file:\n");
				System.out.println("3.List files:\n");
				System.out.println("4.change directory:\n");
				System.out.println("5.change to parent directory:\n");
				System.out.println("6.create directory:\n");
				System.out.println("7.print current directory:\n");
				System.out.println("logout:\n");

				BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				int option = Integer.parseInt(br.readLine());

				switch(option)
				{
					case 1:
							read();
							break;
					case 2:
							store();
							break;
					case 3:
							list();
							break;
					case 4:
							System.out.println("Enter directory:\n");
							String name=br.readLine();
							outstream.print("CWD"+name+"\r\n");
							break;
					case 5:
							outstream.print("CDUP"+"\r\n");
							break;
					case 6:
							System.out.println("enter directory:\n");
							name=br.readLine();
							outstream.print("MKD"+name+"\r\n");
							break;

					case 7:
							outstream.print("PWD"+"\r\n");
							break;

					case 8:
							outstream.print("QUIT"+"\r\n");
							System.exit(1);
							break;

					default:
							System.out.println("choose a valid option!!!\n");
							break;
}
}
}
else connect();
}



static boolean connect()
{
try
{
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.println("Enter the FTP host address:");
String host=br.readLine();
DataSocket=new Socket(host,21);
ipstream=new DataInputStream(DataSocket.getInputStream());
outstream=new PrintStream(DataSocket.getOutputStream());
String s =ipstream.readLine();


if(s.startsWith("220"))
{
	System.out.println("connected to server"+host);
	System.out.println("\n ENTER USERNAME:\t");
	String uname=br.readLine();
	outstream.print("USER"+uname+"\r\n");
	s=ipstream.readLine();

	if(s.startsWith("331"))
	{
	System.out.println("\n ENTER PASSWORD:\t");
	String pass=br.readLine();
	outstream.print("PASS"+pass+"\r\n");
	s=ipstream.readLine();

			if(s.startsWith("230"))
			{
				System.out.println("login successful!! have a nice day!!\n");
				return true;
			}
	}
}
else
{
System.out.println("error connecting to server "+host);
return false;
}
}


catch(UnknownHostException e)
{
System.err.println(e);
}


catch(IOException e)
{
System.err.println(e);
}


return false;
}


static void store()
{
try
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("enter filename:\n");
		String filename=br.readLine();
		FileInputStream inputstream=new FileInputStream(filename);
		outstream.print("PASV"+"\r\n");
		String s=ipstream.readLine();
		String ip=null;
		int port=-1;
		int opening=s.indexOf('(');
		int closing=s.indexOf(')',opening+1);

		
		
		if(closing>0)
{
String dataLink=s.substring(opening+1,closing);
StringTokenizer tokenizer=new StringTokenizer(dataLink,",");
ip=tokenizer.nextToken()+"."+tokenizer.nextToken()+"."+tokenizer.nextToken()+"."+tokenizer.nextToken();
port=Integer.parseInt(tokenizer.nextToken());
outstream.print("STOR"+filename+"\r\n");
Socket dataSocket=new Socket(ip,port);
s=ipstream.readLine();

	if(!s.startsWith("150"))
{
throw new IOException("simple FTP was not allowed to send the file:"+s);
}


BufferedInputStream input=new BufferedInputStream(inputstream);
BufferedOutputStream output=new BufferedOutputStream(DataSocket.getOutputStream());
byte[] buffer=new byte[4096];
int bytesRead=0;
while((bytesRead=input.read(buffer))!=-1)
{
output.write(buffer,0,bytesRead);
}
output.flush();
output.close();
input.close();
}
}

catch(UnknownHostException e)
{
System.err.println(e);
}

catch(IOException e)
{
System.err.println(e);
}
}


static void read()
{
try
{
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
System.out.println("enter the filename\n");
String filename=br.readLine();
outstream.print("PASV"+"\r\n");
String s=ipstream.readLine();
System.out.println(s);
String ip=null;
int port=-1;
int opening=s.indexOf('(');
int closing=s.indexOf(')',opening+1);
if(closing>0)
{
String dataLink=s.substring(opening+1,closing);
StringTokenizer tokenizer=new StringTokenizer(dataLink,",");
ip=tokenizer.nextToken()+"."+tokenizer.nextToken()+"."+tokenizer.nextToken()+"."+tokenizer.nextToken();
port=Integer.parseInt(tokenizer.nextToken())*256+Integer.parseInt(tokenizer.nextToken());
outstream.print("RETR"+filename+"\r\n");
Socket dataSocket=new Socket(ip,port);
s=ipstream.readLine();
if(!s.startsWith("150")){
throw new IOException("simple FTP was not allowed to send the file:"+s);
}
BufferedInputStream input=new BufferedInputStream(dataSocket.getInputStream());
FileOutputStream output=new FileOutputStream("New"+filename);
byte[] buffer=new byte[4096];
int bytesRead=0;
while((bytesRead=input.read(buffer))!=-1){
output.write(buffer,0,bytesRead);
}
s=ipstream.readLine();
System.out.println("FInished!"+s);
output.close();
input.close();
}
}
catch(UnknownHostException e)
{
System.err.println(e);}
catch(IOException e)
{
	System.err.println(e);
}
}


static void list(){
try{
outstream.print("PASV"+"\r\n");
String s=ipstream.readLine();
System.out.println(s);
String ip=null;
int port=-1;
int opening=s.indexOf('(');
int closing=s.indexOf(')',opening+1);


if(closing>0)
{
String dataLink=s.substring(opening+1,closing);
StringTokenizer tokenizer=new StringTokenizer(dataLink,",");
ip=tokenizer.nextToken()+"."+tokenizer.nextToken()+"."+tokenizer.nextToken()+"."+tokenizer.nextToken();
port=Integer.parseInt(tokenizer.nextToken())*256+Integer.parseInt(tokenizer.nextToken());
outstream.print("LIST"+"\r\n");
Socket dataSocket=new Socket(ip,port);
s=ipstream.readLine();
DataInputStream input=new DataInputStream(dataSocket.getInputStream());
String line;

while((line=input.readLine())!=null) System.out.println(line);
input.close();
}
}

catch(UnknownHostException e)
{
System.err.println(e);
}

catch(IOException e)
{
System.err.println(e);
}
}
}
