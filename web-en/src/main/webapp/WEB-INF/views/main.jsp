<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
	  <meta charset="utf-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <title>VMALL</title>
	  <link href="${cp}/css/bootstrap.min.css" rel="stylesheet">
	  <link href="${cp}/css/style.css" rel="stylesheet">

	  <script src="${cp}/js/jquery.min.js" type="text/javascript"></script>
	  <script src="${cp}/js/bootstrap.min.js" type="text/javascript"></script>
	  <script src="${cp}/js/layer.js" type="text/javascript"></script>
    <!--[if lt IE 9]>
      <script src="${cp}/js/html5shiv.min.js"></script>
      <script src="${cp}/js/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <!--导航栏部分-->
	<jsp:include page="include/header.jsp"/>
	<!-- 中间内容 -->
	<div class="container-fluid">
		<div class="row">
			<!-- 控制栏 -->
			<div class="col-sm-3 col-md-2 sidebar sidebar-1">
				<ul class="nav nav-sidebar">
					<li class="list-group-item-diy"><a href="#productArea1">Phone <span class="sr-only">(current)</span></a></li>
					<li class="list-group-item-diy"><a href="#productArea2">Pad</a></li>
					<li class="list-group-item-diy"><a href="#productArea3">Smart Wear</a></li>
					<li class="list-group-item-diy"><a href="#productArea4">Smart Home</a></li>                                       

				</ul>
			</div>
			<!-- 控制内容 -->
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="jumbotron">
					<h1>Welcome to VMALL</h1>
					<p>VMALL is a shopping website where you can simulate buying goods</p>
				</div>

				<div name="productArea1" class="row pd-10" id="productArea1">
				</div>

				<div name="productArea2" class="row" id="productArea2">
				</div>

				<div name="productArea3" class="row" id="productArea3">
				</div>

                <div name="productArea4" class="row" id="productArea4">
				</div>




			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
				<jsp:include page="include/foot.jsp"/>
			</div>
		</div>
	</div>

  <script type="text/javascript">

	  var loading = layer.load(0);

      var productType = new Array;
      productType[1] = "Phone";
      productType[2] = "Pad";
      productType[3] = "Smart wear";
      productType[4] = "Smart home";


	  listProducts();

	  function listProducts() {
		  var allProduct = getAllProducts();
          var mark = new Array;
          mark[1] = 0;
          mark[2] = 0;
          mark[3] = 0;
          mark[4] = 0;

          for(var i=0;i<allProduct.length;i++){
              var html = "";
			  var imgURL = "${cp}/img/"+allProduct[i].id+".png";
			  html += '<div class="col-sm-4 col-md-4" >'+
					  '<div class="boxes pointer" onclick="productDetail('+allProduct[i].id+')">'+
					  '<div class="big bigimg">'+
					  '<img src="'+imgURL+'">'+
					  '</div>'+
					  '<p class="product-name">'+allProduct[i].name+'</p>'+
					  '<p class="product-price">¥'+allProduct[i].price+'</p>'+
					  '</div>'+
					  '</div>';
              var id = "productArea"+allProduct[i].type;
              var productArea = document.getElementById(id);
              if(mark[allProduct[i].type] == 0){
                  html ='<hr/><h1>'+productType[allProduct[i].type]+'</h1><hr/>'+html;
                  mark[allProduct[i].type] = 1;
              }
              productArea.innerHTML += html;
		  }
		  layer.close(loading);
	  }
	  function getAllProducts() {
		  var allProducts = null;
		  var nothing = {};
		  $.ajax({
			  async : false, //设置同步
			  type : 'POST',
			  url : '${cp}/getAllProducts',
			  data : nothing,
			  dataType : 'json',
			  success : function(result) {
				  if (result!=null) {
					  allProducts = result.allProducts;
				  }
				  else{
					  layer.alert('Search error',{"title":"INFO"});
				  }
			  },
			  error : function(resoult) {
				  layer.alert('Search error',{"title":"INFO"});
			  }
		  });
		  //划重点划重点，这里的eval方法不同于prase方法，外面加括号
		  allProducts = eval("("+allProducts+")");
		  return allProducts;
	  }

	  function productDetail(id) {
		  
		  if("${currentUser}" == null || "${currentUser}" == undefined || "${currentUser}" ==""){
			  alert('Please log in first');
              window.location.href = "${cp}/login";
              return;
		  }
			
       
		  var product = {};
		  var jumpResult = '';
		  product.id = id;
		  $.ajax({
			  async : false, //设置同步
			  type : 'POST',
			  url : '${cp}/productDetail',
			  data : product,
			  dataType : 'json',
			  success : function(result) {
				  jumpResult = result.result;
			  },
			  error : function(resoult) {
				  layer.alert('Search error',{"title":"INFO"});
			  }
		  });

		  if(jumpResult == "success"){
			  window.location.href = "${cp}/product_detail";
		  }
	  }

  </script>


  </body>
</html>