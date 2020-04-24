
//remote object
import java.rmi.*;
public interface DNSServerintf extends Remote
{
	public String DNS(String s1)throws RemoteException;
 }
