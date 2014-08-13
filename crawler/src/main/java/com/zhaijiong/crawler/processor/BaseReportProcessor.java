package com.zhaijiong.crawler.processor;

import com.google.common.hash.Hashing;
import com.zhaijiong.crawler.dao.ReportTemplate;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by xuqi86@gmail.com on 2014/8/11.
 */
public class BaseReportProcessor implements PageProcessor {
    private final Site site;
    private final ReportTemplate report;

    public BaseReportProcessor(ReportTemplate report,Site site){
        super();
        this.report = report;
        this.site = site;
    }

    @Override
    public void process(Page page) {
        if(page.getUrl().regex(report.getListPageUrlRule()).match()){
            page.addTargetRequests(page.getHtml().links().regex(report.getListPageUrlRule()).all());
            page.addTargetRequests(page.getHtml().links().regex(report.getPostPageUrlRule()).all());
        }else{
            String url = page.getUrl().regex(report.getPostPageUrlRule()).toString();
            page.putField("id", Hashing.md5().hashBytes(page.getUrl().toString().getBytes()).toString());
            page.putField("url", url);
            page.putField("author", page.getHtml().xpath(report.getReport().getAuthor()).toString());
            page.putField("title", page.getHtml().xpath(report.getReport().getTitle()).toString());
            //这里不是从配置文件获取的，而是用的api
            page.putField("content", page.getHtml().smartContent().toString());
            page.putField("attachment",page.getHtml().xpath(report.getReport().getAttachment()).toString());
            //在模板中固定四种类型
            page.putField("type",report.getReport().getType());
            page.putField("date",page.getHtml().xpath(report.getReport().getDate()).toString());
            page.putField("trader", page.getHtml().xpath(report.getReport().getTrader()).toString());
            page.putField("site",report.getReport().getSite());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
