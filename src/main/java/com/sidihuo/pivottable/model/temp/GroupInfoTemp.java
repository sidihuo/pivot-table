package com.sidihuo.pivottable.model.temp;

import com.sidihuo.pivottable.model.input.InputDataRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Date 2023/7/26 19:10
 * @Created by yanggangjie
 */
public class GroupInfoTemp {

    private Map<String, Set<String>> columnGroupMap;
    private Map<String, List<InputDataRow>> rowGroupMap;

    private List<Integer> rowIndexes;
    private List<Integer> columnIndexes ;
    private List<Integer> dataIndexes;
    private Map<Integer, String> headerIndexNamesMap;


    public Map<String, Set<String>> getColumnGroupMap() {
        return columnGroupMap;
    }

    public void setColumnGroupMap(Map<String, Set<String>> columnGroupMap) {
        this.columnGroupMap = columnGroupMap;
    }

    public Map<String, List<InputDataRow>> getRowGroupMap() {
        return rowGroupMap;
    }

    public void setRowGroupMap(Map<String, List<InputDataRow>> rowGroupMap) {
        this.rowGroupMap = rowGroupMap;
    }

    public List<Integer> getRowIndexes() {
        return rowIndexes;
    }

    public void setRowIndexes(List<Integer> rowIndexes) {
        this.rowIndexes = rowIndexes;
    }

    public List<Integer> getColumnIndexes() {
        return columnIndexes;
    }

    public void setColumnIndexes(List<Integer> columnIndexes) {
        this.columnIndexes = columnIndexes;
    }

    public List<Integer> getDataIndexes() {
        return dataIndexes;
    }

    public void setDataIndexes(List<Integer> dataIndexes) {
        this.dataIndexes = dataIndexes;
    }

    public Map<Integer, String> getHeaderIndexNamesMap() {
        return headerIndexNamesMap;
    }

    public void setHeaderIndexNamesMap(Map<Integer, String> headerIndexNamesMap) {
        this.headerIndexNamesMap = headerIndexNamesMap;
    }
}
