package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.DepartTable;
import com.anixuil.manager_system.entity.MajorTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.entity.StudentTable;
import com.anixuil.manager_system.mapper.DepartTableMapper;
import com.anixuil.manager_system.mapper.MajorTableMapper;
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
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class MajorTableServiceImpl extends ServiceImpl<MajorTableMapper, MajorTable> implements MajorTableService {

    @Resource
    StudentTableService studentTableService;
    @Resource
    DepartTableMapper departTableMapper;

    @Override
    public Rest addMajor(MajorTable majorTable) {
        String msg = "新增专业";
        try{
            //判断同院系下专业名是否重复
            String departUuid = majorTable.getDepartUuid();
            String majorName = majorTable.getMajorName();
            if(this.getOne(new QueryWrapper<MajorTable>().eq("depart_uuid",departUuid).eq("major_name",majorName)) != null) {
                return Rest.fail("专业名重复," + msg, "专业名重复");
            }
            Boolean result = this.save(majorTable);
            if(result){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    @Override
    public Rest updateMajor(MajorTable majorTable) {
        String msg = "修改专业";
        try{
            Boolean result = this.updateById(majorTable);
            if(result){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    @Override
    public Rest deleteMajor(MajorTable majorTable) {
        String msg = "删除专业";
        try{
            String majorUuid = majorTable.getMajorUuid();
            Boolean result = this.remove(new QueryWrapper<MajorTable>().eq("major_uuid",majorUuid));
            if(result){
                return Rest.success(msg, true);
            }
            return Rest.fail(msg, false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    @Override
    public Rest getMajorList(Integer pageNum, Integer pageSize) {
        String msg = "获取所有专业";
        try{
            IPage<MajorTable> page = new Page<>(pageNum,pageSize);
            //分页查询 用stream流来弄出一个list 里面是所有的专业数据 用map来弄出一个list 里面是所有的专业数据 专业数据里添加一个字段 studentCount 用来存放专业下的学生数量
            LambdaQueryWrapper<MajorTable> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(MajorTable::getMajorUuid,MajorTable::getMajorName,MajorTable::getMajorIntro,MajorTable::getDepartUuid,MajorTable::getCreateDate,MajorTable::getUpdateDate,MajorTable::getIsDelete);
            IPage<MajorTable> majorTableIPage = this.page(page, queryWrapper);
            List<MajorTable> majorTableList = majorTableIPage.getRecords();
            List<Map<String,Object>> mapList = majorTableList.stream().map(majorTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("majorUuid",majorTable.getMajorUuid());
                map.put("departUuid",majorTable.getDepartUuid());
                map.put("majorName",majorTable.getMajorName());
                map.put("majorIntro",majorTable.getMajorIntro());
                //格式化一下majorTable.getCreateDate()的时间戳
                map.put("createDate", Datetime.format(majorTable.getCreateDate()));
                map.put("updateDate",Datetime.format(majorTable.getUpdateDate()));
                map.put("isDelete",majorTable.getIsDelete());
                map.put("studentCount",studentTableService.count(new QueryWrapper<StudentTable>().eq("major_uuid",majorTable.getMajorUuid())));
                map.put("departName",departTableMapper.selectOne(new QueryWrapper<DepartTable>().eq("depart_uuid",majorTable.getDepartUuid())).getDepartName());
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            //分页数据
            map.put("total",majorTableIPage.getTotal());
            map.put("currentPage",majorTableIPage.getCurrent());
            map.put("pageSize",majorTableIPage.getSize());
            map.put("pages",majorTableIPage.getPages());
            map.put("records",mapList);
            return Rest.success(msg,map);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }
}
