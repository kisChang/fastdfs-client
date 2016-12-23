package com.github.kischang.fastdfs;

/**
 * FastDFS 文件描述
 *
 * @author KisChang
 * @version 1.0
 */
public class FastDfsRv implements java.io.Serializable {

    private String groupName;
    private String accessName;

    public FastDfsRv() {
    }

    public FastDfsRv(String groupName, String accessName) {
        this.groupName = groupName;
        this.accessName = accessName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    @Override
    public String toString() {
        return "{" +
                "groupName='" + groupName + '\'' +
                ", accessName='" + accessName + '\'' +
                '}';
    }
}
