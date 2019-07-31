package net.sf.jsqlparser.wq;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitorAdapter;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.FromItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.IntoTableVisitorAdapter;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.ParenthesisFromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.select.SetOperation;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.values.ValuesStatement;

import java.util.List;

public class Main {

    private static final FromItemVisitorAdapter fromItemVisitor = new FromItemVisitorAdapter(){
        @Override
        public void visit(Table table) {
            //TODO 输入文件路径
            System.out.println(getSource(table.getName()));
        }

        @Override
        public void visit(SubSelect subSelect) {
            super.visit(subSelect);
        }

        @Override
        public void visit(SubJoin subjoin) {
            super.visit(subjoin);
        }

        @Override
        public void visit(LateralSubSelect lateralSubSelect) {
            super.visit(lateralSubSelect);
        }

        @Override
        public void visit(ValuesList valuesList) {
            super.visit(valuesList);
        }

        @Override
        public void visit(TableFunction valuesList) {
            super.visit(valuesList);
        }

        @Override
        public void visit(ParenthesisFromItem aThis) {
            super.visit(aThis);
        }
    };
    private static final SelectVisitorAdapter selectVisitor = new SelectVisitorAdapter(){
        @Override
        public void visit(PlainSelect plainSelect) {
            FromItem fromItem = plainSelect.getFromItem();


            fromItem.accept(fromItemVisitor);
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
            System.out.println(aThis);
        }
    };

    private static final StatementVisitorAdapter statementVisitor = new StatementVisitorAdapter() {
        @Override
        public void visit(Insert insert) {
            //特定约束，即这里描述的是本地文本数据
            //默认使用，作为字段分隔符，\r\n作为行分隔符
            final Table table = insert.getTable();
            table.accept(new IntoTableVisitorAdapter(){
                @Override
                public void visit(Table tableName) {

                    //TODO 输出文件路径
                    System.out.println(getSource(table.getName()));
                }
            });
            table.accept(new FromItemVisitorAdapter(){
                @Override
                public void visit(Table table) {
                    super.visit(table);
                }

                @Override
                public void visit(SubSelect subSelect) {
                    super.visit(subSelect);
                }

                @Override
                public void visit(SubJoin subjoin) {
                    super.visit(subjoin);
                }

                @Override
                public void visit(LateralSubSelect lateralSubSelect) {
                    super.visit(lateralSubSelect);
                }

                @Override
                public void visit(ValuesList valuesList) {
                    super.visit(valuesList);
                }

                @Override
                public void visit(TableFunction valuesList) {
                    super.visit(valuesList);
                }

                @Override
                public void visit(ParenthesisFromItem aThis) {
                    super.visit(aThis);
                }
            });


            //insert into values(xxx)或values(select)
            ItemsList itemsList = insert.getItemsList();
            if (itemsList != null) {
                itemsList.accept(new ItemsListVisitorAdapter(){
                    @Override
                    public void visit(SubSelect subSelect) {
                        super.visit(subSelect);
                    }
                });
            }


        }

        @Override
        public void visit(Select select) {
            select.getSelectBody().accept(selectVisitor);
        }
    };

    static class Source{

        private String path;

        public Source(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return "Source{" +
                    "path='" + path + '\'' +
                    '}';
        }
    }

    /**
     * TODO 数据源
     * @param path
     * @return
     */
    private static Source getSource(String path) {
        return new Source(path);
    }


