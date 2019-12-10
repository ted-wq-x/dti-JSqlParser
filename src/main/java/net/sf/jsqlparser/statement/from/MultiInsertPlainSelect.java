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

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.List;

import static net.sf.jsqlparser.statement.select.PlainSelect.getStringList;

public class MultiInsertPlainSelect {

    private Expression where;
    private List<SelectItem> selectItems;

    public Expression getWhere() {
        return where;
    }

    public void setWhere(Expression where) {
        this.where = where;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getStringList(selectItems));
        if (where != null) {
            sql.append(" WHERE ").append(where);
        }
        return sql.toString();
    }
}
