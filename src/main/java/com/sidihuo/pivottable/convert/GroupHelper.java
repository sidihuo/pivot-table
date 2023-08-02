package com.sidihuo.pivottable.convert;

import com.sidihuo.pivottable.PivotTableInput;
import com.sidihuo.pivottable.model.input.InputDataColumn;
import com.sidihuo.pivottable.model.input.InputDataColumnHeader;
import com.sidihuo.pivottable.model.input.InputDataRow;
import com.sidihuo.pivottable.model.input.PivotColumnConfig;
import com.sidihuo.pivottable.model.input.PivotConfig;
import com.sidihuo.pivottable.model.input.PivotDataConfig;
import com.sidihuo.pivottable.model.input.PivotRowConfig;
import com.sidihuo.pivottable.model.temp.GroupInfoTemp;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description 分组
 * @Date 2023/7/25 19:03
 * @Created by yanggangjie
 */
@Slf4j
public class GroupHelper {


    /**
     * 对原始数据清单内列表进行按照行标题分组
     *
     * @param pivotTableInput
     * @return
     */
    public static GroupInfoTemp group(PivotTableInput pivotTableInput) {
        log.info("begin group");

        //分组的结果，key=透视表配置列对应的列表头名 ，value=透视表列的值SET（自顶向下的顺序）
        Map<String, Set<String>> columnGroupMap = new HashMap<String, Set<String>>();
        //分组的结果，key=透视表配置行对应的列表头的数据值拼接 ，value=原始输入行信息列表
        Map<String, List<InputDataRow>> rowGroupMap = new HashMap<String, List<InputDataRow>>();

        //调用方输入的清单列表的表头信息对象列表
        List<InputDataColumnHeader> headers = pivotTableInput.getHeaders();
        //清单列表的表头名字列表
        List<String> headerNames = new ArrayList<String>();
        //清单列表的表头索引和名字的映射map
        Map<Integer, String> headerIndexNamesMap = new HashMap<Integer, String>();
        for (int headerIndex = 0; headerIndex < headers.size(); headerIndex++) {
            InputDataColumnHeader header = headers.get(headerIndex);
            headerNames.add(header.getName());
            headerIndexNamesMap.put(headerIndex, header.getName());
        }

        PivotConfig pivotConfig = pivotTableInput.getPivotConfig();
        //调用方输入的透视表行信息配置
        List<PivotRowConfig> rowConfigs = pivotConfig.getRowConfigs();
        //透视表的行的表头的索引
        List<Integer> rowIndexes = new ArrayList<Integer>();
        for (PivotRowConfig rowConfig : rowConfigs) {
            String headerName = rowConfig.getHeaderName();
            int indexOf = headerNames.indexOf(headerName);
            rowIndexes.add(indexOf);
        }

        //调用方输入的透视表列信息配置
        List<PivotColumnConfig> columnConfigs = pivotConfig.getColumnConfigs();
        //透视表的列的表头的索引
        List<Integer> columnIndexes = new ArrayList<Integer>();
        for (PivotColumnConfig columnConfig : columnConfigs) {
            String headerName = columnConfig.getHeaderName();
            int indexOf = headerNames.indexOf(headerName);
            columnIndexes.add(indexOf);
            columnGroupMap.put(headerName, new HashSet<String>());
        }

        //调用方输入的透视表数据信息配置
        List<PivotDataConfig> dataConfigs = pivotConfig.getDataConfigs();
        //透视表的数据的表头的索引
        List<Integer> dataIndexes = new ArrayList<Integer>();
        for (PivotDataConfig dataConfig : dataConfigs) {
            String headerName = dataConfig.getHeaderName();
            int indexOf = headerNames.indexOf(headerName);
            dataIndexes.add(indexOf);
        }

        //调用方输入的清单列表的数据行信息对象列表
        List<InputDataRow> rows = pivotTableInput.getRows();
        //遍历每一行 行数据
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            InputDataRow inputDataRow = rows.get(rowIndex);
            List<InputDataColumn> columns = inputDataRow.getColumns();

            //根据透视表配置的行 分组 清单列表数据
            groupDataByRowColumnValue(rowGroupMap, rowIndexes, inputDataRow);
            //根据透视表配置的列 分组 或许 所有的列表头值
            groupDataByColumnColumnValue(columnGroupMap, headerIndexNamesMap, columnIndexes, columns);

            //重新给每列根据列索引赋值对应的表头名字
            for (InputDataColumn column : columns) {
                int index = column.getIndex();
                String header = headerIndexNamesMap.get(index);
                column.setHeader(header);
            }

        }

