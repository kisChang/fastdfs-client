package com.kischang.fastdfs;

import org.csource.fastdfs.StorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

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

    @Test
    public void testMain() throws InterruptedException {
        final Set<Integer> set = new HashSet<>();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                StorageClient client = dfsTemplate.getClient();
                System.out.println((set.contains(client.hashCode()) ? "OLD": "NEW") + " >>> " + Thread.currentThread().getName() + ">>>" + client.hashCode());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) { }
                set.add(client.hashCode());
                dfsTemplate.releaseClient(client);
            }
        };

        for (int i = 0; i < 20; i++) {
            new Thread(runnable).start();
            Thread.sleep(500);
        }
        Thread.currentThread().join();
    }

}
