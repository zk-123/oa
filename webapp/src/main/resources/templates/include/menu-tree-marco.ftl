<#macro menuTreeLoop mtlist>
    <#list mtlist as mt>

        <li>
            <a href="#" class="dropdown-toggle">
                <div class="menu-name">
                    <i class="icon-file-alt"></i>
                    <span class="glyphicon glyphicon-folder-open"></span>
                    <span class="menu-text">${mt.menu.menuName!""}</span>
                    <b class="arrow icon-angle-down"></b>
                </div>
            </a>
            <ul class="submenu">
                <#if (mt.menuTreeList?size>0)>
                    <@menuTreeLoop mt.menuTreeList></@menuTreeLoop>
                </#if>
                <#list mt.menu.functionSet as function>
                    <li><a href="${ctx}${function.functionUrl}"><span class="glyphicon glyphicon-link"></span><span
                            class="menu-text">${function.functionName!""}</span></a></li>
                </#list>
            </ul>

        </li>


    </#list>
</#macro>