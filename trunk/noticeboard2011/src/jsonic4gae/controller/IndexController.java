package jsonic4gae.controller;

import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class IndexController extends Controller {

    static Logger logger = Logger.getLogger(IndexController.class.getName());
    
    @Override
    public Navigation run() throws Exception {
        
        logger.fine("IndexController#run start.");
        
        return forward("index.html");
    }
}
