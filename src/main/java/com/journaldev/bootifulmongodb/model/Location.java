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
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author jokomakruf
 */
@Setter
@Getter
public class Location {

    @Id
    private ObjectId id = ObjectId.get();

    private String address;
    private String city;
    private String province;
    private String phone;
    private String email;
    private String postCode;
    private String imgUrl;
    private String coordinate;

    public String getId() {
        return id.toHexString();
    }

    public void setAddress(String address) {
        this.address = address.toUpperCase();
    }
}
