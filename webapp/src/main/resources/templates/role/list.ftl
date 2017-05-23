<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "角色列表">
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
                        <a href="#">角色列表</a>
                    </li>
                </ul>

            </div>

            <div class="page-content">
                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="panel-title">
                                    角色列表
                                </div>
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered">
                                    <tr>
                                        <td>序号</td>
                                        <td>角色名称</td>
                                        <td>目录描述</td>
                                        <td>创建时间</td>
                                        <td>操作/修改</td>
                                        <td>操作/删除</td>
                                        <td>分配权限</td>
                                    </tr>
                                    <#if rolePage??>
                                        <#list rolePage.content as role>
                                            <tr>
                                                <td>${role_index+1}</td>
                                                <td>${role.roleName}</td>
                                                <td>${role.roleDescribe}</td>
                                                <td>${role.roleDate}</td>
                                                <td><a target="_blank" href="${ctx}/role/modify?roleId=${role.roleId}">修改</a></td>
                                                <td><a href="javascript:;" onclick="deleteTip('${role.roleId}')">删除</a></td>
                                                <td><a target="_blank" href="${ctx}/function/dispatcher?roleId=${role.roleId}">分配权限</a></td>
                                            </tr>
                                        </#list>
                                    </#if>
                                </table>
                                <div class="tip"></div>
                                <!--page-->
                                <nav aria-label="Page navigation pull-right">
                                    <ul class="pagination">
                                        <#if (rolePage?? && (curPage) > 1)>
                                            <li>
                                                <a href="${ctx}/role/list?p=${curPage - 1}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </#if>
                                        <#list curPage..sumPage as index >
                                            <#if (curPage + 9 > index)>
                                                    <li <#if curPage == index> class="active" </#if>><a href="${ctx}/role/list?p=${index}">${index}</a></li>
                                            </#if>
                                        </#list>
                                        <#if (curPage + 9 <= sumPage)>
                                            <li>
                                                <a href="#" aria-label="Next">
                                                    ......
                                                </a>
                                            </li><li>
                                            <a href="${ctx}/role/list?p=${sumPage}" aria-label="Next">
                                                <span aria-hidden="true">${sumPage}</span>
                                            </a>
                                        </li>
                                        </#if>
                                    </ul>
                                </nav>
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
        if(window.confirm("确认删除该角色？其下的人员不会删除")){
            $.ajax({
                url:"${ctx}/role/delete",
                data:{"roleId":data},
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