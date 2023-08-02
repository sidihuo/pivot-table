package com.sidihuo.pivottable.model.input;

/**
 * @Description 透视表的输入数据的列
 * @Date 2023/7/25 16:21
 * @Created by yanggangjie
 */
public class InputDataColumn {

    /**
     * 客户端调用必输的参数
     */
    private int index;

    /**
     * 原始类型的值
     */
    private Object value;

    /**
     * 客户端调用非必输的参数
     * 程序默认根据index和表头信息重新赋值
     */
    private String header;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
