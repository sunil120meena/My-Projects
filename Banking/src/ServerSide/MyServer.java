/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Sunil
 */
public class MyServer 
{
    public static void main(String[] args) 
    {
        MyServerFrame f=new MyServerFrame();
        try
        {
            Registry reg=LocateRegistry.createRegistry(1032);
            ImplementInterfaceManage skeleton=new ImplementInterfaceManage();
            reg.rebind("DefineInterfaceManage",skeleton);
//            System.out.println("Server started...");
            
            f.setTextStatusOfServer("Server is Ready...");
    	}	
	catch(RemoteException re)
        {
            System.out.print("\nException from server program"+re);
	    }
    }
}
