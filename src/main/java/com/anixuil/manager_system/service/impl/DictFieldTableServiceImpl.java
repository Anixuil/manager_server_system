package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.DictFieldTable;
import com.anixuil.manager_system.entity.DictTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.mapper.DictFieldTableMapper;
import com.anixuil.manager_system.mapper.DictTableMapper;
import com.anixuil.manager_system.pojo.DictData;
import com.anixuil.manager_system.service.DictFieldTableService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anixuil
 * @since 2023-04-09
 */
@Service
public class DictFieldTableServiceImpl extends ServiceImpl<DictFieldTableMapper, DictFieldTable> implements DictFieldTableService {

    @Resource
    DictTableMapper dictTableMapper;
    @Resource
    DictFieldTableMapper dictFieldTableMapper;

    @Override
    public Rest getAllDictField(Integer pageNum, Integer pageSize) {
        return null;
    }

    //新建字典字段
    @Override
    public Rest addDictField(DictData dictData) {
        String msg = "新增字典";
        try{
            if(dictData.getDictTable().getDictType().equals("aln")){
                //判断字典名是否重复
                if(dictTableMapper.selectOne(new LambdaQueryWrapper<DictTable>().eq(DictTable::getDictName,dictData.getDictTable().getDictName())) != null){
                    return Rest.fail(msg,"字典名重复");
                }
            }
            //新增字典
            dictTableMapper.insert(dictData.getDictTable());
            //新增字典项
            dictData.getDictFieldTableList().forEach(dictFieldTable -> {
                dictFieldTable.setDictName(dictData.getDictTable().getDictName());
                dictFieldTableMapper.insert(dictFieldTable);
            });
            return Rest.success(msg,true);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }

    @Override
    public Rest updateDictField(DictFieldTable dictFieldTable) {
        return null;
    }

    @Override
    public Rest deleteDictField(DictFieldTable dictFieldTable) {
        return null;
    }

    //新增字典项
    @Override
    public Rest addDictFieldItem(DictFieldTable dictFieldTable) {
        String msg = "新增字典项";
        try{
            //新增字典项
            System.out.println(dictFieldTable.getDictName());
            boolean result = save(dictFieldTable);
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,false);
        }catch (Exception e){
            return Rest.error(msg,e);
        }
    }
}
