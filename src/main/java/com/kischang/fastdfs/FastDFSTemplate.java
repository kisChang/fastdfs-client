package com.kischang.fastdfs;

import com.kischang.fastdfs.exception.FastDFSException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.IOException;
import java.util.Map;

/**
 * FastDFS 模板类
 *
 * @author KisChang
 * @version 1.0
 */
public class FastDFSTemplate {

    private StorageClient storageClient;
    private TrackerClient trackerClient;
    private TrackerServer trackerServer;
    private StorageServer storageServer;

    public FastDFSTemplate(FastDFSTemplateFactory factory) {
        try {
            trackerClient = new TrackerClient(factory.getG_tracker_group());
            trackerServer = trackerClient.getConnection();
            storageServer = null;
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public FastDfsRv upload(byte[] data, String ext) throws FastDFSException {
        return this.upload(data, ext, null);
    }

    public FastDfsRv upload(byte[] data, String ext, Map<String, String> values) throws FastDFSException {
        NameValuePair[] valuePairs = null;
        if (values != null && !values.isEmpty()){
            valuePairs = new NameValuePair[values.size()];
            int index = 0;
            for(Map.Entry<String, String> entry : values.entrySet()){
                valuePairs[index] = new NameValuePair(entry.getKey(), entry.getValue());
                index ++;
            }
        }
        StorageClient client = getClient();
        try {
            String[] uploadResults = client.upload_file(data, ext, valuePairs);
            String groupName = uploadResults[0];
            String remoteFileName = uploadResults[1];
            return new FastDfsRv(groupName, remoteFileName);
        } catch (Exception e) {
            throw new FastDFSException(e.getMessage(), e, 0);
        } finally {
            releaseClient(client);
        }
    }


    public byte[] loadFile(FastDfsRv dfs) throws FastDFSException {
        return this.loadFile(dfs.getGroupName(), dfs.getAccessName());
    }

    public byte[] loadFile(String groupName, String remoteFileName) throws FastDFSException {
        StorageClient client = getClient();
        try {
            return client.download_file(groupName, remoteFileName);
        } catch (Exception e) {
            throw new FastDFSException(e.getMessage(), e, 0);
        } finally {
            releaseClient(client);
        }
    }

    public void deleteFile(FastDfsRv dfs) throws FastDFSException {
        this.deleteFile(dfs.getGroupName(), dfs.getAccessName());
    }

    public void deleteFile(String groupName, String remoteFileName) throws FastDFSException {
        int code;
        StorageClient client = getClient();
        try {
            code = client.delete_file(groupName, remoteFileName);
        } catch (Exception e) {
            throw new FastDFSException(e.getMessage(), e, 0);
        } finally {
            releaseClient(client);
        }
        if (code != 0){
            throw new FastDFSException(code);
        }
    }



    private StorageClient getClient() {
        return storageClient;
    }

    private void releaseClient(StorageClient client) {
    }

}
