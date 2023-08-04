package com.sidihuo.pivottable.convert;

import com.sidihuo.pivottable.PivotTableInput;
import com.sidihuo.pivottable.model.input.InputDataColumn;
import com.sidihuo.pivottable.model.input.InputDataRow;
import com.sidihuo.pivottable.model.output.OutputDataColumn;
import com.sidihuo.pivottable.model.output.OutputDataRow;
import com.sidihuo.pivottable.model.output.OutputHeaderRow;
import com.sidihuo.pivottable.model.output.PivotColumnHeader;
import com.sidihuo.pivottable.model.temp.GroupInfoTemp;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class PivotHelper {

    public static OutputHeaderRow pivotHeader(PivotTableInput pivotTableInput, GroupInfoTemp groupInfoTemp) {
        log.info("begin pivotHeader");
        OutputHeaderRow headerRow = new OutputHeaderRow();
        Map<String, Set<String>> columnGroupMap = groupInfoTemp.getColumnGroupMap();
        //透视表 列 的表头对象信息
        List<PivotColumnHeader> columnHeaderObjects = new ArrayList<PivotColumnHeader>();
        List<PivotColumnHeader> columnHeaderLeafs = columnHeaderObjects;
        List<Integer> columnIndexes = groupInfoTemp.getColumnIndexes();
        for (int i = 0; i < columnIndexes.size(); i++) {
            Integer columnIndex = columnIndexes.get(i);
            String header = groupInfoTemp.getHeaderIndexNamesMap().get(columnIndex);
            Set<String> columnHeaders = columnGroupMap.get(header);
            columnHeaderLeafs = PivotHelpHandler.loopHeader(columnHeaderLeafs, columnHeaders);
        }

        //透视表 列 的表头对象信息 增加最下面的数据列的表头信息
        List<PivotColumnHeader> dataColumnHeaders = new ArrayList<PivotColumnHeader>();
        groupInfoTemp.setDataColumnHeaders(dataColumnHeaders);
        for (PivotColumnHeader columnHeaderLeaf : columnHeaderLeafs) {
            List<PivotColumnHeader> dataHeaders = new ArrayList<PivotColumnHeader>();
            columnHeaderLeaf.setChildren(dataHeaders);
            List<Integer> dataIndexes = groupInfoTemp.getDataIndexes();
//            int index = 0;
            for (Integer dataIndex : dataIndexes) {
                String header = groupInfoTemp.getHeaderIndexNamesMap().get(dataIndex);
                PivotColumnHeader dataHeader = new PivotColumnHeader();
                dataHeader.setParent(columnHeaderLeaf);
                dataHeader.setName(header);
//                dataHeader.setIndex(index++);
                dataHeaders.add(dataHeader);
                dataColumnHeaders.add(dataHeader);
            }
        }
        headerRow.setColumnHeaders(columnHeaderObjects);
//        headerRow.setColumnHeaderHeight(columnHeaderHeight + 1);
        headerRow.setDataColumnHeaders(dataColumnHeaders);

        //透视表的行的表头的索引对应的表头名字
        List<String> rowHeaders = new ArrayList<String>();
        List<Integer> rowIndexes = groupInfoTemp.getRowIndexes();
        for (Integer rowIndex : rowIndexes) {
            String header = groupInfoTemp.getHeaderIndexNamesMap().get(rowIndex);
            rowHeaders.add(header);
        }
        headerRow.setRowHeaders(rowHeaders);

        //透视表的列的表头的索引对应的表头名字
        List<String> originColumnHeaders = new ArrayList<String>();
        List<Integer> columnIndexesT = groupInfoTemp.getColumnIndexes();
        for (Integer columnIndex : columnIndexesT) {
            String header = groupInfoTemp.getHeaderIndexNamesMap().get(columnIndex);
            originColumnHeaders.add(header);
        }
        headerRow.setOriginColumnHeaders(originColumnHeaders);

        //填充到每行每列的单元格中（合并单元格前）
        PivotHelpHandler.buildHeaderCells(headerRow);
        log.info("end pivotHeader");
        return headerRow;
    }

    public static List<OutputDataRow> pivotData(PivotTableInput pivotTableInput, GroupInfoTemp groupInfoTemp) {
        log.info("begin pivotData");
        List<OutputDataRow> result = new ArrayList<OutputDataRow>();
        List<Integer> rowIndexes = groupInfoTemp.getRowIndexes();
//        List<Integer> columnIndexes = groupInfoTemp.getColumnIndexes();
//        List<Integer> dataIndexes = groupInfoTemp.getDataIndexes();
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
//            int indexDataColumn = 0;
            for (PivotColumnHeader dataColumnHeader : dataColumnHeaders) {
                OutputDataColumn outputDataColumn = new OutputDataColumn();
                columnValues.add(outputDataColumn);
//                outputDataColumn.setIndex(indexDataColumn++);
                List<BigDecimal> pivotValues = new ArrayList<BigDecimal>();
                for (InputDataRow inputDataRow : value) {
                    BigDecimal rowColumnData = PivotHelpHandler.getRowColumnData(inputDataRow, dataColumnHeader, groupInfoTemp);
                    pivotValues.add(rowColumnData);
                }
                BigDecimal pivotValue = PivotHelpHandler.calculatePivotValue(pivotValues);
                outputDataColumn.setValue(pivotValue == null ? null : pivotValue.doubleValue());
                outputDataColumn.setColumnHeaders(PivotHelpHandler.getColumnHeaders(dataColumnHeader));

//                String toString = rowHeaderValues.toString();
//                String aa=dataColumnHeader.getParent().getParent().getName()+"-"+dataColumnHeader.getParent().getName()+"-"+dataColumnHeader.getName();
//                System.out.println(toString+" ### "+aa+"="+outputDataColumn.getValue());
//                aa.hashCode();
            }

        }
        log.info("end pivotData");
        return result;
    }


    public static Map<Integer, List<String>> buildHeaderCellsMap(OutputHeaderRow outputHeaderRow) {
        Map<Integer, List<String>> result = new HashMap<Integer, List<String>>();
        List<PivotColumnHeader> columnHeaders = outputHeaderRow.getColumnHeaders();
        int rowIndex = 0;
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
            PivotHelpHandler.loopBuildHeaderCellsMap(children, rowIndex, result);
        }
        return result;
    }

}
