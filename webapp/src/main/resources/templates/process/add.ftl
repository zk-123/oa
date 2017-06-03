<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "添加流程">
<div class="main-container" id="main-container">
    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <div class="sidebar" id="sidebar">

            <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                    <button class="btn btn-success">
                        <i class="icon-signal"></i>
                    </button>

                    <button class="btn btn-info">
                        <i class="icon-pencil"></i>
                    </button>

                    <button class="btn btn-warning">
                        <i class="icon-group"></i>
                    </button>

                    <button class="btn btn-danger">
                        <i class="icon-cogs"></i>
                    </button>
                </div>

                <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                    <span class="btn btn-success"></span>

                    <span class="btn btn-info"></span>

                    <span class="btn btn-warning"></span>

                    <span class="btn btn-danger"></span>
                </div>
            </div><!-- #sidebar-shortcuts -->

            <ul class="nav nav-list">
                <#if menuTree??>
                      <@menuTreeLoop menuTree.menuTreeList></@menuTreeLoop>
                </#if>

            </ul><!-- /.nav-list -->
        </div>

        <div class="main-content">
            <div class="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <a href="${ctx}">首页</a>
                    </li>
                    <li>
                        <a href="#">添加流程</a>
                    </li>
                </ul>

            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="col-md-offset-1 col-md-10">
                            <div class="panel panel-primary menu-add">
                                <div class="panel-heading">
                                    <div class="panel-title">
                                        添加流程
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <form id="function-new">
                                        <div class="menu-add-one">
                                            流程名称：
                                            <input type="text" placeholder="流程名称" name="processName" class="form-control">
                                        </div>
                                        <div class="menu-add-one">
                                            流程描述：
                                            <input type="text" placeholder="流程描述" name="processDescribe" class="form-control">
                                        </div>
                                        <div class="menu-add-one">
                                            上传模板
                                            <input type="hidden" id="processUrl" name="processUrl" value="">
                                            <input type="file"  class="form-control" id="file">
                                        </div>
                                        <div class="menu-add-one">
                                            流转角色顺序<br/>
                                            <div class="col-md-6">
                                                <table class="table table-bordered already-select auto-sort">
                                                    <tr>
                                                        <td>序号</td>
                                                        <td>已选</td>
                                                        <td>移除</td>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div class="col-md-6">
                                                <table class="table table-bordered auto-sort">
                                                    <tr>
                                                        <td>序号</td>
                                                        <td>未选</td>
                                                        <td>添加</td>
                                                    </tr>

                                                    <#if roleList??>
                                                        <#list roleList as role>
                                                        <tr>
                                                            <td>${role_index+1}</td>
                                                            <td>${role.roleName}</td>
                                                            <td><a href="javascript:;" onclick="addRole('${role.roleId}','${role.roleName}')">添加</a></td>
                                                        </tr>
                                                        </#list>
                                                    </#if>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="tip" style="clear: both"></div>
                                        <button class="btn btn-primary menu-add-submit" style="display: block;clear: both">提交</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</@main>

<script>
    $('.menu-add-submit').click(function () {
        $.ajax({
            url:"${ctx}/process/add",
            data:$("#function-new").serialize(),
            type:"POST",
            success:function (data) {
                showTip(data);
                setTimeout("window.location.reload()",1500);
            },
            error:function (xr) {
                showTip(xr.responseText);
            }
        })
        return false;
    })

    function addRole(roleId,roleName) {
        var len = $('.already-select tr').length;
        $('.already-select').append("<tr><td>"+len+"</td><td>"+roleName+"</td>" +
                "<td><a href='javascript:;' " +
                "onclick='this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);tableAutoSort()'>移除</a></td>" +
                "<input type='hidden' name='roleIds' value='"+roleId+"'></tr>");
    }
    function tableAutoSort() {
        $(".already-select").find("tr").each(function(index,element){
            var tdArr = $(this).children();
            if(index > 0){
                tdArr.eq(0).text(index);
            }
        });
    }
    $("#file").change(function () {
        var formDate = new FormData();
        formDate.append("file",$('#file')[0].files[0]);
        $.ajax({
            url:"http://upload.zkdcloud.cn",
            data:formDate,
            cache: false,
            type:"POST",
            processData: false,
            contentType: false,
            success:function (data) {
                $('#processUrl').val(data);
            }
        })
    })
</script>