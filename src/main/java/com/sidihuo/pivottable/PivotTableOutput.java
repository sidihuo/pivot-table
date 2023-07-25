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

    private List<OutputHeaderRow> headerRows;

    private List<OutputDataRow> dataRows;

    public List<OutputHeaderRow> getHeaderRows() {
        return headerRows;
    }

    public void setHeaderRows(List<OutputHeaderRow> headerRows) {
        this.headerRows = headerRows;
    }

    public List<OutputDataRow> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<OutputDataRow> dataRows) {
        this.dataRows = dataRows;
    }
}
