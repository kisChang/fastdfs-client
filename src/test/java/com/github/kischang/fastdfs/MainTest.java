package com.github.kischang.fastdfs;

import com.github.kischang.fastdfs.exception.FastDFSException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 连接池测试
 *
 * @author KisChang
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-fastdfs.xml")
public class MainTest {

    @Resource
    private FastDFSTemplate dfsTemplate;
    @Resource
    private FastDFSTemplateFactory factory;

    @Test
    public void testUploadAndDel() throws FastDFSException {
        System.out.println(factory.getG_tracker_group());

        FastDfsRv rv = dfsTemplate.upload("".getBytes(), "txt");
        System.out.println(rv);
        dfsTemplate.deleteFile(rv);
    }

    @Test
    public void testPool() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    FastDfsRv rv = dfsTemplate.upload("123123213".getBytes(), "txt");
                    System.out.println(rv);
                    dfsTemplate.deleteFile(rv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 100; i++) {
            new Thread(runnable).start();
        }
        Thread.currentThread().join();
    }

}
