
package com.zhaijiong.crawler.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by xuqi86@gmail.com
 * report代表一份研报实体对象
 */

@Document(indexName = "report",type = "report")
public class Report {

    /*
     * 文章的唯一标识，由md5(url)生成
     */
    @Id
    private String id;

    /*
     * 研报的网址
     */
    private String url;

    /*
     * 研究员，多个研究员使用中文逗号分隔
     */
    private String author;

    /*
     * 研报标题
     */
    private String title;

    /*
     * 研报正文内容，如果是网页则content存储网页正文
     */
    private String content;

    /*
     * 附件地址
     */
    private String attachment;

    /*
     * type代表研报的类型ID，包括：宏观经济/投资策略/行业分析/公司调研/晨会早报
     */
    private String type;

    /*
     * 研报发布时间
     */
    private String date;

    /*
     * 券商公司ID
     */
    private String trader;

    /*
     * 抓取的网站来源
     */
    private String site;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (!url.equals(report.url)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", attachment='" + attachment + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", trader='" + trader + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
