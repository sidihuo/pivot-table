package com.sidihuo.pivottable.model.input;

/**
 * @Description 透视表的输入数据的列
 * @Date 2023/7/25 16:21
 * @Created by yanggangjie
 */
public class InputDataColumn {

    private int index;

    /**
     * 转成字符串的值
     */
    private String value;

    /**
     * 原始类型的值
     */
    private Object objectValue;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(Object objectValue) {
        this.objectValue = objectValue;
    }
}
