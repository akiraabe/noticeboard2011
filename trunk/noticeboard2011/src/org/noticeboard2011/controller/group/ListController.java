package org.noticeboard2011.controller.group;

import java.util.List;

import org.noticeboard2011.model.Group;
import org.noticeboard2011.service.GroupService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class ListController extends Controller {

    private GroupService groupService = new GroupService();

    @Override
    public Navigation run() throws Exception {

        List<Group> groups = groupService.findAll();
        requestScope("groups", groups);

        return forward("list.jsp");
    }
}
