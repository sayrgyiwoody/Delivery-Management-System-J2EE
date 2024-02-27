<%@ include file="../layout/header.jsp" %>
	
<%@ include file="../layout/topNav.jsp" %>
		
<div class="p-5 shadow max-w-sm rounded-md mx-auto mt-10 md:mt-20 bg-white">
<img src="../assets/images/deli_logo.png" class="w-40 h-40 mx-auto" alt="" />

<form class=" mx-auto px-1" action="login" method="post" >
	<input type="hidden" id="status" value="<%= request.getAttribute("status") %>" />
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
        <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
        <input type="password" name="password" id="password" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your password" >
    </div> 
    <div class="flex items-center mb-4">
    <input id="showPassword" type="checkbox" value="" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
    <label for="showPassword" class="cursor-pointer ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">Show Password</label>
</div>
    <div class="mb-4">
       <a href="forgetPassword.jsp" class="cursor-pointer text-blue-600 underline hover:text-blue-700">Forget Password ?</a>
    </div> 
    <input type="submit" value="Login" name="login" id="login" class="cursor-pointer font-semibold w-full text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 rounded-lg text-lg px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">
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
		swal("Welcome","Login Successfully","success");
	}
	if(status == "password not match"){
		swal("Sorry","password not match","error");
	}
	if(status == "fail"){
		swal("Sorry","User email or password not correct.","error");
	}
	if(status == "invalidEmail"){
		swal("Sorry","Please enter valid email","error");
	}
	if(status == "invalidPassword"){
		swal("Sorry","Please enter valid password ","error");
	}
	if(status == "resetSuccess"){
		swal("Welcome","Reset Successfully ","success");
	}
	if(status == "resetFail"){
		swal("Sorry","Reset Fail ","error");
	}
	if(status == "passwordChanged"){
		swal("Hello","Password changed successfully , now you can login with your new password.","success");
	}
	
	var passwordField = document.getElementById("password");
	    var showPasswordCheckbox = document.getElementById("showPassword");

	showPasswordCheckbox.addEventListener("change", function() {
	passwordField.type = this.checked ? "text" : "password";
	});
	</script>
</script>
<%@ include file="../layout/footer.jsp" %>

    

