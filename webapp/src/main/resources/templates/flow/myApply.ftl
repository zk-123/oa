<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "我的申请">
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
                        <a href="#">我的申请</a>
                    </li>
                </ul>

            </div>

            <div class="page-content">
                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="panel-title">
                                    我的申请
                                </div>
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered">
                                    <tr>
                                        <td>序号</td>
                                        <td>申请主题</td>
                                        <td>我提交的申请</td>
                                        <td>状态</td>
                                        <td>申请时间</td>
                                        <td>详情</td>
                                    </tr>
                                    <#if flowPage??>
                                        <#list flowPage.content as flow>
                                            <tr>
                                                <td>${flow_index+1}</td>
                                                <td>${flow.process.processName!""}</td>
                                                <td><a href="${flow.flowUrl!""}">点击下载</a></td>
                                                <td>
                                                    <#if flow.flowStepSet??>
                                                        <#assign f = true />
                                                        <#list flow.flowStepSet as flowStep>
                                                        <#if !flowStep.status> <!--未审批状态-->
                                                            ${flowStep.role.roleName}正在审批
                                                            <#assign f = false/>
                                                            <#break>
                                                        <#elseif (flowStep.status && !flowStep.accept)><!--审批却没通过-->
                                                            <p style="color: red">${flowStep.role.roleName}驳回</p>
                                                            <#assign f = false/>
                                                            <#break>
                                                        </#if>
                                                        </#list>
                                                        <#if f><p style="color: #26ff23">同意审批</p></#if>
                                                    </#if>
                                                </td>
                                                <td>${flow.flowDate!""}</td>
                                                <td><a>详情</a></td>
                                            </tr>
                                        </#list>
                                    </#if>
                                </table>
                                <div class="tip"></div>
                                <!--page-->
                                <nav aria-label="Page navigation pull-right">
                                    <ul class="pagination">
                                        <#if (flowPage?? && (curPage) > 1)>
                                            <li>
                                                <a href="${ctx}/flow/myApply?p=${curPage - 1}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </#if>
                                        <#if (sumPage > 0) >
                                            <#list curPage..sumPage as index >
                                                <#if (curPage + 9 > index)>
                                                    <li <#if curPage == index> class="active" </#if>><a href="${ctx}/flow/myApply?p=${index}">${index}</a></li>
                                                </#if>
                                            </#list>
                                        </#if>
                                        <#if (curPage + 9 <= sumPage)>
                                            <li>
                                                <a href="#" aria-label="Next">
                                                    ......
                                                </a>
                                            </li><li>
                                            <a href="${ctx}/flow/myApply?p=${sumPage}" aria-label="Next">
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
        if(window.confirm("确认删除？")){
            $.ajax({
                url:"${ctx}/process/delete",
                data:{"processId":data},
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