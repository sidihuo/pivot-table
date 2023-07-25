package com.sidihuo.pivottable.model.input;

/**
 * @Description 透视表的输入数据的表头
 * @Date 2023/7/25 16:17
 * @Created by yanggangjie
 */
public class InputDataColumnHeader {

    /**
     * 表头序列
     */
    private int index;
    /**
     * 表头名
     */
    private String name;

    /**
     * 仅此三种 STRING/NUMBER/DATE
     */
    private String dataType;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
