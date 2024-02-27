<%@ include file="../layout/header.jsp" %>
	
	<%

String email = (String) request.getSession().getAttribute("email");
if (email == null) {
    
    response.sendRedirect("login.jsp");
}

%>
		<div class="max-w-sm mx-auto mt-10 mb-4 md:mt-20">
		<a href="forgetPassword" class="py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
			<svg class="w-6 h-6 inline-block" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"><path fill="currentColor" d="M17.59 18L19 16.59L14.42 12L19 7.41L17.59 6l-6 6z"/><path fill="currentColor" d="m11 18l1.41-1.41L7.83 12l4.58-4.59L11 6l-6 6z"/></svg>
			Request OTP again</a>
		</div>
		
	<div class="max-w-sm mx-auto  p-5 bg-white shadow rounded-xl">
	
		<form class="max-w-sm mx-auto" action="validateOtp" method="post" >
		<p class="mb-2 bg-green-400 border border-gray-300 text-gray-900 text-sm text-white rounded focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">If you're seeing this page which means otp code is sent to your email address.</p>
	<input type="hidden" id="status" value="<%= request.getAttribute("status") %>" />
		<input type="hidden" class="hide" name="message" id="message" value="<%= request.getAttribute("message") %>" />
	   <label for="otp" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Otp</label>
		  <div class="relative">
		    <div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
		      <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 16">
		        <path d="m10.036 8.278 9.258-7.79A1.979 1.979 0 0 0 18 0H2A1.987 1.987 0 0 0 .641.541l9.395 7.737Z"/>
		        <path d="M11.241 9.817c-.36.275-.801.425-1.255.427-.428 0-.845-.138-1.187-.395L0 2.6V14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2.5l-8.759 7.317Z"/>
		      </svg>
		    </div>
		    <input type="text" id="otp" name="otp" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required placeholder="Enter otp" >
		  </div>
      <div class="mt-5">
      	<button type="submit" class="text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">Reset Password</button>      		
      </div>  
</form>
	</div>
	
<script type="text/javascript">
	var status = document.getElementById("status").value;
	if(status == "wrong"){
		swal("Sorry","Please enter correct otp","error");
	}
	
	
	</script>
<%@ include file="../layout/footer.jsp" %>