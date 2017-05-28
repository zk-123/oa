<#include "../include/common-macro.ftl">
<#include "../include/menu-tree-marco.ftl">
<#macro olMenuTree menuTreeList>
    <#list menuTreeList as mt>
        <li>
            <input type="checkbox" name="menuIds" <#if roleMenuIds?seq_contains(mt.menu.menuId)>checked</#if> class="menuId" value="${mt.menu.menuId}">&nbsp;<span class="glyphicon glyphicon-folder-open"></span>&nbsp;&nbsp;${mt.menu.menuName}
            <ul class="display-menu-ol">
                <#if (mt.menuTreeList?size>0)>
                    <@olMenuTree mt.menuTreeList></@olMenuTree>
                </#if>
                <#list mt.menu.functionSet as function>
                    <li><input type="checkbox" name="functionIds" <#if roleFunctionIds?seq_contains(function.functionId)>checked</#if>  value="${function.functionId}">&nbsp;<span class="glyphicon glyphicon-link"></span>&nbsp;&nbsp;${function.functionName}</li>
                </#list>
            </ul>
        </li>

    </#list>
</#macro>
<@main "分配权限">
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
                        <a href="#">分配权限</a>
                    </li>
                </ul>

            </div>

            <div class="page-content">
                <div class="row">

                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="panel-title">
                                    正在为${role.roleName}分配权限
                                </div>
                            </div>
                            <div class="panel-body">
                                <form id="dispatcher-power">
                                    <input type="hidden" name="roleId" value="${role.roleId}">
                                    <ul class="display-menu-ol">
                                        <@olMenuTree menuTree.menuTreeList></@olMenuTree>
                                    </ul>
                                    <h5>未显示功能</h5>
                                    <ul class="display-menu-ol">
                                        <#if undisplayFunctions??>
                                            <#list undisplayFunctions as function>
                                                <li><input type="checkbox" name="functionIds" <#if roleFunctionIds?seq_contains(function.functionId)>checked</#if>  value="${function.functionId}">&nbsp;<span class="glyphicon glyphicon-link"></span>&nbsp;&nbsp;${function.functionName}</li>
                                            </#list>
                                        </#if>
                                    </ul>
                                    <button class="btn btn-primary btn-submit">确认</button>

                                </form>
                                <div class="tip"></div>
                                <!--page-->
                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<style>
    ul.display-menu-ol {
        list-style: none;
    }
    ul.display-menu-ol li {
        line-height: 35px ;
    }
</style>
</@main>

<script>
    
    $('.menuId').click(function () {
        if($(this).is(':checked')){
            $(this).parent().find('ul').find("input[type='checkbox']").attr("checked","true");
        }else{
            $(this).parent().find('ul').find("input[type='checkbox']").attr("checked","false");
        }
    })
    
    $('.btn-submit').click(function () {
        $.ajax({
            url:"${ctx}/role/dispatcher",
            type:"POST",
            data:$('#dispatcher-power').serialize(),
            async:false,
            success:function (data) {
                showTip(data);
                setTimeout("window.location.reload();",1500);
            },
            error:function (ex) {
                showTip(ex.responseText);
            }
        })
        return false;
    })

</script>