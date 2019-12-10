/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package net.sf.jsqlparser.statement.from;

import net.sf.jsqlparser.schema.Table;

public class MultiInsert {

    private MultiInsertPlainSelect plainSelect;
    private Table table;

    public MultiInsertPlainSelect getPlainSelect() {
        return plainSelect;
    }

    public void setPlainSelect(MultiInsertPlainSelect plainSelect) {
        this.plainSelect = plainSelect;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(table);
        sql.append(" ");
        sql.append(plainSelect);
        return sql.toString();
    }
}
