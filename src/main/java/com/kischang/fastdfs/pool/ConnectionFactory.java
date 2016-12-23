package com.kischang.fastdfs.pool;

import com.kischang.fastdfs.FastDFSTemplateFactory;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 * 链接创建
 *
 * @author KisChang
 * @version 1.0
 */
class ConnectionFactory extends BasePoolableObjectFactory<StorageClient> {

    private FastDFSTemplateFactory factory;

    public ConnectionFactory(FastDFSTemplateFactory templateFactory) {
        this.factory = templateFactory;
    }

    public StorageClient makeObject() throws Exception {
        TrackerClient trackerClient = new TrackerClient(factory.getG_tracker_group());
        TrackerServer trackerServer = trackerClient.getConnection();
        return new StorageClient(trackerServer, null);
    }

    public void destroyObject(StorageClient obj) throws Exception {
        close(obj.getTrackerServer());
    }

    public boolean validateObject(StorageClient obj) {
        try {
            Socket socket = obj.getTrackerServer().getSocket();
            if (!socket.isConnected()) {
                return false;
            }
            if (socket.isClosed()) {
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}