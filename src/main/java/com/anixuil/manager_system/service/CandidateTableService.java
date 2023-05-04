package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.CandidateTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.pojo.UserAll;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
@Service
public interface CandidateTableService extends IService<CandidateTable> {
    //添加考生
    Boolean addCandidate(UserAll user);

    //删除考生
    Boolean deleteCandidate(CandidateTable candidateTable);

    //修改考生信息
    Rest updateCandidate(CandidateTable candidateTable);
  }
