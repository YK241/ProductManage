package test.servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servlet.AuthFilter;

public class AuthFilterTest {

    private AuthFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private RequestDispatcher dispatcher;

    @Before
    public void setUp() {
        filter = new AuthFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void 未ログイン時はloginjspへforwardされる() throws Exception {

        when(request.getRequestURI()).thenReturn("/product-list");
        when(request.getSession(false)).thenReturn(null);
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        filter.doFilter(request, response, chain);

        verify(dispatcher).forward(request, response);
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    public void ログイン済みなら処理が続行される() throws Exception {

        HttpSession session = mock(HttpSession.class);

        when(request.getRequestURI()).thenReturn("/product-list");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("loginUser")).thenReturn(new Object());

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }
}
