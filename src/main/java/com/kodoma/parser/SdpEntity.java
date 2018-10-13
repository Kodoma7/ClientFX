package com.kodoma.parser;

import com.kodoma.parser.values.RtpCodec;

import java.util.List;

/**
 * Handler of SDP body.
 * Created on 30.09.2018.
 * @author Kodoma.
 */
public class SdpEntity {

    private String userName;

    private String sessionId;

    private String sdpSessionTimes;

    private String networkType;

    private String ipType;

    private String userAddress;

    private String mediaType;

    private String mediaPort;

    private String mediaProtocol;

    private String fmtList;

    private List<RtpCodec> rtpMap;

    public String getUserName() {
        return userName;
    }

    public SdpEntity setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    public SdpEntity setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getSdpSessionTimes() {
        return sdpSessionTimes;
    }

    public SdpEntity setSdpSessionTimes(String sdpSessionTimes) {
        this.sdpSessionTimes = sdpSessionTimes;
        return this;
    }

    public String getNetworkType() {
        return networkType;
    }

    public SdpEntity setNetworkType(String networkType) {
        this.networkType = networkType;
        return this;
    }

    public String getIpType() {
        return ipType;
    }

    public SdpEntity setIpType(String ipType) {
        this.ipType = ipType;
        return this;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public SdpEntity setUserAddress(String userAddress) {
        this.userAddress = userAddress;
        return this;
    }

    public String getMediaType() {
        return mediaType;
    }

    public SdpEntity setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public String getMediaPort() {
        return mediaPort;
    }

    public SdpEntity setMediaPort(String mediaPort) {
        this.mediaPort = mediaPort;
        return this;
    }

    public String getMediaProtocol() {
        return mediaProtocol;
    }

    public SdpEntity setMediaProtocol(String mediaProtocol) {
        this.mediaProtocol = mediaProtocol;
        return this;
    }

    public String getFmtList() {
        return fmtList;
    }

    public SdpEntity setFmtList(String fmtList) {
        this.fmtList = fmtList;
        return this;
    }

    public List<RtpCodec> getRtpMap() {
        return rtpMap;
    }

    public SdpEntity setRtpMap(List<RtpCodec> rtpMap) {
        this.rtpMap = rtpMap;
        return this;
    }

    @Override
    public String toString() {
        return "SdpEntity{" +
               "userName='" + userName + '\'' +
               ", sessionId='" + sessionId + '\'' +
               ", sdpSessionTimes='" + sdpSessionTimes + '\'' +
               ", networkType='" + networkType + '\'' +
               ", ipType='" + ipType + '\'' +
               ", userAddress='" + userAddress + '\'' +
               ", mediaType='" + mediaType + '\'' +
               ", mediaPort='" + mediaPort + '\'' +
               ", mediaProtocol='" + mediaProtocol + '\'' +
               ", fmtList='" + fmtList + '\'' +
               ", rtpMap=" + rtpMap +
               '}';
    }
}
