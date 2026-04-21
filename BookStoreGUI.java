import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BookStoreGUI extends JFrame implements ActionListener {
    JLabel book1Label, book2Label, book1PriceLabel, book2PriceLabel;
    JTextField book1TF, book2TF;
    JTextArea purchaseHistoryArea;
    JButton clearBtn, cartBtn, purchaseBtn;
    JScrollPane scrollPane;
    JPanel panel;

    public BookStoreGUI() {
        super("Book Store Management");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
		
		ImageIcon icon = new ImageIcon("./images/image1.jpg");
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel imgLabel = new JLabel(scaledIcon);
        imgLabel.setBounds(2, 5, 200, 150);
        panel.add(imgLabel);

        ImageIcon icon1 = new ImageIcon("./images/image2.jpg");
        Image img1 = icon1.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(img1);
        JLabel imgLabel1 = new JLabel(scaledIcon1);
        imgLabel1.setBounds(14, 270, 200, 150);
        panel.add(imgLabel1);

        book1Label = new JLabel("Book A Quantity");
        book1Label.setBounds(250, 150, 120, 30);
        panel.add(book1Label);

        book1TF = new JTextField();
        book1TF.setBounds(250, 130, 100, 30);
        panel.add(book1TF);

        book1PriceLabel = new JLabel("Price: 200 Taka");
        book1PriceLabel.setBounds(65, 150, 160, 30);
        panel.add(book1PriceLabel);

        book2Label = new JLabel("Book B Quantity");
        book2Label.setBounds(250, 370, 120, 30);
        panel.add(book2Label);

        book2TF = new JTextField();
        book2TF.setBounds(250, 400, 100, 30);
        panel.add(book2TF);

        book2PriceLabel = new JLabel("Price: 350 Taka");
        book2PriceLabel.setBounds(65, 420, 160, 30);
        panel.add(book2PriceLabel);

        clearBtn = new JButton("Clear");
        clearBtn.setBounds(350, 230, 150, 30);
        clearBtn.addActionListener(this);
        panel.add(clearBtn);

        cartBtn = new JButton("Add To Cart");
        cartBtn.setBounds(350, 260, 150, 30);
        cartBtn.addActionListener(this);
        panel.add(cartBtn);

        purchaseBtn = new JButton("Confirm Purchase");
        purchaseBtn.setBounds(350, 290, 150, 30);
        purchaseBtn.addActionListener(this);
        panel.add(purchaseBtn);

        purchaseHistoryArea = new JTextArea();
        purchaseHistoryArea.setEditable(false);
        scrollPane = new JScrollPane(purchaseHistoryArea);
        scrollPane.setBounds(550, 20, 400, 520);
        panel.add(scrollPane);
 
        this.add(panel);
        this.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        if (command.equals("Add To Cart")) {
            addToCart();
        } else if (command.equals("Confirm Purchase")) {
            purchase();
        } else if (command.equals("Clear")) {
            clearFields();
        }
    }

    private void addToCart() {
        String book1Quantity = book1TF.getText();
        String book2Quantity = book2TF.getText();

        if (book1Quantity.isEmpty() && book2Quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter quantity");
            return;
        }

        BookInfo newInfo;

        if (book1Quantity.isEmpty()) {
            book1Quantity = "0";
        }
        if (book2Quantity.isEmpty()) {
            book2Quantity = "0";
        }

        newInfo = new BookInfo(book1Quantity, book2Quantity);

        try {
            FileWriter writer = new FileWriter("BookPurchaseData.txt", true);
            writer.write("Book A" + ", " + newInfo.getBook1Quantity() + ", " + "Book B" + ", " + newInfo.getBook2Quantity() + ", " + "Total Price" + ", " + newInfo.getTotalPrice() + "\n");
            writer.close();
            JOptionPane.showMessageDialog(this, "Successfully added to cart");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void purchase() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("BookPurchaseData.txt"));
            String line;
            StringBuilder content = new StringBuilder();
            int customerCount = 1;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String book1Qty = parts[1].trim();
                    String book2Qty = parts[3].trim();
                    String total = parts[5].trim();

                    content.append("------------------------------ \n");
                    content.append("Customer ").append(customerCount++).append("\n");
                    content.append("Book A: ").append(book1Qty).append("\n");
                    content.append("Book B: ").append(book2Qty).append("\n");
                    content.append("Total Price: ").append(total).append("\n");
                    content.append("------------------------------ \n");
                }
            }
            reader.close();
            purchaseHistoryArea.setText(content.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        book1TF.setText("");
        book2TF.setText("");
    }
}