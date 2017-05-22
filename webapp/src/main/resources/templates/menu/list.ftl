<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "目录列表">
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
                        <a href="#">目录列表</a>
                    </li>
                </ul>

            </div>

            <div class="page-content">
                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="panel-title">
                                    目录列表
                                </div>
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered">
                                    <tr>
                                        <td>序号</td>
                                        <td>目录名称</td>
                                        <td>目录描述</td>
                                        <td>目录排序序号</td>
                                        <td>修改</td>
                                        <td>删除</td>
                                    </tr>
                                    <#if menuPage??>
                                        <#list menuPage.content as menu>
                                            <tr>
                                                <td>${menu_index+1}</td>
                                                <td>${menu.menuName}</td>
                                                <td>${menu.menuDescribe}</td>
                                                <td>${menu.menuSort}</td>
                                                <td><a href="${ctx}/menu/modify?menuId=${menu.menuId}">修改</a></td>
                                                <td><a href="javascript:;" onclick="deleteTip('${menu.menuId}')">删除</a></td>
                                            </tr>
                                        </#list>
                                    </#if>
                                </table>
                                <div class="tip"></div>
                                <!--page-->
                                <nav aria-label="Page navigation pull-right">
                                    <ul class="pagination">
                                        <#if (menuPage?? && (curPage) > 1)>
                                            <li>
                                                <a href="${ctx}/menu/list?p=${curPage - 1}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </#if>
                                        <#list curPage..sumPage as index >
                                            <#if (curPage + 9 > index)>
                                                    <li <#if curPage == index> class="active" </#if>><a href="${ctx}/menu/list?p=${index}">${index}</a></li>
                                            </#if>
                                        </#list>
                                        <#if (curPage + 9 <= sumPage)>
                                            <li>
                                                <a href="#" aria-label="Next">
                                                    ......
                                                </a>
                                            </li><li>
                                            <a href="${ctx}/menu/list?p=${sumPage}" aria-label="Next">
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
        if(window.confirm("确认删除？其子目录及子目录下的功能全部删除")){
            $.ajax({
                url:"${ctx}/menu/delete",
                data:{"menuId":data},
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