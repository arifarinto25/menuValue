/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journaldev.bootifulmongodb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author jokomakruf
 */
@Setter
@Getter
public class Value {

    @Id
    private ObjectId id = ObjectId.get();

    private Date creationDate = new Date();
    private String creatorId;
    private Boolean enabled = Boolean.TRUE;
    private ObjectId idSpesific;
    private String title;
    private String subtitle;
    private String desc;
    private String desc1;
    private String desc2;
    private String desc3;
    private List<String> schedules = new ArrayList<>();
    private List<String> imgs = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private Integer price;
    private Integer stock;
    private Integer clickCounter;
    private List<SocmedAccount> socmedAccounts = new ArrayList<>();;
    private List<Location> location = new ArrayList<>();
    private List<VAdd> vadd1 = new ArrayList<>();
    private List<VAdd> vadd2 = new ArrayList<>();
    private List<VAdd> vadd3 = new ArrayList<>();
    //commentReview
    
    public String getId() {
        return id.toHexString();
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setTitle(String title) {
        this.title = title.toUpperCase();
    }
}
