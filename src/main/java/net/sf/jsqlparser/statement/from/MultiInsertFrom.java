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
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;

import java.util.List;

/**
 * 多输出
 * <p>
 * FROM from_statement
 * INSERT INTO TABLE tablename1
 * select_statement1 [where where_statement]
 * [INSERT INTO TABLE tablename2
 * select_statement2 [where where_statement]]
 */
public class MultiInsertFrom implements Statement {

    private Table table;
    private List<MultiInsert> multiInserts;


    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<MultiInsert> getMultiInserts() {
        return multiInserts;
    }

    public void setMultiInserts(List<MultiInsert> multiInserts) {
        this.multiInserts = multiInserts;
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();
        sql.append("FROM ").append(table);
        for (MultiInsert multiInsert : multiInserts) {
            sql.append(" ");
            sql.append(multiInsert);
        }

        return sql.toString();
    }
}
