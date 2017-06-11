<#macro main title="管理页面">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="/css/ace.min.css"/>
    <link rel="stylesheet" href="/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="/css/ace-skins.min.css"/>
    <link href="/css/bootstrap.min.css" rel="stylesheet"/>
    <link  href="/css/oa.css" rel="stylesheet"/>
</head>

<body>
<div class="navbar navbar-default top-navbar">


    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="#" class="navbar-brand">
                <small>
                    <i class="icon-leaf"></i>
                    OA
                </small>
            </a>
        </div>

        <div class="navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <#if (messageCount??&&(messageCount > 0))>
                    <li class="light-orange">
                        <a  href="${ctx}/message/my">
                        <span class="user-info">
                            未读消息:${messageCount}条
                        </span>
                            <i class="icon-caret-down"></i>
                        </a>
                    </li>
                </#if>
                <#if (LOGIN_USER.url?? &&LOGIN_USER.url != "") >
                    <li class="light-blue">
							<img style="height: 40px;width: 40px" src="${LOGIN_USER.url}"/>
                            <i class="icon-caret-down"></i>
                    </li>
                </#if>
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <span class="user-info">
									<small>Welcome,</small>
									<#if LOGIN_USER??>${LOGIN_USER.username}</#if>
								</span>

                        <i class="icon-caret-down"></i>
                    </a>
                </li>
                <li class="light-orange">
                    <a  href="${ctx}/user/off">
                        <span class="user-info">
                            <#if LOGIN_USER??>注销</#if>
								</span>

                        <i class="icon-caret-down"></i>
                    </a>
                </li>
            </ul><!-- /.ace-nav -->
        </div><!-- /.navbar-header -->
    </div><!-- /.container -->
</div>
<#nested>


<!-- basic scripts -->
<script src="/js/jquery-2.0.3.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/ace.min.js"></script>
<script src="/js/oa.js"></script>
</body>
</html>

</#macro>
