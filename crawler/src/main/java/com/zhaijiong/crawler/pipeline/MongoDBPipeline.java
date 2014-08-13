package com.zhaijiong.crawler.pipeline;

import com.zhaijiong.crawler.dao.Report;
import com.zhaijiong.crawler.repository.index.ReportIndexRepository;
import com.zhaijiong.crawler.repository.ReportRepository;
import com.zhaijiong.crawler.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * Created by xuqi86@gmail.com on 2014/8/11.
 */

@Component
public class MongoDBPipeline implements Pipeline {
    private static final Logger LOG = LoggerFactory.getLogger(MongoDBPipeline.class);

    @Autowired
    private ReportRepository repository;

    @Autowired
    private ReportIndexRepository indexRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> all = resultItems.getAll();
        try {
            Report report = new Report();
            Utils.transMap2Bean(all, report);
            if(report.getUrl()!=null&&report.getUrl().length()!=0){
                repository.save(report);
                indexRepository.save(report);
            }
        } catch (Exception e) {
            LOG.error("process failed.",e);
        }
    }

    public ReportRepository getRepository() {
        return repository;
    }

    public void setRepository(ReportRepository repository) {
        this.repository = repository;
    }

    public ReportIndexRepository getIndexRepository() {
        return indexRepository;
    }

    public void setIndexRepository(ReportIndexRepository indexRepository) {
        this.indexRepository = indexRepository;
    }
}
