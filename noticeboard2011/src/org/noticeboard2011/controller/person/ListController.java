package org.noticeboard2011.controller.person;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.noticeboard2011.controller.AbstractJsonController;
import org.noticeboard2011.model.Person;
import org.noticeboard2011.service.PersonService;
import org.noticeboard2011.vo.ResultVo;
import org.slim3.controller.Navigation;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ListController extends AbstractJsonController {

    static Logger logger = Logger.getLogger(ListController.class.getName());

    private PersonService personService = new PersonService();

    @Override
    protected Navigation run() throws Exception {

        logger.fine("IndexController#run start.");
        
        // ユーザ情報取得
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
            logger.fine("user : " + user.getEmail());
        }
        // requestパラメータの取得
        int page = new Integer(request.getParameter("page"));
        int rp = new Integer(request.getParameter("rp"));
        String sortorder = request.getParameter("sortorder");
        String qtype = request.getParameter("qtype");
        String query = request.getParameter("query");
        logger.fine("page : " + page);
        logger.fine("rp : " + rp);
        logger.fine("sortorder : " + sortorder);
        logger.fine("qtype : " + qtype);
        logger.fine("query : " + query);

        // ページング用の開始位置の算出
        int startIndex = rp * (page - 1);

        // Datastoreからのデータ取得
        List<Person> people = getPersonList(rp, sortorder, startIndex, query);

        // JSON形式のデータ作成(for flexigrid)
        ResultVo data = generateJsonData(people);

        // JSON形式データの出力処理
        json(data);

        return null;
    }

    private List<Person> getPersonList(int rp, String sortorder, int startIndex, String query) {
       return personService.getPersonList(startIndex, rp, query, sortorder);
    }
    
    private ResultVo generateJsonData(List<Person> people) {
        ResultVo data = new ResultVo();
        data.setPage((String) request.getAttribute("page"));
        data.setTotal(personService.count(request.getParameter("query")));

        // データが入っている配列を作成する
        List array = new ArrayList();

        for (Person person : people) {
            String[] strArray = new String[6];
            strArray[0] = new Long(person.getKey().getId()).toString();
            strArray[1] = person.getVersion().toString();
            strArray[2] = person.getFirstName();
            strArray[3] = person.getLastName();
            strArray[4] = person.getPlace();
            strArray[5] = person.getMemo();
            array.add(strArray);
        }

        for (int i = 0; i < array.size(); i++) {
            data.addRow(i, (String[]) array.get(i));
        }
        return data;
    }

}
