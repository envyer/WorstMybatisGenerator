package com.envy.plugin.core.config;

import java.util.List;

/**
 * @author hzqianyizai on 2019/6/3.
 */
public class TableConfig {
    private String tableName;
    private List<TableItemConfig> items;

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public List<TableItemConfig> getItems() {
        return items;
    }
    public void setItems(List<TableItemConfig> items) {
        this.items = items;
    }
}
