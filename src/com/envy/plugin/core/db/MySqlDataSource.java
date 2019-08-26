package com.envy.plugin.core.db;

import com.envy.plugin.core.Column;
import com.envy.plugin.core.Table;
import com.envy.plugin.core.config.DataSourceConfig;
import com.envy.plugin.core.utils.ColumnUtil;
import com.envy.plugin.core.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlDataSource extends AbstractDataSource {
	public MySqlDataSource(DataSourceConfig dataSourceConfig) {
		super(dataSourceConfig);
	}

	@Override
	public void setColumns(ResultSet rs, Table table) throws SQLException {
		rs.last();
		List<Column> columns = new ArrayList<>(rs.getRow());
		rs.beforeFirst();
		Column pk = table.getPk();
		while (rs.next()) {
			// 去除主键
			/*if (pk != null
					&& rs.getString("COLUMN_NAME").equals(pk.getColumnName())) {
				continue;
			}*/
			Column column = ColumnUtil.getColumnFromRS(rs);
			columns.add(column);
			if (StringUtils.equals(column.getColumnName(), pk.getColumnName())) {
				table.setPk(column);
			}
		}
		table.setColumns(columns);
	}

	@Override
	public void setPK(ResultSet rs, Table table) throws SQLException {
		Column pk = null;
		while (rs.next()) {
			pk = ColumnUtil.getPKFromRS(rs);
			break;
		}
		table.setPk(pk);
	}

}
