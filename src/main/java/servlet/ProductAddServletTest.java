package servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;

public class ProductAddServletTest {

	private ProductAddServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestDispatcher dispatcher;

	private CategoryDAO categoryDao;
	private ProductDAO productDao;

	@Before
	public void setUp() {
		servlet = spy(new ProductAddServlet());

		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		dispatcher = mock(RequestDispatcher.class);

		categoryDao = mock(CategoryDAO.class);
		productDao = mock(ProductDAO.class);

		doReturn(categoryDao).when(servlet).createCategoryDAO();
		doReturn(productDao).when(servlet).createProductDAO();

		when(categoryDao.getAllCategories())
				.thenReturn(new ArrayList<>());

		when(request.getRequestDispatcher("/product-add.jsp"))
				.thenReturn(dispatcher);
	}

	@Test
	public void 商品登録成功時一覧へ遷移() throws Exception {

		when(request.getParameter("name")).thenReturn("商品A");
		when(request.getParameter("price")).thenReturn("100");
		when(request.getParameter("stock")).thenReturn("10");
		when(request.getParameter("categoryId")).thenReturn("1");

		when(productDao.insertProduct(any())).thenReturn(true);

		servlet.doPost(request, response);

		verify(response).sendRedirect("product-list");
	}

	@Test
	public void 必須項目未入力時エラー() throws Exception {

		when(request.getParameter("name")).thenReturn("");
		when(request.getParameter("price")).thenReturn("");
		when(request.getParameter("stock")).thenReturn("");
		when(request.getParameter("categoryId")).thenReturn("");

		when(request.getRequestDispatcher("/product-add.jsp"))
				.thenReturn(dispatcher);

		servlet.doPost(request, response);

		verify(request).setAttribute(eq("error"), anyString());
		verify(dispatcher).forward(request, response);
	}

}
