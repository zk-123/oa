<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "添加角色">
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
                        <a href="#">添加角色</a>
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
                                        添加角色
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <form id="menu-new">
                                        <div class="menu-add-one">
                                            角色名称：
                                            <input type="text" name="roleName" placeholder="角色名称" class="form-control" required>
                                        </div>

                                        <div class="menu-add-one">
                                            角色权限（填写整数，数字越小，权利越大，谨慎填写,您当前权限数字 ${myPowerSize}）：
                                            <input type="text" name="rolePowerSize" placeholder="角色权利" class="form-control" required>
                                        </div>

                                        <div class="menu-add-one">
                                            角色说明：
                                            <input type="text" placeholder="角色说明" name="roleDescribe" class="form-control" required>
                                        </div>
                                        <div class="tip"></div>
                                        <button class="btn btn-primary menu-add-submit">提交</button>
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
            url:"${ctx}/role/add",
            data:$("#menu-new").serialize(),
            type:"POST",
            success:function (data) {
                showTip(data);
                setTimeout("window.location.reload();",1500);
            },
            error:function (xr) {
                showTip(xr.responseText);
            }
        })
        return false;
    })
</script>