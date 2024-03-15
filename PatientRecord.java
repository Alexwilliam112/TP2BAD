import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

class DocNumberGenerator {
    
    private static String prefix = "PRI";
    private static int startingNumber = 110001;
    private static int currentNumber = startingNumber;
    
    public static String generateDocNum() {
        String docNum = prefix + currentNumber;
        currentNumber++;
        return docNum;
    }
}

public class PatientRecord extends JFrame implements ActionListener {

    //test var.
    int ButtonTester = 0;
    String DocStatus1 = "NEW DOCUMENT";
    String DocStatus2 = "APPROVED";
    String DocStatus3 = "POSTED";

    private DefaultTableModel model;
    private JPanel panel;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField txtName, txtAddress, txtNIK, txtDOB;
    @SuppressWarnings("unused")
    private JComboBox<String> cmbDocType;

    
    public PatientRecord() {
        setTitle("CLINIC MANAGEMENT SYSTEM");
        setSize(1540, 800);
        
        JLabel TITLE = new JLabel("PATIENT RECORDS              ");
        TITLE.setFont(new Font("Arial", Font.BOLD, 20));
        TITLE.setHorizontalAlignment(SwingConstants.CENTER);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());


        //table setup
        model = new DefaultTableModel();
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        scrollPane = new JScrollPane(table);
        
        //column setups
        model.addColumn("Document Number");
        model.addColumn("Doc. Status");
        model.addColumn("Patient Name");
        model.addColumn("NIK");
        model.addColumn("Date of Birth");
        model.addColumn("Address");
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(5);//doc.number
        columnModel.getColumn(1).setPreferredWidth(5);//doc.type
        columnModel.getColumn(2).setPreferredWidth(50);//patient name
        columnModel.getColumn(3).setPreferredWidth(50);//NIK
        columnModel.getColumn(4).setPreferredWidth(50);//DOB
        columnModel.getColumn(5).setPreferredWidth(350);//address

