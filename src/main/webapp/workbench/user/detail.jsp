<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2021/5/16
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="userMenu.html"%>
<%
    String basepath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <title>电影详情</title>
    <base href="<%=basepath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script>
        $(function () {
            loadComments();
            loadBalance();
            $("#submitBtn").click(function () {
                var comment=$.trim($("#sendComment").val());
                var grade=$("#movie-grade").val();
                $.ajax({
                    url:"comment/addComment.do",
                    method: "post",
                    data: {
                        "comment":comment,
                        "createBy":"${user.usercode}",
                        "grade":grade,
                        "movieId":"${movie.id}"
                    },
                    success:function (data) {
                        loadComments();
                       alert("发表成功")
                    }
                })
            })
            /*购票按钮的函数*/
            $("#purchaseBtn").click(function () {
                $.ajax({
                    url:"detail/loadDetail.do",
                    dataType: "json",
                    method:"post",
                    data:{
                        "Mid":"${movie.id}",
                        "ProducerId":"${movie.producer}"
                    },
                    /*data:scheduleList,hallList,ticketSetList*/
                    success:function (data) {
                        var dataString = JSON.stringify(data);
                        sessionStorage.setItem("purchaseInfo", dataString);
                        /*填充放映厅*/
                        var html="<option value=''></option>";
                        $.each(data.hallList,function (i,n) {
                            html+='<option value="'+n.id+'">'+n.name+'</option>';
                        })
                        $("#selectHall").html(html);
                        html="<option value='none'>不选择套餐</option>";
                        /*填充套餐*/
                        $.each(data.ticketSetList,function (i,n) {
                            html+='<option value="'+n.id+'">'+n.setInclude+'</option>'
                        })
                        $("#ticketSet").html(html);
                    }
                })
                /*初次更新总金额*/
                $("#total").html(${movie.ticketPrice});
                $("#totalStorage").val(${movie.ticketPrice});
                /*打开模态窗口*/
                $("#purchase-modal").modal("show");
            })
            /*确认支付按钮的函数*/
            $("#confirmBtn").click(function () {
                /*填充总金额，余额与交易后余额*/
                var totalStorage=$("#totalStorage").val();
                totalStorage_int = parseInt(totalStorage);
                var userBalance =$("#userBalance").html();
                userBalance_int = parseInt(userBalance);
                $("#finalPrice").html(totalStorage_int);
                $("#balance").html(userBalance_int);
                $("#Postbalance").html(userBalance_int-totalStorage_int);
                /*获取已被订购座位的列表以及用户选择的座位*/
                var occupiedSeatList = sessionStorage.getItem("occupiedSeats").split(",");
                var customerChoice = $("#selectRow").val()+""+$("#selectCol").val();
                if($("#selectHall").val()==""||$("#selectPeriod").val()==""||$("#selectCol").val()==""||$("#selectRow").val()==""){
                    $("#purchase-msg").html("请选择场次与座位")
                }else if($.inArray(customerChoice,occupiedSeatList)>=0){
                    $("#purchase-msg").html("该座位已被订购，请重新选择")
                } else{
                    $("#purchase-msg").html("");
                    $("#confirm-modal").modal("show");
                }
            })
            /*支付的函数*/
            $("#dealBtn").click(function () {
                if($("#Postbalance").html()<0)
                    alert("余额不足，请充值")
                else{
                    var scheduleId=$("#selectPeriod").val();
                    var rows = $("#selectRow").val();
                    var cols = $("#selectCol").val();
                    var ticketSet =$("#ticketSet").val();
                    var cost = $("#totalStorage").val();
                    $.ajax({
                        url:"purchase/purchase.do",
                        dataType:"text",
                        data:{
                            "userId":"${user.usercode}",
                            "scheduleId":scheduleId,
                            "rows":rows,
                            "cols":cols,
                            "ticketSet":ticketSet,
                            "cost":cost,
                        },
                        method:"post",
                        success:function (data) {
                            if(data=="true") {
                                alert("购买成功，请在‘我的订票’中查看！");
                                updateBalance();
                                $("#confirm-modal").modal("hide");
                                $("#purchase-modal").modal("hide");
                            }else {
                                alert("购买失败，未知原因")
                            }
                        }
                    })

                }
            })
        })
        /*----------------------------------下面是重复调用函数----------------------------------------*/
        function loadComments() {
            /*加载评论*/
            $.ajax({
                url:"comment/loadComments.do",
                method:"post",
                dataType:"json",
                data:{
                    movieId:"${movie.id}"
                },
                success:function (data) {
                    var html="";
                    /*计算评分*/
                    var totalGrade=0;
                    var count = 0;
                    $.each(data,function (i,n) {
                        totalGrade+=parseInt(n.grade);
                        count+=1;
                        html+="<div>";
                        html+="<small>--------------------------------------------------------</small><br>"
                        html+="<h4>"+n.createBy+"    <small>评分："+n.grade+"分</small></h4>"
                        html+="<h4>"+n.comment+"</h4>"
                        html+="<h6>"+(n.editFlag==1?n.editTime:n.createTime)+"</h6>"
                        html+="</div>"
                    })
                    avgGrade = totalGrade/count ;
                    if(isNaN(avgGrade)) {
                        $("#grade").html("暂无评分");
                        updateGrade("Non");
                    }
                    else {
                        $("#grade").html(avgGrade.toFixed(1));
                        updateGrade(avgGrade.toFixed(1)+"");
                    }
                    $("#comment").html(html);
                }
            })

        }
        /*根据影厅实时更新影片上映时间选项*/
        function loadTime() {
            /*清除错误提示信息*/
            $("#purchase-msg").html("");
            /*获取放映信息*/
            var data = JSON.parse(sessionStorage.getItem("purchaseInfo"));
            /*更新上映时间*/
            var html="<option></option>";
            $.each(data.scheduleList,function (i,n) {
                var id=$("#selectHall").val();
                if(n.hallId == id)
                    html+='<option value="'+n.id+'">'+n.showTime+' '+n.showPeriod+'</option>'
            })
            $("#selectPeriod").html(html);
            /*更新列与座*/
            $.each(data.hallList,function (i,n) {
                /*n.id：数据库查询结果的大厅id $("#n.id")：option选项的大厅id*/
                var id=$("#selectHall").val();
                var html="<option></option>";
                if(n.id == id ){
                    for(k=1;k<=n.row;k++){
                        html+='<option value="'+k+'">'+k+'</option>';
                    }
                    $("#selectRow").html(html);
                    html="<option></option>";
                    for(j=1;j<=n.col;j++){
                        html+='<option value="'+j+'">'+j+'</option>';
                    }
                    $("#selectCol").html(html);
                }
            })
        }
        /*加载该场次已被订购的座位*/
        function loadSeat() {
            var scheduleId=$("#selectPeriod").val();
            $.ajax({
                url:"detail/loadOccupiedSeat.do",
                method:"get",
                data:{
                    "scheduleId":scheduleId
                },
                dataType:"json",
                success:function (data) {
                    var html="";
                    var flag="";
                    var occupiedSeats = new Array();
                    if(data!=null&&data!=""){
                        $.each(data,function (i,n) {
                            occupiedSeats.push(n.rows+""+n.cols);
                            /*行数变更时换行*/
                            if(flag!=n.rows) html+='<br>';
                            flag=n.rows;
                            html+=n.rows+'行'+n.cols+'列,';
                        })
                    }else html="尚无订购者"
                    sessionStorage.setItem("occupiedSeats",occupiedSeats);
                    $("#seatOccupied").html(html);
                }
            })
        }
        /*实时更新座位，已被订购的选项将不再出现*/
        /*function loadSeat() {
           var data = JSON.parse(sessionStorage.getItem("hallList"));
            var id=$("#selectHall").val();
            var dataString =sessionStorage.getItem("rowsColsList");
            var rows_and_cols=dataString.split(",");
            alert(rows_and_cols);
            $.each(data,function (i,n) {
                html="<option></option>";
                if(n.id==id){
                    for(j=1;j<=n.col;j++){
                        /!*拼接当前选座信息*!/
                        var seatCode = $("#selectRow").val()+""+j;
                        alert("当前选座："+seatCode+",位于"+$.inArray(seatCode,rows_and_cols))
                        /!*如果选座信息存在于已购信息数组中，不打印该选项*!/
                        if($.inArray(seatCode,rows_and_cols)>=0) continue;
                        html+='<option value="'+j+'">'+j+'</option>';
                        $("#selectCol").html(html);
                    }
                }
            })
        }*/
        /*加载本场次电影已被订购的座位信息*/
        /*function loadOccupiedSeat() {
            /!*movieId-->scheduleId-->rows_and_cols*!/
            $.ajax({
                url:"detail/loadOccupiedSeat.do",
                method:"get",
                data:{
                    "movieId":"${movie.id}"
                },
                dataType:"json",
                /!*data:PurchaseSeatInfoList 属性：rows,cols*!/
                success:function (data) {
                    var rowsColsList = new Array();
                    $.each(data,function (i,n) {
                        rowsColsList.push(n.rows+""+n.cols)
                    })
                    sessionStorage.setItem("rowsColsList",rowsColsList);
                }
            })
        }*/
        /*更新总金额*/
        function updateCash() {
            var data = JSON.parse(sessionStorage.getItem("purchaseInfo"));
            var price="";
            $.each(data.ticketSetList,function (i,n) {
                var setId=$("#ticketSet").val();
                if(n.id==setId){
                    price=n.price;
                    $("#setPrice").html("套餐价格："+price+"元");
                }else if(setId=="none"){
                    price="0";
                    $("#setPrice").html("");
                }
            })
            price_int=parseInt(price);
            ticketPrice = parseInt(${movie.ticketPrice});
            $("#total").html(price_int+ticketPrice);
            $("#totalStorage").val(price_int+ticketPrice);
        }
        function updateBalance() {
            var postBalance = $("#Postbalance").html();
            $.ajax({
                url:"purchase/updateBalance.do",
                method:"post",
                dataType:"text",
                data:{
                    "userId":"${user.usercode}",
                    "postBalance":postBalance
                },
                success:function (data2) {
                    if(data2=="true"){
                        loadBalance();
                    }
                }
            })
        }
        /*更新后台评分*/
        function updateGrade(grade) {
            $.ajax({
                url:"detail/updateGrade.do",
                method:"post",
                dataType:"text",
                data:{
                    movieId:"${movie.id}",
                    "grade":grade
                }
            })
        }
    </script>
