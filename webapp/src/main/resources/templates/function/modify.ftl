<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "编辑功能">
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
                        <a href="#">编辑功能</a>
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
                                        编辑功能
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <form id="menu-modify">
                                        <div class="menu-add-one">
                                            目录位置下：
                                            <select name="menuId" class="form-control">
                                                <#list menuList as menu>
                                                    <#if modifyFunction.menuId == menu.menuId>
                                                        <option value="${menu.menuId}" selected>${menu.menuName}</option>
                                                    <#else >
                                                        <option value="${menu.menuId}">${menu.menuName}</option>
                                                    </#if>
                                                </#list>
                                            </select>
                                        </div>
                                        <input type="hidden" name="functionId" value="${modifyFunction.functionId}">
                                        <div class="menu-add-one">
                                            功能名称：
                                            <input type="text" placeholder="功能名称" value="${modifyFunction.functionName!""}" name="functionName" class="form-control" required>
                                        </div>
                                        <div class="menu-add-one">
                                            功能Url
                                            <input type="text" placeholder="请输入url" value="${modifyFunction.functionUrl!""}" name="functionUrl" class="form-control" required>
                                        </div>
                                        <div class="menu-add-one">
                                            功能顺序(数字越小，优先级越高)
                                            <input type="text" placeholder="请输入数字" value="${modifyFunction.functionSort!""}" name="functionSort" class="form-control" required>
                                        </div>
                                        <div class="menu-add-one">
                                            功能描述
                                            <input type="text" placeholder="简短的词语来描述该功能" value="${modifyFunction.functionDescribe!""}" name="functionDescribe" class="form-control" required>
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
            url:"${ctx}/function/modify",
            data:$("#menu-modify").serialize(),
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
</script>