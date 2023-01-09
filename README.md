# IDDD のサンプルをScalaで実装してみる

[VaughnVernon/IDDD_Samples](https://github.com/VaughnVernon/IDDD_Samples)

## scalikejdbc-gen 使い方

```shell
# in sbt console
# move project 
project infra-scalikejdbc

# 個別のテーブルを作成する
scalikejdbcGen [table-name (class-name)]

# 例
scalikejdbcGen users

# テーブルを全て生成する
scalikejdbcGenAll
```