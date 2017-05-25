<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "功能列表">
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
                        <a href="#">功能列表</a>
                    </li>
                </ul>

            </div>

            <div class="page-content">
                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="panel-title">
                                    功能列表
                                </div>
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered">
                                    <tr>
                                        <td>序号</td>
                                        <td>功能名称</td>
                                        <td>功能描述</td>
                                        <td>功能排序序号</td>
                                        <td>功能访问Url</td>
                                        <td>修改</td>
                                        <td>删除</td>
                                    </tr>
                                    <#if functionPage??>
                                        <#list functionPage.content as function>
                                            <tr>
                                                <td>${function_index+1}</td>
                                                <td>${function.functionName}</td>
                                                <td>${function.functionDescribe}</td>
                                                <td>${function.functionSort}</td>
                                                <td>${function.functionUrl}</td>
                                                <td><a href="${ctx}/function/modify?functionId=${function.functionId}">修改</a></td>
                                                <td><a href="javascript:;" onclick="deleteTip('${function.functionId}')">删除</a></td>
                                            </tr>
                                        </#list>
                                    </#if>
                                </table>
                                <div class="tip"></div>
                                <!--page-->
                                <nav aria-label="Page navigation pull-right">
                                    <ul class="pagination">
                                        <#if (functionPage?? && (curPage) > 1)>
                                            <li>
                                                <a href="${ctx}/function/list?p=${curPage - 1}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </#if>
                                        <#if (sumPage > 0) >
                                        <#list curPage..sumPage as index >
                                            <#if (curPage + 9 > index)>
                                                    <li <#if curPage == index> class="active" </#if>><a href="${ctx}/function/list?p=${index}">${index}</a></li>
                                            </#if>
                                        </#list>
                                        </#if>
                                        <#if (curPage + 9 <= sumPage)>
                                            <li>
                                                <a href="#" aria-label="Next">
                                                    ......
                                                </a>
                                            </li><li>
                                            <a href="${ctx}/function/list?p=${sumPage}" aria-label="Next">
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
        if(window.confirm("确认删除该功能？")){
            $.ajax({
                url:"${ctx}/function/delete",
                data:{"functionId":data},
                async:false,
                type:"POST",
                success:function (data) {
                    showTip(data);
                    setTimeout("window.location.reload();",1500);
                },
                error:function (ex) {
                    showTip(ex.responseText);
                }
            })
        }
    }
</script>