package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;

@WebServlet("/product-delete")
public class ProductDeleteServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		boolean result = new ProductDAO().deleteProduct(id);

		if (result) {
			response.sendRedirect("product-list");
		} else {
			request.setAttribute("error", "削除に失敗しました");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
