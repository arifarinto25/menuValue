/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journaldev.bootifulmongodb.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author jokomakruf
 */
@Setter
@Getter
@Document(collection = "content")
public class Products {
    
    @Id
    private ObjectId id = ObjectId.get();
    
    private ObjectId companyId;
    private String menu;    
    private String displayText;
    private Integer displayNumber;
    private Boolean enabled = Boolean.TRUE;
    private List<Value> value = new ArrayList<>();
    
    public String getId() {
        return id.toHexString();
    }
    
    public String getCompanyId() {
        return companyId.toHexString();
    }
    
    public void setMenu(String menu) {
        this.menu = menu.toLowerCase();
    }
    
    public void setDisplayText(String displayText) {
        this.displayText = displayText.toUpperCase();
    }
}
