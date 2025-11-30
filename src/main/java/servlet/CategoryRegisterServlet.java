package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.CategoryDAO;

@WebServlet("/register-category")
public class CategoryRegisterServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String idStr = request.getParameter("categoryId");
		String name = request.getParameter("categoryName");

		if (idStr == null || idStr.isEmpty() || name == null || name.isEmpty()) {
			request.setAttribute("error", "必須項目が入力されていません。");
			request.getRequestDispatcher("category-register.jsp").forward(request, response);
			return;
		}

		int id;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			request.setAttribute("error", "カテゴリIDは数値を入力してください。");
			request.getRequestDispatcher("category-register.jsp").forward(request, response);
			return;
		}

		CategoryDAO dao = new CategoryDAO();
		boolean result = dao.insertCategory(id, name);

		if (!result) {
			request.setAttribute("error", "データ登録に失敗しました。IDが重複している可能性があります。");
			request.getRequestDispatcher("category-register.jsp").forward(request, response);
			return;
		}

		response.sendRedirect("category-list");
	}
}