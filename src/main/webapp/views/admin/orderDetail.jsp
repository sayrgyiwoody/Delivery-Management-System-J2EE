<%@ include file="../../layout/header.jsp" %>
	
<%@ include file="../../layout/admin_sidenav.jsp" %>

<%@ page import="model.User" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Order" %>

<% Customer customer = (Customer) request.getAttribute("customer"); %>
<% Order order = (Order) request.getAttribute("order"); %>

<%

Object userObj = session.getAttribute("user");

if (userObj == null) {
    response.sendRedirect("/DMS/views/login.jsp");
} else if (userObj == null || !(userObj instanceof User)) {
    response.sendRedirect("/DMS/views/login.jsp");
} else {
    User user = (User) userObj;
    if (!"admin".equals(user.getRole())) {
        response.sendRedirect("/DMS/views/login.jsp");
    }
}
%>

<!-- Main content -->
<div class="p-5 md:p-10  sm:ml-64">

<a href="home" class=" ms-5 py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
<svg class="w-6 h-6 inline-block" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"><path fill="currentColor" d="M17.59 18L19 16.59L14.42 12L19 7.41L17.59 6l-6 6z"/><path fill="currentColor" d="m11 18l1.41-1.41L7.83 12l4.58-4.59L11 6l-6 6z"/></svg>
Back</a>
   
<div class=" max-w-3xl  mx-auto rounded-lg p-5 md:grid md:grid-cols-3 space-y-3 md:space-y-0 md:space-x-3 ">
	
	<div>
			<div class="block cursor-pointer mb-3  p-5 bg-white border border-gray-200 rounded-lg shadow  dark:bg-gray-800 dark:border-gray-700 ">
		
			<h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">Order Information</h5>
			<p class="font-normal text-gray-700 dark:text-gray-400">Order ID : <span class="font-semibold text-zinc-800"><%= order.getId() %></span></p>	
			<%
			    // Assuming order.getCreated_at() returns a java.sql.Timestamp object
			    java.sql.Timestamp createdTimestamp = order.getCreated_at();
			    
			    // Create a SimpleDateFormat object to format the timestamp
			    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
			
			    // Format the timestamp
			    String formattedCreatedDate = sdf.format(createdTimestamp);
			%>
			<p class="font-normal text-gray-700 dark:text-gray-400">Created At : <span class="font-semibold text-zinc-800"><%= formattedCreatedDate %></span></p>	
			<p class="font-normal text-gray-700 dark:text-gray-400">Shop : <span class="font-semibold text-zinc-800"><%= order.getShop_name() %></span></p>
		    <p class="font-normal text-gray-700 dark:text-gray-400">Shop Address : <span class="font-semibold text-zinc-800"><%= order.getShop_address() %> </span></p>
			<p class="font-normal text-gray-700 dark:text-gray-400">Driver : <span class="font-semibold text-zinc-800"><%= (order.getDriver_name() != null) ? order.getDriver_name() : "Not assigned" %></span></p>
			<p class="font-normal text-gray-700 dark:text-gray-400">Order Status : <span class="font-semibold text-zinc-800"><%= order.getOrder_status() %></span></p>
		</div>
		
		<div class="block cursor-pointer   p-5 bg-white border border-gray-200 rounded-lg shadow  dark:bg-gray-800 dark:border-gray-700 ">
		
			<h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">Customer Information</h5>
			<p class="font-normal text-gray-700 dark:text-gray-400">Name : <span class="font-semibold text-zinc-800"><%= customer.getCustomer_name() %></span></p>
			<p class="font-normal text-gray-700 dark:text-gray-400">Phone : <span class="font-semibold text-zinc-800"><%= customer.getCustomer_phone() %></span></p>
			<p class="font-normal text-gray-700 dark:text-gray-400">City : <span class="font-semibold text-zinc-800"><%= customer.getCity() %></span></p>
			<p class="font-normal text-gray-700 dark:text-gray-400">Township : <span class="font-semibold text-zinc-800"><%= customer.getTownship() %></span></p>
			<p class="font-normal text-gray-700 dark:text-gray-400">Detail Address : <span class="font-semibold text-zinc-800"><%= customer.getDetail_address() %></p>
		</div>
	</div>
	
	
	<div class="col-span-2">
	

<div class="relative overflow-x-auto">
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                 
                <th scope="col" class="px-6 py-3">
                    Product Name
                </th>
                <th scope="col" class="px-6 py-3">
                    Price
                </th>
                <th scope="col" class="px-6 py-3">
                    Order Count
                </th>
                <th scope="col" class="px-6 py-3">
                    Total Price
                </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${orderItems}" var="item"> 
            <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                
                <td class="px-6 py-4">
                    ${item.product_name}
                </td>
                <td class="px-6 py-4">
                    ${item.price} Ks
                    
                </td>
                
                <td class="px-6 py-4">
                    ${item.order_count}
                </td>
                <td class="px-6 py-4">
                    ${item.order_count * item.price} Ks
                </td>
                
                
            </tr>
             </c:forEach>
             
        </tbody>
    </table>
    
</div>
	
	</div>
	
	


</div>


</div>



<%@ include file="../../layout/footer.jsp" %>