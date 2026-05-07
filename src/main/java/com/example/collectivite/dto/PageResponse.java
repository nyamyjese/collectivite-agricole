package com.example.collectivite.dto;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private int page;
    private int limit;
    private long totalElements;
    private int totalPages;

    public PageResponse(List<T> content, int page, int limit, long totalElements) {
        this.content = content;
        this.page = page;
        this.limit = limit;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / limit);
    }

    public List<T> getContent() { return content; }
    public int getPage() { return page; }
    public int getLimit() { return limit; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
}