package com.journaldev.bootifulmongodb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.journaldev.bootifulmongodb.model.Content;
import com.journaldev.bootifulmongodb.dal.ContentDAL;
import com.journaldev.bootifulmongodb.dal.ContentRepository;
import com.journaldev.bootifulmongodb.model.Value;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/landing")
public class ContentController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final ContentRepository contentRepository;

    private final ContentDAL contentDAL;

    public ContentController(ContentRepository contentRepository, ContentDAL contentDAL) {
        this.contentRepository = contentRepository;
        this.contentDAL = contentDAL;
    }

    //add new menu
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public Content addNewMenu(@RequestBody Content content) {
        //cek company valid gak, kemudian company dan menu nya ada gak
        String menu = content.getMenu();
        ObjectId id = new ObjectId((String) content.getCompanyId());
        Content contents = contentRepository.findByCompanyIdAndMenu(id, menu);
        if (contents == null) {
            LOG.info("INSERT SUCCESS");
            return contentRepository.insert(content);
        }else{
            LOG.info("FAILED, MENU IS EXIST");
            return content;
        }
    }
    
    //update menu dengan id
    @RequestMapping(value = "/menu", method = RequestMethod.PUT)
    public Content updateMenu(@RequestBody Content content) {
        ObjectId idMenu = new ObjectId((String) content.getId());
        contentDAL.updateMenu(idMenu, content);
        return content;
    }
    
    //get home data
    @RequestMapping(value = "/menu/{companyId}", method = RequestMethod.GET)
    public List<Content> getMenu(@PathVariable ObjectId companyId) {
        List<Content> listContent = contentDAL.getMenu(companyId);
        if (listContent == null) {
            LOG.info("Data Not Found for Company:" + companyId);
        }
        return listContent;
    }
    
    //get one menu
    @RequestMapping(value = "/menu/{menu}/{companyId}", method = RequestMethod.GET)
    public Content getOneMenu(@PathVariable String menu,@PathVariable ObjectId companyId) {
        Content contents = contentRepository.findByCompanyIdAndMenu(companyId,menu);
        return contents;
    }

    //get home data
    @RequestMapping(value = "/home/{companyId}", method = RequestMethod.GET)
    public List<Content> getHome(@PathVariable ObjectId companyId) {
        List<Content> listContent = contentDAL.getHome(companyId);
        if (listContent == null) {
            LOG.info("Data Not Found for Company:" + companyId);
        }
        return listContent;
    }
        
    //add new value in a menu
    @RequestMapping(value = "/value/{menu}/{companyId}", method = RequestMethod.POST)
    public Value addNewValue(@PathVariable String menu, @PathVariable ObjectId companyId, @RequestBody Value value) {
        contentDAL.addValue(menu, companyId, value);
        return value;
    }
    
    //update value in a menu
    @RequestMapping(value = "/value/{menuId}", method = RequestMethod.PUT)
    public Value updateValue(@PathVariable ObjectId menuId, @RequestBody Value value) {
        ObjectId valueId = new ObjectId((String) value.getId());
        contentDAL.updateValue(menuId, valueId, value);
        return value;
    }
    
    //delete value in a menu
    @RequestMapping(value = "/value/{menuId}/{valueId}", method = RequestMethod.DELETE)
    public String deleteValue(@PathVariable ObjectId menuId, @PathVariable ObjectId valueId) {
        contentDAL.deleteValue(menuId, valueId);
        return "DELETED";
    }
    
    //get one value by menuId and valueId
    @RequestMapping(value = "/value/{menuId}/{valueId}", method = RequestMethod.GET)
    public String getOneValue(@PathVariable ObjectId menuId, @PathVariable ObjectId valueId) {
        return contentDAL.getOneValue(menuId, valueId);
    }

    //get value by menu and companyId
    @RequestMapping(value = "/values/{menu}/{companyId}", method = RequestMethod.GET)
    public List<Content> listValue(@PathVariable String menu, @PathVariable ObjectId companyId) {
        List<Content> listContent = contentDAL.getValue(menu, companyId);
        if (listContent == null) {
            LOG.info("Data Not Found at Menu :" + menu + ", Company:" + companyId);
        }
        return listContent;
    }
}
