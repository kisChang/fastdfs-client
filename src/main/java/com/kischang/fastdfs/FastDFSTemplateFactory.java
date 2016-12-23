package com.kischang.fastdfs;

import com.kischang.fastdfs.exception.FastDFSException;
import com.kischang.fastdfs.pool.PoolConfig;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerGroup;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * FastDFS 初始化
 *
 * @author KisChang
 * @version 1.0
 */
public class FastDFSTemplateFactory {

    //connect_timeout
    private int g_connect_timeout;
    //network_timeout
    private int g_network_timeout;

    //charset
    private String g_charset;

    //http.tracker_http_port
    private int g_tracker_http_port;
    //http.anti_steal_token
    private boolean g_anti_steal_token;
    //http.secret_key
    private String g_secret_key;

    private List<String> tracker_servers;

    private TrackerGroup g_tracker_group;

    private PoolConfig poolConfig = new PoolConfig();

    public void init() throws Exception {

        if (g_connect_timeout <= 0) {
            g_connect_timeout = ClientGlobal.DEFAULT_CONNECT_TIMEOUT;
        }

        if (g_network_timeout <= 0) {
            g_network_timeout = ClientGlobal.DEFAULT_NETWORK_TIMEOUT;
        }
        g_connect_timeout *= 1000; //millisecond
        g_network_timeout *= 1000; //millisecond

        if (g_charset == null || g_charset.length() == 0) {
            g_charset = "UTF-8";
        }

        if (g_tracker_http_port <=0 ){
            g_tracker_http_port = 80;
        }

        if (tracker_servers == null || tracker_servers.isEmpty()) {
            throw new FastDFSException("item \"tracker_server\"  not found", -1);
        }

        InetSocketAddress[] tracker_servers_socket = new InetSocketAddress[tracker_servers.size()];
        for (int i = 0; i < tracker_servers.size(); i++) {
            String str = tracker_servers.get(i);
            String[] parts = str.split("\\:", 2);
            if (parts.length != 2) {
                throw new FastDFSException("the value of item \"tracker_server\" is invalid, the correct format is host:port", -2);
            }

            tracker_servers_socket[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }
        g_tracker_group = new TrackerGroup(tracker_servers_socket);

        if (g_anti_steal_token) {
            if (g_secret_key == null || "".equals(g_secret_key)) {
                throw new FastDFSException("item \"secret_key\"  not found", -2);
            }
        }
        setToGlobal();
    }

    private void setToGlobal() {
        ClientGlobal.setG_connect_timeout(this.g_connect_timeout);
        ClientGlobal.setG_network_timeout(this.g_network_timeout);
        ClientGlobal.setG_charset(this.g_charset);
        ClientGlobal.setG_tracker_http_port(this.g_tracker_http_port);
        ClientGlobal.setG_anti_steal_token(this.g_anti_steal_token);
        ClientGlobal.setG_secret_key(this.g_secret_key);
        ClientGlobal.setG_tracker_group(this.g_tracker_group);
    }

    public PoolConfig getPoolConfig() {
        if (poolConfig == null){
            return new PoolConfig();
        }
        return poolConfig;
    }

    public void setPoolConfig(PoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public void setG_connect_timeout(int g_connect_timeout) {
        this.g_connect_timeout = g_connect_timeout;
    }

    public void setG_network_timeout(int g_network_timeout) {
        this.g_network_timeout = g_network_timeout;
    }

    public void setG_charset(String g_charset) {
        this.g_charset = g_charset;
    }

    public void setG_tracker_http_port(int g_tracker_http_port) {
        this.g_tracker_http_port = g_tracker_http_port;
    }

    public void setG_anti_steal_token(boolean g_anti_steal_token) {
        this.g_anti_steal_token = g_anti_steal_token;
    }

    public void setG_secret_key(String g_secret_key) {
        this.g_secret_key = g_secret_key;
    }

    public void setTracker_servers(List<String> tracker_servers) {
        this.tracker_servers = tracker_servers;
    }

    public void setG_tracker_group(TrackerGroup g_tracker_group) {
        this.g_tracker_group = g_tracker_group;
    }

    public TrackerGroup getG_tracker_group() {
        return g_tracker_group;
    }
}
