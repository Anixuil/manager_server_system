package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.DictFieldTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.DictFieldTableService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("dictField")
public class DictFieldTableController {
    @Resource
    DictFieldTableService dictFieldTableService;

    @PostMapping("addDictFieldItem")
    public Rest addDictField(@RequestBody DictFieldTable dictFieldTable){
        return dictFieldTableService.addDictFieldItem(dictFieldTable);
    }
}
