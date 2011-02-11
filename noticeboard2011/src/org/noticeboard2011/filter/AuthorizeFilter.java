package org.noticeboard2011.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.noticeboard2011.service.PersonService;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthorizeFilter implements Filter {

    static Logger logger = Logger.getLogger(AuthorizeFilter.class.getName());
    protected FilterConfig config;
    private ServletContext context;
    private String filterName;

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        // HttpServletRequest取得とロギング
        HttpServletRequest req = (HttpServletRequest) request;
        logger.fine("AuthorizeFilter start...");
        logger.fine("RemoteHost : " + req.getRemoteHost());
        logger.fine("RequestURL : " + req.getRequestURI());

        // ユーザ情報取得
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        // Sessionから認証情報取得
        HttpSession session = req.getSession(true);
        String auth = (String) session.getAttribute("auth");

        // logoutする場合
        if ("/logout".equals(req.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        // 認証処理
        if (auth == null) {
            if (isRegisteredUser(user.getEmail())) {
                session.setAttribute("auth", user.getEmail());
            } else {
                RequestDispatcher rd =
                    request.getRequestDispatcher("/error.jsp");
                rd.forward(request, response);
                return;
            }
        }
        logger.fine("Registered user : " + user.getEmail());
        chain.doFilter(request, response);
        return;
    }

    private boolean isRegisteredUser(String email) {
        PersonService service = new PersonService();
        if (service.findByMailAddress(email).size() > 0) {
            return true;
        }
        logger.warning("Not Registered user : " + email);
        return false;
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        context = config.getServletContext();
        filterName = config.getFilterName();
    }

    public void destroy() {

    }
}
