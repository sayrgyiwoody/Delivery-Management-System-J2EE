package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import controller.auth.Hash;
import model.Otp;
import model.User;
import util.DBConnection;

public class OtpDAO{
Connection connection = null;
ResultSet resultSet = null;
Statement statement = null;
PreparedStatement preparedStatement = null;


public void createUser(Otp user) {
	try {
		LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
		String sql = "INSERT INTO code( email,otp,otp_expiration) VALUES (?,?,?)";
		connection = DBConnection.openConnection();
		preparedStatement = 	connection.prepareStatement(sql);
		preparedStatement.setString(1, user.getEmail());
		preparedStatement.setInt(2, user.getOtp());
		preparedStatement.setTimestamp(3, Timestamp.valueOf(expirationTime));
		preparedStatement.executeUpdate();
		
	}catch(SQLException ex) {
		ex.printStackTrace();
	}
}

public List<User> get() {
	
	
	List<User> list = new ArrayList<>();
	try {
	    
	    String sql = "SELECT * FROM code";
	    connection = DBConnection.openConnection();
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(sql);
	    while(resultSet.next()) {
			Otp user = new Otp();
			user.setOtp(resultSet.getInt("otp"));
			
			user.setEmail(resultSet.getString("email"));
			//list.add(user);
			
	    }
	}catch(SQLException e) {
	   e.printStackTrace();
	}
    return list;
}


public boolean checkOTP(String email, int otp) {
    boolean otpMatch  = false;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        // Prepare the statement
        String sql = "SELECT otp,otp_expiration FROM code WHERE email = ?";
        // Establish the connection
    	connection = DBConnection.openConnection();

        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        
        // Execute the query
        rs = pstmt.executeQuery();

        // Process the result set
        if (rs.next()) {
        	int dbOTP = rs.getInt("otp");
        	LocalDateTime expirationTime = rs.getTimestamp("otp_expiration").toLocalDateTime();
            if (dbOTP == otp && expirationTime.isAfter(LocalDateTime.now())) {
                otpMatch = true;
            }else {
            	System.out.println("dbotp of else " +dbOTP);
            }
        }
    } catch (SQLException e) {
        // Handle the exception
        e.printStackTrace();
    } finally {
        // Close the resources
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return otpMatch;
}
public boolean update(Otp user) {
	   boolean flag = false;
	   try {
		LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
		String sql = "UPDATE code SET otp = ?,otp_expiration = ? where email = ?";
		connection = DBConnection.openConnection();
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, user.getOtp());
		preparedStatement.setTimestamp(2, Timestamp.valueOf(expirationTime));
		preparedStatement.setString(3, user.getEmail());
		
		int rowUpdated = preparedStatement.executeUpdate();
		if (rowUpdated>0) flag = true;
		}catch(SQLException e) {
		e.printStackTrace();
		}
	   System.out.println("flag "+ flag);
		return flag;
	   }
	
public Otp getByEmail(String email) {
    Otp user = null;
    try {
        String sql = "SELECT * FROM code WHERE email = ?";
        connection = DBConnection.openConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            user = new Otp();
            user.setOtp(rs.getInt("otp"));
            user.setEmail(rs.getString("email"));
        }
        
        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return user;
}

}