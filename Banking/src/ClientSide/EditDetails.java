/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Sunil
 */
public class EditDetails extends javax.swing.JFrame {

    /**
     * Creates new form EditDetails
     */
    String username;
    String who;
    String accountno;
    String Laccountno;
    String Lname, Lmobile, Laddress, Laadhar, Ltype, Lemail, Lpassword;
    int Lpincode, Lactive;
    Date LdateofCreated, Ldob;
    boolean set;
    String name, address, mobile, pincode, password, email;

    public EditDetails() {
        initComponents();
    }

    public EditDetails(String username, String accountno, String who) {
        initComponents();
        this.username = username;
        this.who = who;
        this.accountno = accountno;
        this.user.setText(username);

        ClientStub client = new ClientStub();
        Vector v;
        v = new Vector();
        try {
            v = client.stub.loadProfile(this.accountno);
        } catch (RemoteException ex) {
            System.out.println("At Edit Details constructor." + ex);
            Logger.getLogger(EditDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (v.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Record does not found.");
//            if (who.equals("employee")) {
//                this.setVisible(false);
//                Employee e = new Employee(username, who);
//                e.setVisible(true);
//            } else {
//                this.setVisible(false);
//                Customer c = new Customer(username, who);
//                c.setVisible(true);
//            }
        } else {
            Lname = (String) v.get(0);
            Laccountno = (String) v.get(1);
            Lmobile = (String) v.get(2);
            Laddress = (String) v.get(3);
            Lpincode = (int) v.get(4);
            Laadhar = (String) v.get(5);
            Ltype = (String) v.get(6);
            LdateofCreated = (Date) v.get(7);
            Ldob = (Date) v.get(8);
            Lpassword = (String) v.get(9);
//            Lactive=(int) v.get(9);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.nametf.setText(Lname);
            this.mobiletf.setText(Lmobile);
            this.addresstf.setText(Laddress);
            this.pincodetf.setText(Integer.toString(Lpincode));
        }

    }

    boolean validateName() {
        this.name = this.name.trim();
//        Pattern pattern=null;
//        Matcher matcher=null;
//        String patternvalidation="[A-Z][a-z]+( [A-Z][a-z]+)";  //for first name and surname 
////        String patternvalidation="([A-Z][a-z]+)+";
//        pattern = Pattern.compile(patternvalidation);
//        matcher = pattern.matcher(this.name);
//        return matcher.matches();

        String regexName = "\\p{Upper}(\\p{Lower}+\\s?)";
        String patternName = "(" + regexName + "){2,3}";
        return this.name.matches(patternName);
    }

    boolean validateMobile() {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(mobile);
        return (m.find() && m.group().equals(mobile));
    }

    boolean validateMobileNumberRegistered() throws RemoteException {

        ClientStub client = new ClientStub();
        return client.stub.checkMobileNumber(mobile);
    }

    boolean validateAddress() {
        return address.length() >= 15;
    }

    boolean validatePincode() {
        return pincode.matches("\\d{6}");
    }

    boolean validatePassword() {
        if (password.length() > 15) {
            return false;
        } else {
            return !password.equals("");
        }
    }

    boolean UpdateWithoutPassword() {
        password = Lpassword;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        ClientStub client = new ClientStub();
        String InUpdate = "INSERT INTO `updateaccount` (`name`, `mobile`, `password`, `address`, `pincode`,`accountno`, `bywhom`,`dateofupdate`) VALUES ('" + name + "','" + mobile + "','" + password + "','" + address + "','" + pincode + "','" + accountno + "','" + username + "','" + dtf.format(localDate) + "');";
//        String InCustomer="update `customer` set `name`='"+name+"' where `accountno` like '"+accountno+"';";
        String InCustomer = "update `customer` set `name`='" + name + "',`mobile`='" + mobile + "',`address`='" + address + "',`password`='" + password + "',`pincode`='" + pincode + "' where `accountno` like '" + accountno + "';";
        boolean result = false;
        try {
            result = client.stub.UpdateAccountInfo(InUpdate, InCustomer);
        } catch (Exception e) {
            System.out.println("At UpdateWithoutPassword In EditDetials." + e);
        }
        return result;
    }

    boolean UpdateWithPassword() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        ClientStub client = new ClientStub();
        String InUpdate = "INSERT INTO `updateaccount` (`name`, `mobile`, `password`, `address`, `pincode`,`accountno`, `bywhom`,`dateofupdate`) VALUES ('" + name + "','" + mobile + "','" + password + "','" + address + "','" + pincode + "','" + accountno + "','" + username + "','" + dtf.format(localDate) + "');";
        String InCustomer = "update `customer` set `name`='" + name + "',`mobile`='" + mobile + "',`address`='" + address + "',`password`='" + password + "',`pincode`='" + pincode + "' where `accountno` like '" + accountno + "';";
        boolean result = false;
        try {
            result = client.stub.UpdateAccountInfo(InUpdate, InCustomer);
        } catch (Exception e) {
            System.out.println("At UpdateWithPassword In EditDetials." + e);
        }
        return result;
    }

    void sendMail() {
        try {
            ClientStub client = new ClientStub();
            email = client.stub.getEmailAddressByusername(accountno);//for sending mail...
            String subject = "Updated.";
            String body = "Your Account has been updated successfully.";
            SendEmail em = new SendEmail();
            boolean result = em.sendEmail(email, subject, body);
            em=null;
        } catch (Exception ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        user = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nametf = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        mobiletf = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addresstf = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        pincodetf = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        passwordtf = new javax.swing.JPasswordField();
        Update = new javax.swing.JButton();
        back = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Name:");

        nametf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nametfActionPerformed(evt);
            }
        });

