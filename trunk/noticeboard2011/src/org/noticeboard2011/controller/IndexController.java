package org.noticeboard2011.controller;

import java.util.logging.Logger;

import org.noticeboard2011.service.GroupService;
import org.slim3.controller.Navigation;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class IndexController extends AbstractJsonController {

    static Logger logger = Logger.getLogger(IndexController.class.getName());

    @Override
    protected Navigation run() throws Exception {

        logger.fine("IndexController#run start.");
        
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
            requestScope("email", user.getEmail());
        } else {
            requestScope("email", "anonymouse");
        }
        
        GroupService service = new GroupService();
        requestScope("groups", service.findAll());

        return forward("index.jsp");
    }
}
