package com.sidihuo.pivottable.convert;

import com.sidihuo.pivottable.PivotTableInput;
import com.sidihuo.pivottable.model.input.InputDataRow;
import com.sidihuo.pivottable.model.output.OutputHeaderRow;
import com.sidihuo.pivottable.model.output.PivotColumnHeader;
import com.sidihuo.pivottable.model.temp.GroupInfoTemp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Date 2023/7/26 19:12
 * @Created by yanggangjie
 */
public class PivotHelper {

    public static OutputHeaderRow pivotHeader(PivotTableInput pivotTableInput,GroupInfoTemp groupInfoTemp){
        OutputHeaderRow headerRow=new OutputHeaderRow();
        Map<String, Set<String>> columnGroupMap = groupInfoTemp.getColumnGroupMap();
        List<PivotColumnHeader> columnHeaderObjects=new ArrayList<PivotColumnHeader>();
        List<PivotColumnHeader> columnHeaderLeafs=columnHeaderObjects;
        List<Integer> columnIndexes = groupInfoTemp.getColumnIndexes();
        for (int i = 0; i < columnIndexes.size(); i++) {
            Integer columnIndex = columnIndexes.get(i);
            String header = groupInfoTemp.getHeaderIndexNamesMap().get(columnIndex);
            Set<String> columnHeaders = columnGroupMap.get(header);
            columnHeaderLeafs = loopHeader(columnHeaderLeafs, columnHeaders);
        }
        for (PivotColumnHeader columnHeaderLeaf : columnHeaderLeafs) {
            List<PivotColumnHeader> dataHeaders=new ArrayList<PivotColumnHeader>();
            columnHeaderLeaf.setChildren(dataHeaders);
            List<Integer> dataIndexes = groupInfoTemp.getDataIndexes();
            int index=0;
            for (Integer dataIndex : dataIndexes) {
                String header = groupInfoTemp.getHeaderIndexNamesMap().get(dataIndex);
                PivotColumnHeader dataHeader=new PivotColumnHeader();
//                dataHeader.setParent(columnHeaderLeaf);
                dataHeader.setName(header);
                dataHeader.setIndex(index++);
                dataHeaders.add(dataHeader);
            }
        }
        headerRow.setColumnHeaders(columnHeaderObjects);

        List<String> rowHeaders=new ArrayList<String>();
        List<Integer> rowIndexes = groupInfoTemp.getRowIndexes();
        for (Integer rowIndex : rowIndexes) {
            String header = groupInfoTemp.getHeaderIndexNamesMap().get(rowIndex);
            rowHeaders.add(header);
        }
        headerRow.setRowHeaders(rowHeaders);
        return headerRow;
    }

    public static void pivotData(PivotTableInput pivotTableInput,GroupInfoTemp groupInfoTemp){
        Map<String, List<InputDataRow>> rowGroupMap = groupInfoTemp.getRowGroupMap();
        Iterator<Map.Entry<String, List<InputDataRow>>> iteratorRowGroup = rowGroupMap.entrySet().iterator();
        while (iteratorRowGroup.hasNext()){
            Map.Entry<String, List<InputDataRow>> next = iteratorRowGroup.next();

        }
    }

    private static List<PivotColumnHeader> loopHeader(List<PivotColumnHeader> columnHeaders, Set<String> levelColumnHeaders) {
        if (columnHeaders == null) {
            columnHeaders = new ArrayList<PivotColumnHeader>();
        }
        if (levelColumnHeaders == null || levelColumnHeaders.size() == 0) {
            return columnHeaders;
        }
        if (columnHeaders.size() == 0) {
            //第一层表头
            int index = 0;
            for (String levelColumnHeader : levelColumnHeaders) {
                PivotColumnHeader columnHeaderChild = new PivotColumnHeader();
//                columnHeaderChild.setParent(null);
                columnHeaderChild.setName(levelColumnHeader);
                columnHeaderChild.setIndex(index++);
                columnHeaders.add(columnHeaderChild);
            }
            return columnHeaders;
        }
        //非第一层表头
        List<PivotColumnHeader> leafs = new ArrayList<PivotColumnHeader>();
        for (PivotColumnHeader columnHeader : columnHeaders) {
            List<PivotColumnHeader> children = new ArrayList<PivotColumnHeader>();
            columnHeader.setChildren(children);
            int index = 0;
            for (String levelColumnHeader : levelColumnHeaders) {
                PivotColumnHeader columnHeaderChild = new PivotColumnHeader();
//                columnHeaderChild.setParent(columnHeader);
                columnHeaderChild.setName(levelColumnHeader);
                columnHeaderChild.setIndex(index++);
                children.add(columnHeaderChild);
            }
            leafs.addAll(children);
        }
        return leafs;
    }

}
