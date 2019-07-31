package net.sf.jsqlparser.parser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Block;
import net.sf.jsqlparser.statement.Commit;
import net.sf.jsqlparser.statement.SetStatement;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.UseStatement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.statement.values.ValuesStatement;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author toben
 */
public class CCJSqlParserUtilTest {

    public CCJSqlParserUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseExpression method, of class CCJSqlParserUtil.
     */
    @Test
    public void testParseExpression() throws Exception {
        Expression result = CCJSqlParserUtil.parseExpression("a+b");
        assertEquals("a + b", result.toString());
        assertTrue(result instanceof Addition);
        Addition add = (Addition) result;
        assertTrue(add.getLeftExpression() instanceof Column);
        assertTrue(add.getRightExpression() instanceof Column);
    }

    @Test
    public void testParseExpression2() throws Exception {
        Expression result = CCJSqlParserUtil.parseExpression("2*(a+6.0)");
        assertEquals("2 * (a + 6.0)", result.toString());
        assertTrue(result instanceof Multiplication);
        Multiplication mult = (Multiplication) result;
        assertTrue(mult.getLeftExpression() instanceof LongValue);
        assertTrue(mult.getRightExpression() instanceof Parenthesis);
    }

    @Test(expected = JSQLParserException.class)
    public void testParseExpressionNonPartial() throws Exception {
        Expression result = CCJSqlParserUtil.parseExpression("a+", false);
    }

    @Test
    public void testParseExpressionNonPartial2() throws Exception {
        Expression result = CCJSqlParserUtil.parseExpression("a+", true);
        assertEquals("a", result.toString());
    }

    @Test
    public void testParseCondExpression() throws Exception {
        Expression result = CCJSqlParserUtil.parseCondExpression("a+b>5 and c<3");
        assertEquals("a + b > 5 AND c < 3", result.toString());
    }

    @Test
    public void testParseCondExpressionNonPartial() throws Exception {
        Expression result = CCJSqlParserUtil.parseCondExpression("x=92 and y=29", false);
        assertEquals("x = 92 AND y = 29", result.toString());
    }

    @Test(expected = JSQLParserException.class)
    public void testParseCondExpressionNonPartial2() throws Exception {
        Expression result = CCJSqlParserUtil.parseCondExpression("x=92 lasd y=29", false);
        System.out.println(result.toString());
    }

    @Test
    public void testParseCondExpressionPartial2() throws Exception {
        Expression result = CCJSqlParserUtil.parseCondExpression("x=92 lasd y=29", true);
        assertEquals("x = 92", result.toString());
    }

    @Test
    public void testParseCondExpressionIssue471() throws Exception {
        Expression result = CCJSqlParserUtil.parseCondExpression("(SSN,SSM) IN ('11111111111111', '22222222222222')");
        assertEquals("(SSN, SSM) IN ('11111111111111', '22222222222222')", result.toString());
    }

    @Test
    public void testParseStatementsIssue691() throws Exception {
        Statements result = CCJSqlParserUtil.parseStatements(
                "select * from dual;\n"
                + "\n"
                + "select\n"
                + "*\n"
                + "from\n"
                + "dual;\n"
                + "\n"
                + "select *\n"
                + "from dual;");
        assertEquals("SELECT * FROM dual;\n"
                + "SELECT * FROM dual;\n"
                + "SELECT * FROM dual;\n", result.toString());
    }

    @Test
    public void testParseStatementsIssue691_2() throws Exception {
        Statements result = CCJSqlParserUtil.parseStatements(
                "select * from (select name,age from b);"
               );
        assertEquals("SELECT * FROM (SELECT name, age FROM b);\n", result.toString());
    }

    @Test
    public void testFunction() throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse("functionA(funB(funC('aa'),funD(''vv)))");
        StatementVisitorAdapter statementVisitor = new MyStatementVisitorAdapter();
        statement.accept(statementVisitor);

    }



    private static class MyStatementVisitorAdapter extends StatementVisitorAdapter {
        @Override
        public void visit(Select select) {
            SelectBody selectBody = select.getSelectBody();
            selectBody.accept(new MySelectVisitorAdapter());
        }
    }


    private static class MySelectVisitorAdapter extends SelectVisitorAdapter {
        @Override
        public void visit(PlainSelect plainSelect) {
        //    TODO 到这里就是完整的select语句
        }

        @Override
        public void visit(SetOperationList setOpList) {
            super.visit(setOpList);
        }

        @Override
        public void visit(WithItem withItem) {
            super.visit(withItem);
        }

        @Override
        public void visit(ValuesStatement aThis) {
            super.visit(aThis);
        }
    }
}
