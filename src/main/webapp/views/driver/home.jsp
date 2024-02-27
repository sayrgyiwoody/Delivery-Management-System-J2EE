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
<div class="p-5 md:p-10  sm:ml-64">
   <input type="hidden" id="status" value="<%= request.getAttribute("status") %>" />
<div class="flex flex-column sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4">
        <div>
        
                <!-- Modal toggle -->
<button data-modal-target="default-modal" data-modal-toggle="default-modal" class="inline-flex items-center text-gray-500 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-3 py-1.5 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700" type="button">
  Filter 
  <svg class="w-2.5 h-2.5 ms-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
                </svg>
  </button>

<!-- Main modal -->
<div id="default-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-xl max-h-full">
        <!-- Modal content -->
        <form action="/DMS/views/driver/home" method="get" class="relative bg-white rounded-lg shadow dark:bg-gray-700">
            <!-- Modal header -->
            <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                    Filter Order List
                </h3>
                <button type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="default-modal">
                    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                    </svg>
                    <span class="sr-only">Close modal</span>
                </button>
            </div>
            <!-- Modal body -->
            <div class="p-4 md:p-5 space-y-4">
                <div class="mx-auto">
				  <label for="status" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Filter by order status</label>
				  <select name="filterStatus" id="status" class="mb-4 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
				    <option value="" disabled selected>Select OrderStatus</option>
				    <option value="">all</option>
				    <option>assigned</option>
				    <option>onDelivery</option>
				    <option>delivered</option>
				    
				  </select>
				  <label for="township" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Filter by township</label>
				  
				  <select name="township" id="township" class="mb-4 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
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
				</div>
            </div>	
            <!-- Modal footer -->
            <div class="flex items-center p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600">
                <button type="submit" data-modal-hide="default-modal" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Filter</button>
                <button data-modal-hide="default-modal" type="button" class="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">Cancel</button>
            </div>
        </form>
    </div>
</div>

            
        </div>
        
        <p class=" px-4  py-2 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 dark:bg-gray-700 dark:border-gray-600 dark:text-white ">Showing <span class="text-blue-600 font-semibold">${filterStatus}</span> orders from <span class="text-blue-600 font-semibold">${township}</span> </p>
        
        <div class="relative">
            <div class="absolute inset-y-0 left-0 rtl:inset-r-0 rtl:right-0 flex items-center ps-3 pointer-events-none">
                <svg class="w-5 h-5 text-gray-500 dark:text-gray-400" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
            </div>
            <form action="home" method="get">
             <input name="searchKey" value="${searchKey}" type="text" id="table-search" class="block p-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Search by shop or customer name">
            </form>
           
        </div>
        
    </div>
    
