package com.sidihuo.pivottable.model.temp;

import com.sidihuo.pivottable.model.input.InputDataRow;
import com.sidihuo.pivottable.model.output.PivotColumnHeader;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Date 2023/7/26 19:10
 * @Created by yanggangjie
 */
public class GroupInfoTemp {

    /**
     * 分组的结果，key=透视表配置列对应的列表头名 ，value=透视表列的值SET（自顶向下的顺序）
     */
    private Map<String, Set<String>> columnGroupMap;
    /**
     * 分组的结果，key=透视表配置行对应的列表头的数据值拼接 ，value=原始输入行信息列表
     */
    private Map<String, List<InputDataRow>> rowGroupMap;

    /**
     * 透视表的行的表头的索引
     */
    private List<Integer> rowIndexes;
    /**
     * 透视表的列的表头的索引
     */
    private List<Integer> columnIndexes;
    /**
     * 透视表的数据的表头的索引
     */
    private List<Integer> dataIndexes;
    /**
     * 清单列表的表头索引和名字的映射map
     */
    private Map<Integer, String> headerIndexNamesMap;

    private List<PivotColumnHeader> dataColumnHeaders;


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

    public List<PivotColumnHeader> getDataColumnHeaders() {
        return dataColumnHeaders;
    }

    public void setDataColumnHeaders(List<PivotColumnHeader> dataColumnHeaders) {
        this.dataColumnHeaders = dataColumnHeaders;
    }
}
