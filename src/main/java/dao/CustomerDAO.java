package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import controller.auth.Hash;
import model.Customer;
import model.Order;
import model.User;
import util.DBConnection;
public class CustomerDAO{
Connection connection = null;
ResultSet resultSet = null;
Statement statement = null;
PreparedStatement preparedStatement = null;


public int add(Customer customer) {
    int customer_id = -1; // Initialize customer_id to a default value

    try {
        String sql = "INSERT INTO customer (customer_name, customer_phone, city, township, detail_address) VALUES (?, ?, ?, ?, ?)";
        connection = DBConnection.openConnection();
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, customer.getCustomer_name());
        preparedStatement.setString(2, customer.getCustomer_phone());
        preparedStatement.setString(3, customer.getCity());
        preparedStatement.setString(4, customer.getTownship());
        preparedStatement.setString(5, customer.getDetail_address());

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                customer_id = generatedKeys.getInt(1);
               
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        // Close resources in a finally block
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return customer_id;
}

//get customer by customer_id

public Customer get(int customer_id) {
  Customer customer = new Customer();

  try {
      String sql = "SELECT * from customer where id = ?";
      connection = DBConnection.openConnection();
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, customer_id);
      resultSet = preparedStatement.executeQuery(); 

      // Move the cursor to the first row (if exists)
      if (resultSet.next()) {
          customer.setCustomer_name(resultSet.getString("customer_name"));
          customer.setCustomer_phone(resultSet.getString("customer_phone"));
          customer.setCity(resultSet.getString("city"));
          customer.setTownship(resultSet.getString("township"));
          customer.setDetail_address(resultSet.getString("detail_address"));
      }
  } catch(SQLException e) {
      e.printStackTrace();
  } finally {
      // Close resources properly in the finally block
      // Ensure that connections, statements, and result sets are properly closed
      try {
          if (resultSet != null) {
              resultSet.close();
          }
          if (preparedStatement != null) {
              preparedStatement.close();
          }
          if (connection != null) {
              connection.close();
          }
      } catch (SQLException ex) {
          ex.printStackTrace();
      }
  }
  return customer;
}


}

