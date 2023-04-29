package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.*;
import com.anixuil.manager_system.mapper.DictFieldTableMapper;
import com.anixuil.manager_system.mapper.DictTableMapper;
import com.anixuil.manager_system.service.ClassTableService;
import com.anixuil.manager_system.service.DepartTableService;
import com.anixuil.manager_system.service.DictTableService;
import com.anixuil.manager_system.service.MajorTableService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
 * @since 2023-04-09
 */
@Service
public class DictTableServiceImpl extends ServiceImpl<DictTableMapper, DictTable> implements DictTableService {
    @Resource
    DictTableMapper dictTableMapper;

    @Resource
    DictFieldTableMapper dictFieldTableMapper;

    @Resource
    DepartTableService departTableService;

    @Resource
    ClassTableService classTableService;

    @Resource
    MajorTableService majorTableService;

    //新增字典
    @Override
    public Rest addDict(DictTable dictTable) {
        String msg = "新增字典";
        try{
            if(dictTable.getDictType().equals("aln")){
                //判断字典名是否重复
                if(dictTableMapper.selectOne(new LambdaQueryWrapper<DictTable>().eq(DictTable::getDictName,dictTable.getDictName())) != null){
                    return Rest.fail(msg,"字典名重复");
                }
            }
            //新增字典
            dictTableMapper.insert(dictTable);
            return Rest.success(msg,true);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //获取字典
    @Override
    public Rest getDict(String dictType,String dictName){
        String msg = "获取字典";
        try{
            //根据字典类型和字典名获取字典 表域用表域的方法 字典用字典的方法
            if(dictType.equals("aln")){
                //获取字典项
                List<DictFieldTable> dictFieldTableList = dictFieldTableMapper.selectList(new QueryWrapper<DictFieldTable>().eq("dict_name",dictName));
                return Rest.success(msg,dictFieldTableList);
            }else if(dictType.equals("table")){
                //根据dictName对应实体类获取对应的字典
                switch (dictName){
                    case "depart":
                        List<DepartTable> departTableList = departTableService.list();
                        List<Map<String,Object>> departMapList = departTableList.stream().map(departTable -> {
                            Map<String,Object> map = new HashMap<>();
                            map.put("departUuid",departTable.getDepartUuid());
                            map.put("departName",departTable.getDepartName());
                            return map;
                        }).collect(Collectors.toList());
                        return Rest.success(msg,departMapList);
                    case "class":
                        List<ClassTable> classTableList = classTableService.list();
                        List<Map<String,Object>> classMapList = classTableList.stream().map(classTable -> {
                            Map<String,Object> map = new HashMap<>();
                            map.put("classUuid",classTable.getClassUuid());
                            map.put("className",classTable.getClassName());
                            return map;
                        }).collect(Collectors.toList());
                        return Rest.success(msg,classMapList);
                    case "major":
                        List<MajorTable> majorTableList = majorTableService.list();
                        List<Map<String,Object>> majorMapList = majorTableList.stream().map(majorTable -> {
                            Map<String,Object> map = new HashMap<>();
                            map.put("majorUuid",majorTable.getMajorUuid());
                            map.put("majorName",majorTable.getMajorName());
                            return map;
                        }).collect(Collectors.toList());
                        return Rest.success(msg,majorMapList);
                    default:
                        return Rest.fail(msg,"字典类型错误");
                }
            }else{
                return Rest.error(msg,"字典类型错误");
            }
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }
}
