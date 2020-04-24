import java.rmi.*;
import java.rmi.registry.*;
public class DNSClient
{
	public static void main(String args[])throws Exception
	{
		
		Registry r=LocateRegistry.getRegistry("localhost",1234);
		
		DNSServerintf d=(DNSServerintf)r.lookup("mvsrserver");// lookup("string") -- returns the reference of remote object
		
		String str=args[0];//the address given by us to main is stored in str variable
		
		System.out.println("this website name is :"+str);
		
		System.out.println(" the ip address is:"+d.DNS(str));
		}
}