<div class="relative overflow-x-auto shadow-md sm:rounded-lg">

    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">


            <tr>
            
                <th scope="col" class="px-6 py-3">
                    ID
                </th>
                <th scope="col" class="px-6 py-3">
                    Shop
                </th>
                <th scope="col" class="px-6 py-3">
                    Customer
                </th>
                <th scope="col" class="px-6 py-3">
                    City
                </th>
                <th scope="col" class="px-6 py-3">
                    Township
                </th>
                <th scope="col" class="px-6 py-3">
                    Created At
                </th>
                <th scope="col" class="px-6 py-3">
                    Status
                </th>
                
                
                <th scope="col" class="px-6 py-3">
                    Action
                </th>
            </tr>
           
        </thead>
        <tbody>
        
		
		<c:forEach items="${orderList}" var="order"> 
            <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                
                <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                    ${order.id}
                </th>
                <td class="px-6 py-4">
                    ${order.shop_name}
                </td>
                <td class="px-6 py-4">
                    ${order.customer_name}
                </td>
                <td class="px-6 py-4">
                    ${order.customer_city}
                </td>
                <td class="px-6 py-4">
                    ${order.customer_township}
                </td>
                <td class="px-6 py-4">
                    <fmt:formatDate value="${order.created_at}" pattern="dd MMM yyyy" />
                    
                </td>
                <td class="px-6 py-4">
                    
                    <!-- Modal toggle -->
					<button data-modal-target="status-modal-${order.id }" data-modal-toggle="status-modal-${order.id }" class="py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700" type="button">
					  ${order.order_status}
					</button>
					
					<!-- Main modal -->
					<div id="status-modal-${order.id }" tabindex="-1" aria-hidden="true" class="parent-box hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
					    <div class="relative p-4 w-full max-w-2xl max-h-full">
					        <!-- Modal content -->
					        <form action="home" method="post" class="relative bg-white rounded-lg shadow dark:bg-gray-700">
					            <!-- order id to send to servlet -->
					            <input name="orderId" type="hidden" value="${order.id}">
					            
					            <!-- current order status -->
					            <input id="orderStatus" type="hidden" value="${order.order_status}">
					            
					            <!-- Modal header -->
					            <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
					                <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
					                    Change order status for order ${order.id }
					                </h3>
					                <button type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="status-modal-${order.id }">
					                    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
					                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
					                    </svg>
					                    <span class="sr-only">Close modal</span>
					                </button>
					            </div>
					            <!-- Modal body -->
					            <div class="px-8 md:px-12 py-6 md:py-8 space-y-4">
					            <h3 class="text-lg font-semibold mb-1  text-center ">Current Delivery Status : <span class="text-gray-800">${order.order_status}</span></h3>
									<ol class="flex items-center w-fit">
									    <li class="process-assigned whitespace-nowrap flex w-full items-center after:content-[''] after:w-full after:h-1 after:border-b after:border-gray-100 after:border-4 after:inline-block dark:after:border-gray-700">
									        <span class="flex items-center justify-center w-10 h-10 bg-gray-100 rounded-full lg:h-12 lg:w-12 dark:bg-gray-700 shrink-0">
									            <svg class="w-4 h-4 text-gray-500 lg:w-4 lg:h-4 dark:text-gray-100" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"><path fill="currentColor" d="M4 21q-.825 0-1.412-.587T2 19V5q0-.825.588-1.412T4 3h16q.825 0 1.413.588T22 5v14q0 .825-.587 1.413T20 21zm1-4h5v-2H5zm9.55-2l4.95-4.95l-1.425-1.425l-3.525 3.55l-1.425-1.425l-1.4 1.425zM5 13h5v-2H5zm0-4h5V7H5z"/></svg>
									           </span>
									         <p class="mx-2 hidden md:block"> Assigned </p>
									    </li>
									    <li class="process-onDelivery whitespace-nowrap flex w-full items-center after:content-[''] after:w-full after:h-1 after:border-b after:border-gray-100 after:border-4 after:inline-block dark:after:border-gray-700">
									        <span class="flex items-center justify-center w-10 h-10 bg-gray-100 rounded-full lg:h-12 lg:w-12 dark:bg-gray-700 shrink-0">
									            
									            <svg class="w-4 h-4 text-gray-500 lg:w-5 lg:h-5 dark:text-gray-100" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32"><path fill="currentColor" d="M4 16h12v2H4zm-2-5h10v2H2z"/><path fill="currentColor" d="m29.919 16.606l-3-7A.999.999 0 0 0 26 9h-3V7a1 1 0 0 0-1-1H6v2h15v12.556A3.992 3.992 0 0 0 19.142 23h-6.284a4 4 0 1 0 0 2h6.284a3.98 3.98 0 0 0 7.716 0H29a1 1 0 0 0 1-1v-7a.997.997 0 0 0-.081-.394M9 26a2 2 0 1 1 2-2a2.002 2.002 0 0 1-2 2m14-15h2.34l2.144 5H23Zm0 15a2 2 0 1 1 2-2a2.002 2.002 0 0 1-2 2m5-3h-1.142A3.995 3.995 0 0 0 23 20v-2h5Z"/></svg>
									        </span>
									         <p class="mx-2 hidden md:block"> On Delivery </p>
									    </li>
									    <li class="process-delivered whitespace-nowrap flex items-center w-fit">
									        <span class="flex items-center justify-center w-10 h-10 bg-gray-100 rounded-full lg:h-12 lg:w-12 dark:bg-gray-700 shrink-0">
									            
									            <svg class="w-4 h-4 text-gray-500 lg:w-5 lg:h-5 dark:text-gray-100" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"><g fill="none" stroke="currentColor" stroke-linecap="round" stroke-width="2"><path d="m9 10l3.258 2.444a1 1 0 0 0 1.353-.142L20 5"/><path d="M21 12a9 9 0 1 1-6.67-8.693"/></g></svg>
									        </span>
									         <p class="mx-2 hidden md:block"> Delivered </p>
									    </li>
									</ol>
									
									
									
									<div class="change-status-div">
									<hr>
									<h3 class="text-lg font-medium mb-3 mt-3 text-center ">Change Status to :</h3>
									<div class="flex  justify-center">
									    <div class="flex items-center me-4 md:me-6">
									        <input  id="inline-radio${order.id}" type="radio" value="assigned" name="orderStatus" class="cursor-pointer w-6 h-6 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
									        <label for="inline-radio${order.id}" class="ms-2 text-base font-medium text-gray-900 dark:text-gray-500 cursor-pointer">Assigned</label>
									    </div>
									    <div class="flex items-center me-4 md:me-6">
									        <input  id="inline-2-radio${order.id}" type="radio" value="onDelivery" name="orderStatus" class="cursor-pointer w-6 h-6 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
									        <label for="inline-2-radio${order.id}" class="ms-2 text-base font-medium text-gray-900 dark:text-gray-300 cursor-pointer">On Delivery</label>
									    </div>
									    <div class="flex items-center">
									        <input  id="inline-3-radio${order.id}" type="radio" value="delivered" name="orderStatus" class="cursor-pointer w-6 h-6 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600">
									        <label for="inline-3-radio${order.id}" class="ms-2 text-base font-medium text-gray-900 dark:text-gray-300 cursor-pointer">Delivered</label>
									    </div>
									    
									</div>
									</div>
									</div>
					            <!-- Modal footer -->
					            <div class="flex items-center p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600">
					                <button data-modal-hide="status-modal-${order.id }" type="submit" class="disabled:bg-gray-400 btn-status-change text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Change</button>
					                <button data-modal-hide="status-modal-${order.id }" type="button" class="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">Back</button>
					            </div>
					        </form>
					    </div>
					</div>
                </td>
                
                
                <td class=" py-4 space-x-2 flex px-2 md:px-0">
                    <a href="order?action=view&orderId=${order.id}" class="font-medium text-blue-600 dark:text-blue-500 hover:underline bg-blue-600 shadow text-white px-3 py-2 rounded-md">view</a>
                	
                </td>
            </tr>
             </c:forEach>
            
        </tbody>
    </table>
