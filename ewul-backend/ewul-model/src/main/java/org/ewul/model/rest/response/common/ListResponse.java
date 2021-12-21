package org.ewul.model.rest.response.common;

import org.ewul.model.rest.response.Response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ListResponse implements Response {

    @NotNull
    private List<?> items;

    @Min(0)
    private long total;

    public ListResponse() {
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}
