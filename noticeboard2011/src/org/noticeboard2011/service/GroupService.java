package org.noticeboard2011.service;

import java.util.List;
import java.util.Map;

import org.noticeboard2011.meta.GroupMeta;
import org.noticeboard2011.model.Group;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;


public class GroupService {
    
    private GroupMeta groupMeta = GroupMeta.get();

    public Group insert(Map<String, Object> requestMap) {
        Group group = new Group();
        BeanUtil.copy(requestMap, group);
        Datastore.put(group);
        return group;
    }
    
    public List<Group> findAll() {
        return Datastore
        .query(groupMeta)
        .asList();
    }
}
