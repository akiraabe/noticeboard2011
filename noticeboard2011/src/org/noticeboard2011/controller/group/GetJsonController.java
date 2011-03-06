package org.noticeboard2011.controller.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.noticeboard2011.controller.AbstractJsonController;
import org.noticeboard2011.model.Group;
import org.noticeboard2011.service.GroupService;
import org.slim3.controller.Navigation;

public class GetJsonController extends AbstractJsonController  {

    private GroupService groupService = new GroupService();
    
    @Override
    public Navigation run() throws Exception {
        
        Map<String, List> map = new HashMap<String, List>();
        List<String> list = new ArrayList<String>();
        for (Group group : groupService.findAll()) {
            list.add(group.getName());
        }
        map.put("groups",list);
        json(map);
        return null;
    }
}
