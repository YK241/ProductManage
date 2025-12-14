<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>

<h2>ログイン</h2>

<p style="color:red">
    ${error}
</p>

<form action="login" method="post">
    ユーザ名：<input type="text" name="username"><br>
    パスワード：<input type="password" name="password"><br>
    <button type="submit">ログイン</button>
</form>

</body>
</html>
