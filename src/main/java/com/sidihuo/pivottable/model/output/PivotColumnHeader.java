package com.sidihuo.pivottable.model.output;

import java.util.List;

/**
 * @Description
 * @Date 2023/7/25 18:25
 * @Created by yanggangjie
 */
public class PivotColumnHeader {

    private int index;
    private String name;
    private List<PivotColumnHeader> children;
    private PivotColumnHeader parent;

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

    public List<PivotColumnHeader> getChildren() {
        return children;
    }

    public void setChildren(List<PivotColumnHeader> children) {
        this.children = children;
    }

    public PivotColumnHeader getParent() {
        return parent;
    }

    public void setParent(PivotColumnHeader parent) {
        this.parent = parent;
    }
}