    public static void main(String[] args) throws JSQLParserException {
        // Statement parse = CCJSqlParserUtil.parse("insert into \"E\\mm.txt\" from (select name,age,\"cc\" from \"ds.txt\") UNION (select name,age,\"cc\" from dm)");
        // Statement parse = CCJSqlParserUtil.parse("(select name,age,\"cc\" from \"ds.txt\") UNION (select name,age,\"cc\" from dm) limit 0,10");
        // Statement parse = CCJSqlParserUtil.parse(" SELECT data_generate_id()  AS  MD_ID,string_regex_exclude(ID,'(\\t|\\n)')  AS  ID,string_regex_exclude(OPID,'(\\t|\\n)')  AS  OPID,string_regex_exclude(GROUPOPID,'(\\t|\\n)')  AS  GROUPOPID,string_regex_exclude(SERVICEID,'(\\t|\\n)')  AS  SERVICEID,string_regex_exclude(AREACODE,'(\\t|\\n)')  AS  AREACODE,STORETIME  AS  STORETIME,COLLECT_TIME  AS  COLLECT_TIME,string_regex_exclude(ICP_TYPE,'(\\t|\\n)')  AS  ICP_TYPE,string_regex_exclude(APPTYPE,'(\\t|\\n)')  AS  APPTYPE,string_regex_exclude(APPID,'(\\t|\\n)')  AS  APPID,string_regex_exclude(IMOID,'(\\t|\\n)')  AS  IMOID,string_regex_exclude(USERID,'(\\t|\\n)')  AS  USERID,string_regex_exclude(NICKNAME,'(\\t|\\n)')  AS  NICKNAME,string_regex_exclude(PASSWORD,'(\\t|\\n)')  AS  PASSWORD,string_regex_exclude(REALNAME,'(\\t|\\n)')  AS  REALNAME,string_regex_exclude(SEX,'(\\t|\\n)')  AS  SEX,string_regex_exclude(EMAIL,'(\\t|\\n)')  AS  EMAIL,string_regex_exclude(BIRTHDAY,'(\\t|\\n)')  AS  BIRTHDAY,string_regex_exclude(LIVEPLACE,'(\\t|\\n)')  AS  LIVEPLACE,string_regex_exclude(NATIVEPLACE,'(\\t|\\n)')  AS  NATIVEPLACE,string_regex_exclude(TELEPHONE,'(\\t|\\n)')  AS  TELEPHONE,string_regex_exclude(MOBILE,'(\\t|\\n)')  AS  MOBILE,string_regex_exclude(CERTIFICATE_TYPE,'(\\t|\\n)')  AS  CERTIFICATE_TYPE,string_regex_exclude(CERTIFICATE_CODE,'(\\t|\\n)')  AS  CERTIFICATE_CODE,string_regex_exclude(COLLEGE,'(\\t|\\n)')  AS  COLLEGE,string_regex_exclude(STUDY,'(\\t|\\n)')  AS  STUDY,string_regex_exclude(COMPANY,'(\\t|\\n)')  AS  COMPANY,string_regex_exclude(COMPANYID,'(\\t|\\n)')  AS  COMPANYID,string_regex_exclude(OCCUPATION_NAME,'(\\t|\\n)')  AS  OCCUPATION_NAME,string_regex_exclude(JOBLEVEL,'(\\t|\\n)')  AS  JOBLEVEL,string_regex_exclude(INCOME,'(\\t|\\n)')  AS  INCOME,REGIS_TIME  AS  REGIS_TIME,IP  AS  IP,PORT  AS  PORT,string_regex_exclude(MAC,'(\\t|\\n)')  AS  MAC,string_regex_exclude(IMEI,'(\\t|\\n)')  AS  IMEI,string_regex_exclude(IMSI,'(\\t|\\n)')  AS  IMSI,string_regex_exclude(HARDWARESTRING,'(\\t|\\n)')  AS  HARDWARESTRING,string_regex_exclude(LONGITUDE,'(\\t|\\n)')  AS  LONGITUDE,string_regex_exclude(LATITUDE,'(\\t|\\n)')  AS  LATITUDE,string_regex_exclude(POSITIONINFO,'(\\t|\\n)')  AS  POSITIONINFO,STATE  AS  STATE,string_regex_exclude(PASSPORT,'(\\t|\\n)')  AS  PASSPORT,string_regex_exclude(SAFE_QUESTION,'(\\t|\\n)')  AS  SAFE_QUESTION,string_regex_exclude(SAFE_ANSWER,'(\\t|\\n)')  AS  SAFE_ANSWER,string_regex_exclude(HOME_PAGE,'(\\t|\\n)')  AS  HOME_PAGE,string_regex_exclude(BLOOD_TYPE,'(\\t|\\n)')  AS  BLOOD_TYPE,string_regex_exclude(SIGN_NAME,'(\\t|\\n)')  AS  SIGN_NAME,string_regex_exclude(PERSONAL_DESC,'(\\t|\\n)')  AS  PERSONAL_DESC,string_regex_exclude(ZODIAC,'(\\t|\\n)')  AS  ZODIAC,string_regex_exclude(CONSTALLATION,'(\\t|\\n)')  AS  CONSTALLATION,string_regex_exclude(REGPROVNICE,'(\\t|\\n)')  AS  REGPROVNICE,string_regex_exclude(PROVINCE,'(\\t|\\n)')  AS  PROVINCE,string_regex_exclude(POSTAL_ADDRESS,'(\\t|\\n)')  AS  POSTAL_ADDRESS,string_regex_exclude(BINDQQ,'(\\t|\\n)')  AS  BINDQQ,string_regex_exclude(BINDALIPAY,'(\\t|\\n)')  AS  BINDALIPAY,string_regex_exclude(BINDBLOG,'(\\t|\\n)')  AS  BINDBLOG,string_regex_exclude(BINDWANGWANG,'(\\t|\\n)')  AS  BINDWANGWANG,string_regex_exclude(BINDWEBCHAT,'(\\t|\\n)')  AS  BINDWEBCHAT,string_regex_exclude(TERMINALTYPE,'(\\t|\\n)')  AS  TERMINALTYPE,string_regex_exclude(NETWORK,'(\\t|\\n)')  AS  NETWORK,string_regex_exclude(SYSTEM_TYPE,'(\\t|\\n)')  AS  SYSTEM_TYPE,UPTIME  AS  UPTIME,string_regex_exclude(SHOP_ID,'(\\t|\\n)')  AS  SHOP_ID,string_regex_exclude(SHOP_NAME,'(\\t|\\n)')  AS  SHOP_NAME,string_regex_exclude(BANK_NAME,'(\\t|\\n)')  AS  BANK_NAME,string_regex_exclude(BANK_USERNAME,'(\\t|\\n)')  AS  BANK_USERNAME,string_regex_exclude(BANK_ACCOUNT_NUM,'(\\t|\\n)')  AS  BANK_ACCOUNT_NUM,string_regex_exclude(GAME_ROLE,'(\\t|\\n)')  AS  GAME_ROLE,string_regex_exclude(GAME_AREA,'(\\t|\\n)')  AS  GAME_AREA,string_regex_exclude(GAME_SERVER,'(\\t|\\n)')  AS  GAME_SERVER,string_regex_exclude(COLLECTE_PLACE,'(\\t|\\n)')  AS  COLLECTE_PLACE,FIRST_TIME  AS  FIRST_TIME,LAST_TIME  AS  LAST_TIME,string_regex_exclude(DATA_SOURCE,'(\\t|\\n)')  AS  DATA_SOURCE,string_regex_exclude(SENSITIVE_LEVEL,'(\\t|\\n)')  AS  SENSITIVE_LEVEL,TOTAL_DAY  AS  TOTAL_DAY,ADD_DAY  AS  ADD_DAY,string_regex_exclude(AREA,'(\\t|\\n)')  AS  AREA,string_regex_exclude(POSTAL_CODE,'(\\t|\\n)')  AS  POSTAL_CODE,string_regex_exclude(IPID,'(\\t|\\n)')  AS  IPID,string_regex_exclude(RELATEACCOUNT,'(\\t|\\n)')  AS  RELATEACCOUNT,string_regex_exclude(USERPHOTO,'(\\t|\\n)')  AS  USERPHOTO,string_regex_exclude(REGTYPE,'(\\t|\\n)')  AS  REGTYPE,string_regex_exclude(PUBACCTRENDS,'(\\t|\\n)')  AS  PUBACCTRENDS,string_regex_exclude(USERNAME,'(\\t|\\n)')  AS  USERNAME,string_regex_exclude(IMNUM,'(\\t|\\n)')  AS  IMNUM,string_regex_exclude(PRIIMNUM,'(\\t|\\n)')  AS  PRIIMNUM,string_regex_exclude(AGE,'(\\t|\\n)')  AS  AGE,string_regex_exclude(NETCALL,'(\\t|\\n)')  AS  NETCALL,string_regex_exclude(CERTIFICATE_URL,'(\\t|\\n)')  AS  CERTIFICATE_URL,string_regex_exclude(IDENTIFICATION_TYPE,'(\\t|\\n)')  AS  IDENTIFICATION_TYPE,string_regex_exclude(USERTYPE,'(\\t|\\n)')  AS  USERTYPE,string_regex_exclude(DOSSIERNO,'(\\t|\\n)')  AS  DOSSIERNO,string_regex_exclude(DOSSIERNO_URL,'(\\t|\\n)')  AS  DOSSIERNO_URL,string_regex_exclude(VEHICLEID,'(\\t|\\n)')  AS  VEHICLEID,string_regex_exclude(QRCODE,'(\\t|\\n)')  AS  QRCODE,string_regex_exclude(USER_HABITS,'(\\t|\\n)')  AS  USER_HABITS,string_regex_exclude(BD_INSTITUTIONSCODE,'(\\t|\\n)')  AS  BD_INSTITUTIONSCODE,string_regex_exclude(BD_FINANCIALACCOUNTTYPE,'(\\t|\\n)')  AS  BD_FINANCIALACCOUNTTYPE,string_regex_exclude(BD_FINANCIALACCOUNT,'(\\t|\\n)')  AS  BD_FINANCIALACCOUNT,string_regex_exclude(ACCOUNT_BALANCE,'(\\t|\\n)')  AS  ACCOUNT_BALANCE,string_regex_exclude(COMPANY_NAME,'(\\t|\\n)')  AS  COMPANY_NAME,string_regex_exclude(COMPANY_ADDR,'(\\t|\\n)')  AS  COMPANY_ADDR,string_regex_exclude(CONTACT_NAME,'(\\t|\\n)')  AS  CONTACT_NAME,string_regex_exclude(CONTACT_WAY,'(\\t|\\n)')  AS  CONTACT_WAY,string_regex_exclude(FAMILYMOBILE,'(\\t|\\n)')  AS  FAMILYMOBILE,string_regex_exclude(REMARK,'(\\t|\\n)')  AS  REMARK,date_generate_currentTime()  AS  FH_RKSJ FROM ts");
        // Statement parse = CCJSqlParserUtil.parse("select count(1) from t where 1=1");

        // parse.accept(statementVisitor);
        String str = "ZJHM,BMCH,BMCHPY,XB,MZ,CSRQ,CSSJ,CSZMBH,CSDQ,CSQX,CSXZ,JGDQ,JGQX,JGXZ,ZJXY,ZZMM,WHCD,HYZK,BYZK,SG,XX,SF,ZC,ZW,ZY,ZYLB,FWCS,LXDH,ZWSP,SWRQ,SWZMBH,GJDQ,HJQH,HJZRQ,HJXZ,XXJB,RYLB,RYSX,RYZZBH,ZWBH,DNABH,DJR,DJSJ,DJDW,XZZQH,XZZZRQ,XZZXZ,DJDWMC,DJRXM,XGSJ,XGR,XGDW,XGRXM,XGDWMC,HJQHMC,XZZQHMC,RYTBBS,RYCJFS,TZ,DMHS__ROWID,RYBH,GMSFHM,XM,XMPY,CYM,CYMPY,YWX,YWM,ZJZL,SFJZ";
        System.out.println(str.split(",").length);
    }
}
