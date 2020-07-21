/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import DefineInterfaceManage.DefineInterfaceManage;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Sunil
 */
public class ClientStub 
{
    Registry reg;
    DefineInterfaceManage stub;
    ClientStub()
    {
        try
        {
            reg=LocateRegistry.getRegistry("localhost",1032);
        }
        catch(RemoteException e)
        {
            System.out.println(("Problem at Client stub..."));
        }
        
        try
        {
            stub=(DefineInterfaceManage)reg.lookup("DefineInterfaceManage");
        }
        catch(NotBoundException | RemoteException e)
       {
           System.out.println(("Problem in lookup..."));        }
    }
}
