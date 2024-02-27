package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Order;
import model.OrderItem;
import model.User;
import util.DBConnection;

public class OrderDAO{
Connection connection = null;
ResultSet resultSet = null;
Statement statement = null;
PreparedStatement preparedStatement = null;

public int add(Order order) {
    int order_id = -1; // Initialize order_id to a default value

    try {
        String sql = "INSERT INTO orders (user_id, customer_id, order_status) VALUES (?, ?, ?)";
        connection = DBConnection.openConnection();
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, order.getUser_id()); // Assuming user_id is constant
        preparedStatement.setInt(2, order.getCustomer_id());
        preparedStatement.setString(3, order.getOrder_status());

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order_id = generatedKeys.getInt(1); // Get the generated order_id
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
    return order_id;
}

//get order list for admin 
public List<Order> get( String searchKey, String orderStatus , String township) {
    List<Order> list = null;
    Order order = null;

    try {
        list = new ArrayList<>();
        String sql ;
        if(township == "") {
        	
        	sql = "SELECT orders.*, customer.*, "
                    + "driver.name AS driver_name, shop.name AS shop_name "
                    + "FROM orders "
                    + "LEFT JOIN customer ON customer.id = orders.customer_id "
                    + "LEFT JOIN users AS driver ON driver.id = orders.driver_id AND driver.role = 'driver' "
                    + "LEFT JOIN users AS shop ON shop.id = orders.user_id AND shop.role = 'shop' "
                    + "WHERE (customer.customer_name LIKE ? OR shop.name LIKE ?)  AND orders.order_status LIKE ? AND customer.township LIKE ?";
        }else {
        	sql = "SELECT orders.*, customer.*, "
                    + "driver.name AS driver_name, shop.name AS shop_name "
                    + "FROM orders "
                    + "LEFT JOIN customer ON customer.id = orders.customer_id "
                    + "LEFT JOIN users AS driver ON driver.id = orders.driver_id AND driver.role = 'driver' "
                    + "LEFT JOIN users AS shop ON shop.id = orders.user_id AND shop.role = 'shop' "
                    + "WHERE (customer.customer_name LIKE ? OR shop.name LIKE ?)  AND orders.order_status LIKE ? AND customer.township = ?";
            
        }
        connection = DBConnection.openConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + searchKey + "%");
        preparedStatement.setString(2, "%" + searchKey + "%");
        preparedStatement.setString(3, "%" + orderStatus + "%");
        if(township == "") {
        	preparedStatement.setString(4,"%" + township + "%");
        }else {
        	preparedStatement.setString(4,township);
        }
        
        resultSet = preparedStatement.executeQuery(); 	
        while(resultSet.next()) {
            order = new Order();
            order.setId(resultSet.getInt("id"));
            order.setCustomer_name(resultSet.getString("customer_name"));
            order.setCustomer_city(resultSet.getString("city"));
            order.setCustomer_township(resultSet.getString("township"));
            order.setShop_name(resultSet.getString("shop_name"));
            order.setDriver_name(resultSet.getString("driver_name"));
            order.setOrder_status(resultSet.getString("order_status"));
            order.setCreated_at(resultSet.getTimestamp("created_at"));
            order.setUpdated_at(resultSet.getTimestamp("updated_at"));
            list.add(order);
        }
    } catch(SQLException e) {
        e.printStackTrace();
    }
    return list;
}

