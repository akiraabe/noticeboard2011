package org.noticeboard2011.vo;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
