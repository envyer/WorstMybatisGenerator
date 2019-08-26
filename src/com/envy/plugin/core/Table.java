package com.envy.plugin.core;

import com.envy.plugin.core.config.TableItemConfig;

import java.util.List;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public class Table {
    private String name;
    private String className;
    private Column pk;
    private List<Column> columns;
    private List<TableItemConfig> items;
    private int size;
    private DependEntries dependEnties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Column getPk() {
        return pk;
    }

    public void setPk(Column pk) {
        this.pk = pk;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<TableItemConfig> getItems() {
        return items;
    }

    public void setItems(List<TableItemConfig> items) {
        this.items = items;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public DependEntries getDependEnties() {
        return dependEnties;
    }

    public void initDependEnties(int size) {
        this.dependEnties = new DependEntries(size);
    }
}
