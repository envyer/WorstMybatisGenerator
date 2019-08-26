package com.envy.plugin.core.factory;

import com.envy.plugin.core.DefaultGenerator;
import com.envy.plugin.core.IGenerator;
import com.envy.plugin.core.Table;
import com.envy.plugin.core.config.DataSourceConfig;
import com.envy.plugin.core.config.TableConfig;
import com.envy.plugin.core.db.AbstractDataSource;
import com.envy.plugin.core.db.MySqlDataSource;
import com.envy.plugin.core.exception.DataSourceException;
import com.envy.plugin.core.utils.StringUtils;

public abstract class GenerateFactory {
	public static void create(DataSourceConfig dataSourceConfig, TableConfig tableConfig) throws Exception {
		AbstractDataSource dataSource = createDataSource(dataSourceConfig);
		Table table = dataSource.doGetTable(tableConfig);
		IGenerator generate = new DefaultGenerator();
		generate.generate(table);
	}

	private static AbstractDataSource createDataSource(DataSourceConfig dataSourceConfig) {
		AbstractDataSource dataSource;
		String dialect = dataSourceConfig.getJdbcDriver();
		if (StringUtils.isEmpty(dialect)) {
			throw new DataSourceException("jdbc driver is null.");
		}
		switch (dialect) {
		case "com.mysql.jdbc.Driver":
			dataSource = new MySqlDataSource(dataSourceConfig);
			break;
		default:
			dataSource = new MySqlDataSource(dataSourceConfig);
			break;
		}
		return dataSource;
	}
}
