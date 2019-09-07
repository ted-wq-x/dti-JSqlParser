在2.1版本的基础上，做的定制化主要有：

1. 添加udtf函数多别名语法
1. 添加sort by语法
1. JDK版本改为1.7

# Sort By 语意

1. MaxCompute中sortBy的语意是对分片之后的数据进行排序。
1. Hive中sortBy的语意是其在数据进入reducer前完成排序，即组内排序。

对进入reduce前的数据进行排序，在业务上称为组内排序，何时有reduce阶段呢？例如`select * from user sort by id desc`,其结果是按照id排序的，也就是说在没有groupBy是默认将所有的输入数据作为一个分组。
