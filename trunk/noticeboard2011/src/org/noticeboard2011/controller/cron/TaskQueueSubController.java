package org.noticeboard2011.controller.cron;

import java.util.logging.Logger;

import org.noticeboard2011.model.Person;
import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class TaskQueueSubController extends Controller {

    static Logger logger =
        Logger.getLogger(TaskQueueSubController.class.getName());
    private PersonService personService = new PersonService();

    @Override
    public Navigation run() throws Exception {

        logger.fine("TaskQueueSubController start...");
        logger.fine("id : " + request.getParameter("id"));

        try {
            Person person =
                personService.get(new Long(request.getParameter("id")));
            logger.fine("firstName : " + person.getFirstName());
            logger.fine("lastName  : " + person.getLastName());
            logger.fine("place     : " + person.getPlace());
            logger.fine("memo      : " + person.getMemo());
        } catch (Exception e) {
            logger.warning("Could not get from datasource. : "
                + e.getMessage()
                + e);
        }
        return null;
    }
}
