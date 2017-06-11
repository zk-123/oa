<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<@main "个人设置">
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
                        <a href="#">个人设置</a>
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
                                        个人设置
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <form id="menu-new">
                                        <div class="menu-add-one">
                                            所属角色：${LOGIN_USER.role.roleName}
                                        </div>
                                        <div class="menu-add-one">
                                            账号:
                                            <input type="text" name="username" placeholder="人员账号" value="${LOGIN_USER.username!""}" class="form-control">
                                        </div>
                                        <div class="menu-add-one">
                                            邮箱：
                                            <input type="email" name="email" placeholder="个人邮箱" value="${LOGIN_USER.email!""}" class="form-control">
                                        </div>
                                        <div class="menu-add-one">
                                            头像：
                                            <img style="height: 80px;width: 80px;display: block" src="${LOGIN_USER.url!""}"/><br/>
                                            上传新头像:
                                            <input type="hidden" id="url" name="url" value="${LOGIN_USER.url!""}">
                                            <input type="file"  class="form-control" id="file">
                                        </div>
                                        <div class="menu-add-one">
                                            旧密码(如需修密码，请填写旧密码，不需要，则不填)：
                                            <input type="password" name="password" placeholder="旧密码" class="form-control">
                                        </div>
                                        <div class="menu-add-one">
                                            新密码：
                                            <input type="password" name="new_password" placeholder="新密码" class="form-control">
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
            url:"${ctx}/user/personal",
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

    $("#file").change(function () {
        var formDate = new FormData();
        formDate.append("file",$('#file')[0].files[0]);
        $.ajax({
            url:"http://upload.zkdcloud.cn",
            data:formDate,
            cache: false,
            type:"POST",
            processData: false,
            contentType: false,
            success:function (data) {
                $('#url').val(data);
            }
        })
    })
</script>