</div>

</div>


<script>

	const status = document.getElementById("status").value;
	if(status == "success"){
		swal("Status Changed","Order status changed successfully","success");
	}
	if(status == "fail"){
		swal("Sorry","Order status change fail","error");
	}
	if(status == "require info"){
		swal("Status Required","Please select order status.","info");
	}
	
	// Get all rows containing order statuses
	var rows = document.querySelectorAll('.parent-box');

	// Iterate through rows
	rows.forEach(function(row) {
	    // Get order status for this row
	    var orderStatus = row.querySelector('#orderStatus').value;

	    // Get radio buttons within this row
	    var radios = row.querySelectorAll('input[type="radio"]');
	    
	    // Loop over radio buttons with a step of 3
	    for (var i = 0; i < radios.length; i += 3) {
	        if (orderStatus === 'assigned') {
	            radios[i].disabled = true;
	            radios[i + 2].disabled = true;
	        } else if (orderStatus === 'onDelivery') {
	        	radios[i].disabled = true;
	            radios[i + 1].disabled = true;
	        } else if (orderStatus === 'delivered') {
	        	radios[i].disabled = true;
	            radios[i + 1].disabled = true;
	            radios[i + 2].disabled = true;
	            row.querySelector(".change-status-div").classList.add('hidden');
	        }
	    }
	    
	    
	    if(orderStatus == 'assigned'){
	    	var process = row.querySelector(".process-assigned");
	    	
	    	process.classList.add("text-blue-600");
	    	process.querySelector("span").classList.remove("bg-gray-100");
	    	process.querySelector("span").classList.add("bg-blue-100");
	    	process.querySelector("svg").classList.remove("text-gray-500");
	    	process.querySelector("svg").classList.add("text-blue-600");
	    }else if(orderStatus == 'onDelivery'){
	    	var process = row.querySelector(".process-onDelivery");
	    	
	    	process.classList.add("text-blue-600");
	    	process.querySelector("span").classList.remove("bg-gray-100");
	    	process.querySelector("span").classList.add("bg-blue-100");
	    	process.querySelector("svg").classList.remove("text-gray-500");
	    	process.querySelector("svg").classList.add("text-blue-600");
	    }else if(orderStatus == 'delivered'){
	    	var process = row.querySelector(".process-delivered");
	    	
	    	process.classList.add("text-blue-600");
	    	process.querySelector("span").classList.remove("bg-gray-100");
	    	process.querySelector("span").classList.add("bg-blue-100");
	    	process.querySelector("svg").classList.remove("text-gray-500");
	    	process.querySelector("svg").classList.add("text-blue-600");
	    	
	    	//disable the submit button 
	    	row.querySelector(".btn-status-change").classList.add('hidden');
	    }
	    
	    
	    
	    
	});
	
	// Select all input elements that are disabled
	var disabledInputs = document.querySelectorAll('input:disabled');

	// Iterate over each disabled input
	disabledInputs.forEach(function(input) {
	    // Get the parent label element
	    var parent = input.parentNode;
	    
	    var label = parent.querySelector('label');

	    label.classList.remove('text-gray-900');
	    label.classList.add('text-gray-400');
	});
    

</script>

<%@ include file="../../layout/footer.jsp" %>