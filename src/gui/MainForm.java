package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainForm extends JFrame {

    public MainForm() {
        setTitle("HỆ THỐNG QUẢN LÝ BÁN HÀNG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null); // Căn giữa màn hình
        setLayout(new BorderLayout());

        // --- 1. Phần Tiêu đề (Header) ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185)); // Màu xanh dương đẹp
        headerPanel.setPreferredSize(new Dimension(500, 100));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));

        JLabel lblTitle = new JLabel("QUẢN LÝ BÁN HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);

        // --- 2. Phần Nội dung (Các nút bấm) ---
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(245, 246, 250)); // Màu nền xám nhạt
        contentPanel.setLayout(new GridLayout(3, 1, 20, 20)); // 3 dòng, cách nhau 20px
        contentPanel.setBorder(new EmptyBorder(40, 80, 40, 80)); // Căn lề trên, trái, dưới, phải

        JButton btnKhachHang = createStyledButton("Quản lý Khách Hàng", new Color(46, 204, 113)); // Xanh lá
        JButton btnSanPham = createStyledButton("Quản lý Sản Phẩm", new Color(230, 126, 34));  // Cam
        JButton btnHoaDon = createStyledButton("Quản lý Hóa Đơn", new Color(155, 89, 182));   // Tím

        // Gắn sự kiện mở Form
        btnKhachHang.addActionListener(e -> new KhachHangForm().setVisible(true));
        btnSanPham.addActionListener(e -> new SanPhamForm().setVisible(true));
        btnHoaDon.addActionListener(e -> new HoaDonForm().setVisible(true));

        contentPanel.add(btnKhachHang);
        contentPanel.add(btnSanPham);
        contentPanel.add(btnHoaDon);

        // --- 3. Phần Footer (Chân trang) ---
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(245, 246, 250));
        JLabel lblFooter = new JLabel("Đồ án Java - Sinh viên: Lâm Thái Sơn");
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblFooter.setForeground(Color.GRAY);
        footerPanel.add(lblFooter);

        // Thêm tất cả vào Frame
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    // Hàm tạo nút bấm có hiệu ứng đẹp (Bo góc, đổi màu khi di chuột)
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng Hover (Đổi màu khi di chuột vào)
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        // Sử dụng giao diện FlatLaf cho đẹp (hoặc mặc định của Windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(() -> new MainForm().setVisible(true));
    }
}
