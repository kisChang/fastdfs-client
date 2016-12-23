package com.kischang.fastdfs.pool;

/**
 * Pool 连接池配置
 */
public class PoolConfig {
    public int maxIdle = 10;
    public int minIdle = 0;
    public int maxActive = 10;
    public long maxWait = -1L;
    public byte whenExhaustedAction = 1;
    public boolean testOnBorrow = false;
    public boolean testOnReturn = false;
    public boolean testWhileIdle = false;
    public long timeBetweenEvictionRunsMillis = -1L;
    public int numTestsPerEvictionRun = 3;
    public long minEvictableIdleTimeMillis = 1800000L;
    public long softMinEvictableIdleTimeMillis = -1L;
    public boolean lifo = true;

    public PoolConfig() {
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public void setWhenExhaustedAction(byte whenExhaustedAction) {
        this.whenExhaustedAction = whenExhaustedAction;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis) {
        this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    }

    public void setLifo(boolean lifo) {
        this.lifo = lifo;
    }
}