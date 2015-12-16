package com.grimmer.simpleChart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Visit my gitHub blog", Snackbar.LENGTH_LONG)
                        .setAction("Go", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Uri link = Uri.parse("http://enjoyee.github.io");
                                Intent intent = new Intent(Intent.ACTION_VIEW, link);
                                Intent.createChooser(intent, "选择打开方式");
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        // 初始化柱状图
        HistogramView hvOrderStatistics = (HistogramView) findViewById(R.id.hvOrderStatistics);
        // 设置数据
        List<HistogramView.ColumnData> columnDataList = new ArrayList<>();
        HistogramView.ColumnData columnData = new HistogramView.ColumnData();
        columnData.setXColumn(9);
        columnData.setYColumn(5);
        columnDataList.add(columnData);

        columnData = new HistogramView.ColumnData();
        columnData.setXColumn(10);
        columnData.setYColumn(3);
        columnDataList.add(columnData);

        columnData = new HistogramView.ColumnData();
        columnData.setXColumn(11);
        columnData.setYColumn(1);
        columnDataList.add(columnData);

        columnData = new HistogramView.ColumnData();
        columnData.setXColumn(12);
        columnData.setYColumn(4);
        columnDataList.add(columnData);

        columnData = new HistogramView.ColumnData();
        columnData.setXColumn(13);
        columnData.setYColumn(2);
        columnDataList.add(columnData);

        columnData = new HistogramView.ColumnData();
        columnData.setXColumn(14);
        columnData.setYColumn(3);
        columnDataList.add(columnData);

        columnData = new HistogramView.ColumnData();
        columnData.setXColumn(15);
        columnData.setYColumn(5);
        columnDataList.add(columnData);

        columnData = new HistogramView.ColumnData();
        columnData.setXColumn(16);
        columnData.setYColumn(1);
        columnDataList.add(columnData);

        columnData = new HistogramView.ColumnData();
        columnData.setXColumn(17);
        columnData.setYColumn(4);
        columnDataList.add(columnData);

        columnData = new HistogramView.ColumnData();
        columnData.setXColumn(18);
        columnData.setYColumn(3);
        columnDataList.add(columnData);

        hvOrderStatistics.setColumnData(columnDataList);
    }
}
