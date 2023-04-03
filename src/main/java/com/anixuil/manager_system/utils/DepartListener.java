package com.anixuil.manager_system.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.anixuil.manager_system.entity.DepartTable;
import com.anixuil.manager_system.mapper.DepartTableMapper;
import com.anixuil.manager_system.service.DepartTableService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DepartListener extends AnalysisEventListener<DepartTable> {
    private DepartTableService departTableService;
    private List<DepartTable> list = new ArrayList<>();

    public DepartListener(DepartTableService departTableService) {
        this.departTableService = departTableService;
    }

    @Override
    public void invoke(DepartTable departTable, AnalysisContext analysisContext) {
        departTable.setDepartUuid(Uuid.getUuid());
        departTable.setCreateDate(Datetime.getTimestamp());
        departTable.setUpdateDate(departTable.getCreateDate());
        list.add(departTable);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    private void saveData() {
        System.out.println("开始保存数据");
        System.out.println(list.size());
        if(list != null || list.size() != 0){
            try {
                departTableService.saveBatch(list);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        list.clear();
    }
}