        GroupInfoTemp groupInfoTemp = new GroupInfoTemp();
        //分组的结果，key=透视表配置列对应的列表头名 ，value=透视表列的值SET（自顶向下的顺序）
        groupInfoTemp.setColumnGroupMap(columnGroupMap);
        //分组的结果，key=透视表配置行对应的列表头的数据值拼接 ，value=原始输入行信息列表
        groupInfoTemp.setRowGroupMap(rowGroupMap);

        //清单列表的表头索引和名字的映射map
        groupInfoTemp.setHeaderIndexNamesMap(headerIndexNamesMap);
        //透视表的行的表头的索引
        groupInfoTemp.setRowIndexes(rowIndexes);
        //透视表的列的表头的索引
        groupInfoTemp.setColumnIndexes(columnIndexes);
        //透视表的数据的表头的索引
        groupInfoTemp.setDataIndexes(dataIndexes);
        log.info("end group");
        return groupInfoTemp;
    }

    /**
     * 根据透视表配置的列 分组 或许 所有的列表头值
     *
     * @param columnGroupMap      分组的结果，key=透视表配置列对应的列表头名 ，value=透视表列的值SET（自顶向下的顺序）
     * @param headerIndexNamesMap 清单列表的表头索引和名字的映射map
     * @param columnIndexes       透视表配置列的表头索引列表
     * @param columns             当前行的列对象列表
     */
    private static void groupDataByColumnColumnValue(Map<String, Set<String>> columnGroupMap, Map<Integer, String> headerIndexNamesMap, List<Integer> columnIndexes, List<InputDataColumn> columns) {
        for (Integer columnIndex : columnIndexes) {
            InputDataColumn inputDataColumn = columns.get(columnIndex);
            Object value = inputDataColumn.getValue();
            String valueStr = value == null ? "" : value.toString();
            String headerName = headerIndexNamesMap.get(columnIndex);
            Set<String> groupColumns = columnGroupMap.get(headerName);
            groupColumns.add(valueStr);
        }
    }

    /**
     * 根据透视表配置的行 分组 清单列表数据
     *
     * @param rowGroupMap  分组的结果，key=透视表配置行对应的列表头的数据值拼接 ，value=原始输入行信息列表
     * @param rowIndexes   透视表配置行的表头索引列表
     * @param inputDataRow 原始行数据
     */
    private static void groupDataByRowColumnValue(Map<String, List<InputDataRow>> rowGroupMap, List<Integer> rowIndexes, InputDataRow inputDataRow) {
        List<InputDataColumn> columns = inputDataRow.getColumns();
        StringBuilder groupRowKeySB = new StringBuilder();
        //遍历透视表配置的行表头
        for (Integer rowIndexTemp : rowIndexes) {
            InputDataColumn inputDataColumn = columns.get(rowIndexTemp);
            Object value = inputDataColumn.getValue();
            String valueStr = value == null ? "" : value.toString();
            groupRowKeySB.append(valueStr);
        }
        String groupRowKey = groupRowKeySB.toString();
        List<InputDataRow> inputDataRows = rowGroupMap.get(groupRowKey);
        if (inputDataRows == null) {
            inputDataRows = new ArrayList<InputDataRow>();
            rowGroupMap.put(groupRowKey, inputDataRows);
        }
        inputDataRows.add(inputDataRow);
    }

}
