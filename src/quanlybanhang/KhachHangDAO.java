package quanlybanhang; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    // 1. Hàm lấy toàn bộ danh sách khách hàng (Dùng để hiển thị lên bảng)
    public List<Object[]> getAll() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT MaKH, HoTen, SoDienThoai, DiaChi FROM KhachHang";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                // Đưa dữ liệu vào mảng Object để dễ đưa lên JTable
                list.add(new Object[]{
                    rs.getInt("MaKH"),
                    rs.getString("HoTen"),
                    rs.getString("SoDienThoai"),
                    rs.getString("DiaChi")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Hàm thêm khách hàng mới
    public boolean insert(String hoTen, String sdt, String diaChi) {
        String sql = "INSERT INTO KhachHang(HoTen, SoDienThoai, DiaChi) VALUES (?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, hoTen);
            ps.setString(2, sdt);
            ps.setString(3, diaChi);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Hàm cập nhật (Sửa) khách hàng
    public boolean update(int maKH, String hoTen, String sdt, String diaChi) {
        String sql = "UPDATE KhachHang SET HoTen=?, SoDienThoai=?, DiaChi=? WHERE MaKH=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, hoTen);
            ps.setString(2, sdt);
            ps.setString(3, diaChi);
            ps.setInt(4, maKH);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Hàm xóa khách hàng
    public boolean delete(int maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, maKH);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 5. Hàm tìm kiếm theo tên hoặc SĐT
    public List<Object[]> search(String keyword) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT MaKH, HoTen, SoDienThoai, DiaChi FROM KhachHang WHERE HoTen LIKE ? OR SoDienThoai LIKE ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getInt("MaKH"),
                        rs.getString("HoTen"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}