package com.journaldev.bootifulmongodb.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.journaldev.bootifulmongodb.model.Content;
import com.journaldev.bootifulmongodb.model.Value;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Update;

@Repository
public class ContentDALImpl implements ContentDAL {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    //update value
    @Override
    public Content updateMenu(ObjectId idMenu, Content content) {
        Update update = new Update();
        update.set("displayText", content.getDisplayText());
        update.set("displayNumber", content.getDisplayNumber());
        update.set("enabled", content.getEnabled());
        Criteria criteria = Criteria.where("id").is(idMenu);
        mongoTemplate.updateFirst(Query.query(criteria), update, Content.class);
        return content;
    }

    @Override
    public List<Content> getMenu(ObjectId companyId) {
        Query query = new Query();
        Sort orderByNumber = new Sort(Sort.Direction.ASC, "displayNumber");
        query.addCriteria(Criteria.where("companyId").is(companyId));
        query.with(orderByNumber);
        query.fields().exclude("value");
        return mongoTemplate.find(query, Content.class);
    }
    
    @Override
    public List<Content> getHome(ObjectId companyId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("companyId").is(companyId));
        query.fields().slice("value", -5);
        return mongoTemplate.find(query, Content.class);
    }

    //add value
    @Override
    public Value addValue(String menu, ObjectId companyId, Value value) {
        Update update = new Update();
        update.push("value", value);
        Criteria criteria = Criteria.where("companyId").is(companyId).and("menu").is(menu);
        mongoTemplate.updateFirst(Query.query(criteria), update, Content.class);
        return value;
    }
    
    //update value
    @Override
    public Value updateValue(ObjectId idMenu, ObjectId idValue, Value value) {
        Update update = new Update();
        update.set("value.$", value);
        Criteria criteria = Criteria.where("id").is(idMenu).and("value.id").is(idValue);
        mongoTemplate.updateFirst(Query.query(criteria), update, Content.class);
        return value;
    }
    
    @Override
    public Void deleteValue(ObjectId idMenu, ObjectId idValue) {
        Update update = new Update();
        update.unset("value.$");
        Criteria criteria = Criteria.where("id").is(idMenu).and("value.id").is(idValue);
        mongoTemplate.updateFirst(Query.query(criteria), update, Content.class);
        return null;
    }
    
    //get value
    @Override
    public List<Content> getValue(String menu, ObjectId companyId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("menu").is(menu).and("companyId").is(companyId));
        return mongoTemplate.find(query, Content.class);
    }

    @Override
    public String getOneValue(ObjectId idMenu, ObjectId idValue) {
        Criteria criteria = Criteria.where("id").is(idMenu).and("value.id").is(idValue);
        Aggregation agg = newAggregation(
                unwind("value"),
                match(criteria)
        );
//        AggregationResults<Value> results = mongoTemplate.aggregate(agg, Content.class, Value.class);
//        System.out.println("Output ====>" + results.getRawResults().toJson());
//        List<Value> mappedResult = results.getMappedResults();
        return mongoTemplate.aggregate(agg, Content.class, Value.class).getRawResults().toJson();
    }

}
