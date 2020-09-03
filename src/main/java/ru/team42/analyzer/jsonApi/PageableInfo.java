package ru.team42.analyzer.jsonApi;

public class PageableInfo {

    private final long totalElements;
    private final int pageSize;
    private final int totalPages;
    private final int pageNumber;

    public PageableInfo(long totalElements, int pageSize, int totalPages, int pageNumber) {
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.pageNumber = pageNumber;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