//get order list for driver 
public List<Order> getDriverOrder(int driver_id, String searchKey, String orderStatus, String township) {
    List<Order> list = new ArrayList<>();
    Order order = null;

    try {
    	
    	String sql ;
    	if(township == "") {
    		sql = "SELECT orders.*, customer.*, users.name as shop_name " +
                    "FROM orders " +
                    "LEFT JOIN customer ON customer.id = orders.customer_id " +
                    "LEFT JOIN users ON users.id = orders.user_id AND users.role = 'shop' " +
                    "WHERE (customer.customer_name LIKE ? OR users.name LIKE ?) " +
                    "AND orders.order_status LIKE ? " +
                    "AND customer.township LIKE ? " +
                    "AND orders.driver_id = ?";
    	}else {
    		sql = "SELECT orders.*, customer.*, users.name as shop_name " +
                    "FROM orders " +
                    "LEFT JOIN customer ON customer.id = orders.customer_id " +
                    "LEFT JOIN users ON users.id = orders.user_id AND users.role = 'shop' " +
                    "WHERE (customer.customer_name LIKE ? OR users.name LIKE ?) " +
                    "AND orders.order_status LIKE ? " +
                    "AND customer.township = ? " +
                    "AND orders.driver_id = ?";
    	}
        
        connection = DBConnection.openConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + searchKey + "%");
        preparedStatement.setString(2, "%" + searchKey + "%");
        preparedStatement.setString(3, "%" + orderStatus + "%");
        if(township == "") {
        	preparedStatement.setString(4,"%" + township + "%");
        }else {
        	preparedStatement.setString(4,township);
        }
        preparedStatement.setInt(5, driver_id);
        resultSet = preparedStatement.executeQuery(); 	
        while(resultSet.next()) {
            order = new Order();
            order.setId(resultSet.getInt("id"));
            order.setCustomer_name(resultSet.getString("customer_name"));
            order.setCustomer_city(resultSet.getString("city"));
            order.setCustomer_township(resultSet.getString("township"));
            order.setShop_name(resultSet.getString("shop_name"));
            order.setOrder_status(resultSet.getString("order_status"));
            order.setCreated_at(resultSet.getTimestamp("created_at"));
            order.setUpdated_at(resultSet.getTimestamp("updated_at"));
            list.add(order);
        }
    } catch(SQLException e) {
        e.printStackTrace();
    } finally {
        // Close your database resources here
    }
    return list;
}


public boolean delete(String id) {
	
	boolean flag = false;
	   try {
	   String sql = "DELETE FROM orders where id="+id;
		connection = DBConnection.openConnection();
		preparedStatement = connection.prepareStatement(sql);
		int rowDeleted = preparedStatement.executeUpdate();
		if(rowDeleted>0) flag = true;
		}catch(SQLException e) {
		e.printStackTrace();
		}
	   return flag;
	}

//change status for driver // orderDAO.changeStatus(order_id,order_status)
public boolean changeStatus(String id ,String status) {
	
	boolean flag = false;
	   try {
	   String sql = "UPDATE orders SET order_status = ? where id = ?";
	   connection = DBConnection.openConnection();
		preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1, status);
		preparedStatement.setString(2, id);
		
		int rowDeleted = preparedStatement.executeUpdate();
		if(rowDeleted>0) flag = true;
		}catch(SQLException e) {
		e.printStackTrace();
		}
	   return flag;
	}


//assign order to driver
public boolean assignDriver(int id,int driver_id) {
	Boolean flag = false;
    try {
        String sql = "UPDATE Orders SET order_status='assigned', driver_id=? where id = ?";
        connection = DBConnection.openConnection();
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, driver_id);
        preparedStatement.setInt(2, id);
        int rowUpdated = preparedStatement.executeUpdate();
        if (rowUpdated>0) flag = true;
    }catch (SQLException ex) {
        ex.printStackTrace();
    } 
    return flag ;
}


//get order list for shop
public List<Order> get(int user_id, String searchKey, String orderStatus) {
    List<Order> list = null;
    Order order = null;

    try {
        list = new ArrayList<>();
        String sql = "SELECT orders.*, customer.customer_name, "
                + "driver.name AS driver_name, shop.name AS shop_name "
                + "FROM orders "
                + "LEFT JOIN customer ON customer.id = orders.customer_id "
                + "LEFT JOIN users AS driver ON driver.id = orders.driver_id AND driver.role = 'driver' "
                + "LEFT JOIN users AS shop ON shop.id = orders.user_id AND shop.role = 'shop' "
                + "WHERE orders.user_id = ? AND customer.customer_name LIKE ? AND orders.order_status LIKE ?";
        connection = DBConnection.openConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user_id);
        preparedStatement.setString(2, "%" + searchKey + "%");
        preparedStatement.setString(3, "%" + orderStatus + "%");
        resultSet = preparedStatement.executeQuery(); 	
        while(resultSet.next()) {
            order = new Order();
            order.setId(resultSet.getInt("id"));
            order.setCustomer_name(resultSet.getString("customer_name"));
            order.setShop_name(resultSet.getString("shop_name"));
            order.setDriver_name(resultSet.getString("driver_name"));
            order.setOrder_status(resultSet.getString("order_status"));
            order.setCreated_at(resultSet.getTimestamp("created_at"));
            order.setUpdated_at(resultSet.getTimestamp("updated_at"));
            list.add(order);
        }
    } catch(SQLException e) {
        e.printStackTrace();
    }
    return list;
}

