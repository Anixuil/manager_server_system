package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.entity.CandidateTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.CandidateTableService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("candidate")
public class CandidateTableController {
    @Resource
    CandidateTableService candidateTableService;


    //修改考生信息
    @PutMapping("updateCandidate")
    public Rest updateCandidate(@RequestBody CandidateTable candidateTable){
        return candidateTableService.updateCandidate(candidateTable);
    }


}
