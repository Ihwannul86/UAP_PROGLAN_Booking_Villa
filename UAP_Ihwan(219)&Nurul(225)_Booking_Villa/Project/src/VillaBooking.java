import java.awt.*;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VillaBooking {

    private JFrame frame, summaryFrame;
    private JPanel categoryPanel, menuPanel, checkoutPanel;
    private JComboBox<String> categoryComboBox;
    private JButton orderButton, paymentButton, viewSummaryButton;
    private JTextArea receiptArea;
    private JTable summaryTable;
    private DefaultTableModel tableModel;
    private ArrayList<Item> selectedItems = new ArrayList<>();
    private int totalCost = 0;
    private int change = 0;

    // Menu Items
    private Item[] allItems = {
        new Item("Villa Ihwan", 500000, "images/Villa_Ihwan.png", "Standar"),
        new Item("Villa Nurul", 1000000, "images/Villa_Nurul.png", "Standar"),
        new Item("Villa Adam", 1500000, "images/Villa_Adam.png", "Standar"),
        new Item("Villa Ros", 2000000, "images/Villa_Ros.png", "Clasic"),
        new Item("Villa Tasya", 2500000, "images/Villa_Tasya.png", "Clasic"),
        new Item("Vanila Fia", 3000000, "images/Villa_Fia.png", "Clasic")
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VillaBooking().initialize());
    }

    public void initialize() {
        frame = new JFrame("Villa Booking App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Filter kategori menu pemesanan
        categoryPanel = new JPanel();
        categoryComboBox = new JComboBox<>(new String[]{"All", "Standar", "Clasic"});
        categoryComboBox.addActionListener(e -> displayMenuItems((String) categoryComboBox.getSelectedItem()));
        categoryPanel.add(new JLabel("Category:"));
        categoryPanel.add(categoryComboBox);
        frame.add(categoryPanel, BorderLayout.NORTH);

        // Middle Section: Menu Display
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.BLUE);
        JScrollPane menuScroll = new JScrollPane(menuPanel);
        displayMenuItems("All");
        frame.add(menuScroll, BorderLayout.CENTER);

        // Bottom Section: Checkout and Payment
        checkoutPanel = new JPanel();
        orderButton = new JButton("Order Now");
        orderButton.addActionListener(e -> showReceipt());
        checkoutPanel.add(orderButton);

        viewSummaryButton = new JButton("View Summary");
        viewSummaryButton.addActionListener(e -> openSummaryWindow());
        checkoutPanel.add(viewSummaryButton);

        receiptArea = new JTextArea(10, 30);
        checkoutPanel.add(new JScrollPane(receiptArea));
        frame.add(checkoutPanel, BorderLayout.SOUTH);

        frame.setSize(1000, 800);
        frame.setVisible(true);

        // Initialize Summary Frame
        initializeSummaryFrame();
    }

    private void initializeSummaryFrame() {
        summaryFrame = new JFrame("Summary");
        summaryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        summaryFrame.setLayout(new BorderLayout());

        // Tambahkan kolom "Check-in" dan "Check-out"
        String[] columnNames = {"Nama", "Jenis Kelamin", "No Telepon", "Nama Villa", "Check-in", "Check-out", "Total Pembayaran", "Kembalian"};
        tableModel = new DefaultTableModel(columnNames, 0);
        summaryTable = new JTable(tableModel);

        // Atur lebar kolom
        int[] columnWidths = {150, 100, 120, 150, 120, 120, 150, 150};
        for (int i = 0; i < columnWidths.length; i++) {
            summaryTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JScrollPane tableScroll = new JScrollPane(summaryTable);
        summaryFrame.add(tableScroll, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            summaryFrame.setVisible(false);
            frame.setVisible(true);
        });
        summaryFrame.add(backButton, BorderLayout.SOUTH);

        summaryFrame.setSize(1000, 600);
    }

    private void displayMenuItems(String category) {
        menuPanel.removeAll();
        for (Item item : allItems) {
            if (category.equals("All") || item.category.equals(category)) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
                itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                itemPanel.setBackground(Color.CYAN);

                ImageIcon originalIcon = new ImageIcon(item.imagePath);
                Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(scaledImage);

                JLabel imageLabel = new JLabel(resizedIcon);
                JLabel nameLabel = new JLabel(item.name, SwingConstants.CENTER);
                nameLabel.setForeground(Color.BLACK);
                JLabel priceLabel = new JLabel("Rp. " + item.price, SwingConstants.CENTER);
                priceLabel.setForeground(Color.BLACK);

                // Gunakan JSpinner untuk memilih tanggal
                JSpinner checkInSpinner = new JSpinner(new SpinnerDateModel());
                JSpinner checkOutSpinner = new JSpinner(new SpinnerDateModel());

                // Set format tanggal
                JSpinner.DateEditor checkInEditor = new JSpinner.DateEditor(checkInSpinner, "yyyy-MM-dd");
                checkInSpinner.setEditor(checkInEditor);
                JSpinner.DateEditor checkOutEditor = new JSpinner.DateEditor(checkOutSpinner, "yyyy-MM-dd");
                checkOutSpinner.setEditor(checkOutEditor);

                JButton addButton = new JButton("Pesan");

                addButton.addActionListener(e -> {
                    try {
                        // Ambil tanggal dari JSpinner
                        Date checkInDate = (Date) checkInSpinner.getValue();
                        Date checkOutDate = (Date) checkOutSpinner.getValue();

                        // Konversi tanggal menjadi LocalDate
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String checkInStr = sdf.format(checkInDate);
                        String checkOutStr = sdf.format(checkOutDate);

                        LocalDate checkInLocalDate = LocalDate.parse(checkInStr);
                        LocalDate checkOutLocalDate = LocalDate.parse(checkOutStr);

                        long days = ChronoUnit.DAYS.between(checkInLocalDate, checkOutLocalDate);
                        if (days <= 0) {
                            JOptionPane.showMessageDialog(frame, "Check-out harus lebih besar dari Check-in.");
                        } else {
                            int totalPrice = (int) (item.price * days);
                            item.setDates(checkInLocalDate, checkOutLocalDate);
                            item.price = totalPrice;  // Simpan harga total berdasarkan durasi menginap
                            selectedItems.add(item);
                            JOptionPane.showMessageDialog(frame, "Pesanan ditambahkan: " + days + " hari, Total: Rp. " + totalPrice);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Format tanggal salah! Pilih tanggal dari kalender.");
                    }
                });

                JPanel bottomPanel = new JPanel();
                bottomPanel.add(new JLabel("Check-in:"));
                bottomPanel.add(checkInSpinner);
                bottomPanel.add(new JLabel("Check-out:"));
                bottomPanel.add(checkOutSpinner);
                bottomPanel.add(addButton);

                itemPanel.add(imageLabel);
                itemPanel.add(nameLabel);
                itemPanel.add(priceLabel);
                itemPanel.add(bottomPanel);

                menuPanel.add(itemPanel);
            }
        }
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private void showReceipt() {
        totalCost = selectedItems.stream().mapToInt(item -> item.price).sum(); // Jumlahkan harga total per item
        receiptArea.setText("---- PESANAN ----\n");
        for (Item item : selectedItems) {
            receiptArea.append(item.name + " - Rp. " + item.price + "\n");
        }
        receiptArea.append("\nTotal: Rp. " + totalCost);

        if (paymentButton == null) {
            paymentButton = new JButton("Pembayaran");
            paymentButton.addActionListener(e -> showPaymentDialog());
            checkoutPanel.add(paymentButton);
        }
        paymentButton.setEnabled(true);
        frame.revalidate();
        frame.repaint();
    }

    private void showPaymentDialog() {
        String paymentInput = JOptionPane.showInputDialog("Masukkan Pembayaran:");
        if (paymentInput == null) {
            return;
        }

        try {
            int payment = Integer.parseInt(paymentInput);
            if (payment >= totalCost) {
                change = payment - totalCost;
                collectUserInfo();
            } else {
                JOptionPane.showMessageDialog(frame, "Uang Kurang! Masukkan Pembayaran yang Tepat.");
                showPaymentDialog();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Input harus berupa angka.");
            showPaymentDialog();
        }
    }

    private void collectUserInfo() {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nama:"));
        panel.add(nameField);
        panel.add(new JLabel("Jenis Kelamin:"));
        panel.add(genderComboBox);
        panel.add(new JLabel("No Telepon:"));
        panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Masukkan Data Diri", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (nameField.getText().isEmpty() || phoneField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Inputan tidak boleh kosong");
                collectUserInfo();
            } else if (!phoneField.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(frame, "No Telepon harus berupa angka.");
                collectUserInfo();
            } else {
                for (Item item : selectedItems) {
                    tableModel.addRow(new Object[]{
                        nameField.getText(),
                        genderComboBox.getSelectedItem().toString(),
                        phoneField.getText(),
                        item.name,
                        item.checkInDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        item.checkOutDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        "Rp. " + item.price,
                        "Rp. " + change
                    });
                }

                selectedItems.clear();
                displayMenuItems("All");
                receiptArea.setText("");
                paymentButton.setEnabled(false);
                JOptionPane.showMessageDialog(frame, "Pesanan berhasil disimpan!");
            }
        }
    }

    private void openSummaryWindow() {
        frame.setVisible(false);
        summaryFrame.setVisible(true);
    }

    private void saveSummaryToFile() {
        try {
            FileWriter writer = new FileWriter("summary.csv");
            // Tulis header
            writer.write("Nama,Jenis Kelamin,No Telepon,Nama Villa,Check-in,Check-out,Total Pembayaran,Kembalian\n");

            // Tulis setiap baris dari tabel
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    writer.write(tableModel.getValueAt(i, j).toString());
                    if (j < tableModel.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }
            writer.close();
            JOptionPane.showMessageDialog(frame, "Data berhasil disimpan ke summary.csv");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Gagal menyimpan data: " + e.getMessage());
        }
    }

    static class Item {

        String name, imagePath, category;
        int price;
        LocalDate checkInDate, checkOutDate;

        public Item(String name, int price, String imagePath, String category) {
            this.name = name;
            this.price = price;
            this.imagePath = imagePath;
            this.category = category;
        }

        public void setDates(LocalDate checkInDate, LocalDate checkOutDate) {
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
        }
    }
}
