/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package net.sf.jsqlparser.expression;

import java.util.List;

public class Alias {

    private String name;
    private boolean useAs = true;
    private List<String> udafColumns;

    public Alias(String name) {
        this.name = name;
    }

    public Alias(String name, boolean useAs) {
        this.name = name;
        this.useAs = useAs;
    }

    public Alias(boolean useAs, List<String> udafColumns) {
        this.useAs = useAs;
        this.udafColumns = udafColumns;
    }

    public List<String> getUdafColumns() {
        return udafColumns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUseAs() {
        return useAs;
    }

    public void setUseAs(boolean useAs) {
        this.useAs = useAs;
    }

    @Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();
        if (useAs) {
            buffer.append(" AS ");
        }else {
            buffer.append(" ");
        }
        if (udafColumns==null||udafColumns.size()==0) {
            buffer.append(name);
        } else {
            buffer.append("(");
            for (int i = 0; i < udafColumns.size(); i++) {
                if (i > 0) {
                    buffer.append(",");
                }
                buffer.append(udafColumns.get(i));
            }
            buffer.append(")");
        }

        return buffer.toString();
    }
}
