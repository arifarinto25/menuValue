package com.journaldev.bootifulmongodb.dal;

import java.util.List;

import com.journaldev.bootifulmongodb.model.Content;
import com.journaldev.bootifulmongodb.model.Value;
import org.bson.types.ObjectId;

public interface ContentDAL {
    
    Content updateMenu(ObjectId idMenu, Content content);

    List<Content> getMenu(ObjectId companyId);
    
    List<Content> getHome(ObjectId companyId);

    List<Content> getValue(String menu, ObjectId companyId);

    Value addValue(String menu, ObjectId id, Value value);
    
    Value updateValue(ObjectId idMenu, ObjectId idValue, Value value);
    
    Void deleteValue(ObjectId idMenu, ObjectId idValue);
    
    String getOneValue(ObjectId idMenu, ObjectId idValue);
}
