package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.ExamClassTable;
import com.anixuil.manager_system.entity.ExamScoreTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.UserTable;
import com.anixuil.manager_system.mapper.ExamClassTableMapper;
import com.anixuil.manager_system.mapper.ExamScoreTableMapper;
import com.anixuil.manager_system.mapper.UserTableMapper;
import com.anixuil.manager_system.service.ExamScoreTableService;
import com.anixuil.manager_system.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anixuil
 * @since 2023-03-09
 */
@Service
public class ExamScoreTableServiceImpl extends ServiceImpl<ExamScoreTableMapper, ExamScoreTable> implements ExamScoreTableService {
    @Resource
    UserTableMapper userTableMapper;

    @Resource
    ExamClassTableMapper examClassTableMapper;
    @Override
    public Rest getExamScore(String token) {
        String msg = "查询个人成绩";
        try{
            //解析token
            String userUuid = JwtUtils.parseJWT(token).getSubject();
            UserTable userTable = userTableMapper.selectOne(
                    new LambdaQueryWrapper<UserTable>().eq(UserTable::getUserUuid,userUuid)
            );
            //查询所有有关的考试成绩
            List<ExamScoreTable> examScoreTableList = list(
                    new LambdaQueryWrapper<ExamScoreTable>().eq(ExamScoreTable::getUserUuid,userUuid)
            );
            List<Map<String,Object>> result = examScoreTableList.stream().map(examScoreTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("examScoreUuid",examScoreTable.getExamScoreUuid());
                map.put("examScore",examScoreTable.getExamScore());
                map.put("examClassUuid",examScoreTable.getExamClassUuid());
                ExamClassTable examClassTable = examClassTableMapper.selectById(examScoreTable.getExamClassUuid());
                map.put("examClassName",examClassTable.getExamClassName());
                map.put("examType",examClassTable.getExamType());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> info = new HashMap<>();
            info.put("records",result);
            QueryWrapper<ExamScoreTable> examScoreTableQueryWrapper = new QueryWrapper<>();
            examScoreTableQueryWrapper.select("IFNULL(sum(exam_score),0) as firstScore").eq("user_uuid",userTable.getUserUuid()).eq("exam_type","0");
            Map<String,Object> examScoreMap = getMap(examScoreTableQueryWrapper);
            info.put("firstScore",examScoreMap.get("firstScore"));
            //查询考生复试成绩各科总成绩
            examScoreTableQueryWrapper = new QueryWrapper<>();
            examScoreTableQueryWrapper.select("IFNULL(sum(exam_score),0) as secondScore").eq("user_uuid",userTable.getUserUuid()).eq("exam_type","1");
            examScoreMap = getMap(examScoreTableQueryWrapper);
            info.put("secondScore",examScoreMap.get("secondScore"));
            //查询考生调剂成绩各科总成绩
            examScoreTableQueryWrapper = new QueryWrapper<>();
            examScoreTableQueryWrapper.select("IFNULL(sum(exam_score),0) as thirdScore").eq("user_uuid",userTable.getUserUuid()).eq("exam_type","2");
            examScoreMap = getMap(examScoreTableQueryWrapper);
            info.put("thirdScore",examScoreMap.get("thirdScore"));
            return Rest.success(msg,info);

        }catch (Exception e){
            e.printStackTrace();
            return Rest.error(msg,e);
        }
    }
}
