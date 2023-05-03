package com.anixuil.manager_system.service.impl;

import com.anixuil.manager_system.entity.IndexWheelTable;
import com.anixuil.manager_system.entity.Rest;
import com.anixuil.manager_system.mapper.IndexWheelTableMapper;
import com.anixuil.manager_system.service.IndexWheelTableService;
import com.anixuil.manager_system.utils.Datetime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class IndexWheelTableServiceImpl extends ServiceImpl<IndexWheelTableMapper, IndexWheelTable> implements IndexWheelTableService {

    @Override
    public Rest addIndexWheel(IndexWheelTable indexWheelTable) {
        String msg = "新增轮播";
        try {
            boolean result = save(indexWheelTable);
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,true);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.error(msg,false);
        }
    }

    @Override
    public Rest updateIndexWheel(IndexWheelTable indexWheelTable) {
        String msg = "修改轮播";
        try {
            boolean result = updateById(indexWheelTable);
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,true);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.error(msg,false);
        }
    }

    @Override
    public Rest deleteIndexWheel(IndexWheelTable indexWheelTable) {
        String msg = "删除轮播";
        try {
            boolean result = removeById(indexWheelTable);
            if(result){
                return Rest.success(msg,true);
            }
            return Rest.fail(msg,true);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.error(msg,false);
        }
    }

    @Override
    public Rest getIndexWheelList(Integer pageNum, Integer pageSize) {
        String msg = "获取轮播列表";
        try {
            IPage<IndexWheelTable> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<IndexWheelTable> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(
                    IndexWheelTable::getIndexWheelUuid,
                    IndexWheelTable::getIndexWheelTitle,
                    IndexWheelTable::getIndexWheelDesc,
                    IndexWheelTable::getIsDelete,
                    IndexWheelTable::getIndexWheelImage,
                    IndexWheelTable::getIndexWheelHref,
                    IndexWheelTable::getCreateDate,
                    IndexWheelTable::getUpdateDate
            );
            IPage<IndexWheelTable> indexWheelTableIPage = page(page, queryWrapper);
            List<IndexWheelTable> indexWheelTableList = indexWheelTableIPage.getRecords();
            List<Map<String,Object>> mapList = indexWheelTableList.stream().map(indexWheelTable -> {
                Map<String,Object> map = new HashMap<>();
                map.put("indexWheelUuid",indexWheelTable.getIndexWheelUuid());
                map.put("indexWheelTitle",indexWheelTable.getIndexWheelTitle());
                map.put("indexWheelDesc",indexWheelTable.getIndexWheelDesc());
                map.put("isDelete",indexWheelTable.getIsDelete());
                map.put("indexWheelImage",indexWheelTable.getIndexWheelImage());
                map.put("indexWheelHref",indexWheelTable.getIndexWheelHref());
                map.put("createDate", Datetime.format(indexWheelTable.getCreateDate()));
                map.put("updateDate",Datetime.format(indexWheelTable.getUpdateDate()));
                return map;
            }).collect(Collectors.toList());
            Map<String,Object> map = new HashMap<>();
            map.put("total",indexWheelTableIPage.getTotal());
            map.put("records",mapList);
            map.put("pageNum",indexWheelTableIPage.getCurrent());
            map.put("pageSize",indexWheelTableIPage.getSize());
            map.put("pages",indexWheelTableIPage.getPages());
            return Rest.success(msg,map);
        } catch (Exception e) {
            e.printStackTrace();
            return Rest.error(msg,false);
        }
    }
}
