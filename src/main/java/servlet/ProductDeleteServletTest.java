package servlet;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;

public class ProductDeleteServletTest {

    private ProductDeleteServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ProductDAO productDao;

    @Before
    public void setUp() {
        servlet = spy(new ProductDeleteServlet());
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        productDao = mock(ProductDAO.class);

        doReturn(productDao).when(servlet).createProductDAO();
    }

    @Test
    public void 選択した商品がDB削除処理に渡されることを確認() throws Exception {

        when(request.getParameter("id")).thenReturn("10");
        when(productDao.deleteProduct(10)).thenReturn(true);

        servlet.doPost(request, response);

        verify(productDao).deleteProduct(10);
    }

    @Test
    public void 削除後に商品一覧画面へ遷移することを確認() throws Exception {

        when(request.getParameter("id")).thenReturn("10");
        when(productDao.deleteProduct(10)).thenReturn(true);

        servlet.doPost(request, response);

        verify(response).sendRedirect("product-list");
    }
}
