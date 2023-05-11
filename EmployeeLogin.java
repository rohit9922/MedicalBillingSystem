/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package medicalbs;

import com.itextpdf.text.BadElementException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rohit157
 */
public class EmployeeLogin extends javax.swing.JFrame {

    public int billsrno = 1;
    /**
     * Creates new form EmployeeLogin
     */
    DefaultTableModel model;
    DefaultTableModel model1;
    
    public int grandtotal = 0;

    public EmployeeLogin() {
        initComponents();
    }

    public void createpdf() throws FileNotFoundException, BadElementException, IOException {
        Document document = new Document(new Rectangle(PrintPanel.getWidth(), PrintPanel.getHeight()), 0, 0, 0, 0);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Medical Bill.pdf"));
            document.open();
            // create a PDF content byte that we'll draw onto
            PdfContentByte cb = writer.getDirectContent();
            // create a BufferedImage that we'll draw the panel onto
            BufferedImage bi = new BufferedImage(PrintPanel.getWidth(), PrintPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bi.createGraphics();
            PrintPanel.print(g2d);
            g2d.dispose();
            // draw the BufferedImage onto the PDF content byte
            Image image = Image.getInstance(bi, null);
            cb.addImage(image, PrintPanel.getWidth(), 0, 0, PrintPanel.getHeight(), 0, 0);
        } catch (DocumentException e) {
            System.out.println(e);
        } 
    }

    public String setEmpname() {
        Login lg = new Login();
        String username = lg.username;
        return username;
    }

