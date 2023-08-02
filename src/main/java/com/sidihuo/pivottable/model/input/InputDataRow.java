package com.sidihuo.pivottable.model.input;

import java.util.List;

/**
 * @Description 透视表的输入数据的行
 * @Date 2023/7/25 16:21
 * @Created by yanggangjie
 */
public class InputDataRow {

//    private int index;

    private List<InputDataColumn> columns;

//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }

    public List<InputDataColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<InputDataColumn> columns) {
        this.columns = columns;
    }

}
