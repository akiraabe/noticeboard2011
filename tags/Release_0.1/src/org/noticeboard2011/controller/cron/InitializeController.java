package org.noticeboard2011.controller.cron;

import java.util.logging.Logger;

import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class InitializeController extends Controller {

    static Logger logger = Logger.getLogger(InitializeController.class.getName());
    private PersonService personService = new PersonService();
    
    @Override
    public Navigation run() throws Exception {
        
        logger.fine("InitializeController start...");
        
        personService.initialize();
        
        return null;
    }
}
