package com.sidihuo.pivottable.convert;

import com.sidihuo.pivottable.PivotTableInput;
import com.sidihuo.pivottable.model.input.InputDataColumn;
import com.sidihuo.pivottable.model.input.InputDataRow;
import com.sidihuo.pivottable.model.output.OutputDataColumn;
import com.sidihuo.pivottable.model.output.OutputDataRow;
import com.sidihuo.pivottable.model.output.OutputHeaderRow;
import com.sidihuo.pivottable.model.output.PivotColumnHeader;
import com.sidihuo.pivottable.model.temp.GroupInfoTemp;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description 透视表转换
 * @Date 2023/7/26 19:12
 * @Created by yanggangjie
 */
public class PivotHelpHandler {


    public static BigDecimal getRowColumnData(InputDataRow inputDataRow, PivotColumnHeader dataColumnHeader, GroupInfoTemp groupInfoTemp) {
        //Map<Integer, String> headerIndexNamesMap = groupInfoTemp.getHeaderIndexNamesMap();
        String currentDataHeaderName = dataColumnHeader.getName();
        InputDataColumn currentColumn = null;
        List<InputDataColumn> columns = inputDataRow.getColumns();
        Map<Integer, String> columnIndexValuesMap = new HashMap<Integer, String>();
        for (InputDataColumn column : columns) {
            columnIndexValuesMap.put(column.getIndex(), column == null ? null : column.getValue().toString());
            if (StringUtils.equals(currentDataHeaderName, column.getHeader())) {
                currentColumn = column;
                break;
            }
        }

        PivotColumnHeader pivotColumnHeaderTemp = dataColumnHeader.getParent();
        List<Integer> columnIndexes = groupInfoTemp.getColumnIndexes();
        boolean flag = true;
        for (int i = columnIndexes.size() - 1; i >= 0; i--) {
            Integer integer = columnIndexes.get(i);
            String headerTemp = columnIndexValuesMap.get(integer);
            if (pivotColumnHeaderTemp == null || !StringUtils.equals(headerTemp, pivotColumnHeaderTemp.getName())) {
                flag = false;
                break;
            }
            pivotColumnHeaderTemp = pivotColumnHeaderTemp.getParent();
        }
        if (!flag) {
            return null;
        }
        if (currentColumn == null) {
            return null;
        }
        Object value = currentColumn.getValue();
        return value == null ? null : new BigDecimal(value.toString());
    }


    public static List<String> getColumnHeaders(PivotColumnHeader dataColumnHeader) {
        List<String> headers = new ArrayList<String>();
        loopParentHeader(dataColumnHeader, headers);
        return headers;
    }

    private static PivotColumnHeader loopParentHeader(PivotColumnHeader dataColumnHeader, List<String> headers) {
        if (dataColumnHeader == null) {
            return null;
        }
        String name = dataColumnHeader.getName();
        headers.add(name);
        PivotColumnHeader parent = dataColumnHeader.getParent();
        return loopParentHeader(parent, headers);
    }

    public static BigDecimal calculatePivotValue(List<BigDecimal> pivotValues) {
        BigDecimal result = null;
        for (BigDecimal pivotValue : pivotValues) {
            if (pivotValue == null) {
                continue;
            }
            if (result == null) {
                result = BigDecimal.ZERO;
            }
            result = result.add(pivotValue);
        }
        return result;
    }

    /**
     * 透视表 列 的表头对象信息
     * @param columnHeaders  透视表 列 的表头对象信息
     * @param levelColumnHeaders  透视表 列 的表头名SET
     * @return
     */
    public static List<PivotColumnHeader> loopHeader(List<PivotColumnHeader> columnHeaders, Set<String> levelColumnHeaders) {
        if (columnHeaders == null) {
            columnHeaders = new ArrayList<PivotColumnHeader>();
        }
        if (levelColumnHeaders == null || levelColumnHeaders.size() == 0) {
            return columnHeaders;
        }
        if (columnHeaders.size() == 0) {
            //第一层表头
//            int index = 0;
            for (String levelColumnHeader : levelColumnHeaders) {
                PivotColumnHeader columnHeaderChild = new PivotColumnHeader();
                columnHeaderChild.setParent(null);
                columnHeaderChild.setName(levelColumnHeader);
//                columnHeaderChild.setIndex(index++);
                columnHeaders.add(columnHeaderChild);
            }
            return columnHeaders;
        }
        //非第一层表头
        List<PivotColumnHeader> leafs = new ArrayList<PivotColumnHeader>();
        for (PivotColumnHeader columnHeader : columnHeaders) {
            List<PivotColumnHeader> children = new ArrayList<PivotColumnHeader>();
            columnHeader.setChildren(children);
//            int index = 0;
            for (String levelColumnHeader : levelColumnHeaders) {
                PivotColumnHeader columnHeaderChild = new PivotColumnHeader();
                columnHeaderChild.setParent(columnHeader);
                columnHeaderChild.setName(levelColumnHeader);
//                columnHeaderChild.setIndex(index++);
                children.add(columnHeaderChild);
            }
            leafs.addAll(children);
        }
        return leafs;
    }

    public static void buildHeaderCells(OutputHeaderRow outputHeaderRow) {
        List<PivotColumnHeader> dataColumnHeaders = outputHeaderRow.getDataColumnHeaders();
        for (PivotColumnHeader dataColumnHeader : dataColumnHeaders) {
            List<String> cells = new ArrayList<String>();
            dataColumnHeader.setCells(cells);
            cells.add(dataColumnHeader.getName());
            loopHeaderCells(cells, dataColumnHeader);
//            PivotColumnHeader parent = dataColumnHeader.getParent().getParent();
//            System.out.println(parent);
        }
    }

    private static void loopHeaderCells(List<String> cells, PivotColumnHeader dataColumnHeader) {
        if (dataColumnHeader == null || dataColumnHeader.getParent() == null) {
            return;
        }
        PivotColumnHeader parent = dataColumnHeader.getParent();
        List<String> parentCells = parent.getCells();
        if (parentCells == null) {
            parentCells = new ArrayList<String>();
            parent.setCells(parentCells);
        }
        String name = parent.getName();
//        int size = cells.size();
//        for (int index = 0; index < size; index++) {
        parentCells.add(name);
//        }
        loopHeaderCells(parentCells, parent);
    }

    public static void loopBuildHeaderCellsMap(List<PivotColumnHeader> columnHeaders, int rowIndex, Map<Integer, List<String>> result) {
        if (columnHeaders == null || columnHeaders.size() == 0) {
            return;
        }
        List<String> cells = result.get(rowIndex);
        if (cells == null) {
            cells = new ArrayList<String>();
            result.put(rowIndex, cells);
        }
        rowIndex++;
        for (PivotColumnHeader columnHeader : columnHeaders) {
            List<String> cellsTemp = columnHeader.getCells();
            cells.addAll(cellsTemp);
            List<PivotColumnHeader> children = columnHeader.getChildren();
            loopBuildHeaderCellsMap(children, rowIndex, result);
        }
    }

}
