package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.CategoryBean;
import model.dao.CategoryDAO;

@WebServlet("/category-list")
public class CategoryListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CategoryDAO dao = new CategoryDAO();
		List<CategoryBean> categories = dao.getAllCategories();

		System.out.println("カテゴリ件数: " + categories.size());

		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/category-list.jsp").forward(request, response);
	}
}