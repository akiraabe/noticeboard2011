package org.noticeboard2011.controller.person;


import java.util.logging.Logger;

import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.RequestMap;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * {basePath}/insert のURLによって呼ばれるコントローラ<br/>
 * <li>入力内容のバリデーションを行う</li>
 * <li>PersonのEntityを生成する</li>
 * <li>Top画面に遷移（redirect）する</li>
 * 
 * @author akiraabe
 */
public class InsertController extends Controller {
    
    static Logger logger = Logger.getLogger(InsertController.class.getName());
    private PersonService personService = new PersonService();

    @Override
    public Navigation run() throws Exception {
        
        logger.fine("InsertController#run start.");
        
        // ユーザ情報取得
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        logger.fine("user : " + user.getEmail());
        
        if (!validate()) {
            return forward("create.jsp");
        }
        personService.insert(new RequestMap(request));
        return redirect("/");
    }
    
    private boolean validate() {
        Validators v = new Validators(request);
        v.add("firstName", v.required());
        v.add("lastName", v.required());
        v.add("place", v.required());
        return v.validate();
    }
}
