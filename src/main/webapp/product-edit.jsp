<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.entity.ProductBean" %>
<%@ page import="model.entity.CategoryBean" %>
<%@ page import="java.util.List" %>

<%
ProductBean p = (ProductBean) request.getAttribute("product");
List<CategoryBean> categories = (List<CategoryBean>) request.getAttribute("categories");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>商品編集</title>
</head>
<body>

<h2>商品編集</h2>

<%
String error = (String) request.getAttribute("error");
if (error != null) {
%>
<p style="color:red;"><%= error %></p>
<%
}
%>

<form action="product-edit" method="post">
    <input type="hidden" name="id" value="<%= p.getProductId() %>">

    商品名：
    <input type="text" name="name" value="<%= p.getName() %>" required><br>

    価格：
    <input type="number" name="price" value="<%= p.getPrice() %>" required><br>

    在庫数：
    <input type="number" name="stock" value="<%= p.getStock() %>" required><br>

    カテゴリ：
    <select name="categoryId">
        <%
        for (CategoryBean c : categories) {
            String selected = (c.getCategoryId() == p.getCategoryId()) ? "selected" : "";
        %>
            <option value="<%= c.getCategoryId() %>" <%= selected %>>
                <%= c.getName() %>
            </option>
        <%
        }
        %>
    </select><br><br>

    <input type="submit" value="保存">
</form>

<p>
    <a href="product-list">一覧へ戻る</a>
</p>

</body>
</html>
