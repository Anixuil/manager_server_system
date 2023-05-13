package com.anixuil.manager_system.service;

import com.anixuil.manager_system.entity.ExamClassTable;
import com.anixuil.manager_system.entity.Rest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
public interface ExamClassTableService extends IService<ExamClassTable> {
    //新增考试科目
    Rest addExamClass(ExamClassTable examClassTable);

    //修改考试科目
    Rest updateExamClass(ExamClassTable examClassTable);

    //删除考试科目
    Rest deleteExamClass(ExamClassTable examClassTable);

    //查询考试科目
    Rest getExamClassList(Integer pageNum, Integer pageSize, String majorUuid, String examClassName, String examType);
}
