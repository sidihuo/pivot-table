package com.sidihuo.pivottable;

import com.sidihuo.pivottable.model.input.InputDataColumnHeader;
import com.sidihuo.pivottable.model.input.InputDataRow;
import com.sidihuo.pivottable.model.input.PivotConfig;

import java.util.List;

/**
 * @Description 透视表的输入
 * @Date 2023/7/25 16:13
 * @Created by yanggangjie
 */
public class PivotTableInput {

    /**
     * 表头信息
     */
    private List<InputDataColumnHeader> headers;


    /**
     * 行数据
     */
    private List<InputDataRow> rows;

    /**
     * 透视表的配置  行/列/值/排序
     */
    private PivotConfig pivotConfig;

    public List<InputDataColumnHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<InputDataColumnHeader> headers) {
        this.headers = headers;
    }

    public List<InputDataRow> getRows() {
        return rows;
    }

    public void setRows(List<InputDataRow> rows) {
        this.rows = rows;
    }

    public PivotConfig getPivotConfig() {
        return pivotConfig;
    }

    public void setPivotConfig(PivotConfig pivotConfig) {
        this.pivotConfig = pivotConfig;
    }
}
