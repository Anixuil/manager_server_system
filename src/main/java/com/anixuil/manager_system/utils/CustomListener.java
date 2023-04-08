package com.anixuil.manager_system.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//自定义监听器用来导入数据
@Slf4j
public class CustomListener<T> extends AnalysisEventListener<List<T>> {

    public Class<T> clazz;

    public CustomListener(Class<T> clazz) {
        this.clazz = clazz;
    }
    private final List<T> dataList = new ArrayList<>();
    @Override
    public void invoke(List<T> data, AnalysisContext analysisContext) {
//        dataList.add(data);
        //拿取当前行所有的字段数据
        int columnIndex = analysisContext.readRowHolder().getRowIndex();
        System.out.println("当前列数："+columnIndex);
        System.out.println("当前数据："+data);
        //遍历data每一个属性值

        try {
            T obj = clazz.newInstance();
            // 获取实体类的所有属性
            Field[] fields = FieldUtils.getAllFields(clazz);
            // 遍历属性并赋值
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
//                // 获取属性名
                String fieldName = field.getName();
//                System.out.println(fieldName);
//                // 获取属性值
                Object fieldValue = data.get(i);
                System.out.println(fieldValue);
//                // 将属性值赋给实体类对象
//                FieldUtils.writeField(obj, fieldName, fieldValue, true);
            }
            log.info("解析到一条数据:{}",data);


        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
//        System.out.println(data.getUserRole());
        //获取当前所有行的数据并遍历打印每一个单元格的值
//        for (Map.Entry<Integer, Cell> entry : cellMap.entrySet()) {
//            System.out.print(entry.getValue().getStringValue() + " ");
//        }
//        System.out.println();


    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成");
    }

    public List<T> getDataList() {
        return dataList;
    }
}