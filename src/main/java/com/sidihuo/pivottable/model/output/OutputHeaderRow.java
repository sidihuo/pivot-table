package com.sidihuo.pivottable.model.output;

import java.util.List;

/**
 * @Description
 * @Date 2023/7/25 18:07
 * @Created by yanggangjie
 */
public class OutputHeaderRow {

    private List<String> rowHeaders;

    private List<PivotColumnHeader> columnHeaders;

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
}
