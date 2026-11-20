/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlybanhang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Chuỗi kết nối SQL Server
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBanHang;encrypt=true;trustServerCertificate=true;";
            String user = "sa";
            String pass = "123456"; // Mật khẩu SQL Server của bro (nếu khác thì sửa lại)
            
            // Nạp Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Kết nối
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ KẾT NỐI SQL SERVER THÀNH CÔNG RỒI BRO!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ LỖI KẾT NỐI: " + e.getMessage());
        }
        return conn;
    }

    // Hàm main để chạy test
    public static void main(String[] args) {
        getConnection();
    }
}