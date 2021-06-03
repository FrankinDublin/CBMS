<%
    String basepath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆界面</title>
    <base href="<%=basepath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script>
        $(function () {
            $("#registerBtn").click(function () {
                $("#login").hide();
                $("#register").show();
            })
            $("#returnBtn").click(function () {
                $("#login").show();
                $("#register").hide();
            })
            $("#submitBtn").click(function () {
                var usercode =$.trim($("#register-usercode").val());
                var password =$.trim($("#register-password").val());
                var confirmpwd =$.trim($("#register-confirmpwd").val());
                var name =$.trim($("#register-name").val());
                if(usercode=="" || password==""){
                    $("#register-msg").html("用户名或密码不得为空")
                }else if(password!=confirmpwd){
                    $("#register-msg").html("两次输入密码不一致")
                }else{
                    $.ajax({
                        url:"login/register.do",
                        data:{
                            "usercode":usercode,
                            "password":password,
                            "name":name,
                            "role":$("#register-role").val()
                        },
                        method:"post",
                        dataType:"json",
                        success:function (data) {
                            if(data.success=="true"){
                                alert("注册成功,请前往登陆")
                                $("#login").show();
                                $("#register").hide();
                            }else{
                                $("#register-msg").html(data.msg)
                            }
                        }
                    })

                }

            })
            $("#loginBtn").click(function () {
                var usercode =$.trim($("#login-usercode").val());
                var password =$.trim($("#login-password").val());
                if(usercode=="" || password==""){
                    $("#login-msg").html("用户名或密码不得为空");
                } else {
                    $.ajax({
                        url:"login/login.do",
                        data:{
                            "usercode":usercode,
                            "password":password,
                        },
                        method:"post",
                        dataType:"json",
                        success:function (data) {
                            if(data.success=="true"){
                                if(data.role=="0")
                                    window.location.href="workbench/user/index.jsp"
                                else if(data.role=="1")
                                    window.location.href="workbench/producer/index.jsp"
                                else if(data.role=="2")
                                    window.location.href="workbench/Admin/index.jsp"
                            }else{
                                $("#login-msg").html(data.msg)
                            }
                        }
                    })
                }
            })
        })
    </script>
</head>
<body>
    <div id="login" align="center">
        <h1>电影院订票系统</h1>
        <table>
            <tr>
                <td>账号</td>
                <td><input type="text" id="login-usercode"></td>
            </tr>
            <tr>
                <td>密码</td>
                <td><input type="password" id="login-password"></td>
            </tr>
        </table>
        <h5 id="login-msg" style="color: red"></h5>
        <button style="alignment: center" id="loginBtn">登陆</button>
        <button id="registerBtn">注册</button>
    </div>
    <div id="register" align="center" hidden="hidden">
        <h2>账号注册</h2>
        <table align="center">
            <tr>
                <td>账号:</td>
                <td><input type="text" id="register-usercode"></td>
            </tr>
            <tr>
                <td>用户名:</td>
                <td><input type="text" id="register-name"></td>
            </tr>
            <tr>
                <td>密码:</td>
                <td><input type="password" id="register-password"></td>
            </tr>
            <tr>
                <td>确认密码:</td>
                <td><input type="password" id="register-confirmpwd"></td>
            </tr>
            <tr>
                <td>身份：</td>
                <td><select id="register-role">
                    <option value="0">用户</option>
                    <option value="1">商家</option>
                </select></td>
            </tr>
        </table>
        <h5 id="register-msg" style="color: red"></h5>
        <button style="alignment: center" id="submitBtn">注册</button>
        <button id="returnBtn">返回</button>
    </div>
</body>
</html>
