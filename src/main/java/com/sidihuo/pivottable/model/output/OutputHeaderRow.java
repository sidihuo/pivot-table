package com.sidihuo.pivottable.model.output;

import java.util.List;

/**
 * @Description
 * @Date 2023/7/25 18:07
 * @Created by yanggangjie
 */
public class OutputHeaderRow {

    /**
     * 透视表的行的表头的索引对应的表头名字
     */
    private List<String> rowHeaders;

    private List<PivotColumnHeader> columnHeaders;

//    private int columnHeaderHeight;
    private List<PivotColumnHeader> dataColumnHeaders;

    public List<String> getRowHeaders() {
        return rowHeaders;
    }

    public void setRowHeaders(List<String> rowHeaders) {
        this.rowHeaders = rowHeaders;
    }

    public List<PivotColumnHeader> getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(List<PivotColumnHeader> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

//    public int getColumnHeaderHeight() {
//        return columnHeaderHeight;
//    }
//
//    public void setColumnHeaderHeight(int columnHeaderHeight) {
//        this.columnHeaderHeight = columnHeaderHeight;
//    }

    public List<PivotColumnHeader> getDataColumnHeaders() {
        return dataColumnHeaders;
    }

    public void setDataColumnHeaders(List<PivotColumnHeader> dataColumnHeaders) {
        this.dataColumnHeaders = dataColumnHeaders;
    }
}
