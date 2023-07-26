package com.sidihuo.pivottable.test;

import com.alibaba.excel.EasyExcel;
import com.sidihuo.pivottable.PivotTableBuilder;
import com.sidihuo.pivottable.PivotTableInput;
import com.sidihuo.pivottable.model.input.InputDataColumnHeader;
import com.sidihuo.pivottable.model.input.InputDataRow;
import com.sidihuo.pivottable.model.input.PivotColumnConfig;
import com.sidihuo.pivottable.model.input.PivotConfig;
import com.sidihuo.pivottable.model.input.PivotDataConfig;
import com.sidihuo.pivottable.model.input.PivotRowConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Date 2023/7/26 18:12
 * @Created by yanggangjie
 */
public class ExcelTest {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\yanggangjie\\Desktop\\20230726001.xlsx");
        List<InputDataRow> rows=new ArrayList<InputDataRow>();
        List<InputDataColumnHeader> headers=new ArrayList<InputDataColumnHeader>();
        ExcelImportListener excelImportListener = new ExcelImportListener();
        excelImportListener.setRows(rows);
        excelImportListener.setHeaders(headers);
        EasyExcel.read(file, excelImportListener).sheet(1).doRead();

        PivotTableInput pivotTableInput=new PivotTableInput();
        pivotTableInput.setRows(rows);
        pivotTableInput.setHeaders(headers);
        PivotConfig pivotConfig=new PivotConfig();
        List<PivotRowConfig> rowConfigs=new ArrayList<PivotRowConfig>();
        PivotRowConfig rc=new PivotRowConfig();
        rc.setHeaderName("结算月");
        rowConfigs.add(rc);
        rc=new PivotRowConfig();
        rc.setHeaderName("营销员学历");
        rowConfigs.add(rc);
        pivotConfig.setRowConfigs(rowConfigs);
        List<PivotColumnConfig> columnConfigs=new ArrayList<PivotColumnConfig>();
        PivotColumnConfig cc=new PivotColumnConfig();
        cc.setHeaderName("一级渠道");
        columnConfigs.add(cc);
        cc=new PivotColumnConfig();
        cc.setHeaderName("营销员所属分公司");
        columnConfigs.add(cc);
        pivotConfig.setColumnConfigs(columnConfigs);
        List<PivotDataConfig> dataConfigs=new ArrayList<PivotDataConfig>();
        PivotDataConfig dc=new PivotDataConfig();
        dc.setHeaderName("新保保费(剔除万能险)");
        dataConfigs.add(dc);
        dc=new PivotDataConfig();
        dc.setHeaderName("新保期交保费");
        dataConfigs.add(dc);
        dc=new PivotDataConfig();
        dc.setHeaderName("新保趸交保费(剔除万能险)");
        dataConfigs.add(dc);
        pivotConfig.setDataConfigs(dataConfigs);
        pivotTableInput.setPivotConfig(pivotConfig);
        PivotTableBuilder.build(pivotTableInput);
    }
}