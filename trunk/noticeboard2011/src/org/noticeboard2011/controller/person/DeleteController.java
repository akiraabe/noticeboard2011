package org.noticeboard2011.controller.person;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.noticeboard2011.controller.AbstractJsonController;
import org.slim3.controller.Navigation;

public class DeleteController extends AbstractJsonController {

    static Logger logger = Logger.getLogger(DeleteController.class.getName());
    
    @Override
    public Navigation run() throws Exception {
        
        logger.fine("DeleteController#run start.");
        logger.fine("method : " + request.getParameter("method"));
        logger.fine("deleteItems : " + request.getParameter("deleteItems"));

        //TODO 削除処理の実装を行う。（第３イテレーション）
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("returncode", "NOTIMPLEMENT");
        json(map);
        
        return null;
    }
}