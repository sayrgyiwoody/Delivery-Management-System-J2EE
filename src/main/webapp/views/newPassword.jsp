<%@ include file="../layout/header.jsp" %>

<%

Boolean otpVerified = (Boolean) request.getSession().getAttribute("otpVerified");
if (otpVerified == null || !otpVerified) {
    
    response.sendRedirect("login.jsp");
}

%>
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>" />
	<div class="max-w-sm mx-auto mt-10 md:mt-20 p-5 bg-white shadow rounded-xl">
	<form action="newPassword" method="post" class="max-w-sm mx-auto" >
     <div class="mb-6">
        <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
        <input type="password" name="password" id="password" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter new password" >
    </div> 
    <div class="mb-6">
        <label for="confirm_password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Confirm password</label>
        <input type="password" name="confirm_password" id="confirm_password" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter confirm password" >
    </div>
    <input type="submit" value="Reset" class="cursor-pointer text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"/>
</form>
</div>

<script type="text/javascript">
var status = document.getElementById("status").value;
	if(status == "password not match"){
		swal("Sorry","Please enter same passwords","error");
	}
	if(status == "fail"){
		swal("Sorry","Password update failed","error");
	}
</script>


<%@ include file="../layout/footer.jsp" %>