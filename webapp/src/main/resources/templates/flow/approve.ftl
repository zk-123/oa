<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "正在审批${flowStep.flow.user.username}提交的${flowStep.flow.process.processName}">
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
                        <a href="#">审批申请</a>
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
                                        审批申请
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <form id="function-new">
                                        <input type="hidden" name="flowStepId" value="${flowStep.flowStepId}">
                                        <table class="table table-bordered">
                                            <tr>
                                                <td>申请人</td>
                                                <td>${flowStep.flow.user.username}</td>
                                            </tr>
                                            <tr>
                                                <td>申请流程</td>
                                                <td>${flowStep.flow.process.processName}</td>
                                            </tr>
                                            <tr>
                                                <td>提交的申请信息</td>
                                                <td><a href="${flowStep.flow.flowUrl}">[点击下载]</a></td>
                                            </tr>
                                            <tr>
                                                <td>申请时间</td>
                                                <td>${flowStep.flow.flowDate}</td>
                                            </tr>
                                            <tr>
                                                <td>已走流程</td>
                                                <td>
                                                    <#list flowStep.flow.flowStepSet as fs>
                                                        <#if !fs.status>
                                                            <p style="color: #ccc">${fs.role.roleName}正在审批--></p>
                                                            <#break>
                                                        <#elseif (fs.status && !fs.accept)>
                                                            <p style="color: red">${fs.flowStepDate} &nbsp;&nbsp;${fs.role.roleName}驳回--></p>
                                                            <#break >
                                                        <#else >
                                                            <p style="color: #26ff23">${fs.flowStepDate} &nbsp;&nbsp;${fs.role.roleName}同意审批--></p>
                                                        </#if>
                                                    </#list>
                                                </td>
                                            </tr>
                                        </table>
                                        <div class="menu-add-one">
                                            审批备注：
                                            <textarea name="remarks" style="width: 100%;height: 200px" placeholder="写出意见或备注"></textarea>
                                        </div>
                                        <div class="menu-add-one">
                                            审批操作<br/>
                                            同意 ：<input type="radio" name="accept" value="1"><br/>
                                            驳回 : <input type="radio" name="accept" value="0">
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
        if($("input[type='radio']:checked").val() == null){
            showTip("选择批改操作");
            return false;
        }

        $.ajax({
            url:"${ctx}/flow/approve",
            data:$("#function-new").serialize(),
            type:"POST",
            success:function (data) {
                showTip(data);
                setTimeout("window.location.href='${ctx}/flow/myApprove'",1500);
            },
            error:function (xr) {
                showTip(xr.responseText);
            }
        })
        return false;
    })
</script>