package com.anixuil.manager_system.controller;

import com.anixuil.manager_system.Pojo.DictData;
import com.anixuil.manager_system.entity.DepartTable;
import com.anixuil.manager_system.entity.DictFieldTable;
import com.anixuil.manager_system.entity.DictTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.service.DepartTableService;
import com.anixuil.manager_system.service.DictFieldTableService;
import com.anixuil.manager_system.service.DictTableService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("dict")
public class DictController {
    @Autowired
    DepartTableService departTableService;
    @Autowired
    DictTableService dictTableService;
    @Autowired
    DictFieldTableService dictFieldTableService;

    //获取院系字典
    @GetMapping("depart")
    public Rest getDepartDict(){
        String msg = "获取院系字典";
        try{
            List<DepartTable> departTableList = departTableService.list();
            List<Map<String,Object>> mapList = departTableList.stream().map(departTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("departUuid",departTable.getDepartUuid());
                map.put("departName",departTable.getDepartName());
                return map;
            }).collect(Collectors.toList());
            return Rest.success(msg,mapList);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //新增字典
    @PostMapping("add")
    public Rest addDict(@RequestBody DictData dictData){
        String msg = "新增字典";
        try{
            if(dictData.getDictTable().getDictType().equals("aln")){
                //判断字典名是否重复
                if(dictTableService.getOne(new QueryWrapper<DictTable>().eq("dict_name",dictData.getDictTable().getDictName())) != null){
                    return Rest.fail(msg,"字典名重复");
                }
            }
            //新增字典
            dictTableService.save(dictData.getDictTable());
            //新增字典项
            dictData.getDictFieldTableList().forEach(dictFieldTable -> {
                dictFieldTable.setDictName(dictData.getDictTable().getDictName());
                dictFieldTableService.save(dictFieldTable);
            });
            return Rest.success(msg,true);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    //根据传入的字典名获取字典
    @GetMapping("item")
    public Rest getDict(String dictType,String dictName){
        String msg = "获取字典";
        try{
            //根据字典类型和字典名获取字典 表域用表域的方法 字典用字典的方法
            if(dictType.equals("aln")){
                //获取字典项
                List<DictFieldTable> dictFieldTableList = dictFieldTableService.list(new QueryWrapper<DictFieldTable>().eq("dict_name",dictName));
                return Rest.success(msg,dictFieldTableList);
//                //获取字典和相关的字典项
//                List<Map<String,Object>> mapList = dictTableService
//                        .list(
//                                new QueryWrapper<DictTable>()
//                                        .eq("dict_type",dictType)
//                                        .eq("dict_name",dictName)
//                                        .eq("is_delete","0"))
//                        .stream()
//                        .map(dictTable -> {
//                            Map<String,Object> map = new HashMap<>();
//                            map.put("dictUuid",dictTable.getDictUuid());
//                            map.put("dictType",dictTable.getDictType());
//                            map.put("dictName",dictTable.getDictName());
//                            map.put("createDate",dictTable.getCreateDate());
//                            map.put("updateDate",dictTable.getUpdateDate());
//                            map.put("isDelete",dictTable.getIsDelete());
//                            map.put("children",dictFieldTableService
//                                    .list(
//                                            new QueryWrapper<DictFieldTable>()
//                                                    .eq("dict_name",dictName))
//                                    .stream()
//                                    .map(dictFieldTable -> {
//                                        Map<String,Object> map1 = new HashMap<>();
//                                        map1.put("dictFieldUuid",dictFieldTable.getDictFieldUuid());
//                                        map1.put("dictName",dictFieldTable.getDictName());
//                                        map1.put("dictFieldLabel",dictFieldTable.getDictFieldLabel());
//                                        map1.put("dictFieldValue",dictFieldTable.getDictFieldValue());
//                                        return map1;
//                                    }).collect(Collectors.toList()));
//                            return map;
//                        }).collect(Collectors.toList());
//                return Rest.success(msg,mapList);
            }else if(dictType.equals("table")){
//                List<Map<String,Object>> mapList = dictTableService.getDict(dictName);
                //根据dictName对应实体类获取对应的字典
                switch (dictName){
                    case "depart":
                        List<DepartTable> departTableList = departTableService.list();
                        List<Map<String,Object>> mapList = departTableList.stream().map(departTable -> {
                            Map<String,Object> map = new HashMap<>();
                            map.put("departUuid",departTable.getDepartUuid());
                            map.put("departName",departTable.getDepartName());
                            return map;
                        }).collect(Collectors.toList());
                        return Rest.success(msg,mapList);
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
