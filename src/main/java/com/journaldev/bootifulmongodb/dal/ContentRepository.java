package com.journaldev.bootifulmongodb.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.journaldev.bootifulmongodb.model.Content;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface ContentRepository extends MongoRepository<Content, String> {

    public Content findByCompanyIdAndMenu(ObjectId id, String menu);
    
    @Query(fields = "{ 'id': 1, 'companyId':1, 'menu':1,'displayText':1,'enabled':1,'value.$': 1 }")
    public Content findByIdAndValueId(Object id, ObjectId valueId);
    
    @Query(fields = "{ 'id': 1, 'companyId':1, 'menu':1,'displayText':1,'enabled':1,'value.$': 1 }")
    public Content findByCompanyIdAndMenuAndValueId(ObjectId companyId, String menu, ObjectId valueId);
}
