$( document ).ready(function() {
	
	var url = window.location;
	
	// SUBMIT FORM
    $("#orderForm").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		ajaxPost();
	});
    
    
    function ajaxPost(){
    	
    	// PREPARE FORM DATA
    	var formData = {
    		customerId : $("#customerid").val(),
    		order : $("#order").val()
    	}
    	
    	// DO POST
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : url + "postorder",
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(result) {
				if(result.status == "Done"){
					$("#postResultDiv").html(
							"<strong>" + 
							"Post Successfully! Customer's Info: ID = " + result.data.customerId + " , " +
									"Name = " + result.data.name + " , " +
											"new Order = " + result.data.orders[result.data.orders.length - 1].name
							+ "</strong>"
							);
				} else {
					$("#postResultDiv").html("<strong>~~ Error</strong>");
				}
				console.log(result);
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
    	
    	// Reset FormData after Posting
    	resetData();

    }
    
    function resetData(){
    	$("#customerid").val("");
    }
})