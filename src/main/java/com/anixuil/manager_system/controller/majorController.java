package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.MajorTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.MajorTableService;
import com.anixuil.manager_system.utils.Datetime;
import com.anixuil.manager_system.utils.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("major")
public class majorController {
    @Autowired
    MajorTableService majorTableService;

    //新增专业
    @PostMapping("addMajor")
    public Rest addMajor(@RequestBody MajorTable majorTable){
        String msg = "新增专业";
        try{
            majorTable.setMajorUuid(Uuid.getUuid());
            majorTable.setCreateDate(Datetime.getTimestamp());
            Boolean result = majorTableService.save(majorTable);
            if(result){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

}
