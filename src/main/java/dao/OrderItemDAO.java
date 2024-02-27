package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.OrderItem;
import model.User;
import util.DBConnection;

public class OrderItemDAO{
Connection connection = null;
ResultSet resultSet = null;
Statement statement = null;
PreparedStatement preparedStatement = null;


	
public boolean add(OrderItem orderItem) {
	
	try {
		String sql = "INSERT INTO order_item (order_id, product_name, order_count , price) VALUES (? , ?, ?, ?)";
		connection = DBConnection.openConnection();
		preparedStatement = 	connection.prepareStatement(sql);
		preparedStatement.setInt(1, orderItem.getOrder_id());
		preparedStatement.setString(2, orderItem.getProduct_name());
		preparedStatement.setInt(3, orderItem.getOrder_count());
		preparedStatement.setInt(4, orderItem.getPrice());
		
		int rowsInserted = preparedStatement.executeUpdate();
		return rowsInserted > 0;
	}catch(SQLException ex) {
		ex.printStackTrace();
	}
	return false;
}

public List<OrderItem> getOrderItems(String orderId) {
    List<OrderItem> orderItems = new ArrayList<>();
    OrderItem orderItem = null;

    try {
        
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        connection = DBConnection.openConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, orderId);
        
        resultSet = preparedStatement.executeQuery(); 	
        while(resultSet.next()) {
            orderItem = new OrderItem();
            orderItem.setId(resultSet.getInt("id"));
            orderItem.setProduct_name(resultSet.getString("product_name"));
            orderItem.setOrder_count(resultSet.getInt("order_count"));
            orderItem.setPrice(resultSet.getInt("price"));
            
            orderItems.add(orderItem);
        }
    } catch(SQLException e) {
        e.printStackTrace();
    }
    return orderItems;
}

}

