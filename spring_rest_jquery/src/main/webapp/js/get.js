$( document ).ready(function() {
	
	var url = window.location;
	
	// GET REQUEST
	$("#getBtn").click(function(event){
		event.preventDefault();
		ajaxGet();
	});
	
	// DO GET
	function ajaxGet(){
		
    	var customerId = $("#customerid_get").val();
		
		$.ajax({
			type : "GET",
			url : url + "getcustomer/" + customerId,
			success: function(result){
				if(result.status == "Done"){
					console.log("url: ", url);
					var links = result.data.links;
					
					$("#getResultDiv").html(
							"<strong>" + 
							"Customer: ID = " + result.data.customerId + " , " +
							"Name = " + result.data.name + "</strong>" + "<br/>"
							);
					
					$.each(links, function(i, link){
						var _link = links[i].rel + ": <a href=" + links[i].href + ">" + links[i].href + "</a><br/>";
						$("#getResultDiv").append(_link)
			        });
					console.log(result);
				} else {
					$("#getResultDiv").html("<strong>Error</strong>");
					console.log("Fail: ", result);
				}
			},
			error : function(e) {
				$("#getResultDiv").html("<strong>Error</strong>");
				console.log("ERROR: ", e);
			}
		});	
	}
})