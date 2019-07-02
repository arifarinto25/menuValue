/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journaldev.bootifulmongodb.dal;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JackzHouse-PC
 */
@Getter
@Setter
public class SearchRequestDto {
    
    private List<FieldRequestDto> search = new ArrayList<>();
    
}
