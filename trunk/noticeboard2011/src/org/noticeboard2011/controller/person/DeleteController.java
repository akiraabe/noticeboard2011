package org.noticeboard2011.controller.person;

import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class DeleteController extends Controller {

    static Logger logger = Logger.getLogger(DeleteController.class.getName());
    
    @Override
    public Navigation run() throws Exception {
        
        logger.fine("DeleteController#run start.");
        logger.fine("method : " + request.getParameter("method"));
        logger.fine("deketeItems : " + request.getParameter("deleteItems"));

        //TODO 削除処理の実装を行う。（第３イテレーション）
        
        return null;
    }
}
