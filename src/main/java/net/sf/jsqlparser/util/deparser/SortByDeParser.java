/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package net.sf.jsqlparser.util.deparser;

import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.statement.select.SortByElement;

import java.util.Iterator;
import java.util.List;

public class SortByDeParser {

    protected StringBuilder buffer;
    private ExpressionVisitor expressionVisitor;

    SortByDeParser() {
    }

    public SortByDeParser(ExpressionVisitor expressionVisitor, StringBuilder buffer) {
        this.expressionVisitor = expressionVisitor;
        this.buffer = buffer;
    }

    public void deParse(List<SortByElement> sortByElementList) {
        buffer.append(" SORT BY ");

        for (Iterator<SortByElement> iter = sortByElementList.iterator(); iter.hasNext();) {
            SortByElement sortByElement = iter.next();
            deParseElement(sortByElement);
            if (iter.hasNext()) {
                buffer.append(", ");
            }
        }
    }

    public void deParseElement(SortByElement sortBy) {
        sortBy.getExpression().accept(expressionVisitor);
        if (!sortBy.isAsc()) {
            buffer.append(" DESC");
        } else if (sortBy.isAscDescPresent()) {
            buffer.append(" ASC");
        }
    }

    void setExpressionVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }
}
