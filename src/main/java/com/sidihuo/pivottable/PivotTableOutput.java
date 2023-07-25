package com.sidihuo.pivottable;

import com.sidihuo.pivottable.model.output.OutputDataRow;
import com.sidihuo.pivottable.model.output.OutputHeaderRow;

import java.util.List;

/**
 * @Description 透视表的输出
 * @Date 2023/7/25 16:13
 * @Created by yanggangjie
 */
public class PivotTableOutput {

    private OutputHeaderRow headerRow;

    private List<OutputDataRow> dataRows;

    public OutputHeaderRow getHeaderRow() {
        return headerRow;
    }

    public void setHeaderRow(OutputHeaderRow headerRow) {
        this.headerRow = headerRow;
    }

    public List<OutputDataRow> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<OutputDataRow> dataRows) {
        this.dataRows = dataRows;
    }
}
