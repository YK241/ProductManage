package servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServletTest {

    private LoginServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @Before
    public void setUp() {
        servlet = new LoginServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void ログイン成功時はセッションにユーザを保存してリダイレクトされる() throws Exception {

        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("1234");
        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("loginUser"), any());
        verify(response).sendRedirect("product-list");
    }

    @Test
    public void ログイン失敗時はエラーを設定してloginjspへforwardされる() throws Exception {

        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("9999");
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
        verify(dispatcher).forward(request, response);
    }
    
    @Test
    public void 存在しないユーザーの場合はエラーを設定してloginjspへforwardされる() throws Exception {

        when(request.getParameter("username")).thenReturn("unknown");
        when(request.getParameter("password")).thenReturn("1234");
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
        verify(dispatcher).forward(request, response);
    }
}
