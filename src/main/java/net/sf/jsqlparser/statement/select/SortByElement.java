/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package net.sf.jsqlparser.statement.select;

import net.sf.jsqlparser.expression.Expression;

/**
 * sort by的语义是组内排序，所以必须有group by
 */
public class SortByElement {
    private Expression expression;
    private boolean asc = true;
    /**
     * 表示是否有asc/desc 例如 sort by name ,那么ascDesc为false
     */
    private boolean ascDesc = false;

    public boolean isAsc() {
        return asc;
    }


    public void setAsc(boolean b) {
        asc = b;
    }

    public void accept(SortByVisitor sortByVisitor) {
        sortByVisitor.visit(this);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean isAscDescPresent() {
        return ascDesc;
    }

    public void setAscDescPresent(boolean b) {
        ascDesc = b;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(expression.toString());
        if (!asc) {
            b.append(" DESC");
        } else if (ascDesc) {
            b.append(" ASC");
        }
        return b.toString();
    }
}
