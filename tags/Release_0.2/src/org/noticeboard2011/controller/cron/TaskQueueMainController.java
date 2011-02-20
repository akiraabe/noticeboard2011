package org.noticeboard2011.controller.cron;

import java.util.List;
import java.util.logging.Logger;

import org.noticeboard2011.model.Person;
import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * TaskQueを生成し処理を呼び出す。
 * <br/>
 * GoogleCalendarControllerを呼び出す。
 * 
 * @author akiraabe
 *
 */
public class TaskQueueMainController extends Controller {

    static Logger logger =
        Logger.getLogger(TaskQueueMainController.class.getName());
    private PersonService personService = new PersonService();
    
    @Override
    public Navigation run() throws Exception {
        
        logger.fine("TaskQueueMainController start...");
        
        // TaskQueueの生成
        Queue queue = QueueFactory.getDefaultQueue();
        
        // Personを全て取り出す。
        List<Person> people = personService.getPersonListAll();
        
        for (Person person : people) {
            queue.add(TaskOptions.Builder
                .withUrl("/cron/retrieveGoogleCalendar")
                .param("id", new Long(person.getKey().getId()).toString()));
            logger.fine("submitted a task for id " + new Long(person.getKey().getId()).toString());
        }
        
        return null;
    }
}
