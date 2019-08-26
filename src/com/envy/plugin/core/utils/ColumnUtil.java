package com.envy.plugin.core.utils;

import com.envy.plugin.core.Column;
import com.envy.plugin.core.JavaType;
import com.envy.plugin.core.JavaTypeHandler;
import com.envy.plugin.core.MybatisTypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnUtil {
	public static Column getColumnFromRS(ResultSet rs) throws SQLException {
		return getColumnFromRS(rs, false);
	}

	public static Column getPKFromRS(ResultSet rs) throws SQLException {
		return getColumnFromRS(rs, true);
	}

	private static Column getColumnFromRS(ResultSet rs, boolean isPK)
			throws SQLException {
		Column col = new Column();
		col.setColumnName(rs.getString("COLUMN_NAME"));
		if (!isPK) {
			col.setJdbcType(MybatisTypeHandler.getType(rs.getString("TYPE_NAME")));
			col.setDefaultValue(rs.getString("COLUMN_DEF"));
			col.setComment(rs.getString("REMARKS"));
			col.setNullable(StringUtils.equals(rs.getString("NULLABLE"), "1"));
			convertField(col);
		}
		return col;
	}

	private static void convertField(Column col) {
		col.setName(getName(col.getColumnName()));
		JavaType javaType = JavaTypeHandler.getType(col.getJdbcType());
		if (col.isNullable() && javaType.isWrapClass()) {
			col.setJavaType(javaType.getJavaPackageType());
			col.setWrapClass(true);
		} else {
			col.setJavaType(javaType.getJavaType());
		}
		String methodName = getMethodName(col.getName());
		col.setSetMethod(methodName);
		if (col.getJavaType().equalsIgnoreCase("boolean")) {
			col.setGetMethod("is" + methodName);
		} else {
			col.setGetMethod("get" + methodName);
		}
	}

	private static String getName(String columnName) {
		if (columnName.startsWith("is_")) {
			columnName = columnName.substring(3);
		}
		if (!columnName.contains("_")) {
			return columnName;
		}
		String[] arrs = columnName.split("_");
		int al = arrs.length;
		StringBuilder sb = new StringBuilder();
		sb.append(arrs[0]);
		if (al > 1) {
			for (int i = 1; i < al; i++) {
				String s = arrs[i];
				sb.append(Character.toUpperCase(s.charAt(0)));
				sb.append(s.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}

	private static String getMethodName(String name) {
		return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

}
