<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2021/5/20
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basepath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <title>电影详情--修改编辑</title>
    <base href="<%=basepath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <style>
        a{text-decoration: none}
        table{
            border-collapse: collapse;
        }
        table,table tr td {
            border:1px solid #ccc;
        }
        table tr td{
            padding: 5px 10px;
        }
    </style>
    <script>
        $(function () {
            loadScheduleInfo();
            /*添加场次按钮的函数*/
            $("#addBtn").click(function () {
                $.ajax({
                    url:"producer/loadScheduleDetail.do",
                    method:"post",
                    data:{
                        "movieId":"${movie.id}"
                    },
                    dataType:"json",
                    success:function (data) {
                        var html="<option value=''></option>";
                        $.each(data,function (i,n) {
                            html+='<option value="'+n.id+'">'+n.name+'</option>';
                        })
                        $("#addHall").html(html);
                    }
                })
                $("#addSchedule-modal").modal("show");
            })
            /*修改电影信息按钮的函数*/
            $("#editBtn").click(function () {
                /*获取当前信息，填充表格*/
                var curMovieName= "${movie.name}";
                var curMovieShowTime= "${movie.showTime}";
                var curTicketPrice = "${movie.ticketPrice}";
                var curMovieType = "${movie.movieType}";
                var curIntroduce = "${movie.introduce}";
                $("#editMovieName").val(curMovieName);
                $("#editShowTime").val(curMovieShowTime);
                $("#editTicketPrice").val(curTicketPrice);
                $("#editMovieType").val(curMovieType);
                $("#editIntroduce").val(curIntroduce);
                $("#editMovie-modal").modal("show");
            })
            /*提交修改电影信息的函数*/
            $("#saveEditBtn").click(function () {
                /*获取修改后的信息*/
                var editName=$("#editMovieName").val();
                var editShowTime=$("#editShowTime").val();
                var editTicketPrice=$("#editTicketPrice").val();
                var editMovieType=$("#editMovieType").val();
                var editIntroduce=$("#editIntroduce").val();
                if(editName==""||editShowTime==""||editTicketPrice=="")
                    $("#editMovie-msg").html("带*为必填项")
                else{
                    $.ajax({
                        url:"producer/editMovie.do",
                        dataType:"text",
                        method:"post",
                        data:{
                            "id":"${movie.id}",
                            "name":editName,
                            "showTime":editShowTime,
                            "ticketPrice":editTicketPrice,
                            "movieType":editMovieType,
                            "introduce":editIntroduce
                        },
                        success:function (data) {
                            if(data=="true"){
                                alert("电影修改成功！")
                                $("#editMovie-modal").modal("hide");
                                $("#editMovieForm")[0].reset();
                                loadMovies();
                            }else {
                                alert("上架失败，未知原因")
                            }
                        }
                    })
                }
            })
            /*提交场次的函数*/
            $("#submitBtn").click(function () {
                var hall =$("#addHall").val()
                var showTime=$("#addDate").val()
                var showPeriod=$("#startTime").val()+"-"+$("#endTime").val();
                $.ajax({
                    url:"producer/addSchedule.do",
                    data:{
                        "movieId":"${movie.id}",
                        "hallId":hall,
                        "showTime":showTime,
                        "showPeriod":showPeriod
                    },
                    method: "post",
                    dataType: "text",
                    success:function (data) {
                        if(data=="true"){
                            alert("添加场次成功");
                            $("#addSchedule-modal").modal("hide");
                            loadScheduleInfo();
                        }else{
                            alert("添加失败，未知原因")
                        }
                    }

                })
            })
        })
        /*加载当前电影已添加的场次信息*/
        function loadScheduleInfo() {
            var html="<tr>\n" +
                "<td>场馆</td>\n" +
                "<td>上映日期</td>\n" +
                "<td>上映时间</td>\n" +
                "</tr>";
            $.each(${scheduleInfoList},function (i,n) {
                html+='<tr>'
                html+='<td><a onclick="openSeatSituation(\''+n.id+'\',\''+n.hall+'\',\''+n.showTime+'\',\''+n.showPeriod+'\',\''+n.row+'\',\''+n.col+'\')"> '+n.hall+'</a></td>'
                html+='<td>'+n.showTime+'</td>'
                html+='<td>'+n.showPeriod+'</td>'
                html+='</tr>'
            })
            $("#scheduleList").html(html);
        }
        /*加载已订购信息的函数*/
        function openSeatSituation(id,hall,showTime,showPeriod,rows,cols) {
            $("#seatHall").html(hall);
            $("#seatTime").html(showTime);
            $("#seatPeriod").html(showPeriod);
            $("#seatRows").html(rows);
            $("#seatCols").html(cols);
            $.ajax({
                url:"producer/loadUserPurchases.do",
                method:"get",
                data:{
                    "scheduleId":id
                },
                dataType:"json",
                success:function (data) {
                    var html="";
                    var flag="";
                    if(data!=null&&data!=""){
                        $.each(data,function (i,n) {
                            /*行数变更时换行*/
                            if(flag!=n.rows) html+='<br>';
                            flag=n.rows;
                            html+=n.rows+'行'+n.cols+'列,';
                        })
                    }else html="尚无订购者"
                    $("#seatOccupied").html(html);
                }
            })
            $("#seatSituation-modal").modal("show");
        }
    </script>
