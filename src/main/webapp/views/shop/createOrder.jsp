<%@ include file="../../layout/header.jsp" %>
	
<%@ include file="../../layout/shop_sidenav.jsp" %>


<%@ page import="model.User" %>

<%

Object userObj = session.getAttribute("user");

if (userObj == null) {
    response.sendRedirect("/DMS/views/login.jsp");
} else if (userObj == null || !(userObj instanceof User)) {
    response.sendRedirect("/DMS/views/login.jsp");
} else {
    User user = (User) userObj;
    if (!"shop".equals(user.getRole())) {
        response.sendRedirect("/DMS/views/login.jsp");
    }
}
%>

<!-- Main content -->
<div class="p-5 md:p-10 sm:ml-64">

<div class="md:grid md:space-x-4 space-y-4 md:space-y-0 md:grid-cols-3 mb-4">

         <div class=" p-5 rounded bg-gray-50 dark:bg-gray-800">
         <h2 class="text-2xl  dark:text-gray-300 text-center mb-4 text-blue-600">
    Customer Information Form
  </h2>
  <hr class="mb-4">
            <div class="mb-4">
		        <label for="cusName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Customer Name</label>
		        <input type="text" name="cusName" id="cusName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter customer name" >
		    </div> 
		    <div class="mb-4">
		        <label for="cusPhone" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Customer Phone Number</label>
		        <input type="text" name="cusPhone" id="cusPhone" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter customer phone number" >
		    </div> 
		    <div class="mb-4">
		        <label for="cusCity" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Customer City</label>
		        <select name="cusCity" id="cusCity" class="mb-4 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
				  <option selected value="Yangon">Yangon</option>
				</select>
				<label for="cusTownship" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Customer Township</label>
				<select name="cusTownship" id="cusTownship" class="mb-4 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
				    <option value="" disabled selected>Select Customer Town Ship</option>
				    <option value="Ahlon">Ahlon</option>
				    <option value="Bahan">Bahan</option>
				    <option value="Dagon">Dagon</option>
				    <option value="Hlaing">Hlaing</option>
				    <option value="Insein">Insein</option>
				    <option value="Kamayut">Kamayut</option>
				    <option value="Kyauktada">Kyauktada</option>
				    <option value="Lanmadaw">Lanmadaw</option>
				    <option value="Latha">Latha</option>
				    <option value="Mayangon">Mayangon</option>
				    <option value="Mingala Taungnyunt">Mingala Taungnyunt</option>
				    <option value="North Dagon">North Dagon</option>
				    <option value="Pabedan">Pabedan</option>
				    <option value="Sanchaung">Sanchaung</option>
				    <option value="South Dagon">South Dagon</option>
				    <option value="Thingangyun">Thingangyun</option>
				    <option value="Yankin">Yankin</option>
				</select>		
				<label for="cusDetailAddress" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Customer Detail Address</label>
				<textarea id="cusDetailAddress" name="cusDetailAddress" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter customer detail address"></textarea>			
		    </div> 
         </div>
         
<div class="p-5 col-span-2 rounded bg-gray-50 h-88   dark:bg-gray-800">
  
  <h2 class="text-2xl text-blue-600 dark:text-gray-300 text-center mb-4">
    Order Item Form
  </h2>
  <hr class="mb-4">
  
<form id="orderForm" >
        <div class="w-full">
            <label for="productName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Product Name</label>
            <input type="text" id="productName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter product name">
        </div>
        <div class="w-full  mt-4">
            <label for="productPrice" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Product Price</label>
            <input min="0" type="number" id="productPrice" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter product price">
        </div>
        <div class="w-full  mt-4">
            <label for="orderCount" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Order Count</label>
            <input min="1" type="number" id="orderCount" aria-describedby="helper-text-explanation" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Order Count: " required>
        </div>
        
        <button type="submit" class="mt-4 py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">Add Item</button>
        
    </form>
    
    	 <div class="w-full  mt-4" id="ordersContainer">
        	<h1 class="text-2xl font-bold mb-4">Order Items</h1>
        	<div class=" flex-wrap px-4 bg-gray-100 rounded-xl p-3" id="orders" style=" display: flex;">
            
          
          </div>
		</div>
      

</div>

</div>
<div class="">
	<button id="btn-submit" class="flex text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-md text-sm px-5 py-3 text-lg text-center me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Submit Order
	<svg class="inline-block ms-1 w-5 h-5" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"><g fill="none"><path d="M24 0v24H0V0zM12.593 23.258l-.011.002l-.071.035l-.02.004l-.014-.004l-.071-.035c-.01-.004-.019-.001-.024.005l-.004.01l-.017.428l.005.02l.01.013l.104.074l.015.004l.012-.004l.104-.074l.012-.016l.004-.017l-.017-.427c-.002-.01-.009-.017-.017-.018m.265-.113l-.013.002l-.185.093l-.01.01l-.003.011l.018.43l.005.012l.008.007l.201.093c.012.004.023 0 .029-.008l.004-.014l-.034-.614c-.003-.012-.01-.02-.02-.022m-.715.002a.023.023 0 0 0-.027.006l-.006.014l-.034.614c0 .012.007.02.017.024l.015-.002l.201-.093l.01-.008l.004-.011l.017-.43l-.003-.012l-.01-.01z"/><path fill="currentColor" d="M20.25 3.532a1 1 0 0 1 1.183 1.329l-6 15.5a1 1 0 0 1-1.624.362l-3.382-3.235l-1.203 1.202c-.636.636-1.724.186-1.724-.714v-3.288L2.309 9.723a1 1 0 0 1 .442-1.691l17.5-4.5Zm-2.114 4.305l-7.998 6.607l3.97 3.798zm-1.578-1.29L4.991 9.52l3.692 3.53l7.875-6.505Z"/></g></svg>
	</button>
</div>	


<script src="/DMS/assets/js/createOrder.js"></script>




<%@ include file="../../layout/footer.jsp" %>