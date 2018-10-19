package com.kodoma.parser.values;

import com.kodoma.parser.patterns.Patterns;
import com.kodoma.parser.util.Util;

import static com.kodoma.parser.util.Util.getAttribute;

/**
 * Candidate class.
 * Created on 16.10.2018.
 * @author Kodoma.
 */
public class Candidate extends SdpValue {

    private static final String HOST_TYPE = "typ host";

    private String name;

    private String foundation;

    private String componentId;

    private String protocol;

    private String priority;

    private String ip;

    private String port;

    /**
     * Тип адреса.
     * Может быть 3 видов: host, srflx и relay,
     * host содержат информацию, полученную локально,
     * srflx — то, как узел выглядит для внешнего сервера (STUN),
     * relay — информация для проксирования трафика через TURN-сервер.
     */
    private String type;

    private String ipPort;

    @Override
    public SdpValue fill(final String raw, final String attributeName) {
        this.name = Util.getAttribute(attributeName, Patterns.ATTRIBUTE_A_PATTERN);
        this.foundation = getAttribute(raw, Patterns.FOUNDATION_PATTERN);
        this.componentId = getAttribute(raw, Patterns.COMPONENT_ID_PATTERN);
        this.protocol = getAttribute(raw, Patterns.CANDIDATE_PROTOCOL_PATTERN);
        this.priority = getAttribute(raw, Patterns.PRIORITY_PATTERN);
        this.ip = getAttribute(raw, Patterns.CANDIDATE_IP_PATTERN);
        this.port = getAttribute(raw, Patterns.CANDIDATE_PORT_PATTERN);
        this.type = getAttribute(raw, Patterns.CANDIDATE_TYPE_PATTERN);
        if (!HOST_TYPE.equals(type)) {
            this.ipPort = getAttribute(raw, Patterns.CANDIDATE_IP_PORT_PATTERN);
        }
        return this;
    }

    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
