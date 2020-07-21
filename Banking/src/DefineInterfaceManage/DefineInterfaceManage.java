/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DefineInterfaceManage;

import ClientSide.CheckBalance;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Sunil
 */
public interface DefineInterfaceManage extends Remote
{
	abstract boolean loginfromemployee(String username,String password) throws RemoteException;
        abstract boolean loginfromcustomer(String username,String password) throws RemoteException;
        abstract int depositBalance(String accountno,int amount,String username,int balance) throws RemoteException;
        abstract int withdrawBalance(String accountno,int setamount,String username,int withdrawamount) throws RemoteException;
//        abstract int transferBalance() throws RemoteException;
        abstract int checkBalance(String accountno) throws RemoteException;
        abstract String getAccountnoByusername(String username) throws RemoteException;
        abstract String getEmailAddressByusername(String username) throws RemoteException;
        abstract boolean checkAccountno(String accountno) throws RemoteException;
        abstract boolean checkMobileNumber(String mobile) throws RemoteException;
        abstract boolean checkAadharNumber(String aadhar) throws RemoteException;
        abstract boolean checkEmailAddress(String email) throws RemoteException;
        abstract boolean insertInNewCustomerDatabase(String insertQuery) throws RemoteException;
        
        abstract Vector loadProfile(String accountno) throws RemoteException;
        abstract Vector loadTransection(String accountno) throws RemoteException;
        abstract Vector returNewCustomerTable() throws RemoteException;
        abstract boolean deleteAccount(String accountno,String username) throws RemoteException;
        
        abstract boolean approveNewCustomerAndRegisterNewCustomer(int id,String username) throws RemoteException;
        abstract boolean checkInDeletedCustomer(int id) throws RemoteException;
        abstract  boolean  RejectCustomer(int id) throws RemoteException;
        
        
        abstract boolean UpdateAccountInfo(String InUpdate,String InCustomer) throws RemoteException;
        
}

