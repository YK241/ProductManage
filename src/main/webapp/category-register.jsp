<%@ page contentType="text/html; charset=UTF-8"%>
<html lang="ja">
<head>
<title>カテゴリ登録</title>
</head>
<body>
	<h2>カテゴリ登録フォーム</h2>
	<%
	if (request.getAttribute("error") != null) {
	%>
	<p style="color: red;"><%=request.getAttribute("error")%></p>
	<%
	}
	%>
	<form action="register-category" method="post">
		<div>
			<label>カテゴリID：</label> <input type="number" name="categoryId"
				required>
		</div>
		<div>
			<label>カテゴリ名：</label> <input type="text" name="categoryName" required>
		</div>

		<button type="submit">登録</button>

	</form>
</body>
</html>