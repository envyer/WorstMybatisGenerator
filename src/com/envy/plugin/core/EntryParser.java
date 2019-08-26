package com.envy.plugin.core;

import com.envy.plugin.core.config.TableItemConfig;
import com.envy.plugin.core.utils.StringUtils;

import java.util.*;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public class EntryParser {
    private Set<String> bindings;
    private Map<String, SpecialColumn> cacheEnum = new HashMap<>();

    public List<Entry> initEntries(Table table) {
        List<TableItemConfig> items = table.getItems();
        int size = items.size();
        bindings = new HashSet<>(size);
        List<Entry> entries = new ArrayList<>(size);
        for (TableItemConfig item : items) {
            entries.add(initEntry(item, table));
        }
        return entries;
    }

    private Entry initEntry(TableItemConfig item, Table table) {
        Entry entry = new Entry(getTempPath(item), item.isDefaultTemp(), table);
        setTable(entry, table);
        setFile(entry, item);
        setColumns(entry, item, table);
        updateDepend(entry, table);
        return entry;
    }

    private String getTempPath(TableItemConfig item) {
        String tempPath = item.getTempPath();
        checkPath(tempPath);
        return tempPath;
    }

    private void setTable(Entry entry, Table table){
        entry.put(ConfigConstants.TABLE, table.getName());
        entry.put(ConfigConstants.PK, table.getPk());
    }

    private void setFile(Entry entry, TableItemConfig item) {
        String filePath = item.getDirectoryPath();
        checkPath(filePath);

        if (filePath.contains(".java")) {
            entry.setJavaFile(filePath);
            setBinding(entry, item);
        }
        entry.setFilePath(filePath);
    }

    private void setColumns(Entry entry, TableItemConfig item, Table table) {
        if (item.isInjectColumns()) {
            List<Column> columns = table.getColumns();
            specialColumn(columns, entry);
            entry.put(ConfigConstants.COLUMNS, columns);
        }
    }

    private void updateDepend(Entry entry, Table table){
        table.getDependEnties().addEnty(entry);
    }

    private void setBinding(Entry entry, TableItemConfig item) {
        String binding = item.getBind();
        if (StringUtils.isEmpty(binding)) {
            throw new IllegalArgumentException("binding is null.");
        }
        checkBindingUnique(binding);
        entry.setBinding(binding);
    }

    private void specialColumn(List<Column> columns, Entry entry) {
        int sign = 0;
        for (int i = 0, size = columns.size(); i < size; i++) {
            Column column = columns.get(i);
            SpecialColumn specialColumn = cacheEnum.computeIfAbsent(column.getJavaType(), k -> SpecialColumnHandler.resolve(column.getJavaType()));
            // 可能不是特殊字段，将会返回null
            if (specialColumn != null && specialColumn.isSpecial(sign)) {
                entry.put(specialColumn.getKey(), true);
                sign |= specialColumn.getSign();
            }
        }
    }

    /**
     * 检查binding是否唯一(在一个table中)
     */
    private void checkBindingUnique(String binding) {
        if (bindings.contains(binding)) {
            throw new IllegalArgumentException("binding is not allowed repeat.");
        }
        bindings.add(binding);
    }

    /**
     * 检查路径是否正确
     */
    private void checkPath(String path) {
        if (StringUtils.isEmpty(path) || !hasExtension(path)) {
            throw new IllegalArgumentException(
                    "path is null or path do not have extension.");
        }
    }

    /**
     * 是否拥有后缀
     */
    private boolean hasExtension(String path) {
        int index = path.indexOf(StringUtils.CURRENT_PATH);
        return index > 0 && path.length() > index;
    }

}
