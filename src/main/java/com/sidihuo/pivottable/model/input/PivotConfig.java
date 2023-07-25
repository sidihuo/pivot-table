package com.sidihuo.pivottable.model.input;

import java.util.List;

/**
 * @Description 透视表的配置  行/列/值/排序
 * @Date 2023/7/25 16:50
 * @Created by yanggangjie
 */
public class PivotConfig {

    private List<PivotRowConfig> rowConfigs;
    private List<PivotColumnConfig> columnConfigs;
    private List<PivotDataConfig> dataConfigs;

    public List<PivotRowConfig> getRowConfigs() {
        return rowConfigs;
    }

    public void setRowConfigs(List<PivotRowConfig> rowConfigs) {
        this.rowConfigs = rowConfigs;
    }

    public List<PivotColumnConfig> getColumnConfigs() {
        return columnConfigs;
    }

    public void setColumnConfigs(List<PivotColumnConfig> columnConfigs) {
        this.columnConfigs = columnConfigs;
    }

    public List<PivotDataConfig> getDataConfigs() {
        return dataConfigs;
    }

    public void setDataConfigs(List<PivotDataConfig> dataConfigs) {
        this.dataConfigs = dataConfigs;
    }
}
