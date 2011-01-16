package org.noticeboard2011.controller.person;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.noticeboard2011.controller.AbstractJsonController;
import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

/**
 * {basePath}/update のURLによって呼ばれるコントローラ<br/>
 * <li>入力内容のバリデーションを行う</li>
 * <li>PersonのEntityを更新する</li>
 * <li>非同期処理なので画面遷移は行わない</li>
 * 
 * @author akiraabe
 */
public class UpdateController extends AbstractJsonController {
    
    static Logger logger = Logger.getLogger(UpdateController.class.getName());
    private PersonService personService = new PersonService();

    @Override
    public Navigation run() throws Exception {
        
        logger.fine("UpdateController#run start.");
        logger.fine("command : " + request.getParameter("command"));
        logger.fine("firstName : " + request.getParameter("firstName"));
        logger.fine("lastName : " + request.getParameter("lastName"));
        logger.fine("id : " + request.getParameter("id"));
        logger.fine("place : " + request.getParameter("place"));
        
        Map<String, String> map = new HashMap<String, String>();
        if (!validate() && "PROPERTY".equals(request.getParameter("command"))) {
            //TODO エラー処理
            map.put("returncode", "INVALID");
            logger.warning("VALIDATION ERROR");
            json(map);
            return null;
        } else {
            map.put("returncode", "VALID");
        }
        
        if ("PLACE".equals(request.getParameter("command")))
            personService.updatePlace(new Long(request.getParameter("id")), request.getParameter("place"));
        
        if ("PROPERTY".equals(request.getParameter("command")))
            personService.updateProperty(new Long(request.getParameter("id")), request.getParameter("firstName"), request.getParameter("lastName"));

        json(map);
        return null;
    }
    
    private boolean validate() {
        Validators v = new Validators(request);
        v.add("firstName", v.required());
        v.add("lastName", v.required());
        return v.validate();
    }

}
