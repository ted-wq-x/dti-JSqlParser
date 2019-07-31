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

import net.sf.jsqlparser.statement.values.ValuesStatement;

/**
 * 针对select语句的visitor
 */
public interface SelectVisitor {

    void visit(PlainSelect plainSelect);

    /**
     * 处理UNION,INTERSECT,MINUS,EXCEPT
     * @param setOpList
     */
    void visit(SetOperationList setOpList);

    /**
     * 处理with语句
     * @param withItem
     */
    void visit(WithItem withItem);

    void visit(ValuesStatement aThis);
}
