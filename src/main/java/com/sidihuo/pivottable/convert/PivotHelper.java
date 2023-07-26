package com.sidihuo.pivottable.convert;

import com.sidihuo.pivottable.PivotTableInput;
import com.sidihuo.pivottable.model.input.InputDataRow;
import com.sidihuo.pivottable.model.output.OutputHeaderRow;
import com.sidihuo.pivottable.model.temp.GroupInfoTemp;

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
        Iterator<Map.Entry<String, Set<String>>> iteratorColumnGroup = columnGroupMap.entrySet().iterator();
        while (iteratorColumnGroup.hasNext()){
            Map.Entry<String, Set<String>> next = iteratorColumnGroup.next();

        }
        List<Integer> columnIndexes = groupInfoTemp.getColumnIndexes();
        for (int i = 0; i < columnIndexes.size(); i++) {
            Integer columnIndex = columnIndexes.get(i);
            String header = groupInfoTemp.getHeaderIndexNamesMap().get(columnIndex);
            Set<String> columnHeaders = columnGroupMap.get(header);

        }

        return headerRow;
    }

    public static void pivotData(PivotTableInput pivotTableInput,GroupInfoTemp groupInfoTemp){
        Map<String, List<InputDataRow>> rowGroupMap = groupInfoTemp.getRowGroupMap();
        Iterator<Map.Entry<String, List<InputDataRow>>> iteratorRowGroup = rowGroupMap.entrySet().iterator();
        while (iteratorRowGroup.hasNext()){
            Map.Entry<String, List<InputDataRow>> next = iteratorRowGroup.next();

        }
    }
}
