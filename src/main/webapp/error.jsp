<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>エラー</title>
</head>
<body>

<h2>エラーが発生しました</h2>

<%
String error = (String) request.getAttribute("error");
if (error != null) {
%>
<p style="color:red;"><%= error %></p>
<%
}
%>

<a href="product-list">商品一覧へ戻る</a>

</body>
</html>
