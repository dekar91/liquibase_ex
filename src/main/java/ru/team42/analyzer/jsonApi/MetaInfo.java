package ru.team42.analyzer.jsonApi;

import com.fasterxml.jackson.annotation.JsonInclude;

public class MetaInfo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PageableInfo pageable;

    public MetaInfo() {
    }

    public PageableInfo getPageable() {
        return pageable;
    }

    public void setPageable(PageableInfo pageable) {
        this.pageable = pageable;
    }
}
