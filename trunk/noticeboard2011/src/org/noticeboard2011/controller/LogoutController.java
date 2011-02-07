package org.noticeboard2011.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LogoutController extends Controller {

    static Logger logger = Logger.getLogger(LogoutController.class.getName());
    
    @Override
    public Navigation run() throws Exception {
        
        logger.fine("LogoutController#run start.");
        
        UserService userService = UserServiceFactory.getUserService();
        String urlLogout = userService.createLogoutURL("/");
        
        HttpSession session = request.getSession(true);
        session.removeAttribute("auth");
        
        return redirect(urlLogout);
    }
}