        jLabel2.setText("Mobile:");

        mobiletf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mobiletfFocusGained(evt);
            }
        });
        mobiletf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobiletfActionPerformed(evt);
            }
        });

        jLabel3.setText("Address:");

        addresstf.setColumns(20);
        addresstf.setRows(5);
        addresstf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                addresstfFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(addresstf);

        jLabel4.setText("Pincode:");

        pincodetf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pincodetfFocusGained(evt);
            }
        });

        jLabel5.setText("Password:");

        passwordtf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordtfFocusGained(evt);
            }
        });

        Update.setText("Update");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });

        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))))
                    .addComponent(back))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nametf)
                            .addComponent(mobiletf, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pincodetf, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordtf, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(138, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(Update)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nametf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(27, 27, 27)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mobiletf, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(pincodetf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(passwordtf, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addComponent(Update))
                    .addComponent(back))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nametfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nametfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nametfActionPerformed

    private void mobiletfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobiletfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobiletfActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        if (who.equals("employee")) {
            this.setVisible(false);
            Employee e = new Employee(username, who);
            e.setVisible(true);
        } else {
            this.setVisible(false);
            Customer c = new Customer(username, who);
            c.setVisible(true);
        }
    }//GEN-LAST:event_backActionPerformed

    private void mobiletfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mobiletfFocusGained
    }//GEN-LAST:event_mobiletfFocusGained

    private void addresstfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addresstfFocusGained
    }//GEN-LAST:event_addresstfFocusGained

    private void pincodetfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pincodetfFocusGained
    }//GEN-LAST:event_pincodetfFocusGained

    private void passwordtfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordtfFocusGained
    }//GEN-LAST:event_passwordtfFocusGained

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed

        this.name = nametf.getText();
        set = validateName();
        if (set == false) {
            nametf.requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Wrong Name pattern(First+Last name)");
//            this.myname.setText("");
        } else {
            this.set = false;
            this.mobile = mobiletf.getText();
            set = validateMobile();
            if (set == false) {
                mobiletf.requestFocusInWindow();
                JOptionPane.showMessageDialog(this, "Invalid mobile number.");
            } else if (!this.mobile.equals(Lmobile)) {
//            mobiletf.requestFocusInWindow();
//            JOptionPane.showMessageDialog(this, "same mobile number.");
//                set = false;
                try {
                    set = validateMobileNumberRegistered();
                } catch (RemoteException ex) {
                    Logger.getLogger(NewCustomer.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (set == true) {
                    mobiletf.requestFocusInWindow();
                    JOptionPane.showMessageDialog(this, "mobile number already exist.");
//                this.mymobile.setText("");
                } else {
                    this.set = false;
                    this.address = addresstf.getText();
                    set = validateAddress();

                    if (set == false) {
                        addresstf.requestFocusInWindow();
                        JOptionPane.showMessageDialog(this, "Adrress must be greater than 15 character.");
                    } else {
                        this.set = false;
                        this.pincode = pincodetf.getText();
                        set = validatePincode();
                        if (set == false) {
                            pincodetf.requestFocusInWindow();
                            JOptionPane.showMessageDialog(this, "Wrong pincode.");
                        } else {
                            this.set = false;
                            this.password = new String(passwordtf.getPassword());
                            if (this.password.equals("")) {
//                                JOptionPane.showMessageDialog(this, "Password is not changed.");
                                set = UpdateWithoutPassword();
                                if (set == true) {
                                    sendMail();
                                    JOptionPane.showMessageDialog(this, "Updated successfully.");
                                    if (who.equals("employee")) {
                                        this.setVisible(false);
                                        Employee e = new Employee(username, who);
                                        e.setVisible(true);
                                    } else {
                                        this.setVisible(false);
                                        Customer c = new Customer(username, who);
                                        c.setVisible(true);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Update Failed.");
                                }
                            } else {
                                set = validatePassword();
                                if (set == false) {
                                    passwordtf.requestFocusInWindow();
                                    JOptionPane.showMessageDialog(this, "Password less than 15 charactor");
                                } else {
                                    this.set = false;
//                                    JOptionPane.showMessageDialog(this, "Password is changed and move to update.");
                                    set = UpdateWithPassword();
                                    if (set == true) {
                                        sendMail();
                                        JOptionPane.showMessageDialog(this, "Updated successfully.");
                                        if (who.equals("employee")) {
                                            this.setVisible(false);
                                            Employee e = new Employee(username, who);
                                            e.setVisible(true);
                                        } else {
                                            this.setVisible(false);
                                            Customer c = new Customer(username, who);
                                            c.setVisible(true);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(this, "Update Failed.");
                                    }
                                }
                            }
                        }
                    }
                }

            } else {
                this.set = false;
                this.address = addresstf.getText();
                set = validateAddress();

                if (set == false) {
                    addresstf.requestFocusInWindow();
                    JOptionPane.showMessageDialog(this, "Adrress must be greater than 15 character.");
                } else {
                    this.set = false;
                    this.pincode = pincodetf.getText();
                    set = validatePincode();
                    if (set == false) {
                        pincodetf.requestFocusInWindow();
                        JOptionPane.showMessageDialog(this, "Wrong pincode.");
                    } else {
                        this.set = false;
                        this.password = new String(passwordtf.getPassword());
                        if (this.password.equals("")) {
//                            JOptionPane.showMessageDialog(this, "Password is not changed.");
                            set = UpdateWithoutPassword();
                            if (set == true) {
                                sendMail();
                                JOptionPane.showMessageDialog(this, "Updated successfully.");
                                if (who.equals("employee")) {
                                    this.setVisible(false);
                                    Employee e = new Employee(username, who);
                                    e.setVisible(true);
                                } else {
                                    this.setVisible(false);
                                    Customer c = new Customer(username, who);
                                    c.setVisible(true);
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Update Failed.");
                            }
                        } else {
                            set = validatePassword();
                            if (set == false) {
                                passwordtf.requestFocusInWindow();
                                JOptionPane.showMessageDialog(this, "Password less than 15 charactor");
                            } else {
                                this.set = false;
//                                JOptionPane.showMessageDialog(this, "Password is changed and move to update.");
                                set = UpdateWithPassword();
                                if (set == true) {
                                    sendMail();
                                    JOptionPane.showMessageDialog(this, "Updated successfully.");
                                    if (who.equals("employee")) {
                                        this.setVisible(false);
                                        Employee e = new Employee(username, who);
                                        e.setVisible(true);
                                    } else {
                                        this.setVisible(false);
                                        Customer c = new Customer(username, who);
                                        c.setVisible(true);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Update Failed.");
                                }
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_UpdateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Update;
    private javax.swing.JTextArea addresstf;
    private javax.swing.JButton back;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mobiletf;
    private javax.swing.JTextField nametf;
    private javax.swing.JPasswordField passwordtf;
    private javax.swing.JTextField pincodetf;
    private javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables
}
