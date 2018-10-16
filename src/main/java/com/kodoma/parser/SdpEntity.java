package com.kodoma.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kodoma.parser.util.Util;
import com.kodoma.parser.values.MediaValue;
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

    private String version;

    private String networkType;

    private String ipType;

    private String userAddress;

    private MediaValue audio;

    private MediaValue video;

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

    public String getVersion() {
        return version;
    }

    public SdpEntity setVersion(String version) {
        this.version = version;
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

    public MediaValue getAudio() {
        return audio;
    }

    public SdpEntity setAudio(MediaValue audio) {
        this.audio = audio;
        return this;
    }

    public SdpEntity setAudioRtpMap(List<RtpCodec> rtpMap) {
        this.audio.setRtpMap(rtpMap);
        return this;
    }

    public MediaValue getVideo() {
        return video;
    }

    public SdpEntity setVideo(MediaValue video) {
        this.video = video;
        return this;
    }

    public SdpEntity setVideoRtpMap(List<RtpCodec> rtpMap) {
        this.video.setRtpMap(rtpMap);
        return this;
    }

    @Override
    public String toString() {
        try {
            return Util.MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
