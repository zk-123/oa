<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "申请流程详情">
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
                        <a href="#">申请流程详情</a>
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
                                        申请流程详情
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <table class="table table-bordered">
                                        <tr>
                                            <td>序号</td>
                                            <td>处理角色</td>
                                            <td>处理状态</td>
                                            <td>处理人</td>
                                            <td>备注</td>
                                            <td>处理时间</td>
                                        </tr>
                                        <#if flow??>
                                            <#list flow.flowStepSet as fs>
                                                <tr>
                                                    <td>${fs_index+1}</td>
                                                    <td>${fs.role.roleName}</td>
                                                    <#if (fs.status && fs.accept)>
                                                        <td class="success">同意</td>
                                                    <#elseif (!fs.status && fs.accept)>
                                                        <td class="info">正在审批</td>
                                                    <#elseif (fs.status && !fs.accept)>
                                                        <td class="danger">驳回</td>
                                                    <#else >
                                                        <td>等待中</td>
                                                    </#if>
                                                    <td>${fs.dealUsername!""}</td>
                                                    <td>${fs.remarks!""}</td>
                                                    <td>${fs.flowStepDate!""}</td>
                                                </tr>
                                            </#list>
                                        </#if>
                                    </table>
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