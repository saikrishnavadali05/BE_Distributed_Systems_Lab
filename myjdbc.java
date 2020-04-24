import java.sql.*;  
class myjdbc{  
public static void main(String args[]) throws ClassNotFoundException, SQLException{  
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection("jdbc:mysql://192.168.7.77/student","student","");  
Statement stmt=con.createStatement();  
ResultSet rs=stmt.executeQuery("select * from emp");  
while(rs.next())  
System.out.println(rs.getString(1));      
}  
} 
//javac myjdbc.java
//java -cp .:/usr/share/java/mysql.jar myjdbc
