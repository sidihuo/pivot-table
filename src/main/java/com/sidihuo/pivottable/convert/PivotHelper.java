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
 * @Description
 * @Date 2023/7/26 19:12
 * @Created by yanggangjie
 */
public class PivotHelper {

    public static OutputHeaderRow pivotHeader(PivotTableInput pivotTableInput, GroupInfoTemp groupInfoTemp) {
        OutputHeaderRow headerRow = new OutputHeaderRow();
        Map<String, Set<String>> columnGroupMap = groupInfoTemp.getColumnGroupMap();
        List<PivotColumnHeader> columnHeaderObjects = new ArrayList<PivotColumnHeader>();
        List<PivotColumnHeader> columnHeaderLeafs = columnHeaderObjects;
        List<Integer> columnIndexes = groupInfoTemp.getColumnIndexes();
        for (int i = 0; i < columnIndexes.size(); i++) {
            Integer columnIndex = columnIndexes.get(i);
            String header = groupInfoTemp.getHeaderIndexNamesMap().get(columnIndex);
            Set<String> columnHeaders = columnGroupMap.get(header);
            columnHeaderLeafs = loopHeader(columnHeaderLeafs, columnHeaders);
        }
        List<PivotColumnHeader> dataColumnHeaders = new ArrayList<PivotColumnHeader>();
        groupInfoTemp.setDataColumnHeaders(dataColumnHeaders);
        for (PivotColumnHeader columnHeaderLeaf : columnHeaderLeafs) {
            List<PivotColumnHeader> dataHeaders = new ArrayList<PivotColumnHeader>();
            columnHeaderLeaf.setChildren(dataHeaders);
            List<Integer> dataIndexes = groupInfoTemp.getDataIndexes();
            int index = 0;
            for (Integer dataIndex : dataIndexes) {
                String header = groupInfoTemp.getHeaderIndexNamesMap().get(dataIndex);
                PivotColumnHeader dataHeader = new PivotColumnHeader();
                dataHeader.setParent(columnHeaderLeaf);
                dataHeader.setName(header);
                dataHeader.setIndex(index++);
                dataHeaders.add(dataHeader);
                dataColumnHeaders.add(dataHeader);
            }
        }
        headerRow.setColumnHeaders(columnHeaderObjects);

        List<String> rowHeaders = new ArrayList<String>();
        List<Integer> rowIndexes = groupInfoTemp.getRowIndexes();
        for (Integer rowIndex : rowIndexes) {
            String header = groupInfoTemp.getHeaderIndexNamesMap().get(rowIndex);
            rowHeaders.add(header);
        }
        headerRow.setRowHeaders(rowHeaders);
        return headerRow;
    }

    public static List<OutputDataRow> pivotData(PivotTableInput pivotTableInput, GroupInfoTemp groupInfoTemp) {
        List<OutputDataRow> result = new ArrayList<OutputDataRow>();
        List<Integer> rowIndexes = groupInfoTemp.getRowIndexes();
        List<Integer> columnIndexes = groupInfoTemp.getColumnIndexes();
        List<Integer> dataIndexes = groupInfoTemp.getDataIndexes();
        List<PivotColumnHeader> dataColumnHeaders = groupInfoTemp.getDataColumnHeaders();
        Map<String, List<InputDataRow>> rowGroupMap = groupInfoTemp.getRowGroupMap();
        Iterator<Map.Entry<String, List<InputDataRow>>> iteratorRowGroup = rowGroupMap.entrySet().iterator();
        int index = 0;
        while (iteratorRowGroup.hasNext()) {
            OutputDataRow row = new OutputDataRow();
            row.setIndex(index++);
            List<String> rowHeaderValues = new ArrayList<String>();
            row.setRowHeaderValues(rowHeaderValues);
            result.add(row);

            Map.Entry<String, List<InputDataRow>> next = iteratorRowGroup.next();
            List<InputDataRow> value = next.getValue();
            row.setOriginInputDataRows(value);
            InputDataRow inputDataRowTemp = value.get(0);
            List<InputDataColumn> columnsTemp = inputDataRowTemp.getColumns();
            for (Integer rowIndex : rowIndexes) {
                InputDataColumn inputDataColumn = columnsTemp.get(rowIndex);
                Object valueRowTempObj = inputDataColumn.getValue();
                String valueRowTempStr = valueRowTempObj == null ? "" : valueRowTempObj.toString();
                rowHeaderValues.add(valueRowTempStr);
            }

            List<OutputDataColumn> columnValues = new ArrayList<OutputDataColumn>();
            row.setColumnValues(columnValues);
//            int indexDataColumn=rowIndexes.size();
            int indexDataColumn = 0;
            for (PivotColumnHeader dataColumnHeader : dataColumnHeaders) {
                OutputDataColumn outputDataColumn = new OutputDataColumn();
                columnValues.add(outputDataColumn);
                outputDataColumn.setIndex(indexDataColumn++);
                List<BigDecimal> pivotValues = new ArrayList<BigDecimal>();
                for (InputDataRow inputDataRow : value) {
                    BigDecimal rowColumnData = getRowColumnData(inputDataRow, dataColumnHeader, groupInfoTemp);
                    pivotValues.add(rowColumnData);
                }
                BigDecimal pivotValue = calculatePivotValue(pivotValues);
                outputDataColumn.setValue(pivotValue == null ? null : pivotValue.doubleValue());
                outputDataColumn.setColumnHeaders(getColumnHeaders(dataColumnHeader));
            }

        }
        return result;
    }

    private static BigDecimal getRowColumnData(InputDataRow inputDataRow, PivotColumnHeader dataColumnHeader, GroupInfoTemp groupInfoTemp) {
        //Map<Integer, String> headerIndexNamesMap = groupInfoTemp.getHeaderIndexNamesMap();
        String currentDataHeaderName = dataColumnHeader.getName();
        InputDataColumn currentColumn = null;
        List<InputDataColumn> columns = inputDataRow.getColumns();
        Map<Integer, String> columnIndexValuesMap = new HashMap<Integer, String>();
        for (InputDataColumn column : columns) {
            columnIndexValuesMap.put(column.getIndex(), (String) column.getValue());
            if (StringUtils.equals(currentDataHeaderName, column.getHeader())) {
                currentColumn = column;
            }
        }

        PivotColumnHeader pivotColumnHeaderTemp = dataColumnHeader.getParent();
        List<Integer> columnIndexes = groupInfoTemp.getColumnIndexes();
        boolean flag = true;
        for (int i = columnIndexes.size() - 1; i > 0; i--) {
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
        return value == null ? null : new BigDecimal((String) value);
    }


    private static List<String> getColumnHeaders(PivotColumnHeader dataColumnHeader) {
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

    private static BigDecimal calculatePivotValue(List<BigDecimal> pivotValues) {
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
                columnHeaderChild.setParent(null);
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
                columnHeaderChild.setParent(columnHeader);
                columnHeaderChild.setName(levelColumnHeader);
                columnHeaderChild.setIndex(index++);
                children.add(columnHeaderChild);
            }
            leafs.addAll(children);
        }
        return leafs;
    }

}
