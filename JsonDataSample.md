コード説明のTOPへ ExplanationOfSourceCode


&lt;hr/&gt;


# JSON形式のデータサンプル #

flexigrid用のJSON形式のデータは、以下のような形で、サーバサイドから返却される。
<br />
（読みやすい形に整形して表示してある。）

```
{
 "page":"1",
 "rows":[{"id":0,"cell":["8001","54","Aretha","Franklin","Meeting"]},
         {"id":1,"cell":["10001","22","B.B.","King","Present"]},
         {"id":2,"cell":["17001","15","Ben E.","King","Absent"]},
         {"id":3,"cell":["15001","16","Erykah","Badu","Present"]},
         {"id":4,"cell":["7001","18","James","Brown","Absent"]},
         {"id":5,"cell":["11001","16","K.K.","Shiraishi","Absent"]},
         {"id":6,"cell":["13001","23","Marvin","Gaye","Present"]},
         {"id":7,"cell":["6001","32","Otis","Redding","Present"]},
         {"id":8,"cell":["9002","40","Ray","Charles","Present"]},
         {"id":9,"cell":["9003","23","Sam","Cooke","undef"]}],
 "total":17
}
```



&lt;hr/&gt;


コード説明のTOPへ ExplanationOfSourceCode