<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<title>Spring Boot Rest - JQuery AJAX</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<script	src="http://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="/js/post.js"></script>
	<script src="/js/get.js"></script>
	<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<h1>Customer Order Management</h1>      
	    <div id="postdiv" class="row">
	        <form id="orderForm" class="col-md-6">
	            <label>Existing Customer ID</label>
	            <input type="text" id="customerid" class="form-control"/>
	            <br/>
	            <label>New Order</label>
	            <input type="text" id="order" class="form-control"/>
	            <br/>
	            <button type="submit" id="postBtn" class="btn btn-primary">Add Order</button>
	        </form>
	    </div>
	    <br/>
	    <div id="postResultDiv" class="row">
	    </div>
	    <br/>
	    <div id="getdiv" class="row">
	        <label>Existing Customer ID</label>
	        <input type="text" id="customerid_get" class="form-control" style="width:100px;"/>
	        <br/>
	        <button id="getBtn">Find Customer</button>
	    </div>
	    <br/>
	    <div id="getResultDiv" class="row">
	    </div>

	</div>
</body>
</html>