package com.algaworks.brewer.controller.page;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PageWrapper<T> {

    private Page<T> page;
    private UriComponentsBuilder uriBuilder;

    public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
        this.page = page;
        this.uriBuilder = ServletUriComponentsBuilder.fromRequest(httpServletRequest);
    }

    public List<T> getContent() {
        return page.getContent();
    }

    public boolean isVazia() {
        return page.getContent().isEmpty();
    }

    public int getCurrentPage() {
        return page.getNumber();
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public String urlToCurrentPage(int page) {
        return uriBuilder.replaceQueryParam("page", page).build(true).encode().toUriString();
    }
}
