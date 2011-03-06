package org.noticeboard2011.controller.group;

import org.noticeboard2011.model.Group;
import org.noticeboard2011.service.GroupService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.RequestMap;

public class InsertController extends Controller {

    private GroupService groupService = new GroupService();

    @Override
    public Navigation run() throws Exception {
        if (!validate()) {
            return forward("create.jsp");
        }
        Group group = groupService.insert(new RequestMap(request));
        return redirect("/");
    }
    
    private boolean validate() {
        Validators v = new Validators(request);
        v.add("name", v.required());
        v.add("description", v.required());
        return v.validate();
    }
}
