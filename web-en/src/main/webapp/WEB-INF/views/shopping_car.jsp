<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>购物+</title>
	  <link href="${cp}/css/bootstrap.min.css" rel="stylesheet">
	  <link href="${cp}/css/style.css" rel="stylesheet">

	  <script src="${cp}/js/jquery.min.js" type="text/javascript"></script>
	  <script src="${cp}/js/bootstrap.min.js" type="text/javascript"></script>
	  <script src="${cp}/js/layer.js" type="text/javascript"></script>
    <!--[if lt IE 9]>
      <script src="${cp}/js/html5shiv.min.js"></script>
      <script src="${cp}/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <!--导航栏部分-->
	<jsp:include page="include/header.jsp"/>

	<!-- 中间内容 -->
	<div class="container-fluid bigHead">
		<div class="row">
			<div class="col-sm-10  col-md-10 col-sm-offset-1 col-md-offset-1">
				<div class="jumbotron">
					<h1>Shopping Cart</h1>
					<p>Your Shopping Lists</p>
				</div>
			</div>
			<div class="col-sm-10  col-md-10 col-sm-offset-1 col-md-offset-1">
				<table class="table table-hover center" id="shoppingCarTable">
				</table>

				<hr/>
				<div class="row">
					<div class="col-lg-4 col-md-4 col-sm-4"></div>
					<button type="button" class="btn btn-danger btn-lg col-lg-4 col-md-4 col-sm-4" onclick="confirmPre()">Buy</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 尾部 -->
	<jsp:include page="include/foot.jsp"/>

	<script type="text/javascript">
		updateShoppingCars();

		function updateShoppingCars() {
			var shoppingCarTable = document.getElementById("shoppingCarTable");
			var allShoppingCars = getShoppingCars();
			shoppingCarTable.innerHTML = "";
			var html = '<tr>'+
							'<th>Whether to buy</th>'+
							'<th>Name</th>'+
							'<th>Price</th>'+
							'<th>Count</th>'+
						'</tr>';
			for(var i=0;i<allShoppingCars.length;i++){
				var product = getProductById(allShoppingCars[i].productId);
				html  += '<tr>'+
						'<td>'+
						'<div class="checkbox">'+
						'<label>'+
						'<input type="checkbox" id="checkbox'+allShoppingCars[i].productId+'" value="option1">'+
						'</label>'+
						'</div>'+
						'</td>'+
						'<td>'+product.name+'</td>'+
						'<td>'+product.price+'</td>'+
						'<td>'+allShoppingCars[i].counts+'</td>'+
						'</tr>';
			}
			shoppingCarTable.innerHTML += html;

		}

		function getShoppingCars() {
			judgeIsLogin();
			var shoppingCarProducts = "";
			var user = {};
			user.userId = ${currentUser.id};
			$.ajax({
				async : false, //设置同步
				type : 'POST',
				url : '${cp}/getShoppingCars',
				data : user,
				dataType : 'json',
				success : function(result) {
					shoppingCarProducts = result.result;
				},
				error : function(result) {
					layer.alert('Search error',{"title":"INFO"});
				}
			});
			shoppingCarProducts = eval("("+shoppingCarProducts+")");
			return shoppingCarProducts;
		}

		function confirmPre() {
			var allShoppingCars = getShoppingCars();
			var buyProducts = new Array;
			var buyProductsCounts = new Array;
			var buyCounts = 0;
			for(var i=0;i<allShoppingCars.length;i++){
				var checkBox = document.getElementById("checkbox"+allShoppingCars[i].productId);
				if(checkBox.checked){
					buyProducts[buyCounts] = allShoppingCars[i].productId;
					buyProductsCounts[buyCounts] = allShoppingCars[i].counts;
					buyCounts++;
				}
			}
			if(buyCounts == 0){
				layer.msg("No product selected",{"title":"INFO"});
			}
			else{
				buyConfirm(buyProducts,buyProductsCounts);
			}
		}

		function getProductById(id) {
			var productResult = "";
			var product = {};
			product.id = id;
			$.ajax({
				async : false, //设置同步
				type : 'POST',
				url : '${cp}/getProductById',
				data : product,
				dataType : 'json',
				success : function(result) {
					productResult = result.result;
				},
				error : function(result) {
					layer.alert('Search error',{"title":"INFO"});
				}
			});
			productResult = JSON.parse(productResult);
			return productResult;
		}

        function judgeIsLogin() {
            if("${currentUser.id}" == null || "${currentUser.id}" == undefined || "${currentUser.id}" ==""){
                window.location.href = "${cp}/login";
            }
        }

		function buyConfirm(productsId,productsCounts) {
			var address = getUserAddress(${currentUser.id});
			var phoneNumber = getUserPhoneNumber(${currentUser.id});
			var totalPrice = 0;

			var html = '<div class="col-sm-1 col-md-1 col-lg-1"></div>'+
					'<div class="col-sm-10 col-md-10 col-lg-10">'+
					'<table class="table confirm-margin">';
			for(var i=0;i<productsId.length;i++){
				var product = getProductById(productsId[i]);
				html +=	'<tr>'+
					'<th>Product'+(i+1)+'Name：</th>'+
					'<td>'+product.name+'</td>'+
					'</tr>'+
					'<tr>'+
					'<th>Price：</th>'+
					'<td>'+product.price+'</td>'+
					'</tr>'+
					'<tr>'+
					'<th>Count：</th>'+
					'<td>'+productsCounts[i]+'</td>'+
					'</tr>'+
					'<tr>';
				totalPrice+=product.price*productsCounts[i];
			}
			html +='<th>Total amount：</th>'+
					'<td>'+totalPrice+'</td>'+
					'</tr>'+
					'<tr>'+
					'<th>Address：</th>'+
					'<td>'+address+'</td>'+
					'</tr>'+
					'<tr>'+
					'<th>Phone：</th>'+
					'<td>'+phoneNumber+'</td>'+
					'</tr>'+
					'</table>'+
					'<div class="row">'+
					'<div class="col-sm-4 col-md-4 col-lg-4"></div>'+
					'<button class="btn btn-danger col-sm-4 col-md-4 col-lg-4" onclick="addToShoppingRecordsPre(['+productsId+'],['+productsCounts+'])">确认购买</button>'+
					'</div>'+
					'</div>';
			layer.open({
				type:1,
				title:'Please confirm the order information：',
				content:html,
				area:['650px','350px'],
			});
		}


		function getUserAddress(id) {
			var address = "";
			var user = {};
			user.id = id;
			$.ajax({
				async : false, //设置同步
				type : 'POST',
				url : '${cp}/getUserAddressAndPhoneNumber',
				data : user,
				dataType : 'json',
				success : function(result) {
					address = result.address;
				},
				error : function(result) {
					layer.alert('Search error',{"title":"INFO"});
				}
			});
			return address;
		}


		function getUserPhoneNumber(id) {
			var phoneNumber = "";
			var user = {};
			user.id = id;
			$.ajax({
				async : false, //设置同步
				type : 'POST',
				url : '${cp}/getUserAddressAndPhoneNumber',
				data : user,
				dataType : 'json',
				success : function(result) {
					phoneNumber = result.phoneNumber;
				},
				error : function(result) {
					layer.alert('Search error',{"title":"INFO"});
				}
			});
			return phoneNumber;
		}

		function addToShoppingRecordsPre(productsId,productsCounts) {
			for(var i=0;i<productsId.length;i++){
				addToShoppingRecords(productsId[i],productsCounts[i]);
			}
			layer.confirm('Go to Order？', {icon: 1, title:'Purchase Success',btn:['Go to Order','Continue to Buy']},
					function(){
						window.location.href = "${cp}/shopping_record";
					},
					function(index){
                        window.location.href = "${cp}/shopping_car";
					}
			);
		}

		function addToShoppingRecords(productId,productCounts) {
			judgeIsLogin();
			var shoppingRecord = {};
			shoppingRecord.userId = ${currentUser.id};
			shoppingRecord.productId = productId;
			shoppingRecord.counts = productCounts;
			var buyResult = "";
			$.ajax({
				async : false,
				type : 'POST',
				url : '${cp}/addShoppingRecord',
				data : shoppingRecord,
				dataType : 'json',
				success : function(result) {
					buyResult = result.result;
				},
				error : function(result) {
					layer.alert('Error',{"title":"INFO"});
				}
			});
			var product = getProductById(productId);
			if(buyResult == "success") {
                deleteShoppingCar(productId);
				layer.msg("Product "+product.name+" purchase successfully",{"title":"INFO"});
			}
			else if(buyResult == "unEnough"){
				layer.alert("Product "+product.name+":Insufficient inventory, purchase failed",{"title":"INFO"})
			}
		}

		function deleteShoppingCar(productId) {
            var shoppingCar = {};
            shoppingCar.userId = ${currentUser.id};
            shoppingCar.productId = productId;
            var deleteResult = "";
            $.ajax({
                async : false,
                type : 'POST',
                url : '${cp}/deleteShoppingCar',
                data : shoppingCar,
                dataType : 'json',
                success : function(result) {
                    deleteResult = result.result;
                },
                error : function(result) {
                    layer.alert('Search user error',{"title":"INFO"});
                }
            });
        }
	</script>
  </body>
</html>