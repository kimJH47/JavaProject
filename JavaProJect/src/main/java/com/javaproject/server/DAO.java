package com.javaproject.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DAO {

    private Connection conn; // Connection :

    public DAO() { //
        try {
            String dbURL = "jdbc:mysql://121.181.97.97:9000/myschemas";
            String dbID = "root";
            String dbPassword = "1145";
            Class.forName("com.mysql.cj.jdbc.Driver"); // Class.forName

            conn = DriverManager.getConnection(dbURL, dbID, dbPassword); // conn : getConnection(db URL, dbID,
            // dbPassword
            System.out.println("DB connection");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean signIn(String user_ID, String user_pw) {
        try {
            System.out.println("login checking");
            String SQL = "SELECT user_pw FROM user_info WHERE user_id = ?"; // ������ DB�� �Էµ� ��ɾ SQL �������� ����.
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, user_ID);
            ResultSet rs = pstmt.executeQuery(); // ��� ����� �޾ƿ��� ResultSet Ÿ���� rs ������ �������� ������ ����� �־���
            if (rs.next()) {
                if (rs.getString(1).contentEquals(user_pw)) {
                    System.out.println("login ");
                    return true; //
                } else {
                    return false; //
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean signUp(String user_ID, String user_pw, String name) {
        //�ߺ����� DB�������� Ȯ��
        try {
            if (overCheck(user_ID)) {
                String SQL = "INSERT INTO user_info VALUES(?,?)";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1,user_ID);
                pstmt.setString(2,user_pw);
                pstmt.executeUpdate();
                System.out.println("sign up success");
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // DB ����
        }
    }

    public boolean overCheck(String user_ID) {

        try {
            String SQL = "SELECT user_ID FROM user_info WHERE user_id = ?"; // ������ DB�� �Էµ� ��ɾ SQL �������� ����.
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, user_ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (user_ID.equals(rs.getString(user_ID))) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

