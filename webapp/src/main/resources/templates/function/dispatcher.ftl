<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "分配权限">
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
                        <a href="#">分配权限</a>
                    </li>
                </ul>

            </div>

            <div class="page-content">
                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="panel-title">
                                    正在为${role.roleName}分配权限
                                </div>
                            </div>
                            <div class="panel-body">
                                <form>
                                    <ul>
                                        <#list functionList as function>
                                            <li><input type="checkbox" checked value="${function.functionId}" name="functionIds">${function.functionName}</li>
                                        </#list>
                                    </ul>
                                </form>
                                <div class="tip"></div>
                                <!--page-->

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
    function deleteTip(data) {
        if(window.confirm("确认删除该功能？")){
            $.ajax({
                url:"${ctx}/function/delete",
                data:{"functionId":data},
                async:false,
                type:"POST",
                success:function (data) {
                    setTimeout("showTip(data);",1500);
                    window.location.reload();
                },
                error:function (ex) {
                    showTip(ex.responseText);
                }
            })
        }
    }
</script>