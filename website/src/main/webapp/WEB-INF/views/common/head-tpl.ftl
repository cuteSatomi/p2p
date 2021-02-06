<div class="el-header">
    <div class="container" style="position: relative;">
        <ul class="nav navbar-nav navbar-right">
            <li><a href="/">首页</a></li>
            <#-- 没有登陆才显示登陆和快速注册 -->
            <#if !loginInfo??>
                <li><a href="/login.html">登录</a></li>
                <li><a href="/register.html">快速注册</a></li>
            <#else>
                <li>
                    <a class="el-current-user" href="/personal.do">${loginInfo.username}</a>
                </li>
                <li><a href="/recharge.do">账户充值 </a></li>
                <li><a href="/logout.do">注销 </a></li>
                <li><a href="#">帮助</a></li>
            </#if>
        </ul>
    </div>
</div>
