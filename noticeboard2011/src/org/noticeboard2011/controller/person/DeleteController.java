package org.noticeboard2011.controller.person;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.noticeboard2011.controller.AbstractJsonController;
import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Navigation;

public class DeleteController extends AbstractJsonController {

    static Logger logger = Logger.getLogger(DeleteController.class.getName());
    private PersonService personService = new PersonService();
    
    @Override
    public Navigation run() throws Exception {
        
        logger.fine("DeleteController#run start.");
        logger.fine("method : " + request.getParameter("method"));
        logger.fine("deleteItems : " + request.getParameter("deleteItems"));

        personService.delete(new Long(request.getParameter("deleteItems")));
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("returncode", "VALID");
        json(map);
        
        return null;
    }
}
