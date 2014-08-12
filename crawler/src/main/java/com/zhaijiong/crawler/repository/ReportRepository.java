package com.zhaijiong.crawler.repository;

import com.zhaijiong.crawler.dao.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by xuqi.xq on 2014/8/12.
 */
public interface ReportRepository extends MongoRepository<Report,String> {

    public List<Report> findByAuthor(String author);

    public List<Report> findByType(String type);

    public List<Report> findByTrader(String trader);

    public List<Report> findBySite(String site);

}
