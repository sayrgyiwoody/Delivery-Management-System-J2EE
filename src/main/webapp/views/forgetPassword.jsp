<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/topNav.jsp" %>

<div class="max-w-sm mx-auto mt-10 md:mt-20 p-5 bg-white shadow rounded-xl">

<input type="hidden" id="status" value="<%= request.getAttribute("status") %>" />
	<h2 class="text-2xl font-semibold mb-1 text-zinc-800">Forgot your password?</h2>
	<p class="text-sm text-zincc-600">Change your password in three easy steps. This will help you to secure your password!</p>
	<div class="text-sm bg-gray-100 rounded my-3 px-3 py-2">
		 <p>1. Enter your email address below.</p>
		 <p>2. Our system will send you an OTP to your email</p>
		 <p>3. Enter the OTP on the next page</p>
	</div>
	<form class="" action="forgetPassword" method="post" >
		
	   <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your Email</label>
		  <div class="relative">
		    <div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
		      <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 16">
		        <path d="m10.036 8.278 9.258-7.79A1.979 1.979 0 0 0 18 0H2A1.987 1.987 0 0 0 .641.541l9.395 7.737Z"/>
		        <path d="M11.241 9.817c-.36.275-.801.425-1.255.427-.428 0-.845-.138-1.187-.395L0 2.6V14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2.5l-8.759 7.317Z"/>
		      </svg>
		    </div>
		    <input type="email" id="email" name="email" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter your email" >
		  </div>
      <div class="mt-5">
      
      	<button type="submit" class="cursor-pointer text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">Get New Password</button>
      	<a class="text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700" href="/DMS/views/login">
      		Back to Login   	
      	</a>
      </div>  
</form>
</div>


<script type="text/javascript">
var status = document.getElementById("status").value;
	if(status == "emailNotExit"){
		swal("Sorry","Email Not Exist!","error");
	}
</script>
<%@ include file="../layout/footer.jsp" %>