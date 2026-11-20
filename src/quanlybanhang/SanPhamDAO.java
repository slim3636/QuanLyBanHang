/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlybanhang;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    
    public List<Object[]> getAll() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT MaSP, TenSP, DonGia, SoLuongTon, DonViTinh FROM SanPham";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getBigDecimal("DonGia"),
                    rs.getInt("SoLuongTon"),
                    rs.getString("DonViTinh")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(String tenSP, BigDecimal donGia, int soLuongTon, String donViTinh) {
        String sql = "INSERT INTO SanPham(TenSP, DonGia, SoLuongTon, DonViTinh) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenSP);
            ps.setBigDecimal(2, donGia);
            ps.setInt(3, soLuongTon);
            ps.setString(4, donViTinh);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(int maSP, String tenSP, BigDecimal donGia, int soLuongTon, String donViTinh) {
        String sql = "UPDATE SanPham SET TenSP=?, DonGia=?, SoLuongTon=?, DonViTinh=? WHERE MaSP=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenSP);
            ps.setBigDecimal(2, donGia);
            ps.setInt(3, soLuongTon);
            ps.setString(4, donViTinh);
            ps.setInt(5, maSP);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}