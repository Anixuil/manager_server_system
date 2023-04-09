package com.anixuil.manager_system.Pojo;

import com.anixuil.manager_system.entity.DictFieldTable;
import com.anixuil.manager_system.entity.DictTable;

import java.util.List;

public class DictData {
    private DictTable dictTable;
    private List<DictFieldTable> dictFieldTableList;

    public DictTable getDictTable() {
        return dictTable;
    }

    public void setDictTable(DictTable dictTable) {
        this.dictTable = dictTable;
    }

    public List<DictFieldTable> getDictFieldTableList() {
        return dictFieldTableList;
    }

    public void setDictFieldTableList(List<DictFieldTable> dictFieldTableList) {
        this.dictFieldTableList = dictFieldTableList;
    }
}