//get order by id

public Order get(String order_id) {
    Order order = new Order();

    try {
    	String sql = "SELECT orders.*, customer.customer_name, "
                + "driver.name AS driver_name, shop.name AS shop_name , shop.address as shop_address "
                + "FROM orders "
                + "LEFT JOIN customer ON customer.id = orders.customer_id "
                + "LEFT JOIN users AS driver ON driver.id = orders.driver_id AND driver.role = 'driver' "
                + "LEFT JOIN users AS shop ON shop.id = orders.user_id AND shop.role = 'shop' "
                + "WHERE orders.id = ?";
        connection = DBConnection.openConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, order_id);
        resultSet = preparedStatement.executeQuery(); 

        // Move the cursor to the first row (if exists)
        if (resultSet.next()) {
        	order.setUser_id(resultSet.getInt("user_id"));
        	order.setCustomer_id(resultSet.getInt("customer_id"));
        	order.setDriver_id(resultSet.getInt("driver_id"));
            order.setCustomer_name(resultSet.getString("customer_name"));
            order.setShop_name(resultSet.getString("shop_name"));
            order.setShop_address(resultSet.getString("shop_address"));
            order.setDriver_name(resultSet.getString("driver_name"));
            order.setOrder_status(resultSet.getString("order_status"));
            order.setCreated_at(resultSet.getTimestamp("created_at"));
            order.setUpdated_at(resultSet.getTimestamp("updated_at"));
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
    return order;
}

//get driver info by order id

public User getDriver(int order_id) {
  User user = new User();

  try {
  	String sql = "SELECT orders.*,users.* from \r\n"
  			+ "orders left join users on \r\n"
  			+ "users.id = orders.driver_id\r\n"
  			+ "where orders.id = ?";
      connection = DBConnection.openConnection();
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, order_id);
      resultSet = preparedStatement.executeQuery(); 

      // Move the cursor to the first row (if exists)
      if (resultSet.next()) {
    	  user.setId(resultSet.getInt("id"));
      	user.setName( resultSet.getString("name"));
      	user.setEmail( resultSet.getString("email"));
      	user.setPhone( resultSet.getString("phone"));
      	user.setRole( resultSet.getString("role"));
      	user.setAddress(resultSet.getString("address"));
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
  return user;
}

public User getShop(int order_id) {
	  User user = new User();

	  try {
	  	String sql = "SELECT orders.*,users.* from \r\n"
	  			+ "orders left join users on \r\n"
	  			+ "users.id = orders.user_id\r\n"
	  			+ "where orders.id = ?";
	      connection = DBConnection.openConnection();
	      preparedStatement = connection.prepareStatement(sql);
	      preparedStatement.setInt(1, order_id);
	      resultSet = preparedStatement.executeQuery(); 

	      // Move the cursor to the first row (if exists)
	      if (resultSet.next()) {
	    	  user.setId(resultSet.getInt("id"));
	      	user.setName( resultSet.getString("name"));
	      	user.setEmail( resultSet.getString("email"));
	      	user.setPhone( resultSet.getString("phone"));
	      	user.setRole( resultSet.getString("role"));
	      	user.setAddress(resultSet.getString("address"));
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
	  return user;
	}

public boolean checkUser(int id , int orderId) {
    boolean isValid = false;

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        // Establish the connection
        connection = DBConnection.openConnection();

        // Prepare the statement
        String sql = "SELECT COUNT(*) FROM orders " +
                     "WHERE (user_id = ? OR driver_id = ?) AND id = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.setInt(2, id);
        pstmt.setInt(3, orderId);

        // Execute the query
        rs = pstmt.executeQuery();

        // Process the result set
        if (rs.next()) {
            int count = rs.getInt(1);
            isValid = count > 0;
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

