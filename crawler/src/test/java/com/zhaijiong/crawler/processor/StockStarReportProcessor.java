package com.zhaijiong.crawler.processor;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.zhaijiong.crawler.pipeline.MongoDBPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by xuqi.xq on 2014/8/10.
 */
public class StockStarReportProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public static final String URL_POST = "http://stock.stockstar.com/JC.*.shtml";

    public static final String URL_LIST = "http://stock.stockstar.com/list/3491.*.shtml";

    @Override
    public void process(Page page) {
            if(page.getUrl().regex(URL_LIST).match()){
                page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
                page.addTargetRequests(page.getHtml().links().regex(URL_POST).all());
            }else{
                System.out.println(page.getUrl());
                String url = page.getUrl().regex(URL_POST).toString();
                page.putField("id", Hashing.md5().hashBytes(page.getUrl().toString().getBytes()).toString());
                page.putField("url", url);
                page.putField("author", page.getHtml().xpath("//span[@id='author_baidu']/a/text()").toString());
                page.putField("title", page.getHtml().xpath("//div[@id='container-box']/h1/text()").toString());
                page.putField("content", page.getHtml().smartContent().toString());
                page.putField("attachment",page.getHtml().xpath("//div[@id='container-article']/p/a/@href").toString());
                page.putField("type","公司调研");
                page.putField("date",page.getHtml().xpath("//span[@id='pubtime_baidu']/text()").toString());
                page.putField("trader", page.getHtml().xpath("//span[@id='source_baidu']/a/text()").toString());
                page.putField("site","http://stock.stockstar.com");
            }
    }

    @Override
    public Site getSite() {
        return site;
    }

//    public static void main(String[] args) {
//        Spider.create(new StockStarReportProcessor())
//                .addUrl("http://stock.stockstar.com/list/3491.shtml")
//                .thread(5)
//                .addPipeline(new MongoDBPipeline())
//                .run();
//    }
}
