package com.zhaijiong.crawler.scheduler;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.zhaijiong.crawler.dao.ReportTemplate;
import com.zhaijiong.crawler.utils.Constants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Created by xuqi.xq on 2014/8/12.
 */
public class RedisDuplicateRemover implements DuplicateRemover {
    private JedisPool pool;
    private ReportTemplate template;

    private static final String HASH_PREFIX = "h_";

    private int expectedInsertions;
    private double fpp = 0.01;
    private final BloomFilter<CharSequence> bloomFilter;

    private AtomicInteger counter;

    public RedisDuplicateRemover(ReportTemplate template,JedisPool pool,int expectedInsertions) {
        this.template = template;
        this.pool = pool;
        this.expectedInsertions = expectedInsertions;
        this.bloomFilter = rebuildBloomFilter();
    }

    @Override
    public boolean isDuplicate(Request request, Task task) {
        Jedis jedis = pool.getResource();
        try {
            boolean isDuplicate = jedis.hexists(getHashKey(task), request.getUrl());
            //如果redis中不存在
            if (!isDuplicate) {
                //检查是否是post规则的url
                if(Pattern.matches(template.getPostPageUrlRule(),request.getUrl())){
                    //如果是，则加入redis，以后不再抓取
                    jedis.hset(getHashKey(task), request.getUrl(),"");
                }else{
                    //如果不是，则判断是是否已经加入到bloomfilter中
                    isDuplicate = bloomFilter.mightContain(request.getUrl());
                    if(!isDuplicate){
                        //如果bloomfilter不存在，则加入其中
                        bloomFilter.put(request.getUrl());

                    }
                }
                counter.incrementAndGet();
            }
            return isDuplicate;
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void resetDuplicateCheck(Task task) {
        rebuildBloomFilter();
    }

    protected String getHashKey(Task task) {
        return Constants.PROJECT + HASH_PREFIX + task.getSite().getDomain();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        Jedis jedis = pool.getResource();
        try {
            Long size = jedis.hlen(getHashKey(task));
            return size.intValue();
        } finally {
            pool.returnResource(jedis);
        }
    }

    protected BloomFilter<CharSequence> rebuildBloomFilter() {
        counter = new AtomicInteger(0);
        return BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), expectedInsertions, fpp);
    }
}
