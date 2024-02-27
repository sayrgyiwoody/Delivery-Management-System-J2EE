<%@ include file="../layout/header.jsp" %>
	
<%@ include file="../layout/topNav.jsp" %>

<div class="p-5 shadow max-w-lg rounded-md mx-auto mt-10 md:mt-20 bg-white mb-10">
<img src="../assets/images/deli_logo.png" class="w-40 h-40 mx-auto" alt="" />

<form class=" mx-auto px-1" action="register" method="post" >
	<input type="hidden" id="status" value="<%= request.getAttribute("status") %>" />
   <div class="mb-4">
        <label for="username" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your Name</label>
        <input type="text" name="username" id="username" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your name" >
    </div> 
   <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your Email</label>
	  
	  <div class="relative mb-4">
	    <div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
	      <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 16">
	        <path d="m10.036 8.278 9.258-7.79A1.979 1.979 0 0 0 18 0H2A1.987 1.987 0 0 0 .641.541l9.395 7.737Z"/>
	        <path d="M11.241 9.817c-.36.275-.801.425-1.255.427-.428 0-.845-.138-1.187-.395L0 2.6V14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2.5l-8.759 7.317Z"/>
	      </svg>
	    </div>
	    <input type="email" id="email" name="email" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your email" >
	  </div>
	  <div class="mb-4">
        <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your Phone</label>
        <input type="text" name="phone" id="phone" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your phone number" >
    </div> 
	  
		<label for="role" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Select role</label>
		<select name="role" id="role" class="mb-4 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
		  <option disabled selected>Choose account type</option>
		  <option value="shop">shop</option>
		  <option value="driver">driver</option>
		</select>
			  
     <div class="mb-4">
        <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
        <input type="password" name="password" id="password" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your password" >
    </div> 
    <div class="mb-4">
        <label for="confirm_password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Confirm Password</label>
        <input type="password" name="confirm_password" id="confirm_password" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your password" >
    </div> 
    <div class="flex items-center mb-4">
    <input id="showPassword" type="checkbox" value="" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
    <label for="showPassword" class="cursor-pointer ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">Show Password</label>
</div>
    <input type="submit" value="Register" name="login" id="login" class="cursor-pointer font-semibold w-full text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 rounded-lg text-lg px-5 py-2.5 me-2 mt-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">
</form>
</div>

<%@ page import="model.User" %>

	   <% 
	  
	   Object userObj = session.getAttribute("user");
	   User user = (User) userObj;
	    if (userObj != null) {
	    	if("admin".equals(user.getRole())){
	    		response.sendRedirect("/DMS/views/admin/home");
	    	}else if("shop".equals(user.getRole())){
	    		response.sendRedirect("/DMS/views/shop/home");
	    	}else if("driver".equals(user.getRole())){
	    		response.sendRedirect("/DMS/views/driver/home");
	    	}
	    	 
	    }
%>

<script type="text/javascript">
	var status = document.getElementById("status").value;
	if(status == "success"){
		swal("Welcome","Account Created Successfully","success");
	}
	if(status == "invalidName"){
		swal("Sorry","Please Enter Name","error");
	}
	if(status == "invalidPassword"){
		swal("Sorry","Please Enter Password","error");
	}
	if(status == "invalidLength"){
		swal("Sorry","Please Enter at least 5 digit Password","error");
	}
	if(status == "pswNotMatch"){
		swal("Sorry","Please Enter Same Password","error");
	}
	if(status == "invalidEmail"){
		swal("Sorry","Please Enter Email","error");
	}
	if(status == "emailExist"){
		swal("Sorry","This Email is already used by another account.","error");
	}
	if(status == "invalidRole"){
		swal("Sorry","Please Select account type","error");
	}
	
	var passwordField = document.getElementById("password");
    var confirmPasswordField = document.getElementById("confirm_password");
    var showPasswordCheckbox = document.getElementById("showPassword");

    showPasswordCheckbox.addEventListener("change", function() {
        var type = this.checked ? "text" : "password";
        passwordField.setAttribute("type", type);
        confirmPasswordField.setAttribute("type", type);
    });
</script>

<%@ include file="../layout/footer.jsp" %>


