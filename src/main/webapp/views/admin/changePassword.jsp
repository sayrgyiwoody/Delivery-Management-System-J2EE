<%@ include file="../../layout/header.jsp" %>
<%@ include file="../../layout/admin_sidenav.jsp" %>
<%@ page import="model.User" %>


<!-- Main content -->
<input type=hidden id="status" value="<%= request.getAttribute("status") %>" />
<div class="p-5 md:p-10  sm:ml-64">
<div class="max-w-xl mx-auto bg-white shadow p-5 rounded-xl">
 <h2 class="font-semibold text-2xl text-blue-600 mb-4">Change your account Password</h2>
       <form  action="changePassword" method="post">
       <input type="hidden" id="status" value="<%= request.getAttribute("status") %>" />
       <div class="mb-4">
	        <label for="oldPassword" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Old Password</label>
	        <input type="password" name="oldPassword" id="oldPassword" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your password" >
	    </div> 
         <div class="mb-4">
	        <label for="newPassword" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">New Password</label>
	        <input type="password" name="newPassword" id="newPassword" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your password" >
	    </div> 
	    <div class="mb-4">
	        <label for="confirmPassword" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Confirm Password</label>
	        <input type="password" name="confirmPassword" id="confirmPassword" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your password" >
	    </div> 
    <div class="flex items-center mb-4">
    <input id="showPassword" type="checkbox" value="" class="cursor-pointer w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
    <label for="showPassword" class="cursor-pointer ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">Show Password</label>
</div>
    <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-md text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Change Password</button>
       
       </form>
      </div>
</div>

<script>

	const status = document.getElementById("status").value;
	if(status == "success"){
		swal("Success","Password changed successfully","success");
		
	}
	if(status == "fail"){
		swal("Sorry","Password Can't Change","error");
		
	}
	if(status == "passwordNotMatch"){
		swal("Sorry","Please enter same password","error");
	}
	if(status == "wrongOldPass"){
		swal("Sorry","Your old password wrong","error");
	}
	var oldPassword = document.getElementById("oldPassword");
	var passwordField = document.getElementById("newPassword");
    var confirmPasswordField = document.getElementById("confirmPassword");
    var showPasswordCheckbox = document.getElementById("showPassword");

    showPasswordCheckbox.addEventListener("change", function() {
        var type = this.checked ? "text" : "password";
        oldPassword.setAttribute("type",type);
        passwordField.setAttribute("type", type);
        confirmPasswordField.setAttribute("type", type);
    });
</script>

<%@ include file="../../layout/footer.jsp" %>