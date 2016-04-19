コード説明のTOPへ ExplanationOfSourceCode


&lt;hr/&gt;


# ResultVoのソースコード説明 #
ResultVoクラスの責務は、flexigrid（クライアントサイドで使うjQueryのプラグイン）の仕様に合わせたJSONデータ作成やりやすくするというものである。<br />
ResultVoはflexigridの仕様に合わせた項目、項目の階層をJavaBeans形式で保持している。<br />
これをJSONICに入力することでJSON形式のデータ作成が可能となる。

```
package org.noticeboard2011.vo;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * JSON形式でflexigrid用のデータを返却するためのValueObjectである。<br/>
 * このオブジェクトをJSONICに渡してJSON形式のデータを作成する。
 * 
 * @author akiraabe
 *
 */
public class ResultVo {
    
    private String page;
    private Integer total;
    private Set rows = new LinkedHashSet();
    
    public void addRow(Integer id, String... value) {
        Map<String, Object> row = new HashMap<String, Object>();
        row.put("id", id);
        String[] strs = new String[value.length];
        for (int i = 0; i < value.length; i++) {
            strs[i] = value[i];
        }
        row.put("cell", strs);
        rows.add(row);
    }
    
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Set getRows() {
        return rows;
    }
    public void setRows(Set rows) {
        this.rows = rows;
    }

}

```

### 関連事項 ###
JsonDataSample JSON形式のデータ

### ToDo ###
このクラスは、名前（又はパッケージ）を変更しflexigrid専用であることを明示した方が良いと思われる。


&lt;hr/&gt;


コード説明のTOPへ ExplanationOfSourceCode