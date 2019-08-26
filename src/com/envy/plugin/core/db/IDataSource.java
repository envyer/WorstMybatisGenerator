package com.envy.plugin.core.db;

import com.envy.plugin.core.Table;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDataSource {
	void setColumns(ResultSet rs, Table table) throws SQLException;
	void setPK(ResultSet rs, Table table) throws SQLException;
}