</head>
<body>
<h1>电影详情--${movie.name}</h1>
<h3>类型：${movie.movieType} 发行商：${movie.producer}</h3>
<h3>上映日期:${movie.showTime} 评分:</h3>
<div>
    <h2>简介</h2>
    ${movie.introduce}
</div>
<button class="btn btn-default" id="addBtn">增加上映场次</button><button class="btn btn-default" id="editBtn">修改电影信息</button>
<br><button onclick="window.location.href='workbench/producer/index.jsp'" class="btn">返回</button>
<div id="scheduleInfo">
    <h3>已添加的场次信息：</h3>
    <table id="scheduleList" class="table" style="width: 50%">
    </table>
</div>
<%--增加场次的模态窗口--%>
<div class="modal fade" id="addSchedule-modal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 30%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">增加场次</h4>
            </div>
            <div class="modal-body">
                影厅：<select id="addHall"></select>
                <br>上映日期：<input type="date" id="addDate">
                <br>上映时间段：<input type="time" id="startTime">--<input type="time" id="endTime">
            </div>
            <div class="modal-footer">
                <button id="submitBtn">提交</button>
                <button data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<%--座位安排情况的模态窗口--%>
<div class="modal fade" id="seatSituation-modal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 30%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">座位安排情况</h4>
            </div>
            <div class="modal-body">
                影厅：<font id="seatHall"></font>&nbsp;大小：<font id="seatRows"></font>行<font id="seatCols"></font>列
                <br>上映日期：<font id="seatTime"></font>&nbsp;&nbsp;&nbsp;上映时段：<font id="seatPeriod"></font>
                <h4>已被订购的座位：</h4>
                <div id="seatOccupied">
                </div>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<%--修改电影信息的模态窗口--%>
<div class="modal fade" id="editMovie-modal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 40%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">电影信息修改</h4>
            </div>
            <div class="modal-body">
                <form id="editMovieForm">
                    <table>
                        <tr>
                            <td>电影名称：</td>
                            <td><input type="text" id="editMovieName"><font color="red">*</font></td>
                        </tr>
                        <tr>
                            <td>上映日期：<br></td>
                            <td><input type="date" id="editShowTime"><font color="red">*</font></td>
                        </tr>
                        <tr>
                            <td>票价:</td>
                            <td><input type="text" id="editTicketPrice"><font color="red">*</font></td>
                        </tr>
                        <tr>
                            <td>电影类型:</td>
                            <td><input type="text" id="editMovieType"></td>
                        </tr>
                        <tr>
                            <td>简介:</td>
                            <td><textarea id="editIntroduce"></textarea></td>
                        </tr>
                    </table>
                    <font color="red" id="editMovie-msg"></font>
                </form>
            </div>
            <div class="modal-footer">
                <button id="saveEditBtn">提交</button>
                <button data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
