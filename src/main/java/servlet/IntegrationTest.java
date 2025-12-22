package servlet;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.ProductDAO;
import model.entity.ProductBean;

public class IntegrationTest {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private RequestDispatcher dispatcher;
	private FilterChain chain;

	private ProductDAO productDAO;

	@Before
	public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		dispatcher = mock(RequestDispatcher.class);
		chain = mock(FilterChain.class);

		when(request.getSession()).thenReturn(session);
		when(request.getSession(false)).thenReturn(session);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

		productDAO = new ProductDAO();

		for (ProductBean p : productDAO.getAllProducts()) {
			productDAO.deleteProduct(p.getProductId());
		}
	}

	@After
	public void tearDown() throws Exception {
		for (ProductBean p : productDAO.getAllProducts()) {
			productDAO.deleteProduct(p.getProductId());
		}
	}

	@Test
	public void 正常ログイン後_商品一覧へ遷移できる() throws Exception {

		when(request.getParameter("username")).thenReturn("admin");
		when(request.getParameter("password")).thenReturn("1234");

		LoginServlet servlet = new LoginServlet();
		servlet.doPost(request, response);

		verify(session).setAttribute(eq("loginUser"), any());
		verify(response).sendRedirect("product-list");
	}

	@Test
	public void ログアウト後_ログイン画面へリダイレクトされる() throws Exception {

		when(request.getSession(false)).thenReturn(session);

		LogoutServlet servlet = new LogoutServlet();
		servlet.doGet(request, response);

		verify(session).invalidate();
		verify(response).sendRedirect("login.jsp");
	}

	@Test
	public void ログアウト後_商品管理機能にアクセスできない() throws Exception {

		when(request.getRequestURI()).thenReturn("/product-list");
		when(request.getSession(false)).thenReturn(null);
		when(request.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

		AuthFilter filter = new AuthFilter();
		filter.doFilter(request, response, chain);

		verify(dispatcher).forward(request, response);
		verify(chain, never()).doFilter(request, response);
	}

	@Test
	public void ログイン後_商品登録でき一覧に反映される() throws Exception {

		when(session.getAttribute("loginUser")).thenReturn(new Object());

		when(request.getParameter("name")).thenReturn("テスト商品");
		when(request.getParameter("price")).thenReturn("1000");
		when(request.getParameter("stock")).thenReturn("10");
		when(request.getParameter("categoryId")).thenReturn("1");

		ProductAddServlet servlet = new ProductAddServlet();
		servlet.doPost(request, response);

		List<ProductBean> list = productDAO.getAllProducts();
		assertEquals(1, list.size());
		assertEquals("テスト商品", list.get(0).getName());
	}

	@Test
	public void 商品一覧から選択した商品が詳細表示される() throws Exception {

	    ProductBean product = new ProductBean("詳細商品", 2000, 3, 1);
	    productDAO.insertProduct(product);

	    int id = productDAO.getAllProducts().get(0).getProductId();

	    when(request.getParameter("id")).thenReturn(String.valueOf(id));
	    when(request.getRequestDispatcher("/product-delete-confirm.jsp"))
	            .thenReturn(dispatcher);

	    ProductDeleteConfirmServlet servlet = new ProductDeleteConfirmServlet();
	    servlet.doGet(request, response);

	    verify(request).setAttribute(eq("product"), any(ProductBean.class));
	    verify(dispatcher).forward(request, response);
	}

	@Test
	public void ログイン後_商品削除でき一覧から消える() throws Exception {

		ProductBean product = new ProductBean("削除商品", 1000, 5, 1);
		productDAO.insertProduct(product);

		int id = productDAO.getAllProducts().get(0).getProductId();

		when(session.getAttribute("loginUser")).thenReturn(new Object());
		when(request.getParameter("id")).thenReturn(String.valueOf(id));

		ProductDeleteServlet servlet = new ProductDeleteServlet();
		servlet.doPost(request, response);

		assertEquals(0, productDAO.getAllProducts().size());
	}

	@Test
	public void ログイン後_商品編集内容が反映される() throws Exception {

		ProductBean product = new ProductBean("旧商品", 1000, 5, 1);
		productDAO.insertProduct(product);

		ProductBean before = productDAO.getAllProducts().get(0);

		when(session.getAttribute("loginUser")).thenReturn(new Object());
		when(request.getParameter("id")).thenReturn(String.valueOf(before.getProductId()));
		when(request.getParameter("name")).thenReturn("新商品");
		when(request.getParameter("price")).thenReturn("3000");
		when(request.getParameter("stock")).thenReturn("20");
		when(request.getParameter("categoryId")).thenReturn("2");

		ProductEditServlet servlet = new ProductEditServlet();
		servlet.doPost(request, response);

		ProductBean after = productDAO.findById(before.getProductId());

		assertEquals("新商品", after.getName());
		assertEquals(3000, after.getPrice());
		assertEquals(20, after.getStock());
		assertEquals(2, after.getCategoryId());
	}
}
