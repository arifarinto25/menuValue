/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journaldev.bootifulmongodb.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author jokomakruf
 */
@Setter
@Getter
public class VAdd {

    @Id
    private ObjectId id = ObjectId.get();

    private String add0;
    private String add1;
    private String add2;
    private String add3;

    public String getId() {
        return id.toHexString();
    }
}
