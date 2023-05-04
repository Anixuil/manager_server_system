package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.DepartTable;
import com.anixuil.manager_system.entity.MajorTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.StudentTable;
import com.anixuil.manager_system.mapper.DepartTableMapper;
import com.anixuil.manager_system.service.DepartTableService;
import com.anixuil.manager_system.service.MajorTableService;
import com.anixuil.manager_system.service.StudentTableService;
import com.anixuil.manager_system.utils.Datetime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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
public class DepartTableServiceImpl extends ServiceImpl<DepartTableMapper, DepartTable> implements DepartTableService {
    @Resource
    private DepartTableMapper departTableMapper;
    @Resource
    private MajorTableService majorTableService;
    @Resource
    private StudentTableService studentTableService;

    @Override
    public Rest getAllDepart(Integer pageNum, Integer pageSize) {
        String msg = "获取所有院系";
        try{
            IPage<DepartTable> page = new Page<>(pageNum,pageSize);
            //分页查询 用stream流来弄出一个list 里面是所有的院系数据 用map来弄出一个list 里面是所有的专业数据 专业数据里添加一个字段 studentCount 用来存放专业下的学生数量
            LambdaQueryWrapper<DepartTable> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(DepartTable::getDepartUuid,DepartTable::getDepartName,DepartTable::getDepartIntro,DepartTable::getCreateDate,DepartTable::getUpdateDate,DepartTable::getIsDelete);
            IPage<DepartTable> departTableIPage = this.page(page, queryWrapper);
            List<DepartTable> departTableList = departTableIPage.getRecords();
            List<Map<String,Object>> mapList = departTableList.stream().map(departTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("departUuid",departTable.getDepartUuid());
                map.put("departName",departTable.getDepartName());
                map.put("departIntro",departTable.getDepartIntro());
                //格式化一下departTable.getCreateDate()的时间戳
                map.put("createDate", Datetime.format(departTable.getCreateDate()));
                map.put("updateDate",Datetime.format(departTable.getUpdateDate()));
                map.put("isDelete",departTable.getIsDelete());
                map.put("majorCount",majorTableService.count(new LambdaQueryWrapper<MajorTable>().eq(MajorTable::getDepartUuid,departTable.getDepartUuid())));
                AtomicLong studentCount = new AtomicLong();
                map.put("majorList",majorTableService.list(new LambdaQueryWrapper<MajorTable>().eq(MajorTable::getDepartUuid,departTable.getDepartUuid())).stream().map(majorTable -> {
                    Map<String,Object> majorMap = new HashMap<>();
                    majorMap.put("majorUuid",majorTable.getMajorUuid());
                    majorMap.put("majorName",majorTable.getMajorName());
                    majorMap.put("majorIntro",majorTable.getMajorIntro());
                    //格式化一下majorTable.getCreateDate()的时间戳
                    majorMap.put("createDate", Datetime.format(majorTable.getCreateDate()));
                    majorMap.put("updateDate",Datetime.format(majorTable.getUpdateDate()));
                    majorMap.put("isDelete",majorTable.getIsDelete());
                    majorMap.put("studentCount",studentTableService.count(new LambdaQueryWrapper<StudentTable>().eq(StudentTable::getMajorUuid,majorTable.getMajorUuid())));
                    studentCount.addAndGet(studentTableService.count(new LambdaQueryWrapper<StudentTable>().eq(StudentTable::getMajorUuid, majorTable.getMajorUuid())));
                    return majorMap;
                }).collect(Collectors.toList()));
                map.put("studentCount",studentCount.get());
                return map;
            }).collect(Collectors.toList());
            //返回的数据
            Map<String,Object> map = new HashMap<>();
            //分页数据
            map.put("total",departTableIPage.getTotal());
            map.put("currentPage",departTableIPage.getCurrent());
            map.put("pageSize",departTableIPage.getSize());
            map.put("pages",departTableIPage.getPages());
            map.put("records",mapList);
            return Rest.success(msg,map);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //新建院系
    @Override
    public Rest addDepart(DepartTable departTable) {
        String msg = "新建院系";
        try{
            //是否有重复的院系名
            Integer count = Math.toIntExact(departTableMapper.selectCount(new QueryWrapper<DepartTable>().eq("depart_name", departTable.getDepartName())));
            if(count > 0){
                return Rest.fail("院系名重复," + msg,"院系名重复");
            }
            int result = departTableMapper.insert(departTable);
            if(result != 0){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //修改院系
    @Override
    public Rest updateDepart(DepartTable departTable) {
        String msg = "修改院系";
        try{
            int result = departTableMapper.updateById(departTable);
            if(result != 0){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //删除院系
    @Override
    public Rest deleteDepart(DepartTable departTable) {
        String msg = "删除院系";
        try{
            String departUuid = departTable.getDepartUuid();
            int result = departTableMapper.deleteById(new QueryWrapper<DepartTable>().eq("depart_uuid",departUuid));
            if(result != 0){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }
}
