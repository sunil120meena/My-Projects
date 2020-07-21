/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import com.toedter.calendar.JDateChooser;
import java.awt.Window;
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sunil
 */
public class NewCustomer extends javax.swing.JFrame {

    /**
     * Creates new form NewCustomer
     */
    String email;
    String name;
    String mobile;
    String password;
    String address;
    String pincode;
    Date dob;
//    JDateChooser dob;
    String aadhar;
    String type;

    boolean set;

    private static Pattern aadhaarPattern = Pattern.compile("^[2-9]{1}[0-9]{11}$");

    public NewCustomer() {
        this.type = "";
        this.set = false;
        initComponents();
    }

    boolean validateEmail() {
//        String regex = "^(.+)@(.+)$";
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    boolean validateEmailNumberRegistered() throws RemoteException {
        ClientStub client = new ClientStub();
        return client.stub.checkEmailAddress(email);
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
        String patternName = "(" + regexName + "){1,3}";
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

    boolean validatePassword() {
        if (password.length() > 15) {
            return false;
        } else {
            return !password.equals("");
        }
    }

    boolean validateAddress() {
        return address.length() >= 15;
    }

    boolean validatePincode() {
        return pincode.matches("\\d{6}");
    }

    boolean validateAadhar() {
        Matcher matcher = aadhaarPattern.matcher(aadhar);
        return matcher.find();
    }

    boolean validateAadharRegistered() throws RemoteException {
        ClientStub client = new ClientStub();
        return client.stub.checkAadharNumber(aadhar);
    }

    boolean validateType() {
        return !type.equals("");
    }

    boolean insertInNewCustomer(String insertQuery) throws RemoteException {
        ClientStub client = new ClientStub();
        return client.stub.insertInNewCustomerDatabase(insertQuery);
    }

    boolean Validateotp() {
        Random rand = new Random();
        String validotp = String.format("%04d", rand.nextInt(10000));
        int flag = 1;
        String m = "hello";
        String subject = "OTP!";
        String body;
        boolean result = false;
        SendEmail em = new SendEmail();

        try {
            result = em.sendEmail(email, subject, validotp);
            em = null;
        } catch (Exception ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (result == false) {
            JOptionPane.showMessageDialog(this, "Your internet is not working ...please try after sometime");
            return false;
        }
        while (flag == 1) {

            m = JOptionPane.showInputDialog("Enter otp sent on your email:"+email);
            if (m == null) {
                return false;
            }
            if (m.equals(validotp)) {
                flag = 0;
                break;
            } else {
                JOptionPane.showMessageDialog(this, "Invalid OTP. Please enter a valid otp");
            }
        }
        if (flag == 0) {
            return true;
        } else {
            return false;
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

        back = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        myname = new javax.swing.JTextField();
        mymobile = new javax.swing.JTextField();
        mypincode = new javax.swing.JTextField();
        myaadhar = new javax.swing.JTextField();
        ok = new javax.swing.JButton();
        mydob = new com.toedter.calendar.JDateChooser();
        saving = new javax.swing.JRadioButton();
        current = new javax.swing.JRadioButton();
        addresswarning = new javax.swing.JLabel();
        passwordwarning = new javax.swing.JLabel();
        mobilewarning = new javax.swing.JLabel();
        pincodewarning = new javax.swing.JLabel();
        aadharwarning = new javax.swing.JLabel();
        accounttypewarning = new javax.swing.JLabel();
        namewarning = new javax.swing.JLabel();
        mypassword = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        myaddress = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        myemail = new javax.swing.JTextField();
        emailwarning = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        jLabel1.setText("Name:");

        jLabel2.setText("Mobile no:");

        jLabel3.setText("Password");

        jLabel4.setText("Address");

        jLabel5.setText("Pincode");

        jLabel6.setText("Date of Birth");

        jLabel7.setText("Aadhar no:");

        jLabel8.setText("Account Type:");

        myname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mynameFocusGained(evt);
            }
        });
        myname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mynameActionPerformed(evt);
            }
        });

        mymobile.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mymobileFocusGained(evt);
            }
        });
        mymobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                none(evt);
            }
        });

        mypincode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mypincodeFocusGained(evt);
            }
        });

        myaadhar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                myaadharFocusGained(evt);
            }
        });

        ok.setText("Submit");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        saving.setText("Saving");
        saving.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savingActionPerformed(evt);
            }
        });

        current.setText("Current");
        current.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentActionPerformed(evt);
            }
        });

        mypassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mypasswordFocusGained(evt);
            }
        });
        mypassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mypasswordActionPerformed(evt);
            }
        });

        myaddress.setColumns(20);
        myaddress.setRows(5);
        myaddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                myaddressFocusGained(evt);
            }
        });
        myaddress.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                myaddressPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(myaddress);

        jLabel9.setText("Email:");

        myemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myemailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(back)
                                        .addGap(40, 40, 40))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))
                                        .addGap(25, 25, 25))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(7, 7, 7))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(saving)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(current))
                                        .addComponent(mymobile)
                                        .addComponent(myname)
                                        .addComponent(mydob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(myaadhar)
                                        .addComponent(mypincode, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                                    .addComponent(mypassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(myemail)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(aadharwarning, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(accounttypewarning, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pincodewarning, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addresswarning, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(namewarning, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mobilewarning, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(passwordwarning, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                            .addComponent(emailwarning, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(back)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(myemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailwarning, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(61, 61, 61))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(myname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addComponent(namewarning, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(mymobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addComponent(mobilewarning, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(mypassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addComponent(passwordwarning, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addresswarning, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mypincode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(pincodewarning, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mydob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(myaadhar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addComponent(aadharwarning, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saving)
                        .addComponent(current)
                        .addComponent(jLabel8))
                    .addComponent(accounttypewarning, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(ok)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:

        this.setVisible(false);
        login l = new login();
        l.setVisible(true);
    }//GEN-LAST:event_backActionPerformed

    private void none(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_none
        // TODO add your handling code here:


    }//GEN-LAST:event_none

    private void mymobileFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mymobileFocusGained
        // TODO add your handling code here:
        this.name = myname.getText();
        set = validateName();
        if (set == false) {
            myname.requestFocusInWindow();
            this.namewarning.setText("Wrong Name pattern(First+Last name)");
            this.myname.setText("");
        } else {
            this.namewarning.setText("");
            this.set = false;
        }
    }//GEN-LAST:event_mymobileFocusGained

    private void mypincodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mypincodeFocusGained
        // TODO add your handling code here:
        address = myaddress.getText();
        set = validateAddress();

        if (set == false) {
            myaddress.requestFocusInWindow();
            this.addresswarning.setText("Adrress must be greater than 15 character");
            this.myaddress.setText("");
        } else {
            this.addresswarning.setText("");
            this.set = false;
        }

    }//GEN-LAST:event_mypincodeFocusGained

    private void myaddressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_myaddressFocusGained
        // TODO add your handling code here:

        password = new String(mypassword.getPassword());
        set = validatePassword();

        if (set == false) {
            mypassword.requestFocusInWindow();
            this.passwordwarning.setText("password less than 15 charactor");
            this.mypassword.setText("");
        } else {
            this.passwordwarning.setText("");
            this.set = false;
        }
    }//GEN-LAST:event_myaddressFocusGained

    private void mypasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mypasswordFocusGained
        // TODO add your handling code here

        this.mobile = mymobile.getText();
        set = validateMobile();
        if (set == false) {
            mymobile.requestFocusInWindow();
            this.mobilewarning.setText("Wrong mobile number");
            this.mymobile.setText("");
        } else {
            this.mobilewarning.setText("");
            try {
                set = validateMobileNumberRegistered();
            } catch (RemoteException ex) {
                Logger.getLogger(NewCustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (set == true) {
                mymobile.requestFocusInWindow();
                this.mobilewarning.setText("mobile number already exist.");
                this.mymobile.setText("");
            } else {
                this.set = false;
                this.mobilewarning.setText("");
            }
        }

    }//GEN-LAST:event_mypasswordFocusGained

    private void myaadharFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_myaadharFocusGained
        // TODO add your handling code here:
        this.type = "";
        current.setSelected(false);
        saving.setSelected(false);

        this.pincode = mypincode.getText();
        set = validatePincode();
        if (set == false) {
            mypincode.requestFocusInWindow();
            this.pincodewarning.setText("Wrong pincode");
            this.mypincode.setText("");
        } else {
            this.pincodewarning.setText("");
//            this.set=false;
        }
        if (set == true) {
            String d = "";
//            dob=new JDateChooser();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            d = df.format(mydob.getDate());
//            this.aadharwarning.setText(d);
            try {
                dob = df.parse(d);
            } catch (ParseException ex) {
                Logger.getLogger(NewCustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
//            System.out.println(df.format(dob));
        }
    }//GEN-LAST:event_myaadharFocusGained

    private void savingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savingActionPerformed
        // TODO add your handling code here:
        this.type = "saving";
        current.setSelected(false);

        this.aadhar = myaadhar.getText();
        set = validateAadhar();
        if (set == false) {
            myaadhar.requestFocusInWindow();
            this.aadharwarning.setText("Wrong aadhar number");
            this.myaadhar.setText("");
        } else {
            this.aadharwarning.setText("");
//            this.set=false;
        }
    }//GEN-LAST:event_savingActionPerformed

    private void currentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentActionPerformed
        // TODO add your handling code here:
        this.type = "current";
        saving.setSelected(false);

        this.aadhar = myaadhar.getText();
        set = validateAadhar();
        if (set == false) {
            myaadhar.requestFocusInWindow();
            this.aadharwarning.setText("Wrong aadhar number");
            this.myaadhar.setText("");
        } else {
            this.aadharwarning.setText("");
//            this.set=false;
        }
    }//GEN-LAST:event_currentActionPerformed

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        // TODO add your handling code here:
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        set = validateType();
        if (set == false) {
            this.aadharwarning.setText("");
//                saving.requestFocusInWindow();
            this.accounttypewarning.setText("Select account type.");
//                saving.setSelected(true);
        } else {
            this.accounttypewarning.setText("");
            try {
                set = validateAadharRegistered();
            } catch (RemoteException ex) {
                Logger.getLogger(NewCustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (set == true) {
                myaadhar.requestFocusInWindow();
                this.aadharwarning.setText("aadhar number already exist.");
                this.myaadhar.setText("");
            } else {
                this.set = false;
                this.aadharwarning.setText("");

                ok.setEnabled(false);
                ok.setText("Please wait...");

                int input = JOptionPane.showConfirmDialog(null, "Register now?");
                if (input == 0) {

                    String insertQuery = "INSERT INTO `newcustomer` (`Email`, `name`, `mobile`, `password`, `address`, `pincode`, `dob`, `aadhar`, `accounttype`) VALUES ('" + email + "','" + name + "','" + mobile + "','" + password + "','" + address + "','" + pincode + "','" + df.format(dob) + "','" + aadhar + "','" + type + "');";

                    boolean result = false;
                    result = Validateotp();
                    if (result == true) {
                        try {
                            set = insertInNewCustomer(insertQuery);
                        } catch (RemoteException ex) {
                            Logger.getLogger(NewCustomer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (set == true) {
                            JOptionPane.showMessageDialog(this, "Request has been sent to bank succesfully. Once they will validate you will recieve a mail.");
                            this.setVisible(false);
                            login l = new login();
                            l.setVisible(true);

                        } else {
                            JOptionPane.showMessageDialog(this, "Registered Failed.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Registered Failed.");
                        this.setVisible(false);
                        login l = new login();
                        l.setVisible(true);
                    }
                }

            }
        }


    }//GEN-LAST:event_okActionPerformed

    private void myaddressPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_myaddressPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_myaddressPropertyChange

    private void myemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_myemailActionPerformed

    private void mynameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mynameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mynameActionPerformed

    private void mypasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mypasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mypasswordActionPerformed

    private void mynameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mynameFocusGained
        // TODO add your handling code here:
        this.email = myemail.getText();
        set = validateEmail();
        if (set == false) {
            myemail.requestFocusInWindow();
            this.emailwarning.setText("Wrong Email.");
            this.myemail.setText("");
        } else {
            this.emailwarning.setText("");
//            this.set = false;
            try {
                set = validateEmailNumberRegistered();
            } catch (RemoteException ex) {
                Logger.getLogger(NewCustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (set == true) {
                myemail.requestFocusInWindow();
                this.emailwarning.setText("Email address already exist.");
                this.myemail.setText("");
            } else {
                this.set = false;
                this.emailwarning.setText("");
            }
        }
    }//GEN-LAST:event_mynameFocusGained

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
            java.util.logging.Logger.getLogger(NewCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new NewCustomer().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aadharwarning;
    private javax.swing.JLabel accounttypewarning;
    private javax.swing.JLabel addresswarning;
    private javax.swing.JButton back;
    private javax.swing.JRadioButton current;
    private javax.swing.JLabel emailwarning;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mobilewarning;
    private javax.swing.JTextField myaadhar;
    private javax.swing.JTextArea myaddress;
    private com.toedter.calendar.JDateChooser mydob;
    private javax.swing.JTextField myemail;
    private javax.swing.JTextField mymobile;
    private javax.swing.JTextField myname;
    private javax.swing.JPasswordField mypassword;
    private javax.swing.JTextField mypincode;
    private javax.swing.JLabel namewarning;
    private javax.swing.JButton ok;
    private javax.swing.JLabel passwordwarning;
    private javax.swing.JLabel pincodewarning;
    private javax.swing.JRadioButton saving;
    // End of variables declaration//GEN-END:variables
}
