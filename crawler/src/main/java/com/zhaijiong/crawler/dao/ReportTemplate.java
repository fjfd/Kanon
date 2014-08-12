package com.zhaijiong.crawler.dao;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.zhaijiong.crawler.utils.Constants;
import com.zhaijiong.crawler.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xuqi.xq on 2014/8/11.
 */
public class ReportTemplate {
    private final Report report;
    /*
     * 起始地址
     */
    private final String seedUrl;
    /*
     * 正文页的url正则表达式
     */
    private final String postPageUrlRule;
    /*
     * 列表页的url正则表达式
     */
    private final String listPageUrlRule;

    public static ReportTemplate build(String path){
        Properties prop = new Properties();
        try {
            BufferedReader bufferedReader = Files.newReader(new File(path), Charset.forName("utf-8"));
            prop.load(bufferedReader);
            Report report = new Report();
            Map<String,Object> kvs = Maps.newHashMap();
            for(Map.Entry<Object, Object> kv : prop.entrySet()){
                kvs.put((String)kv.getKey(),kv.getValue());
            }
            Utils.transMap2Bean(kvs,report);

            return new ReportTemplate(report,
                    prop.getProperty(Constants.SEED_URL),
                    prop.getProperty(Constants.POST_PAGE_URL_RULE),
                    prop.getProperty(Constants.LIST_PAGE_URL_RULE));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ReportTemplate(Report report, String seedUrl, String postPageUrlRule, String listPageUrlRule) {
        this.report = report;
        this.seedUrl = seedUrl;
        this.postPageUrlRule = postPageUrlRule;
        this.listPageUrlRule = listPageUrlRule;
    }

    public Report getReport() {
        return report;
    }

    public String getSeedUrl() {
        return seedUrl;
    }

    public String getPostPageUrlRule() {
        return postPageUrlRule;
    }

    public String getListPageUrlRule() {
        return listPageUrlRule;
    }

}
