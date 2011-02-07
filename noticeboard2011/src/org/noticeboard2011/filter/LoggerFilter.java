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

public class LoggerFilter implements Filter {

    static Logger logger = Logger.getLogger(LoggerFilter.class.getName());
    protected FilterConfig config;
    private ServletContext context;
    private String filterName;

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        // HttpServletRequest取得
        HttpServletRequest req = (HttpServletRequest) request;
        logger.fine("LoggerFilter start...");
        logger.fine("RemoteHost : " + req.getRemoteHost());
        logger.fine("RequestURL : " + req.getRequestURI());

        // ユーザ情報取得
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        // Session情報取得
        HttpSession session = req.getSession(true);
        String auth = (String) session.getAttribute("auth");
        if (auth != null)
            logger.fine("auth : " + auth);

        //TODO とんでもないif文だ!!!
        if (user != null) {
            logger.fine("user : " + user.getEmail());
            if (!"/logout".equals(req.getRequestURI())) {
                if (auth == null) {
                    if (authrizedUser(user.getEmail())) {
                        session.setAttribute("auth", user.getEmail());
                    } else {
                        RequestDispatcher rd =
                            request.getRequestDispatcher("/error.jsp");
                        rd.forward(request, response);
                        return;
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    private boolean authrizedUser(String email) {

        // PersonService取得
        PersonService service = new PersonService();
        if (service.findByMailAddress(email).size() > 0) {
            logger.fine("ｗｗｗｗｗｗ");
            return true;
        }

        if (email.equals("xxxxxxxx@gmail.com")) {
            return false;
        }
        return true;
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        context = config.getServletContext();
        filterName = config.getFilterName();
    }

    public void destroy() {

    }
}
