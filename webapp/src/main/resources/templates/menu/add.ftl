<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "添加目录">
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
                        <a href="#">添加目录</a>
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
                                        添加目录
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <form id="menu-new">
                                        <div class="menu-add-one">
                                            目录位置下：
                                            <select name="parentId" class="form-control">
                                                <option value="#root">根目录</option>
                                                <#list menuList as menu>
                                                    <option value="${menu.menuId}">${menu.menuName}</option>
                                                </#list>
                                            </select>
                                        </div>
                                        <div class="menu-add-one">
                                            目录名称：
                                            <input type="text" placeholder="目录名称" name="menuName" class="form-control" required>
                                        </div>
                                        <div class="menu-add-one">
                                            目录顺序(数字越小，优先级越高)
                                            <input type="text" placeholder="请输入数字" name="menuSort" class="form-control" required>
                                        </div>
                                        <div class="menu-add-one">
                                            目录描述
                                            <input type="text" placeholder="简短的词语来描述该目录" name="menuDescribe" class="form-control" required>
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
            url:"${ctx}/menu/add",
            data:$("#menu-new").serialize(),
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