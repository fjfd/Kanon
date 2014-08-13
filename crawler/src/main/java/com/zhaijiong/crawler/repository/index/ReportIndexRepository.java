package com.zhaijiong.crawler.repository.index;

import com.zhaijiong.crawler.dao.Report;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by xuqi.xq on 2014/8/12.
 */
public interface ReportIndexRepository extends ElasticsearchCrudRepository<Report, String>,PagingAndSortingRepository<Report, String> {

}
