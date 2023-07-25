package com.sidihuo.pivottable.model.output;

import com.sidihuo.pivottable.model.input.InputDataRow;

import java.util.List;

/**
 * @Description
 * @Date 2023/7/25 18:07
 * @Created by yanggangjie
 */
public class OutputDataRow {

    private int index;

    private List<String> rowHeaderValues;

    private List<OutputDataColumn> columnValues;

    private List<InputDataRow> originInputDataRows;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getRowHeaderValues() {
        return rowHeaderValues;
    }

    public void setRowHeaderValues(List<String> rowHeaderValues) {
        this.rowHeaderValues = rowHeaderValues;
    }

    public List<OutputDataColumn> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(List<OutputDataColumn> columnValues) {
        this.columnValues = columnValues;
    }

    public List<InputDataRow> getOriginInputDataRows() {
        return originInputDataRows;
    }

    public void setOriginInputDataRows(List<InputDataRow> originInputDataRows) {
        this.originInputDataRows = originInputDataRows;
    }
}
