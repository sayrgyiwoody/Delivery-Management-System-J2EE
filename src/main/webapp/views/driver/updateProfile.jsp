<%@ include file="../../layout/header.jsp" %>
<%@ include file="../../layout/driver_sidenav.jsp" %>
<%@ page import="model.User" %>

<%

Object userObj = session.getAttribute("user");

if (userObj == null) {
    response.sendRedirect("/DMS/views/login.jsp");
} else if (userObj == null || !(userObj instanceof User)) {
    response.sendRedirect("/DMS/views/login.jsp");
} else {
    User user = (User) userObj;
    if (!"driver".equals(user.getRole())) {
        response.sendRedirect("/DMS/views/login.jsp");
    }
}
%>
<!-- Main content -->
<input type=hidden id="status" value="<%= request.getAttribute("status") %>" />
<div class="p-5 md:p-10  sm:ml-64">
<div class="max-w-xl mx-auto bg-white shadow p-5 rounded-xl">
<div class="p-2 border-2 rounded-xl w-24 h-24 flex items-center justify-center mx-auto">
          <img class="mx-auto  object-cover mb-1 " src="/DMS/assets/images/logo.png" alt="">
       </div>
       <form  action="account" method="post">
        <input type="hidden" id="id" name="id" value="${user.id }" />
    <div class="mb-4">
        <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Name</label>
        <input value="${user.name}" type="text" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your name" >
    </div>
    <div class="mb-4">
        <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Email</label>
        <input value="${user.email }" type="text" name="email" id="email" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your email" >
    </div>
    <div class="mb-4">
        <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Phone Number</label>
        <input value="${user.phone }" type="text" name="phone" id="phone" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your phone number" >
    </div>
    <label for="address" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Address</label>
	<textarea id="address" name="address" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 mb-4" placeholder="Enter your address">${user.address }</textarea>
    
    <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-md text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Update Information</button>
       
       </form>
      </div>
</div>

<script>
	const status = document.getElementById("status").value;
	
	if(status == "updated"){
		swal("Success","Account information updated","success");
	}
	
</script>

<%@ include file="../../layout/footer.jsp" %>