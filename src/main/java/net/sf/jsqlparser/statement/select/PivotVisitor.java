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

/**
 * 行列转换，sql有pivot关键字
 */
public interface PivotVisitor {

    void visit(Pivot pivot);

    void visit(PivotXml pivot);

}
