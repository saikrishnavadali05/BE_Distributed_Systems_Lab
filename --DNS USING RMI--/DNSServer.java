import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
public class DNSServer extends UnicastRemoteObject implements DNSServerintf
{
	public DNSServer()throws RemoteException
	{
		super();
	}
	public String DNS(String s1) throws RemoteException
	{
		if(s1.equals("www.osmania.ac.in"))
			return "50.32.24.29";
		if(s1.equals("www.mvsrec.edu.in"))
			return "90.82.44.89";
		if(s1.equals("www.jntu.ac.in"))
			return "150.32.64.20";
		if(s1.equals("www.yahoo.com"))
			return "88.39.124.129";
		else
			return "No info about this address";
	}
	public static void main(String args[])throws Exception
	{
		Registry r=LocateRegistry.createRegistry(1234);
		r.rebind("mvsrserver",new DNSServer());
		System.out.println("server started...");
	}
}