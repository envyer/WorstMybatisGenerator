May be the worst mybatis generator.

Entrance: Code > MybatisGenerator
1. You can use your own ftl template to generate custom code
Temp select button choose your own ftl
2. Contains 3 Default ftl
(1) DO
(2) Mapper
(3) Mapper.xml

Custom ftl template contains some variable:
1. Package
you can use (bind + Package) represent acutal package directory, such as DOPackage, MapperPackage, all depends on bind you defined
2. columns
all column which have name、columnName、javaType、comment attributes
3. hasDate
exist any Date type column
4. hasDecimal
exist any Decimal type column
5. pk
primary key of table, contains name、columnName、javaType attributes
6. table
table name

