<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/oa.css">
    <title>登录</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <div class="panel panel-primary" style="margin: 200px 0 0 0;">
                <div class="panel-heading">
                    <div class="panel-title">
                        登录
                    </div>
                </div>
                <div class="panel-body">
                    <form id="user">
                        用户名：<input type="text" name="username" placeholder="用户名" class="form-control">
                        密码：<input type="password" name="password" placeholder="密码" class="form-control">
                        <div class="tip"></div>
                        <button class="btn btn-primary user-login-btn" style="margin: 20px 0 0 0">登录</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="/js/jquery-2.0.3.min.js"></script>
<script src="/js/oa.js"></script>
<script>
    $('.user-login-btn').click(function () {
        $.ajax({
            url:"${ctx}/user/login",
            data:$('#user').serialize(),
            type:"POST",
            success:function (data) {
                window.location="${ctx}";
            },
            error:function (ex) {
                showTip(ex.responseText);
            }
        })
        return false;
    })
</script>
</html>