        //textfield PATIENT NAME (1)
        txtName = new JTextField(20);
        txtName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (txtName.getText().length() >= 20 ||
                !(Character.isLetter(c)) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE && c != KeyEvent.VK_SPACE) {
                    e.consume();
                }
            }
        });
        //validation for empty field
        

        //textfield ADDRESS (2)
        txtAddress = new JTextField(50);
        txtAddress.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = txtAddress.getText();
                if (Character.isLowerCase(c)) {
                    e.setKeyChar(Character.toUpperCase(c));
                }
                if (text.length() >= 50) {
                    e.consume();
                }
            }
        });

        //textfield NIK (3)
        txtNIK = new JTextField(15);
        txtNIK.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                String text = txtNIK.getText();
                if (text.length() >= 15 ||
                !(Character.isDigit(c)) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE){
                    e.consume();
                }
            }
        });

        //textfield DOB (4)
        txtDOB = new JTextField(8);
        txtDOB.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                //set allowchars
                if (!(Character.isDigit(c) || Character.isLetter(c) || c == '-') && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    return;
                }
                String text = txtDOB.getText();
                //auto uppercase
                if (Character.isLowerCase(c)) {
                    e.setKeyChar(Character.toUpperCase(c));
                }
                //text length val.
                if (text.length() >= 11) {
                    e.consume();
                    return;
                } else if (text.length() < 4 && Character.isLetter(c) || text.length() < 4 && c == '-') {
                    e.consume();
                } else if (text.length() == 4 && c != '-' || text.length() == 8 && c != '-') {
                    e.consume();
                } else if (text.length() > 4 && Character.isDigit(c) && text.length() < 8 || text.length() > 4 && c == '-'
                && text.length() < 8 ) {
                    e.consume();
                } else if (text.length() > 8 && Character.isLetter(c) || text.length() > 8 && c == '-') {
                    e.consume();
                }
            }
        });


        //JPANEL FOR TEXTFIELD
        JPanel textPanel = new JPanel(new FlowLayout());

        //textfield add
        textPanel.add(new JLabel("Patient Name: "));
        textPanel.add(txtName);

        textPanel.add(new JLabel("Patient NIK: "));
        textPanel.add(txtNIK);

        textPanel.add(new JLabel("Date of Birth (YYYY-MMM-DD): "));
        textPanel.add(txtDOB);

        textPanel.add(new JLabel("Patient Address: "));
        textPanel.add(txtAddress);

        //buttons create and add
        JPanel titlepanel = new JPanel(new FlowLayout());
        titlepanel.add(TITLE);

        //JPANEL FOR buttons
        JPanel actionPanel = new JPanel(new FlowLayout());

        JButton btnSIM = new JButton("SIMULATION");
        btnSIM.addActionListener(this);
        actionPanel.add(btnSIM);

        JButton btnAdd = new JButton("NEW PATIENT");
        btnAdd.addActionListener(this);
        actionPanel.add(btnAdd);
        
        JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.addActionListener(this);
        actionPanel.add(btnUpdate);
        
        JButton btnDelete = new JButton("DELETE");
        btnDelete.addActionListener(this);
        actionPanel.add(btnDelete);
        
        JButton btnPrev = new JButton("Previous Row");
        btnPrev.addActionListener(this);
        actionPanel.add(btnPrev);
        
        JButton btnNext = new JButton("Next Row");
        btnNext.addActionListener(this);
        actionPanel.add(btnNext);
        
        JButton btnApprove = new JButton("APPROVE");
        btnApprove.addActionListener(this);
        actionPanel.add(btnApprove);

        JButton btnPOST = new JButton("POST");
        btnPOST.addActionListener(this);
        actionPanel.add(btnPOST);
        
        JButton btnExit = new JButton("EXIT");
        btnExit.addActionListener(this);
        actionPanel.add(btnExit);

        //borderlayout components
        setLayout(new BorderLayout());
        add(titlepanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private int selectedRow = -1; //row selection counter
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("NEW PATIENT")) {
            {
                Object[] message = {
                    "Patient Name: ", txtName,
                    "Patient NIK: ", txtNIK,
                    "Date of Birth (YYYY-MMM-DD): ", txtDOB,
                    "Patien Address: ", txtAddress
                };
                txtName.setText("");
                txtNIK.setText("");
                txtDOB.setText("");
                txtAddress.setText("");
                //summon dialogbox
                int newRecord = JOptionPane.showConfirmDialog(null, message, "NEW Patient Record", JOptionPane.OK_CANCEL_OPTION);
                if (newRecord == JOptionPane.OK_OPTION) {
                    //textfield validation empty and length
                    String dobformat = txtDOB.getText();
                    String nikformat = txtNIK.getText();
                    if (txtName.getText().isEmpty() || nikformat.length() < 15 || dobformat.length() < 11 || txtAddress.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields according to set format.", "INVALID DATA", JOptionPane.ERROR_MESSAGE);
                    return;
                    }
                    String DocNumber = DocNumberGenerator.generateDocNum();
                    String name = txtName.getText();
                    String nik = txtNIK.getText();
                    String dob = txtDOB.getText();
                    String address = txtAddress.getText();
                    //NIK VALIDATION-new
                    String newNik = nik;
                    boolean isUniqueNik = true;
                    for (int row = 0; row < model.getRowCount(); row++) {
                        String nikValue = (String) model.getValueAt(row, 3);
                        if (nikValue.equals(newNik)) {
                            isUniqueNik = false;
                            break;
                        }
                    }
                    if (isUniqueNik) {
                        //no dup, add to table.
                        model.addRow(new Object[]{DocNumber, DocStatus1, name, newNik, dob, address});
                    } else {
                        //dup found, reject.
                        JOptionPane.showMessageDialog(null,
                        "Patient NIK already registered in the system.",
                        "FAILED TO CREATE DOCUMENT", JOptionPane.ERROR_MESSAGE);
                    }
                    txtName.setText("");
                    txtNIK.setText("");
                    txtDOB.setText("");
                    txtAddress.setText("");
                }
            }
        }

        else if (e.getActionCommand().equals("UPDATE")) {
            if (selectedRow >= 0) {
                Object[] message = {
                    "Patient Name: ", txtName,
                    "Patient NIK: ", txtNIK,
                    "Date of Birth (YYYY-MMM-DD): ", txtDOB,
                    "Patien Address: ", txtAddress
                };
                //summon dialogbox
                int update = JOptionPane.showConfirmDialog(null, message, "UPDATE Patient Record", JOptionPane.OK_CANCEL_OPTION);
                if (update == JOptionPane.OK_OPTION) {
                    //textfield validation empty and length
                    String dobformat = txtDOB.getText();
                    String nikformat = txtNIK.getText();
                    if (txtName.getText().isEmpty() || nikformat.length() < 15 || dobformat.length() < 11 || txtAddress.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields according to set format.", "INVALID DATA", JOptionPane.ERROR_MESSAGE);
                    return;
                    }
                    String name = txtName.getText();
                    String nik = txtNIK.getText();
                    String dob = txtDOB.getText();
                    String address = txtAddress.getText();
                    //NIK VALIDATION-update
                    String newNik = nik;
                    String currentNIK = model.getValueAt(selectedRow, 3).toString();
                    boolean isUniqueNik = true;
                    for (int row = 0; row < model.getRowCount(); row++) {
                        String nikValue = (String) model.getValueAt(row, 3);
                            if (nikValue.equals(newNik) && nikValue != currentNIK) {
                                isUniqueNik = false;
                                break;
                        }
                    }
                    if (isUniqueNik) {
                        //no dup, modify and save.
                        model.setValueAt(DocStatus1, selectedRow, 1);
                        model.setValueAt(name, selectedRow, 2);
                        model.setValueAt(nik, selectedRow, 3);
                        model.setValueAt(dob, selectedRow, 4);
                        model.setValueAt(address, selectedRow, 5);
                    } else {
                        //dup found, reject.
                        JOptionPane.showMessageDialog(null,
                        "Patient NIK already registered in the system.",
                        "FAILED TO UPDATE DOCUMENT", JOptionPane.ERROR_MESSAGE);
                    }
                    txtName.setText("");
                    txtNIK.setText("");
                    txtDOB.setText("");
                    txtAddress.setText("");
                    table.clearSelection();
                    table.addRowSelectionInterval(0, 0);
                    selectedRow = -1;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to UPDATE.", "No Selection Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getActionCommand().equals("DELETE")) {
            if (selectedRow >= 0) {
                String deletenum = model.getValueAt(selectedRow, 0).toString();
            int confirmation = JOptionPane.showConfirmDialog(null, "Confirm Delete Document Number : " + deletenum,
                "DELETE Record Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    model.removeRow(selectedRow);
                    selectedRow = -1;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to DELETE.", "No Selection Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getActionCommand().equals("Previous Row")) {
                if (selectedRow > 0) {
                    selectedRow--;
                    table.setRowSelectionInterval(selectedRow,selectedRow);
                    txtName.setText(table.getValueAt(selectedRow, 2).toString());
                    txtNIK.setText(table.getValueAt(selectedRow, 3).toString());
                    txtDOB.setText(table.getValueAt(selectedRow, 4).toString());
                    txtAddress.setText(table.getValueAt(selectedRow, 5).toString());
                }
        }

        else if (e.getActionCommand().equals("Next Row")) {
            if (selectedRow < table.getRowCount() -1) {
                selectedRow++;
                table.setRowSelectionInterval(selectedRow, selectedRow);;
                table.setRowSelectionInterval(selectedRow,selectedRow);
                txtName.setText(table.getValueAt(selectedRow, 2).toString());
                txtNIK.setText(table.getValueAt(selectedRow, 3).toString());
                txtDOB.setText(table.getValueAt(selectedRow, 4).toString());
                txtAddress.setText(table.getValueAt(selectedRow, 5).toString());
            }
        }

        else if (e.getActionCommand().equals("APPROVE")) {
            if (selectedRow >= 0) {
                String status = model.getValueAt(selectedRow, 1).toString();
                if (status.equals(DocStatus1)) {
                    model.setValueAt(DocStatus2, selectedRow, 1);
                    JOptionPane.showMessageDialog(null, "Record Approved", "ACTION SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR : Document Already Approved", "ACTION FAILED", JOptionPane.ERROR_MESSAGE);
                };
            } else {
                JOptionPane.showMessageDialog(null, "Select a Patient Record.", "No Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        else if (e.getActionCommand().equals("POST")) {
            if (selectedRow >= 0) {
                String status = model.getValueAt(selectedRow, 1).toString();
                if (status.equals(DocStatus2)) {
                    model.setValueAt(DocStatus3, selectedRow, 1);
                    JOptionPane.showMessageDialog(null, "Record Posted", "ACTION SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR : Document is either not yet Approved or already Posted", "ACTION FAILED", JOptionPane.ERROR_MESSAGE);
                };
            } else {
                JOptionPane.showMessageDialog(null, "Select a Patient Record.", "No Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        else if (e.getActionCommand().equals("EXIT")) {
            {
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
                "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }

        else if (e.getActionCommand().equals("SIMULATION")) {
            String DocNumber = DocNumberGenerator.generateDocNum();
            model.addRow(new Object[]{DocNumber + "0S", DocStatus3, "Jack Daniels", "785612868541458", "1998-DEC-04", "Jalan Sutera Jelita IV no.22"});
            model.addRow(new Object[]{DocNumber + "1S", DocStatus3, "Johnie Anderson", "626780423201819", "1992-FEB-26", "Jalan Buntu no.33, Kebayoran Lama"});
            model.addRow(new Object[]{DocNumber + "2S", DocStatus3, "Jenna Tanya", "249051845821829", "2000-JAN-31", "Jalan gading boulevard"});
            model.addRow(new Object[]{DocNumber + "3S", DocStatus3, "Jaime Lannister", "424885523621731", "1990-AUG-22", "Jalan KEPALA gading III"});
            model.addRow(new Object[]{DocNumber + "4S", DocStatus3, "Andrea Wijaya", "381544581327293", "2004-FEB-13", "Jalan Kelapa gading utara no.44"});
            model.addRow(new Object[]{DocNumber + "5S", DocStatus2, "Freddie", "193439490721828", "2002-NOV-10", "Jalan seha blok 2 no.3a"});
            model.addRow(new Object[]{DocNumber + "6S", DocStatus2, "Darrel Harlim", "145650688960872", "1997-OCT-31", "Jalan gading boulevard"});
            model.addRow(new Object[]{DocNumber + "7S", DocStatus2, "Viola Gracia", "137531370725049", "2001-JUL-17", "Jalan meruya barat 4 no.99b"});
            JOptionPane.showMessageDialog(null, "System has Generated 8 Simulation Data.", "ACTION SUCCESS", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new PatientRecord();
    }
}
