package org.noticeboard2011.controller.cron;

import java.util.logging.Logger;

import org.noticeboard2011.model.Person;
import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * タスクキュー実験用のクラス。
 * （現在未使用なので、近いうちに削除）
 * @author akiraabe
 */
public class TaskQueueSubController extends Controller {

    static Logger logger =
        Logger.getLogger(TaskQueueSubController.class.getName());
    private PersonService personService = new PersonService();

    /**
     * Personモデルの主要項目をログに表示する。
     * 
     * @throws Exception idでgetできなかった時に例外を返す。
     * 可能性としては、mainからタスクキューで実行される間にデータが削除されたケースを想定。
     * （例外をcatchしておかないとタスクが無限ループしてしまう）
     */
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
