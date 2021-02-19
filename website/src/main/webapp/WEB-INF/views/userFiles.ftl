<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>蓝源Eloan-P2P平台</title>
    <#include "common/links-tpl.ftl" />
    <link type="text/css" rel="stylesheet" href="/css/account.css"/>
    <script type="text/javascript" src="/js/plugins/uploadify/jquery.uploadify.min.js"></script>

    <style type="text/css">
        .uploadify {
            height: 20px;
            font-size: 13px;
            line-height: 20px;
            text-align: center;
            position: relative;
            margin-left: auto;
            margin-right: auto;
            cursor: pointer;
            background-color: #337ab7;
            border-color: #2e6da4;
            color: #fff;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            // 点击按钮相当于点击了隐藏的input框
            $("#btn_uploadUserFiles").click(function () {
                $("#file").click();
            });

            // 监听输入框的change事件
            $("#file").change(function () {
                upload();
            });

            // 上传图片方法
            function upload() {
                var formData = new FormData();
                for (var i = 0; i < $("#file")[0].files.length; i++) {  //循环获取上传个文件
                    formData.append("file", $("#file")[0].files[i]);
                }
                //formData.append("bid", bid);//此处可以添加一个参数和图片一起上传
                $.ajax({
                    "url": "/userFileUpload.do",
                    "data": formData,
                    "dataType": "json",
                    "type": "post",
                    "contentType": false, //上传文件一定要是false
                    "processData": false,
                    "success": function (data) {
                        if (data.success) {
                            $.messager.popup("上传成功！");
                            setInterval(function () {
                                window.location.reload();
                            }, 500)
                        } else {
                            $.messager.popup(data.msg);
                        }
                    }
                });
            }
        });
    </script>
</head>
<body>
<!-- 网页顶部导航 -->
<#include "common/head-tpl.ftl"/>
<#assign currentNav="personal" />
<#include "common/navbar-tpl.ftl" />

<div class="container">
    <div class="row">
        <!--导航菜单-->
        <div class="col-sm-3">
            <#assign currentMenu="userFile"/>
            <#include "common/leftmenu-tpl.ftl" />
        </div>
        <!-- 功能页面 -->
        <div class="col-sm-9">
            <div class="panel panel-default">
                <div class="panel-heading">
                    用户认证文件信息
                </div>
            </div>
            <div class="row">
                <#list userFiles as file>
                    <div class="col-sm-6 col-md-4">
                        <div class="thumbnail">
                            <img src="${file.image}"/>
                            <div class="caption">
                                <h4>${file.fileType.title}</h4>
                                <p>得分：${file.score} &nbsp;&nbsp;状态：${file.stateDisplay}</p>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
            <div class="row" style="text-align:center">
                <input type="file" style="display: none" name="files" class="form-control" id="file"
                       multiple="multiple">
                <button class="btn btn-primary" id="btn_uploadUserFiles">上传用户风控资料文件</button>
            </div>
        </div>
    </div>
</div>
<#include "common/footer-tpl.ftl" />
</body>
</html>