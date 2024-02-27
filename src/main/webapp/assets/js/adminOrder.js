//Get all rows containing order statuses
var rows = document.querySelectorAll('.parent-box');

// Iterate through rows
rows.forEach(function(row) {
    // Get order status for this row
    var orderStatus = row.querySelector('#orderStatus').value;

    // Get radio buttons within this row
    
    if(orderStatus == 'requesting'){
    	var process = row.querySelector(".process-requesting");
    	
    	process.classList.add("text-blue-600");
    	process.querySelector("span").classList.remove("bg-gray-100");
    	process.querySelector("span").classList.add("bg-blue-100");
    	process.querySelector("svg").classList.remove("text-gray-500");
    	process.querySelector("svg").classList.add("text-blue-600");
    }else if(orderStatus == 'assigned'){
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

//////

var selectedItems = [];

const updateSelectedItems = (itemId,isSelected)=>{
	if(isSelected && itemId !== undefined){
		selectedItems.push(itemId);
	}else{
		const index = selectedItems.indexOf(itemId);
		if(index > -1){
			selectedItems.splice(index,1);
		}
	}
	
}
   document.querySelectorAll('.order-checkbox').forEach(checkbox => {
        checkbox.addEventListener("change", (e) => {
            const orderId = e.target.dataset.orderId;
            const isSelected = e.target.checked;
            updateSelectedItems(orderId, isSelected);
            
        });
    })	    
    
    document.getElementById("checkbox-all-search").addEventListener("change", (e)=> {
	selectedItems = [];
    const checkboxes = document.querySelectorAll('.order-checkbox'); 
    const isSelectedAll = e.target.checked; 

    checkboxes.forEach(checkbox => {
        checkbox.checked = isSelectedAll; 
        const orderId = checkbox.dataset.orderId;
        updateSelectedItems(orderId, isSelectedAll); 
		
});
});


document.getElementById("btn-delete-selected").addEventListener("click",()=>{
	var dataToSend = {
		    selectedItems: selectedItems
		};

		// Convert the data to JSON format
		var jsonData = JSON.stringify(dataToSend);

		// Define the fetch options
		var fetchOptions = {
		    method: 'POST',
		    headers: {
		        'Content-Type': 'application/json'
		    },
		    body: jsonData
		};


		

		// Make the fetch request
		fetch('order', fetchOptions)
			
		    .then(response => {
		        if (!response.ok) {
		            throw new Error('Network response was not ok');
		        }
		        return response.text(); // Assuming you're expecting a text response
		    })
		    .then(data => {

		        if(data == "success"){
		            swal("Success","Selected Orders deleted successfully","success")
		            .then((result) => {
		                const baseUrl = window.location.protocol + "//" + window.location.host;
		                const newPath = "/DMS/views/admin/home";
		                window.location.href = baseUrl + newPath;
		              });
		          
		         
		        }else if(data == "fail"){
		          swal("Sorry","Order delete fail.","error");
		         
		        }
		    })
		    .catch(error => {
		        console.error('There was a problem with your fetch operation:', error);
		    });
});



let driverId;
document.addEventListener('DOMContentLoaded',() =>{
const driverElement = document.getElementById('driver');

driverElement.addEventListener('change',(e)=>{
	driverId = e.target.value;
	
})

})

const btn_submit = document.querySelector("#btn-submit");

btn_submit.addEventListener("click",()=> {

  sendDataToServlet(driverId,selectedItems.slice());//send data to servlet

selectedItems.length = 0;
})

function showSpinner(){
	document.querySelector("#btn-assign").classList.add('hidden');
	document.querySelector("#btn-spin").classList.remove('hidden');
}

function hideSpinner(){
	document.querySelector("#btn-assign").classList.remove('hidden');
	document.querySelector("#btn-spin").classList.add('hidden');
}


function sendDataToServlet(driverId,selectedItems) {

// Define the data you want to send
var dataToSend = {
    driverId: driverId,
    selectedItems: selectedItems
};

// Convert the data to JSON format
var jsonData = JSON.stringify(dataToSend);

// Define the fetch options
var fetchOptions = {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: jsonData
};


//loading true
if(driverId && selectedItems.length != 0){
	showSpinner();
	// Make the fetch request
fetch('home', fetchOptions)
	
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text(); // Assuming you're expecting a text response
    })
    .then(data => {
		hideSpinner();
        if(data == "ordered"){
            swal("Success","Order assigned to driver successfully","success")
            .then((result) => {
                const baseUrl = window.location.protocol + "//" + window.location.host;
                const newPath = "/DMS/views/admin/home";
                window.location.href = baseUrl + newPath;
              });
          
         
        }else if(data == "fail"){
          swal("Sorry","Assigned fail.","error");
         
        }
		//loading false
    })
    .catch(error => {
        console.error('There was a problem with your fetch operation:', error);
    });
}



}