# DTI-JSqlParser

[原项目地址](https://github.com/JSQLParser/JSqlParser)

基于2.1版本开发，因为之后的版本都是基于jdk1.8

## 新增语法

1. 添加udtf函数多别名语法
1. 添加sort by语法
1. JDK版本改为1.7
1. 添加多路输出语法
1. 删除对groupConcat的特殊解析

### Sort By 语意

1. MaxCompute中sortBy的语意是对分片之后的数据进行排序。
1. Hive中sortBy的语意是其在数据进入reducer前完成排序，即组内排序。

对进入reduce前的数据进行排序，在业务上称为组内排序，何时有reduce阶段呢？例如`select * from user sort by id desc`,其结果是按照id排序的，也就是说在没有groupBy是默认将所有的输入数据作为一个分组。

### Multi-Insert

```
FROM table_name1 [ [ AS ] alias1 ]
INSERT INTO table_name2  [ [ AS ] alias2 ] SELECT  select_statement1 [WHERE where_statement1]]
[ INSERT INTO table_name3  [ [ AS ] alias3 ] SELECT  select_statement2 [WHERE where_statement2]] ]

```