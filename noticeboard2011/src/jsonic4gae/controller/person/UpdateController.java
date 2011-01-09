package jsonic4gae.controller.person;

import jsonic4gae.service.PersonService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * {basePath}/update のURLによって呼ばれるコントローラ<br/>
 * <li>入力内容のバリデーションを行う</li>
 * <li>PersonのEntityを更新する</li>
 * <li>非同期処理なので画面遷移は行わない</li>
 * 
 * @author akiraabe
 */
public class UpdateController extends Controller {
    
    private PersonService personService = new PersonService();

    @Override
    public Navigation run() throws Exception {
        
        //TODO 入力パラメータのチェックが必要
//        System.out.println(request.getAttribute("firstName"));
//        System.out.println(request.getAttribute("place"));
        
        personService.update((String) request.getAttribute("firstName"), (String) request.getAttribute("place"));
        
        return null;
    }
}
