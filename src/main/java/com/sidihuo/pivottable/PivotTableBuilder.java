package com.sidihuo.pivottable;

import com.sidihuo.pivottable.convert.GroupHelper;
import com.sidihuo.pivottable.convert.PivotHelper;
import com.sidihuo.pivottable.exception.PivotTableException;
import com.sidihuo.pivottable.model.input.PivotConfig;
import com.sidihuo.pivottable.model.output.OutputDataRow;
import com.sidihuo.pivottable.model.output.OutputHeaderRow;
import com.sidihuo.pivottable.model.temp.GroupInfoTemp;

import java.util.List;

/**
 * @Description
 * @Date 2023/7/25 16:12
 * @Created by yanggangjie
 */
public class PivotTableBuilder {

    public static PivotTableOutput build(PivotTableInput pivotTableInput) {
        valid(pivotTableInput);
        GroupInfoTemp groupInfoTemp = GroupHelper.group(pivotTableInput);
        OutputHeaderRow outputHeaderRow = PivotHelper.pivotHeader(pivotTableInput, groupInfoTemp);
        List<OutputDataRow> outputDataRows = PivotHelper.pivotData(pivotTableInput, groupInfoTemp);
        PivotTableOutput pivotTableOutput = new PivotTableOutput();
        pivotTableOutput.setHeaderRow(outputHeaderRow);
        pivotTableOutput.setDataRows(outputDataRows);
        return pivotTableOutput;
    }

    private static void valid(PivotTableInput pivotTableInput) {
        if (pivotTableInput == null) {
            throw new PivotTableException("1001", "pivot table pivotTableInput is null");
        }
        if (pivotTableInput.getPivotConfig() == null) {
            throw new PivotTableException("1002", "pivot table pivotTableInput pivotConfig is null");
        }
        if (pivotTableInput.getHeaders() == null || pivotTableInput.getHeaders().size() == 0) {
            throw new PivotTableException("1003", "pivot table pivotTableInput headers is empty");
        }
        if (pivotTableInput.getRows() == null || pivotTableInput.getRows().size() == 0) {
            throw new PivotTableException("1004", "pivot table pivotTableInput rows is empty");
        }
        PivotConfig pivotConfig = pivotTableInput.getPivotConfig();
        if (pivotConfig.getRowConfigs() == null || pivotConfig.getRowConfigs().size() == 0) {
            throw new PivotTableException("1005", "pivot table pivotConfig.getRowConfigs() is empty");
        }
        if (pivotConfig.getColumnConfigs() == null || pivotConfig.getColumnConfigs().size() == 0) {
            throw new PivotTableException("1006", "pivot table pivotConfig.getColumnConfigs() is empty");
        }
        if (pivotConfig.getDataConfigs() == null || pivotConfig.getDataConfigs().size() == 0) {
            throw new PivotTableException("1007", "pivot table pivotConfig.getDataConfigs() is empty");
        }
    }


}
