package com.bookreviews.repository;

import com.bookreviews.entity.BookSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookSearchRepository extends ElasticsearchRepository<BookSearchDocument, Long> {



}
