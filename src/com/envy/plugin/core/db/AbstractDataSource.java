package com.envy.plugin.core.db;

import com.envy.plugin.core.Table;
import com.envy.plugin.core.config.DataSourceConfig;
import com.envy.plugin.core.config.TableConfig;
import com.envy.plugin.core.config.TableItemConfig;
import com.envy.plugin.core.exception.DataSourceException;
import com.envy.plugin.core.utils.ListUtils;
import com.envy.plugin.core.utils.StringUtils;

import java.sql.*;
import java.util.List;

public abstract class AbstractDataSource implements IDataSource {
    private Connection conn = null;
    private DataSourceConfig dataSourceConfig;

    public AbstractDataSource(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    private Connection getConnection() throws Exception {
        if (conn != null) {
            return conn;
        }

        Class.forName(dataSourceConfig.getJdbcDriver()).newInstance();
        conn = DriverManager.getConnection(
                dataSourceConfig.getJdbcUrl(),
                dataSourceConfig.getJdbcUserName(),
                dataSourceConfig.getJdbcUserPassword());
        return conn;
    }

    /**
     * 根据数据库表名产生table对象
     */
    public Table doGetTable(TableConfig tableConfig) throws Exception {
        ResultSet rs = null;
        try {
            Table table = new Table();
            rs = initTable(table, tableConfig);
            return table;
        } finally {
            close(rs);
        }
    }

    private ResultSet initTable(Table table, TableConfig tableConfig) throws Exception {
        setTableName(table, tableConfig.getTableName());
        setItems(table, tableConfig.getItems());
        DatabaseMetaData dmd = getConnection().getMetaData();
        ResultSet rs = dmd.getPrimaryKeys(null, null, table.getName());
        setPK(rs, table);
        rs = dmd.getColumns(null, "", table.getName(), "");
        setColumns(rs, table);
        return rs;
    }

    private void setTableName(Table table, String name) {
        if (StringUtils.isEmpty(name)) {
            throw new DataSourceException("table name is required.");
        }
        table.setName(name);
    }

    private void setItems(Table table, List<TableItemConfig> items) {
        table.setItems(items);
        int childSize = (ListUtils.isEmpty(items) ? 0 : items.size());
        table.setSize(childSize);
        table.initDependEnties(childSize);
    }

    private void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
