package com.sidihuo.pivottable.convert;

import com.sidihuo.pivottable.PivotTableInput;
import com.sidihuo.pivottable.model.input.InputDataColumn;
import com.sidihuo.pivottable.model.input.InputDataColumnHeader;
import com.sidihuo.pivottable.model.input.InputDataRow;
import com.sidihuo.pivottable.model.input.PivotColumnConfig;
import com.sidihuo.pivottable.model.input.PivotConfig;
import com.sidihuo.pivottable.model.input.PivotDataConfig;
import com.sidihuo.pivottable.model.input.PivotRowConfig;
import com.sidihuo.pivottable.model.output.OutputHeaderRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Date 2023/7/25 19:03
 * @Created by yanggangjie
 */
public class GroupHelper {


    public static OutputHeaderRow group(PivotTableInput pivotTableInput) {

        Map<String, List<String>> columnGroupMap = new HashMap<String, List<String>>();
        Map<String, List<InputDataRow>> rowGroupMap = new HashMap<String, List<InputDataRow>>();

        List<InputDataColumnHeader> headers = pivotTableInput.getHeaders();
        List<String> headerNames = new ArrayList<String>();
        Map<Integer, String> headerIndexNamesMap = new HashMap<Integer, String>();
        for (int headerIndex = 0; headerIndex < headers.size(); headerIndex++) {
            InputDataColumnHeader header = headers.get(headerIndex);
            headerNames.add(header.getName());
            headerIndexNamesMap.put(headerIndex, header.getName());
        }

        PivotConfig pivotConfig = pivotTableInput.getPivotConfig();
        List<PivotRowConfig> rowConfigs = pivotConfig.getRowConfigs();
        List<Integer> rowIndexes = new ArrayList<Integer>();
        for (PivotRowConfig rowConfig : rowConfigs) {
            String headerName = rowConfig.getHeaderName();
            int indexOf = headerNames.indexOf(headerName);
            rowIndexes.add(indexOf);
        }

        List<PivotColumnConfig> columnConfigs = pivotConfig.getColumnConfigs();
        List<Integer> columnIndexes = new ArrayList<Integer>();
        for (PivotColumnConfig columnConfig : columnConfigs) {
            String headerName = columnConfig.getHeaderName();
            int indexOf = headerNames.indexOf(headerName);
            columnIndexes.add(indexOf);
            columnGroupMap.put(headerName, new ArrayList<String>());
        }

        List<PivotDataConfig> dataConfigs = pivotConfig.getDataConfigs();
        List<Integer> dataIndexes = new ArrayList<Integer>();
        for (PivotDataConfig dataConfig : dataConfigs) {
            String headerName = dataConfig.getHeaderName();
            int indexOf = headerNames.indexOf(headerName);
            dataIndexes.add(indexOf);
        }


        List<InputDataRow> rows = pivotTableInput.getRows();
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            InputDataRow inputDataRow = rows.get(rowIndex);
            List<InputDataColumn> columns = inputDataRow.getColumns();
            StringBuilder groupRowKeySB = new StringBuilder();
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

            for (Integer columnIndex : columnIndexes) {
                InputDataColumn inputDataColumn = columns.get(columnIndex);
                Object value = inputDataColumn.getValue();
                String valueStr = value == null ? "" : value.toString();
                String headerName = headerIndexNamesMap.get(columnIndex);
                List<String> groupColumns = columnGroupMap.get(headerName);
                groupColumns.add(valueStr);
            }
        }
        //TODO
        OutputHeaderRow outputHeaderRow = new OutputHeaderRow();
        return outputHeaderRow;
    }

}