</head>
<body>
    <%--储存总金额--%>
    <input type="hidden" id="totalStorage">
    <h1>电影详情--${movie.name}</h1>
    <h3>类型：${movie.movieType} 发行商：${movie.producer}</h3>
    <h3>上映日期:${movie.showTime} 评分:<font id="grade"></font></h3>
    <div>
        <h2>简介</h2>
        ${movie.introduce}
    </div>
    <button id="purchaseBtn" type="button" class="btn">购票</button>
    <button onclick="window.location.href='workbench/user/index.jsp'" class="btn">返回</button>
    <div id="movie-comment">
        评分：<select id="movie-grade">
            <option value="0">0</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select><br/>
    <textarea id="sendComment">
        发表你的评论吧！
    </textarea>
        <br/>
        <button id="submitBtn" class="btn">发表</button>
    </div>
    <div id="movie-showComment">
        <h3>评论区</h3>
        <div id="comment"></div>
    </div>
    <%--购票的模态窗口--%>
    <div class="modal fade" id="purchase-modal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">购票页面</h4>
                </div>
                <div class="modal-body">
                    <h3>电影：${movie.name}</h3>
                    <small>上映日期：${movie.showTime}</small>
                    <br><br>票价:${movie.ticketPrice}元
                    <br><br>选择影厅：<select id="selectHall" onchange="loadTime()" class="form-select-button"></select>
                            上映时间：<select id="selectPeriod" onchange="loadSeat()"></select>
                    <br><br>座位：<select id="selectRow"></select>行<select id="selectCol"></select>列
                    <br><br>已被订购的座位：
                    <div id="seatOccupied"></div>
                    <br>选择套餐:<select id="ticketSet" onchange="updateCash()"></select>
                    <br><font color="red" id="purchase-msg"></font>
                    <small id="setPrice"></small>
                    <h4>总价：<font color="red" id="total"></font> 元</h4>
                    <button id="confirmBtn" class="btn btn-default">确认支付</button>
                </div>
            </div>
        </div>
    </div>
    <%--确认支付的模态窗口--%>
    <div class="modal fade" id="confirm-modal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">确认支付</h4>
                </div>
                <div class="modal-body">
                    <h3>总价：<font color="red" id="finalPrice"></font>元</h3>
                    <h3>账户余额：<font color="red" id="balance"></font>元</h3>
                    <br>
                    <h2>交易后余额：<font color="red" id="Postbalance"></font>元</h2>
                </div>
                <div class="modal-footer">
                    <button id="dealBtn">支付</button>
                    <button data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
