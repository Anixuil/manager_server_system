package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.ExamScoreTable;
import com.anixuil.manager_system.entity.Rest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
public interface ExamScoreTableService extends IService<ExamScoreTable> {
    //查询个人考试成绩
    Rest getExamScore(String token);

}
