





	import java.io.*;
	import java.net.*;
	import java.sql.*;
	import java.util.*;

		class Server1{
			
			
			
			public static void main(String a[]) {
			ServerSocket sock;
			Socket client;
			DataInputStream input;
			PrintStream ps;
			String url,u,s;
			Connection con;
			Statement smt;
			ResultSet rs;

			try{
					
					
					s=u="\0";//intializing string to null character
					
					Class.forName("com.mysql.jdbc.Driver");//instantiating jdbc driver
					
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/DNS","root","root");//connecting to the local dbase
					
					smt=con.createStatement();//creation of database connection statement
					
					
					sock = new ServerSocket(5123);//creation of server socket

					
					
					while(true){

					client = sock.accept();//Socket client.... ServerSocket sock....

					input = new DataInputStream(client.getInputStream());//get input from client through client socket

					ps = new PrintStream(client.getOutputStream());

					url = input.readLine(); // String url... gets input from input variable via socket //ex ::: google.com

					StringTokenizer st = new StringTokenizer(url,".");//breaks given url as <google> and <com>

					while(st.countTokens()>1)
					{
						s = s+st.nextToken()+".";//string s     nextToken() -- returns next token as a string
					}
					
						s = s.substring(0,s.length()-1).trim();//cutting the substring     substring(start-index,end-index)

						u = st.nextToken();//string u

						rs = smt.executeQuery("select port,ipadd from root where name='"+u+"'");

						if(rs.next()){

						ps.println(Integer.parseInt(rs.getString(1)));

						ps.println(Integer.parseInt(rs.getString(2)));

						ps.println(s);

						}

						
						else{

						
						ps.println("illegal address please check the spelling again");

						con.close();

						}

					    }

						}

						
						

						catch(Exception e)

						{
							System.err.println(e);
						}
	}
}

