package org.noticeboard2011.controller.person;

import java.util.logging.Logger;

import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * {basePath}/update のURLによって呼ばれるコントローラ<br/>
 * <li>入力内容のバリデーションを行う</li>
 * <li>PersonのEntityを更新する</li>
 * <li>非同期処理なので画面遷移は行わない</li>
 * 
 * @author akiraabe
 */
public class UpdateController extends Controller {
    
    static Logger logger = Logger.getLogger(UpdateController.class.getName());
    private PersonService personService = new PersonService();

    @Override
    public Navigation run() throws Exception {
        
        logger.fine("UpdateController#run start.");
        logger.fine("firstName : " + request.getParameter("firstName"));
        logger.fine("id : " + request.getParameter("id"));
        logger.fine("place : " + request.getParameter("place"));
        
        personService.update(new Long(request.getParameter("id")), request.getParameter("place"));
        
        return null;
    }

}
