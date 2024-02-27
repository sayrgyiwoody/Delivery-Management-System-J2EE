package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import controller.auth.Hash;
import model.User;
import util.DBConnection;

public class UserDAO{
Connection connection = null;
ResultSet resultSet = null;
Statement statement = null;
PreparedStatement preparedStatement = null;


public User createUser(User user) {
	User returnUser = new User() ;
	try {
		String sql = "INSERT INTO users(name, email,phone,role, hashed_password) VALUES (?,?,?,?,?)";
		connection = DBConnection.openConnection();
		preparedStatement = 	connection.prepareStatement(sql);
		preparedStatement.setString(1, user.getName());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getPhone());
		if(user.getRole().equals("shop") || user.getRole().equals("driver")) {
		    preparedStatement.setString(4, user.getRole());
		}else {
			preparedStatement.setString(4,"shop");
		}
		preparedStatement.setString(5, user.getHashed_password());
		preparedStatement.executeUpdate();
		
		returnUser = getUser(user.getEmail());
	}catch(SQLException ex) {
		ex.printStackTrace();
	}
	return returnUser;
}

public boolean update(User user) {
	   boolean flag = false;
	   try {
		String sql = "UPDATE users SET name = ?, email = ?, phone = ? , address = ? where id = ?";
		connection = DBConnection.openConnection();
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, user.getName());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getPhone());
		preparedStatement.setString(4, user.getAddress());
		preparedStatement.setInt(5, user.getId());
		int rowUpdated = preparedStatement.executeUpdate();
		if (rowUpdated>0) flag = true;
		}catch(SQLException e) {
		e.printStackTrace();
		}
		return flag;
	   }

public boolean updatePassword(String email, String hashed_password) {
	boolean flag = false;
	   try {
		String sql = "UPDATE users SET hashed_password = ? where email = ?";
		connection = DBConnection.openConnection();
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, hashed_password);
		preparedStatement.setString(2, email);
		
		int rowUpdated = preparedStatement.executeUpdate();
		if (rowUpdated>0) flag = true;
		}catch(SQLException e) {
		e.printStackTrace();
		}
		return flag;
}

public List<User> get(String role , String searchKey) {
	List<User> list = null;
	User user = null;

	try {
	    list = new ArrayList<User>();
	    
	    
	    
	    String sql = "SELECT * FROM users where role = ? AND name LIKE ?";
	    connection = DBConnection.openConnection();
	    PreparedStatement pstmt = connection.prepareStatement(sql);
	    pstmt.setString(1, role);
	    pstmt.setString(2, "%" + searchKey + "%");
	    resultSet = pstmt.executeQuery();
	    while(resultSet.next()) {
			user = new User();
			user.setId(resultSet.getInt("id"));
			user.setName(resultSet.getString("name"));
			user.setEmail(resultSet.getString("email"));
			user.setPhone(resultSet.getString("phone"));
			user.setAddress(resultSet.getString("address"));
			user.setRole(resultSet.getString("role"));
//			user.setHashed_password(resultSet.getString("hashed_password"));
			list.add(user);
	    }
	}catch(SQLException e) {
	   e.printStackTrace();
	}
    return list;
}

//delete account by id 
public boolean delete(String id) {

	boolean flag = false;
	   try {
	   String sql = "DELETE FROM users where id="+id;
		connection = DBConnection.openConnection();
		preparedStatement = connection.prepareStatement(sql);
		int rowDeleted = preparedStatement.executeUpdate();
		if(rowDeleted>0) flag = true;
		}catch(SQLException e) {
		e.printStackTrace();
		}
	   return flag;
	}


public User get(int id) {
	   User user = null;
	   try {
		user = new User();
		String sql = "SELECT * FROM users where id="+id;
		connection = DBConnection.openConnection();
		statement = connection.createStatement();
		resultSet = statement.executeQuery(sql);
		if(resultSet.next()) {
			
			user.setName(resultSet.getString("name"));
			user.setEmail(resultSet.getString("email"));
//			user.setHashed_password(resultSet.getString("hashed_password"));
			}
		}catch(SQLException e) {
		e.printStackTrace();
	      }
	    return user;
	}

public boolean validateUser(String email, String password) {
    boolean isValid = false;

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        // Establish the connection
    	connection = DBConnection.openConnection();

        // Prepare the statement
        String sql = "SELECT hashed_password FROM users WHERE email = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        

        // Execute the query
        rs = pstmt.executeQuery();

        // Process the result set
        if (rs.next()) {
        	boolean isMatch = Hash.verifyPassword(password, rs.getString("hashed_password"));
        	if(isMatch) {
        		isValid = true;
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
    return isValid;
}

public String getRole(String email) {
	String role = null;
	
	PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        // Establish the connection
    	connection = DBConnection.openConnection();

        // Prepare the statement
        String sql = "SELECT role FROM users WHERE email = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        

        // Execute the query
        rs = pstmt.executeQuery();

        // Process the result set
        if (rs.next()) {
        	role = rs.getString("role");
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
    return role;
}

public User getUser(String email) {
	User user = new User();
	
	PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        // Establish the connection
    	connection = DBConnection.openConnection();

        // Prepare the statement
        String sql = "SELECT * FROM users WHERE email = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        

        // Execute the query
        rs = pstmt.executeQuery();

        // Process the result set
        if (rs.next()) {
        	user.setId(rs.getInt("id"));
        	user.setName( rs.getString("name"));
        	user.setEmail( rs.getString("email"));
        	user.setPhone( rs.getString("phone"));
        	user.setRole( rs.getString("role"));
        	user.setAddress(rs.getString("address"));
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
    return user;
}



public boolean checkEmail(String email) {
    boolean isValid = false;

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        // Establish the connection
    	connection = DBConnection.openConnection();

        // Prepare the statement
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        

        // Execute the query
        rs = pstmt.executeQuery();

        // Process the result set
        if (rs.next()) {
        	int count = rs.getInt(1);
        	return count > 0;
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
    return isValid;
}

}