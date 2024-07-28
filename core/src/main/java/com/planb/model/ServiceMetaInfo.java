package com.planb.model;


import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * @author PlanB
 * 服务元信息（注册信息）
 */
@Data
public class ServiceMetaInfo {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务版本号
     */
    private String serverVersion;

    /**
     * 服务主机
     */
    private String serverHost;

    /**
     * 服务端口号
     */
    private Integer serverPort;

    /**
     * 服务分组
     */
    private String serviceGroup = "default";

    /**
     * 获取服务键名
     *
     * @return
     */
    public String getServiceKey() {
        return String.format("%s:%s", serviceName, serverVersion);
    }

    /**
     * 获取服务注册节点键名
     *
     * @return
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serverHost, serverPort);
    }

    /**
     * 获取完整服务地址
     *
     * @return 服务地址
     */
    public String getServerAddress() {
        if (!StrUtil.contains(serverHost, "http")) {
            return String.format("http://%s:%s", serverHost, serverPort);
        }
        return String.format("%s/%s", serverHost, serverPort);
    }
}
