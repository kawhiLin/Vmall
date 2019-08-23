<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
     
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
    <div class="container-fluid">
        <h1 class="title center">Sign up</h1>
        <br/>
        <div class="col-sm-offset-2 col-md-offest-2">
            <!-- 表单输入 -->
            <div  class="form-horizontal">
                <div class="form-group">
                    <label for="inputEmail" class="col-sm-2 col-md-2 control-label">Username(required)</label>
                    <div class="col-sm-6 col-md-6">
                        <input type="text" class="form-control" id="inputUserName"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputEmail" class="col-sm-2 col-md-2 control-label">Email(required)</label>
                    <div class="col-sm-6 col-md-6">
                        <input type="email" class="form-control" id="inputEmail"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputNickname" class="col-sm-2 col-md-2 control-label">Nickname(required)</label>
                    <div class="col-sm-6 col-md-6">
                        <input type="text" class="form-control" id="inputNickname" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-sm-2 col-md-2 control-label">Password(required)</label>
                    <div class="col-sm-6 col-md-6">
                        <input type="password" class="form-control" id="inputPassword"  />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPhoneNumber" class="col-sm-2 col-md-2 control-label">Phone</label>
                    <div class="col-sm-6 col-md-6">
                        <input type="text" class="form-control" id="inputPhoneNumber"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="man" class="col-sm-2 col-md-2 control-label">Gender</label>
                    <div class="col-sm-6 col-md-6">
                        <label class="radio-inline">
                            <input type="radio" id="man" value="option1"> Male
                        </label>
                        <label class="radio-inline">
                            <input type="radio" id="woman" value="option2"> Female
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="birthday" class="col-sm-2 col-md-2 control-label">Birthday</label>
                    <div class="col-sm-6 col-md-6">
                        <input type="text" class="form-control" id="birthday"  />
                    </div>
                </div>
                <div class="form-group">
                    <label for="postcodes" class="col-sm-2 col-md-2 control-label">Postal code</label>
                    <div class="col-sm-6 col-md-6">
                        <input type="text" class="form-control" id="postcodes" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="address" class="col-sm-2 col-md-2 control-label">Address</label>
                    <div class="col-sm-6 col-md-6">
                        <input type="text" class="form-control" id="address"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-6">
                        <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="startRegister()">Sign up</button>
                    </div>
                </div>
            </div>
            <br/>
        </div>
    </div>

    <!--尾部-->
    <jsp:include page="include/foot.jsp"/>
    <script type="text/javascript">
        function startRegister() {
            var loading = layer.load(0);
            var user = {};
            user.userName = document.getElementById("inputUserName").value;
            user.email = document.getElementById("inputEmail").value;
            user.nickName = document.getElementById("inputNickname").value;
            user.password = document.getElementById("inputPassword").value;
            user.phoneNumber = document.getElementById("inputPhoneNumber").value;
            user.birthday = document.getElementById("birthday").value;
            user.postNumber = document.getElementById("postcodes").value;
            user.address = document.getElementById("address").value;
            user.sex = 0;
            if(document.getElementById("woman").checked)
                user.sex = 1;
            if(user.userName == ''){
                layer.msg('Username cannot be empty',{"title":"INFO"});
                return;
            }
            else if(user.userName.length >= 12){
                layer.msg('Username cannot exceed 12 characters in length',{"title":"INFO"});
                return;
            }
            if(user.nickName == ''){
                layer.msg('Nickname cannot be empty',{"title":"INFO"});
                return;
            }
            else if(user.nickName.length >= 15){
                layer.msg('Nickname cannot exceed 15 characters in length',{"title":"INFO"});
                return;
            }
            else if(user.password == ''){
                layer.msg('Password cannot be empty',{"title":"INFO"});
                return;
            }
            else if(user.password.length>= 20){
                layer.msg('Password cannot exceed 20 characters in length',{"title":"INFO"});
                return;
            }
            var registerResult = null;
            $.ajax({
                async : false, //设置同步
                type : 'POST',
                url : '${cp}/doRegister',
                data : user,
                dataType : 'json',
                success : function(result) {
                    registerResult = result.result;
                },
                error : function(result) {
                    layer.alert('Search user error',{"title":"INFO"});
                }
            });
            if(registerResult == 'success'){
                layer.close(loading);
                layer.msg('Sign up Successfully',{"title":"INFO"});
                window.location.href="${cp}/login";
            }
            else if(registerResult == 'nameExist'){
                layer.close(loading);
                layer.msg('This username or email has been registered',{"title":"INFO"});
            }
            else if(registerResult == 'emailExist'){
                layer.close(loading);
                layer.msg('This username or email has been registered',{"title":"INFO"});
            }
            else if(registerResult == 'fail'){
                layer.close(loading);
                layer.msg('Error',{"title":"INFO"});
            }
        }
    </script>
  </body>
</html>