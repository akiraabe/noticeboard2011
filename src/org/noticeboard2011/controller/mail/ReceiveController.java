package org.noticeboard2011.controller.mail;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ReceiveController extends Controller {
    
    static Logger logger =
        Logger.getLogger(ReceiveController.class.getName());
    private PersonService personService = new PersonService();
    
    @Override
    public Navigation run() throws Exception {
        
        logger.fine("ReceiveController start...");
        
        // ユーザ情報取得
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
            logger.fine("user : " + user.getEmail());
        }
        
        // mail 受信
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session, request.getInputStream());
        
        logger.fine("subject : " + message.getSubject());
        logger.fine("from : " + message.getFrom()[0].toString());
        logger.fine("to : " + request.getParameter("address"));
        
        String toAddress = request.getParameter("address");
        int length = toAddress.indexOf("@");
        personService.updatePlaceFromTwitter(toAddress.substring(0,length), message.getSubject());
        return null;
    }
}
