package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.*;
import com.anixuil.manager_system.mapper.CandidateTableMapper;
import com.anixuil.manager_system.pojo.UserAll;
import com.anixuil.manager_system.service.CandidateTableService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
@Service
public class CandidateTableServiceImpl extends ServiceImpl<CandidateTableMapper, CandidateTable> implements CandidateTableService {

    @Override
    public Boolean addCandidate(UserAll user) {
        try{
            CandidateTable candidateTable = new CandidateTable();
            if(user.getCandidateId().isEmpty()) {
                candidateTable.setCandidateId(String.valueOf(new Date().getTime()));
            }else{
                candidateTable.setCandidateId(user.getCandidateId());
            }
            candidateTable.setUserUuid(user.getUserUuid());
            candidateTable.setMajorUuid(user.getMajorUuid());
            candidateTable.setCandidateStatus(user.getCandidateStatus());
            candidateTable.setExamPlace(user.getExamPlace());
            candidateTable.setInformationStatus(user.getInformationStatus());
            candidateTable.setExamDate(user.getExamDate());
           return save(candidateTable);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteCandidate(CandidateTable candidateTable) {
        try{
            LambdaQueryWrapper<CandidateTable> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CandidateTable::getUserUuid,candidateTable.getUserUuid());
            return remove(wrapper);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Rest updateCandidate(CandidateTable candidateTable) {
        String msg = "修改考生信息";
        try {
            boolean result = updateById(candidateTable);
            if(result) {
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        }catch (Exception e) {
            return Rest.error(msg,e);
        }
    }


}