    public String setDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public String setTime() {

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String strTime = dateFormat.format(date);

        return strTime;

    }

    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) StockTable.getModel();
        model.setRowCount(0);
    }

    public void avilablestocktable() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from medicin_stock");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int med_ID = rs.getInt("id");
                String med_Name = rs.getString("med_name");
                int med_Quantity = rs.getInt("med_stock");
                int med_Price = rs.getInt("med_price");
                Object[] obj = {med_ID, med_Name, med_Quantity, med_Price};
                model1 = (DefaultTableModel) StockTable.getModel();
                model1.addRow(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addToBill() {
if (billsrno <= 15){
        int medid = Integer.parseInt(IdField.getText().trim());

        int medQuantity = Integer.parseInt(QuantityField.getText().trim());
        String setmed = null;
        String setqty=null;
        String setprice=null;
        String setrs=null;
        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from medicin_stock where id=" + medid);

            while (rs.next()) {
                int med_ID = rs.getInt("id");
                String med_Name = rs.getString("med_name");
                int med_Quantity = rs.getInt("med_stock");
                int med_Price = rs.getInt("med_price");
                if (med_Quantity >= medQuantity) {
                    int totalrs = medQuantity * med_Price;
                    grandtotal += totalrs;
                    setmed = med_Name;
                    setqty=Integer.toString(medQuantity);
                    setprice=Integer.toString(med_Price);
                    setrs=Integer.toString(totalrs);
                    Object[] obj = {med_ID, med_Name, medQuantity, med_Price, totalrs};
                    model = (DefaultTableModel) billTable.getModel();
                    model.addRow(obj);

                    PreparedStatement ps1 = null;
                    ps1 = con.prepareStatement("Update medicin_stock set med_stock=? where id='" + med_ID + "'");

                    ps1.setInt(1, med_Quantity - medQuantity);

                    ps1.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (billsrno == 1) {
            med1.setText(setmed);
            med1_1.setText(setqty);
            med1_2.setText(setprice);
            med1_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 2) {
            med2.setText(setmed);
            med2_1.setText(setqty);
            med2_2.setText(setprice);
            med2_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 3) {
            med3.setText(setmed);
            med3_1.setText(setqty);
            med3_2.setText(setprice);
            med3_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 4) {
            med4.setText(setmed);
            med4_1.setText(setqty);
            med4_2.setText(setprice);
            med4_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 5) {
            med5.setText(setmed);
            med5_1.setText(setqty);
            med5_2.setText(setprice);
            med5_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 6) {
            med6.setText(setmed);
            med6_1.setText(setqty);
            med6_2.setText(setprice);
            med6_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 7) {
            med7.setText(setmed);
            med7_1.setText(setqty);
            med7_2.setText(setprice);
            med7_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 8) {
            med8.setText(setmed);
            med8_1.setText(setqty);
            med8_2.setText(setprice);
            med8_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 9) {
            med9.setText(setmed);
            med9_1.setText(setqty);
            med9_2.setText(setprice);
            med9_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 10) {
            med10.setText(setmed);
            med10_1.setText(setqty);
            med10_2.setText(setprice);
            med10_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 11) {
            med11.setText(setmed);
            med11_1.setText(setqty);
            med11_2.setText(setprice);
            med11_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 12) {
            med12.setText(setmed);
            med12_1.setText(setqty);
            med12_2.setText(setprice);
            med12_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 13) {
            med13.setText(setmed);
            med13_1.setText(setqty);
            med13_2.setText(setprice);
            med13_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 14) {
            med14.setText(setmed);
            med14_1.setText(setqty);
            med14_2.setText(setprice);
            med14_3.setText(setrs);
            billsrno++;
            setmed = null;
        } else if (billsrno == 15) {
            med15.setText(setmed);
            med15_1.setText(setqty);
            med15_2.setText(setprice);
            med15_3.setText(setrs);
            billsrno++;
            setmed = null;
        }
    }
else{
     JOptionPane.showMessageDialog(this, " Bill is Full Create another Bill ");
}
    }


    public boolean checkProductStock(String productName, String productQuantity) {

        try {
            int medid = Integer.parseInt(productName);
            int product_quantity = Integer.parseInt(productQuantity);
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select med_stock from medicin_stock where id='" + medid + "'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int chectQuantity = rs.getInt("med_stock");
                if (product_quantity > chectQuantity) {
                    return false;
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkQuantity(String productQuantity) {
        int len = productQuantity.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isDigit(productQuantity.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        QuantityField = new javax.swing.JTextField();
        IdField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        AddToBillbtn = new javax.swing.JButton();
        Refreshbtn = new javax.swing.JButton();
        Clearbtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        StockTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TimeLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        DateLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        Logoutbtn = new javax.swing.JButton();
        Printbtn = new javax.swing.JButton();
        GrandTotalbtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        GrandTotalLabel = new javax.swing.JLabel();
        clearbillbtn = new javax.swing.JButton();
        PrintPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        billPricejLabel = new javax.swing.JLabel();
        med2 = new javax.swing.JLabel();
        med5 = new javax.swing.JLabel();
        med4 = new javax.swing.JLabel();
        med6 = new javax.swing.JLabel();
        med7 = new javax.swing.JLabel();
        med3 = new javax.swing.JLabel();
        med8 = new javax.swing.JLabel();
        med9 = new javax.swing.JLabel();
        med10 = new javax.swing.JLabel();
        med11 = new javax.swing.JLabel();
        med12 = new javax.swing.JLabel();
        med13 = new javax.swing.JLabel();
        med14 = new javax.swing.JLabel();
        med15 = new javax.swing.JLabel();
        med1 = new javax.swing.JLabel();
        med1_1 = new javax.swing.JLabel();
        med2_1 = new javax.swing.JLabel();
        med3_1 = new javax.swing.JLabel();
        med4_1 = new javax.swing.JLabel();
        med5_1 = new javax.swing.JLabel();
        med6_1 = new javax.swing.JLabel();
        med7_1 = new javax.swing.JLabel();
        med8_1 = new javax.swing.JLabel();
        med9_1 = new javax.swing.JLabel();
        med10_1 = new javax.swing.JLabel();
        med12_1 = new javax.swing.JLabel();
        med13_1 = new javax.swing.JLabel();
        med14_1 = new javax.swing.JLabel();
        med15_1 = new javax.swing.JLabel();
        med11_1 = new javax.swing.JLabel();
        med15_2 = new javax.swing.JLabel();
        med14_2 = new javax.swing.JLabel();
        med13_2 = new javax.swing.JLabel();
        med12_2 = new javax.swing.JLabel();
        med11_2 = new javax.swing.JLabel();
        med10_2 = new javax.swing.JLabel();
        med9_2 = new javax.swing.JLabel();
        med8_2 = new javax.swing.JLabel();
        med7_2 = new javax.swing.JLabel();
        med6_2 = new javax.swing.JLabel();
        med5_2 = new javax.swing.JLabel();
        med4_2 = new javax.swing.JLabel();
        med3_2 = new javax.swing.JLabel();
        med2_2 = new javax.swing.JLabel();
        med1_2 = new javax.swing.JLabel();
        med1_3 = new javax.swing.JLabel();
        med2_3 = new javax.swing.JLabel();
        med3_3 = new javax.swing.JLabel();
        med4_3 = new javax.swing.JLabel();
        med5_3 = new javax.swing.JLabel();
        med6_3 = new javax.swing.JLabel();
        med7_3 = new javax.swing.JLabel();
        med8_3 = new javax.swing.JLabel();
        med9_3 = new javax.swing.JLabel();
        med10_3 = new javax.swing.JLabel();
        med11_3 = new javax.swing.JLabel();
        med12_3 = new javax.swing.JLabel();
        med13_3 = new javax.swing.JLabel();
        med14_3 = new javax.swing.JLabel();
        med15_3 = new javax.swing.JLabel();
        billPricejLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MedicalBS");
        setBackground(new java.awt.Color(0, 255, 255));
        setMinimumSize(new java.awt.Dimension(1580, 860));

        jPanel1.setBackground(new java.awt.Color(0, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 153));
        jLabel3.setText("ID");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 100, 30));

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 153));
        jLabel4.setText("QUANTITY");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 130, 30));

        QuantityField.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        QuantityField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        jPanel1.add(QuantityField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 460, 30));

        IdField.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        IdField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        jPanel1.add(IdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 460, 30));

        jLabel5.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 153));
        jLabel5.setText("AVAILABLE STOCK");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 310, 30));

        AddToBillbtn.setBackground(new java.awt.Color(255, 0, 153));
        AddToBillbtn.setFont(new java.awt.Font("Liberation Mono", 1, 24)); // NOI18N
        AddToBillbtn.setForeground(new java.awt.Color(255, 255, 255));
        AddToBillbtn.setText("Add To Bill");
        AddToBillbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AddToBillbtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AddToBillbtnMouseExited(evt);
            }
        });
        AddToBillbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddToBillbtnActionPerformed(evt);
            }
        });
        jPanel1.add(AddToBillbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 190, 40));

        Refreshbtn.setBackground(new java.awt.Color(255, 0, 153));
        Refreshbtn.setFont(new java.awt.Font("Liberation Mono", 1, 24)); // NOI18N
        Refreshbtn.setForeground(new java.awt.Color(255, 255, 255));
        Refreshbtn.setText("Refresh");
        Refreshbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RefreshbtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                RefreshbtnMouseExited(evt);
            }
        });
        Refreshbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshbtnActionPerformed(evt);
            }
        });
        jPanel1.add(Refreshbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 370, 190, 40));

        Clearbtn.setBackground(new java.awt.Color(255, 0, 153));
        Clearbtn.setFont(new java.awt.Font("Liberation Mono", 1, 24)); // NOI18N
        Clearbtn.setForeground(new java.awt.Color(255, 255, 255));
        Clearbtn.setText("Clear");
        Clearbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ClearbtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ClearbtnMouseExited(evt);
            }
        });
        Clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearbtnActionPerformed(evt);
            }
        });
        jPanel1.add(Clearbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 240, 190, 40));

        StockTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(102, 102, 102), null, null));
        StockTable.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        StockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRODUCT ID", "NAME", "QUANTITY", "PRICE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        StockTable.setAlignmentX(0.0F);
        StockTable.setName(""); // NOI18N
        jScrollPane1.setViewportView(StockTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 640, 370));

        jLabel6.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 153));
        jLabel6.setText("EMPLOYEE NAME :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 160, 290, -1));

        jLabel7.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 153));
        jLabel7.setText("BILLING COUNTER");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 290, -1));

        jLabel9.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 153));
        jLabel9.setText("Time :");
        jLabel9.setFocusable(false);
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel9.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 240, 100, 40));

        TimeLabel.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        TimeLabel.setFocusable(false);
        TimeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TimeLabel.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(TimeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 240, 340, 40));

        jLabel13.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 153));
        jLabel13.setText("Date :");
        jLabel13.setFocusable(false);
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel13.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 200, 100, 40));

        DateLabel.setFont(new java.awt.Font("Liberation Sans", 3, 30)); // NOI18N
        DateLabel.setText(setDate());
        jPanel1.add(DateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 200, 520, 40));

        userNameLabel.setFont(new java.awt.Font("Liberation Sans", 3, 30)); // NOI18N
        userNameLabel.setText(setEmpname());
        jPanel1.add(userNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 160, 340, 40));

        Logoutbtn.setBackground(new java.awt.Color(255, 0, 153));
        Logoutbtn.setFont(new java.awt.Font("Liberation Mono", 1, 24)); // NOI18N
        Logoutbtn.setForeground(new java.awt.Color(255, 255, 255));
        Logoutbtn.setText("Logout");
        Logoutbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LogoutbtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LogoutbtnMouseExited(evt);
            }
        });
        Logoutbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutbtnActionPerformed(evt);
            }
        });
        jPanel1.add(Logoutbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 810, 190, 40));

        Printbtn.setBackground(new java.awt.Color(255, 0, 153));
        Printbtn.setFont(new java.awt.Font("Liberation Mono", 1, 24)); // NOI18N
        Printbtn.setForeground(new java.awt.Color(255, 255, 255));
        Printbtn.setText("Print");
        Printbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PrintbtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PrintbtnMouseExited(evt);
            }
        });
        Printbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintbtnActionPerformed(evt);
            }
        });
        jPanel1.add(Printbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 790, 190, 40));

        GrandTotalbtn.setBackground(new java.awt.Color(255, 0, 153));
        GrandTotalbtn.setFont(new java.awt.Font("Liberation Mono", 1, 24)); // NOI18N
        GrandTotalbtn.setForeground(new java.awt.Color(255, 255, 255));
        GrandTotalbtn.setText("Grand Total");
        GrandTotalbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                GrandTotalbtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                GrandTotalbtnMouseExited(evt);
            }
        });
        GrandTotalbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrandTotalbtnActionPerformed(evt);
            }
        });
        jPanel1.add(GrandTotalbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 720, 190, 40));

        billTable.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        billTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "QUANTITY", "PRICE", "TOTAL Rs"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(billTable);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 290, 750, 420));

        jLabel12.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        jLabel12.setText("Grand Total:");
        jLabel12.setFocusable(false);
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel12.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(282, 488, 195, -1));

        jLabel14.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 153));
        jLabel14.setText("BILL");
        jLabel14.setFocusable(false);
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel14.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 10, 290, -1));

        GrandTotalLabel.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        GrandTotalLabel.setFocusable(false);
        GrandTotalLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        GrandTotalLabel.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(GrandTotalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 720, 220, 40));

        clearbillbtn.setBackground(new java.awt.Color(255, 0, 153));
        clearbillbtn.setFont(new java.awt.Font("Liberation Mono", 1, 24)); // NOI18N
        clearbillbtn.setForeground(new java.awt.Color(255, 255, 255));
        clearbillbtn.setText("CREATE A NEW BILL");
        clearbillbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearbillbtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearbillbtnMouseExited(evt);
            }
        });
        clearbillbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbillbtnActionPerformed(evt);
            }
        });
        jPanel1.add(clearbillbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 790, 270, 40));

        PrintPanel.setBackground(new java.awt.Color(255, 255, 255));
        PrintPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Yrsa SemiBold", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 153));
        jLabel8.setText("--------------------------------");
        PrintPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 920, -1));

        jLabel11.setFont(new java.awt.Font("Yrsa SemiBold", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 153));
        jLabel11.setText("--------------------------------");
        PrintPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 920, -1));

        jLabel15.setFont(new java.awt.Font("Yrsa SemiBold", 1, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 153));
        jLabel15.setText("PRINT RECEIPT");
        PrintPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 290, -1));

        jLabel16.setFont(new java.awt.Font("Yrsa SemiBold", 1, 24)); // NOI18N
        jLabel16.setText("NAME                   QTY      PRICE   Rs.");
        PrintPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 370, -1));

        billPricejLabel.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        PrintPanel.add(billPricejLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 650, 180, 40));

        med2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 150, 20));

        med5.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 150, 20));

        med4.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 150, 20));

        med6.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 150, 20));

        med7.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 150, 20));

        med3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 150, 20));

        med8.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 150, 20));

        med9.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 150, 20));

        med10.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 150, 20));

        med11.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 150, 20));

        med12.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 150, 20));

        med13.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 150, 20));

        med14.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 150, 20));

        med15.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 150, 20));

        med1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 150, 20));

        med1_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med1_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med1_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 60, 20));

        med2_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med2_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med2_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 60, 20));

        med3_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med3_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med3_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 60, 20));

        med4_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med4_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med4_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 60, 20));

        med5_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med5_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med5_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 60, 20));

        med6_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med6_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med6_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 60, 20));

        med7_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med7_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med7_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 330, 60, 20));

        med8_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med8_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med8_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 60, 20));

        med9_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med9_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med9_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, 60, 20));

        med10_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med10_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med10_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 420, 60, 20));

        med12_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med12_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med12_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 480, 60, 20));

        med13_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med13_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med13_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 510, 60, 20));

        med14_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med14_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med14_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 540, 60, 20));

        med15_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med15_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med15_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 570, 60, 20));

        med11_1.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med11_1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med11_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 450, 60, 20));

        med15_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med15_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med15_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 570, 60, 20));

        med14_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med14_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med14_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 540, 60, 20));

        med13_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med13_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med13_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 510, 60, 20));

        med12_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med12_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med12_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 480, 60, 20));

        med11_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med11_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med11_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 450, 60, 20));

        med10_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med10_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med10_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 60, 20));

        med9_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med9_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med9_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, 60, 20));

        med8_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med8_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med8_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 60, 20));

        med7_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med7_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med7_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, 60, 20));

        med6_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med6_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med6_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 60, 20));

        med5_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med5_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med5_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 60, 20));

        med4_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med4_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med4_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 60, 20));

        med3_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med3_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med3_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, 60, 20));

        med2_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med2_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med2_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, 60, 20));

        med1_2.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med1_2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med1_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 60, 20));

        med1_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med1_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med1_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, 60, 20));

        med2_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med2_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med2_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, 60, 20));

        med3_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med3_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med3_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 60, 20));

        med4_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med4_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med4_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 240, 60, 20));

        med5_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med5_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med5_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 270, 60, 20));

        med6_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med6_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med6_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, 60, 20));

        med7_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med7_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med7_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, 60, 20));

        med8_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med8_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med8_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 360, 60, 20));

        med9_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med9_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med9_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, 60, 20));

        med10_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med10_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med10_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 420, 60, 20));

        med11_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med11_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med11_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 450, 60, 20));

        med12_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med12_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med12_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 480, 60, 20));

        med13_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med13_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med13_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 510, 60, 20));

        med14_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med14_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med14_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 540, 60, 20));

        med15_3.setFont(new java.awt.Font("Yrsa SemiBold", 1, 18)); // NOI18N
        med15_3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));
        PrintPanel.add(med15_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 570, 60, 20));

        billPricejLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 30)); // NOI18N
        billPricejLabel1.setText("Total Bill Rs :");
        PrintPanel.add(billPricejLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 650, -1, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PrintPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PrintPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddToBillbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddToBillbtnMouseEntered
        Color clr = new Color(255, 0, 51);
        AddToBillbtn.setBackground(clr);
    }//GEN-LAST:event_AddToBillbtnMouseEntered

    private void AddToBillbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddToBillbtnMouseExited
        Color clr = new Color(255, 0, 153);
        AddToBillbtn.setBackground(clr);
    }//GEN-LAST:event_AddToBillbtnMouseExited

    private void AddToBillbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToBillbtnActionPerformed
        String productName = IdField.getText();
        String productQuqntity = QuantityField.getText();
        if (productName.trim().equals("") && productQuqntity.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Medicin Name And Quantity");
        } else if (productName.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Medicin ID ");
        } else if (productQuqntity.trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Medicin Quantity ");
        } else {
            if (!checkQuantity(productQuqntity)) {
                JOptionPane.showMessageDialog(this, "Enter Medicin Quantity in Number Only ");
            } else {
                if (!checkProductStock(productName, productQuqntity)) {
                    JOptionPane.showMessageDialog(this, " Medicin Quantity is not available ");
                } else {
                    addToBill();

                }
            }
        }

    }//GEN-LAST:event_AddToBillbtnActionPerformed

    private void RefreshbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefreshbtnMouseEntered
        Color clr = new Color(255, 0, 51);
        Refreshbtn.setBackground(clr);
    }//GEN-LAST:event_RefreshbtnMouseEntered

    private void RefreshbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefreshbtnMouseExited
        Color clr = new Color(255, 0, 153);
        Refreshbtn.setBackground(clr);
    }//GEN-LAST:event_RefreshbtnMouseExited

    private void RefreshbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshbtnActionPerformed
        clearTable();
        avilablestocktable();
        TimeLabel.setText(setTime());

    }//GEN-LAST:event_RefreshbtnActionPerformed

    private void ClearbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearbtnMouseEntered
        Color clr = new Color(255, 0, 51);
        Clearbtn.setBackground(clr);
    }//GEN-LAST:event_ClearbtnMouseEntered

    private void ClearbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearbtnMouseExited
        Color clr = new Color(255, 0, 153);
        Clearbtn.setBackground(clr);
    }//GEN-LAST:event_ClearbtnMouseExited

    private void ClearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearbtnActionPerformed
        IdField.setText("");

        QuantityField.setText("");
    }//GEN-LAST:event_ClearbtnActionPerformed

    private void GrandTotalbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GrandTotalbtnMouseEntered
        Color clr = new Color(255, 0, 51);
        GrandTotalbtn.setBackground(clr);
    }//GEN-LAST:event_GrandTotalbtnMouseEntered

    private void GrandTotalbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GrandTotalbtnMouseExited
        Color clr = new Color(255, 0, 153);
        GrandTotalbtn.setBackground(clr);
    }//GEN-LAST:event_GrandTotalbtnMouseExited

    private void GrandTotalbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrandTotalbtnActionPerformed
        String gt = Integer.toString(grandtotal);
        gt = gt.concat(".00 Rs");
        GrandTotalLabel.setText(gt);
        billPricejLabel.setText(gt);
    }//GEN-LAST:event_GrandTotalbtnActionPerformed

    private void LogoutbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutbtnMouseEntered
        Color clr = new Color(255, 0, 51);
        Logoutbtn.setBackground(clr);
    }//GEN-LAST:event_LogoutbtnMouseEntered

    private void LogoutbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutbtnMouseExited
        Color clr = new Color(255, 0, 153);
        Logoutbtn.setBackground(clr);
    }//GEN-LAST:event_LogoutbtnMouseExited

    private void LogoutbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutbtnActionPerformed
        Login lg = new Login();
        lg.show();
        this.dispose();

    }//GEN-LAST:event_LogoutbtnActionPerformed

    private void PrintbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrintbtnMouseEntered
        Color clr = new Color(255, 0, 51);
        Printbtn.setBackground(clr);
    }//GEN-LAST:event_PrintbtnMouseEntered

    private void PrintbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrintbtnMouseExited
        Color clr = new Color(255, 0, 153);
        Printbtn.setBackground(clr);
    }//GEN-LAST:event_PrintbtnMouseExited

    private void PrintbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintbtnActionPerformed
        try {
                    PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print Data");

        job.setPrintable(new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum) {
                pf.setOrientation(PageFormat.LANDSCAPE);
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(0.47, 0.47);

                PrintPanel.print(g2);

                return Printable.PAGE_EXISTS;

            }
        });
        
        boolean ok = job.printDialog();
        if (ok) {
            try {

                job.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
createpdf();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmployeeLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException ex) {
            Logger.getLogger(EmployeeLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PrintbtnActionPerformed

    private void clearbillbtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearbillbtnMouseEntered
        Color clr = new Color(255, 0, 51);
       clearbillbtn.setBackground(clr);
    }//GEN-LAST:event_clearbillbtnMouseEntered

    private void clearbillbtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearbillbtnMouseExited
          Color clr = new Color(255, 0, 153);
        clearbillbtn.setBackground(clr);
    }//GEN-LAST:event_clearbillbtnMouseExited

    private void clearbillbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbillbtnActionPerformed

int rows = model.getRowCount(); 
for(int i = rows - 1; i >=0; i--)
{
   model.removeRow(i); 
}
            med1.setText("");
            med1_1.setText("");
            med1_2.setText("");
            med1_3.setText("");
            
            med2.setText("");
            med2_1.setText("");
            med2_2.setText("");
            med2_3.setText("");
            
            med3.setText("");
            med3_1.setText("");
            med3_2.setText("");
            med3_3.setText("");
            
            med4.setText("");
            med4_1.setText("");
            med4_2.setText("");
            med4_3.setText("");
            
            med5.setText("");
            med5_1.setText("");
            med5_2.setText("");
            med5_3.setText("");
            
            med6.setText("");
            med6_1.setText("");
            med6_2.setText("");
            med6_3.setText("");
            
            med7.setText("");
            med7_1.setText("");
            med7_2.setText("");
            med7_3.setText("");
            
            med8.setText("");
            med8_1.setText("");
            med8_2.setText("");
            med8_3.setText("");
            
            med9.setText("");
            med9_1.setText("");
            med9_2.setText("");
            med9_3.setText("");
            
            med10.setText("");
            med10_1.setText("");
            med10_2.setText("");
            med10_3.setText("");
            
            med11.setText("");
            med11_1.setText("");
            med11_2.setText("");
            med11_3.setText("");
            
            med12.setText("");
            med12_1.setText("");
            med12_2.setText("");
            med12_3.setText("");
            
            med13.setText("");
            med13_1.setText("");
            med13_2.setText("");
            med13_3.setText("");
            
            med14.setText("");
            med14_1.setText("");
            med14_2.setText("");
            med14_3.setText("");
            
            med15.setText("");
            med15_1.setText("");
            med15_2.setText("");
            med15_3.setText("");

    }//GEN-LAST:event_clearbillbtnActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new EmployeeLogin().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddToBillbtn;
    private javax.swing.JButton Clearbtn;
    private javax.swing.JLabel DateLabel;
    private javax.swing.JLabel GrandTotalLabel;
    private javax.swing.JButton GrandTotalbtn;
    private javax.swing.JTextField IdField;
    private javax.swing.JButton Logoutbtn;
    private javax.swing.JPanel PrintPanel;
    private javax.swing.JButton Printbtn;
    private javax.swing.JTextField QuantityField;
    private javax.swing.JButton Refreshbtn;
    private javax.swing.JTable StockTable;
    private javax.swing.JLabel TimeLabel;
    private javax.swing.JLabel billPricejLabel;
    private javax.swing.JLabel billPricejLabel1;
    private javax.swing.JTable billTable;
    private javax.swing.JButton clearbillbtn;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel med1;
    private javax.swing.JLabel med10;
    private javax.swing.JLabel med10_1;
    private javax.swing.JLabel med10_2;
    private javax.swing.JLabel med10_3;
    private javax.swing.JLabel med11;
    private javax.swing.JLabel med11_1;
    private javax.swing.JLabel med11_2;
    private javax.swing.JLabel med11_3;
    private javax.swing.JLabel med12;
    private javax.swing.JLabel med12_1;
    private javax.swing.JLabel med12_2;
    private javax.swing.JLabel med12_3;
    private javax.swing.JLabel med13;
    private javax.swing.JLabel med13_1;
    private javax.swing.JLabel med13_2;
    private javax.swing.JLabel med13_3;
    private javax.swing.JLabel med14;
    private javax.swing.JLabel med14_1;
    private javax.swing.JLabel med14_2;
    private javax.swing.JLabel med14_3;
    private javax.swing.JLabel med15;
    private javax.swing.JLabel med15_1;
    private javax.swing.JLabel med15_2;
    private javax.swing.JLabel med15_3;
    private javax.swing.JLabel med1_1;
    private javax.swing.JLabel med1_2;
    private javax.swing.JLabel med1_3;
    private javax.swing.JLabel med2;
    private javax.swing.JLabel med2_1;
    private javax.swing.JLabel med2_2;
    private javax.swing.JLabel med2_3;
    private javax.swing.JLabel med3;
    private javax.swing.JLabel med3_1;
    private javax.swing.JLabel med3_2;
    private javax.swing.JLabel med3_3;
    private javax.swing.JLabel med4;
    private javax.swing.JLabel med4_1;
    private javax.swing.JLabel med4_2;
    private javax.swing.JLabel med4_3;
    private javax.swing.JLabel med5;
    private javax.swing.JLabel med5_1;
    private javax.swing.JLabel med5_2;
    private javax.swing.JLabel med5_3;
    private javax.swing.JLabel med6;
    private javax.swing.JLabel med6_1;
    private javax.swing.JLabel med6_2;
    private javax.swing.JLabel med6_3;
    private javax.swing.JLabel med7;
    private javax.swing.JLabel med7_1;
    private javax.swing.JLabel med7_2;
    private javax.swing.JLabel med7_3;
    private javax.swing.JLabel med8;
    private javax.swing.JLabel med8_1;
    private javax.swing.JLabel med8_2;
    private javax.swing.JLabel med8_3;
    private javax.swing.JLabel med9;
    private javax.swing.JLabel med9_1;
    private javax.swing.JLabel med9_2;
    private javax.swing.JLabel med9_3;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables
}
