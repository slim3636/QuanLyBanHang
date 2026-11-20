package quanlybanhang;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    
    // Hàm lưu hóa đơn (Dùng Transaction để đảm bảo toàn vẹn dữ liệu)
    // chiTietList: Danh sách các sản phẩm, mỗi phần tử là mảng [MaSP, SoLuong, DonGia]
    public boolean insertHoaDon(int maKH, Date ngayLap, List<Object[]> chiTietList) {
        String sqlHD = "INSERT INTO HoaDon(MaKH, NgayLap) VALUES (?, ?)";
        String sqlCT = "INSERT INTO ChiTietHoaDon(MaHD, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = DBConnect.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction (Chưa lưu thật vào DB)
            
            // 1. Thêm vào bảng HoaDon
            PreparedStatement psHD = conn.prepareStatement(sqlHD, Statement.RETURN_GENERATED_KEYS);
            psHD.setInt(1, maKH);
            psHD.setDate(2, ngayLap);
            psHD.executeUpdate();
            
            // Lấy Mã Hóa Đơn vừa tự động sinh ra
            ResultSet rs = psHD.getGeneratedKeys();
            int maHD = 0;
            if (rs.next()) {
                maHD = rs.getInt(1);
            }
            
            // 2. Thêm vào bảng ChiTietHoaDon
           PreparedStatement psCT = conn.prepareStatement(sqlCT);
            PreparedStatement psUpdateTon = conn.prepareStatement("UPDATE SanPham SET SoLuongTon = SoLuongTon - ? WHERE MaSP = ?");
            
            for (Object[] row : chiTietList) {
                int maSP = (int) row[0];
                int soLuong = (int) row[1];
                java.math.BigDecimal donGia = (java.math.BigDecimal) row[2];
                
                // Thêm chi tiết hóa đơn
                psCT.setInt(1, maHD);
                psCT.setInt(2, maSP);
                psCT.setInt(3, soLuong);
                psCT.setBigDecimal(4, donGia);
                psCT.executeUpdate();
                
                // Cập nhật (trừ) số lượng tồn kho
                psUpdateTon.setInt(1, soLuong);
                psUpdateTon.setInt(2, maSP);
                psUpdateTon.executeUpdate();
            }
            
            conn.commit(); // Lưu tất cả thành công vào Database
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } // Hủy bỏ nếu có lỗi
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}