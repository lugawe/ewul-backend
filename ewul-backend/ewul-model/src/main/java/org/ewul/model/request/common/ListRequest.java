package org.ewul.model.request.common;

import org.ewul.model.request.Request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class ListRequest implements Request {

    @NotNull
    private String term;

    @Min(0)
    private long offset;

    @Min(0)
    private long limit;

    @NotNull
    private Map<String, Object> filter = new HashMap<>();

    public ListRequest() {
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }

}
