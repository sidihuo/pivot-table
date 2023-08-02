package com.sidihuo.pivottable.model.output;

import java.util.List;

/**
 * @Description
 * @Date 2023/7/25 18:07
 * @Created by yanggangjie
 */
public class OutputDataColumn {

//    private int index;

    private Double value;

    private List<String> columnHeaders;

//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public List<String> getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(List<String> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }
}
