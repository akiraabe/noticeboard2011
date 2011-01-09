package jsonic4gae.controller.person;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * {basePath}/create のURLによって呼ばれるコントローラ<br/>
 * <li>create.jspに遷移する</li>
 * @author akiraabe
 */
public class CreateController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("/person/create.jsp");
    }
}
