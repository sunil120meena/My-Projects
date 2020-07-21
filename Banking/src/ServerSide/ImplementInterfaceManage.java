/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

import DefineInterfaceManage.DefineInterfaceManage;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeJava.type;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 *
 * @author Sunil
 */
class ImplementInterfaceManage extends UnicastRemoteObject implements DefineInterfaceManage {

    public ImplementInterfaceManage() throws RemoteException {
        super();
    }

    @Override
    public boolean loginfromemployee(String username, String password) {
        try {
            Connection conn = new Connect().getConnection();
            if (conn != null) {
//        System.out.println("Inside if in implement page");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `employeelogin` where `username`=? AND `password`=?");
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                return rs.next();//      pass=rs.getString(2);
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error in login in loginfromemployee function in implement page");
        }
        return false;
    }

    public boolean loginfromcustomer(String username, String password) {
        try {
            Connection conn = new Connect().getConnection();
            if (conn != null) {
//        System.out.println("Inside if in implement page");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `customerlogin` where `username`=? AND `password`=?");
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                return rs.next();//      pass=rs.getString(2);
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error in login in loginfromcustomer function in implement page");
        }
        return false;
    }

    @Override
    public int depositBalance(String accountno, int amount, String username, int balance) {
        int count;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        try {
            Connection conn = new Connect().getConnection();
            Statement stmt;
            stmt = conn.createStatement();
            count = stmt.executeUpdate("UPDATE customer SET balance = " + "\"" + amount + "\"" + " WHERE customer.accountno like " + "\"" + accountno + "\"" + ";");
            stmt.executeUpdate("INSERT INTO `deposit` (`balance`, `accountno`, `amount`, `date`, `bywhom`) VALUES ('" + amount + "','" + accountno + "','" + balance + "','" + dtf.format(localDate) + "','" + username + "');");
            return count;
        } catch (Exception e) {
            System.out.println("at depositBalance at Implement" + e);
            return 0;
        }

    }

    @Override
    public int withdrawBalance(String accountno, int setamount, String username, int withdrawamount) {
        int count;
//        int acc = Integer.parseInt(accountno);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        try {
            Connection conn = new Connect().getConnection();
            Statement stmt;
            stmt = conn.createStatement();
            count = stmt.executeUpdate("UPDATE customer SET balance = " + "\"" + setamount + "\"" + " WHERE customer.accountno like " + "\"" + accountno + "\"" + ";");
            stmt.executeUpdate("INSERT INTO `withdraw` (`balance`, `accountno`, `amount`, `date`, `bywhom`) VALUES ('" + setamount + "','" + accountno + "','" + (-1*withdrawamount) + "','" + dtf.format(localDate) + "','" + username + "');");
            return count;
        } catch (Exception e) {
            System.out.println("at withdrawBalance at Implement" + e);
            return 0;
        }
    }

//    @Override
//    public int transferBalance()
//    {
//        return 15;
//    }
    @Override
    public int checkBalance(String accountno) {
//        int no=Integer.parseInt(accountno);
        try {
//            int no = Integer.parseInt(accountno);
            Connection conn = new Connect().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT balance FROM customer WHERE accountno like " + "\"" + accountno + "\"" + ";");
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            System.out.println("In Implement at checkBalance" + e);
            return 0;
        }
    }

