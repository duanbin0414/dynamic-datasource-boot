package com.deven.boot.datasource.controller;

import com.deven.boot.datasource.service.DynamicDataSourceService;
import com.deven.boot.datasource.vo.CorpDataSourceNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicDataSourceController {

    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;

    @PostMapping(path = "/datasources/nodes")
    public Object addNode(@RequestBody CorpDataSourceNode node) {

        return dynamicDataSourceService.addDataSourceNode(node);

    }
}