    @Override
    public boolean checkAccountno(String accountno) {
        try {
//            int no = Integer.parseInt(accountno);
            Connection conn = new Connect().getConnection();
            Statement stmt = conn.createStatement();
//            System.out.println(accountno);
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer WHERE accountno like " + "\"" + accountno + "\"" + ";");
            return rs.next() != false;
        } catch (Exception e) {
            System.out.println("In Implement at checkaccountno" + e);
            return false;
        }

    }

    @Override
    public boolean checkMobileNumber(String mobile) {
        String query = "SELECT name FROM newcustomer WHERE mobile=" + "\"" + mobile + "\"" + ";";  //get username
        try {
            Connection conn = new Connect().getConnection();
            Statement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);
//            return rs.next();
            if ((rs.next()) == true) {
                return true;
            } else {
                query = "SELECT name FROM customer WHERE mobile=" + "\"" + mobile + "\"" + ";";
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery(query);
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("In checkMobileNumber at implemetn side.");
            return true;
        }
    }

    @Override
    public boolean checkEmailAddress(String email) {
        String query = "SELECT name FROM newcustomer WHERE Email like " + "\"" + email + "\"" + ";";  //get username
        try {
            Connection conn = new Connect().getConnection();
            Statement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);
//            return rs.next();
            if ((rs.next()) == true) {
                return true;
            } else {
                query = "SELECT name FROM customer WHERE Email like " + "\"" + email + "\"" + ";";
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery(query);
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("In checkEmailNumber at implemetn side.");
            return true;
        }
    }

    @Override
    public boolean checkAadharNumber(String aadhar) {
        String query = "SELECT name FROM newcustomer WHERE aadhar=" + "\"" + aadhar + "\"" + ";";
        try {
            Connection conn = new Connect().getConnection();
            Statement stmt;
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);
//            return rs.next();
            if ((rs.next()) == true) {
                return true;
            } else {
                query = "SELECT name FROM customer WHERE aadhar=" + "\"" + aadhar + "\"" + ";";
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery(query);
                return rs.next();
            }
        } catch (SQLException e) {
            return true;
        }
    }

    @Override
    public boolean insertInNewCustomerDatabase(String insertQuery) {
        int count = 0;
        try {
            Connection conn = new Connect().getConnection();
            Statement stmt;
            stmt = conn.createStatement();
            count = stmt.executeUpdate(insertQuery);
        } catch (Exception e) {
            System.out.println("In insertInNewCustomerDatabase at implementInterface" + e);
        }
        return count == 1;
    }

    @Override
    public Vector loadProfile(String accountno) {
        String name, email, mobile, address, aadhar, type, password;
        int pincode, active;
        Date dateofCreated, dob;
        try {
//            int acc = Integer.parseInt(accountno);
            Connection conn = new Connect().getConnection();
            String query = "SELECT * FROM customer WHERE accountno like " + "\"" + accountno + "\"" + ";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("name");
                accountno = rs.getString("accountno");
                mobile = rs.getString("mobile");
                address = rs.getString("address");
                pincode = rs.getInt("pincode");
                aadhar = rs.getString("aadhar");
                type = rs.getString("type");
                password = rs.getString("password");
//                active = rs.getInt("active");

                dateofCreated = rs.getDate("dateofcreate");
                dob = rs.getDate("dob");
                email = rs.getString("Email");
//                System.out.println(name+accountno+mobile+address+pincode+aadhar+type+dateofCreated+dob);
                Vector v = new Vector();
                v.add(0, name);
                v.add(1, accountno);
                v.add(2, mobile);
                v.add(3, address);
                v.add(4, pincode);
                v.add(5, aadhar);
                v.add(6, type);
                v.add(7, dateofCreated);
                v.add(8, dob);
                v.add(9, password);
                v.add(10, email);
//                v.add(9, active);

//                System.out.println("Vector is: " + v); 
                return v;

            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println("in loadProfile at implement" + e);
        }
        Vector c = new Vector();
        return c;
    }

    @Override
    public Vector returNewCustomerTable() {
        try {
            Connection conn = new Connect().getConnection();
            String query = "SELECT * FROM newcustomer;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            Vector outer = new Vector();
            while (rs.next()) {
                Vector v = new Vector();
                for (int i = 1; i <= c; i++) {
                    v.add(rs.getString("id"));
                    v.add(rs.getString("name"));
                    v.add(rs.getString("mobile"));
                    v.add(rs.getString("address"));
                    v.add(rs.getString("pincode"));
                    v.add(rs.getDate("dob"));
                    v.add(rs.getString("aadhar"));
                    v.add(rs.getString("accounttype"));
                    v.add(rs.getString("Email"));
                }
                outer.add(v);
            }
            return outer;
        } catch (Exception e) {
            System.out.println("In returnNewCustomerTable at implement side." + e);
            Vector c = new Vector();
            return c;
        }
    }

    @Override
    public boolean deleteAccount(String accountno, String username) {
        String name = null, email = null, mobile = null, address = null, aadhar = null, type = null, bywhom = null, password = null;
        int pincode = 0, active, balance = 0;
        Date dateofCreated = null, dob = null;
        int count = 0;
        try {
//            int acc = Integer.parseInt(accountno);
            Connection conn = new Connect().getConnection();
//            String query  = "update customer set active=0 WHERE accountno=" +"\""+acc+"\""+" and active=1;";
            String query = "select * from customer WHERE accountno like " + "\"" + accountno + "\"" + ";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("name");
                accountno = rs.getString("accountno");
                mobile = rs.getString("mobile");
                address = rs.getString("address");
                pincode = rs.getInt("pincode");
                aadhar = rs.getString("aadhar");
                type = rs.getString("type");
//                active=rs.getInt("active");
                bywhom = rs.getString("bywhom");
                password = rs.getString("password");
                balance = rs.getInt("balance");
                dateofCreated = rs.getDate("dateofcreate");
                dob = rs.getDate("dob");
                email = rs.getString("Email");
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.now();
            String query1;
            query1 = "INSERT INTO `deletedcustomer` (`name`,`accountno`, `mobile`,`address`, `pincode`,`aadhar`,`type`,`createdbywhom`,`password`,`balance`,`dateofcreation`, `dob`,`deletedbywhom`,`dateofdeleted`, `Email`) VALUES ('" + name + "','" + accountno + "','" + mobile + "','" + address + "','" + pincode + "','" + aadhar + "','" + type + "','" + bywhom + "','" + password + "','" + balance + "','" + dateofCreated + "','" + dob + "','" + username + "','" + dtf.format(localDate) + "','" + email + "');";
            st = conn.createStatement();
            count = st.executeUpdate(query1);

            //Remove active information
            String query2;
            query2 = "delete from customer WHERE accountno like " + "\"" + accountno + "\"" + ";";
            st = conn.createStatement();
            count = st.executeUpdate(query2);

            //Delete login information for stop login
            String query3;
            query3 = "delete from customerlogin WHERE username like " + "\"" + aadhar + "\"" + ";";
            st = conn.createStatement();
            count = st.executeUpdate(query3);

            return count > 0;
        } catch (NumberFormatException | SQLException e) {
            System.out.println("in deleteAccount at implement." + e);
            return false;
        }
    }

    @Override
    public String getAccountnoByusername(String username) {
        try {
            Connection conn = new Connect().getConnection();
            String query = "select accountno from customer WHERE aadhar like " + "\"" + username + "\"" + ";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            return rs.getString("accountno");
        } catch (Exception e) {
            System.out.println("In getAccountnoByusername at implementSide." + e);
            return "";
        }
    }

    public String getEmailAddressByusername(String username) {
        try {
            Connection conn = new Connect().getConnection();
            String query = "select Email from customer WHERE aadhar like " + "\"" + username + "\"" + ";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            return rs.getString("Email");
        } catch (Exception e) {
            System.out.println("In getEmailAddressByusername at implementSide." + e);
            return "";
        }
    }

    @Override
    public boolean approveNewCustomerAndRegisterNewCustomer(int id, String username) {
        String name = null, email = null, mobile = null, address = null, pincode = null, aadhar = null, type = null, password = null;
        Date dob = null, doc = null;
        int row = 0;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        try {
            Connection conn = new Connect().getConnection();
            String query = "select * from newcustomer WHERE id=" + "\"" + id + "\"" + ";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                name = rs.getString("name");
                mobile = rs.getString("mobile");
                address = rs.getString("address");
                pincode = rs.getString("pincode");
                dob = rs.getDate("dob");
                aadhar = rs.getString("aadhar");
                type = rs.getString("accounttype");
                password = rs.getString("password");
                email = rs.getString("Email");
            }
            String query1 = "INSERT INTO customer(name,mobile,password,address,pincode,dob,aadhar,type,accountno,bywhom,dateofcreate,balance,Email) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query1);
            ps.setString(1, name);
            ps.setString(2, mobile);
            ps.setString(3, password);
            ps.setString(4, address);
            ps.setString(5, pincode);
            ps.setDate(6, (java.sql.Date) dob);
            ps.setString(7, aadhar);
            ps.setString(8, type);
            ps.setString(9, aadhar);
            ps.setString(10, username);
            ps.setString(11, dtf.format(localDate));
            ps.setInt(12, 0);
            ps.setString(13, email);
            row = ps.executeUpdate();

            String query2 = "delete from newcustomer WHERE id=" + "\"" + id + "\"" + ";";
            st = conn.createStatement();
            row = st.executeUpdate(query2);

            String query3 = "INSERT INTO customerlogin VALUES (?,?)";
            PreparedStatement ps1 = conn.prepareStatement(query3);
            ps1.setString(1, aadhar);
            ps1.setString(2, password);
            row = ps1.executeUpdate();

            String query4 = "INSERT INTO updateaccount(name,mobile,password,address,pincode,accountno,bywhom,dateofupdate) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps2 = conn.prepareStatement(query4);
            ps2.setString(1, name);
            ps2.setString(2, mobile);
            ps2.setString(3, password);
            ps2.setString(4, address);
            ps2.setString(5, pincode);
            ps2.setString(6, aadhar);
            ps2.setString(7, username);
            ps2.setString(8, dtf.format(localDate));
            row = ps2.executeUpdate();

            return row > 0;
        } catch (Exception e) {
            System.out.println("In approveNewCustomerAndRegisterNewCustomer at implement." + e);
            return false;
        }
    }

    @Override
    public boolean checkInDeletedCustomer(int id) {

        try {
            Connection conn = new Connect().getConnection();
            String query = "select nc.name from newcustomer nc join deletedcustomer dc on nc.aadhar like dc.aadhar where nc.id=" + id + "";
            Statement ps = conn.createStatement();

            ResultSet row = ps.executeQuery(query);
            return row.next();
        } catch (Exception e) {
            System.out.println("In checkInDeletedCustomer at implement side." + e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean RejectCustomer(int id) throws RemoteException {
        try {
            Connection conn = new Connect().getConnection();
            String query = "delete from newcustomer where id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            System.out.println("In RejectCustomer at implement side." + e);
            return false;
        }
    }

    @Override
    public boolean UpdateAccountInfo(String InUpdate, String InCustomer) throws RemoteException {
        int row = 0, pincode, count = 0;
        String name, accountno, address, mobile, password;
        try {
            Connection conn = new Connect().getConnection();
            Statement stmt, stmt2;
            stmt = conn.createStatement();
            count = stmt.executeUpdate(InUpdate);
            stmt2 = conn.createStatement();
            count = stmt.executeUpdate(InCustomer);
            return count == 1;
        } catch (Exception e) {
            System.out.println("In UpdateAccountInfo at implementInterface" + e);
            return false;
        }

    }

    @Override
    public Vector loadTransection(String accountno) {
        try {
            Connection conn = new Connect().getConnection();
            String query = "SELECT * FROM deposit WHERE accountno like " + "\"" + accountno + "\"" + ";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            Vector outer = new Vector();
            while (rs.next()) {
                Vector v = new Vector();
                for (int i = 1; i <= c; i++) {
                    v.add(rs.getString("id"));
                    v.add(rs.getString("accountno"));
                    v.add(rs.getString("amount"));
                    v.add(rs.getString("date"));
                    v.add(rs.getInt("balance"));
                }
                outer.add(v);
            }
            return outer;
        } catch (Exception e) {
            System.out.println("In loadTransection at implement side." + e);
            Vector c = new Vector();
            return c;
        }
    }

}
