package com.kodoma.parser.values;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.kodoma.parser.util.Util.getAttribute;
import static com.kodoma.parser.util.Util.getAttributes;

/**
 * MediaValue class.
 * Created on 15.10.2018.
 * @author Kodoma.
 */
public class MediaValue extends SdpValue {

    private static final Pattern CODEC_NUMBERS_PATTERN = Pattern.compile(".*?\\s.*?\\s(.*)");

    private String type;

    private String port;

    private String protocol;

    private List<String> codecNumbers;

    private List<RtpCodec> rtpMap;

    public MediaValue(String type) {
        this.type = type;
    }

    @Override
    public SdpValue fill(String raw) {
        this.port = getAttribute(raw, FIRST_VALUE_PATTERN);
        this.protocol = getAttribute(raw, SECOND_VALUE_PATTERN);
        this.codecNumbers = getAttributes(raw, CODEC_NUMBERS_PATTERN);
        return this;
    }

    public String getType() {
        return type;
    }

    public MediaValue setType(String type) {
        this.type = type;
        return this;
    }

    public String getPort() {
        return port;
    }

    public MediaValue setPort(String port) {
        this.port = port;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public MediaValue setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public List<String> getCodecNumbers() {
        return codecNumbers;
    }

    public MediaValue setCodecNumbers(List<String> codecNumbers) {
        this.codecNumbers = codecNumbers;
        return this;
    }

    public List<RtpCodec> getRtpMap() {
        return rtpMap;
    }

    public MediaValue setRtpMap(List<RtpCodec> rtpMap) {
        this.rtpMap = getRelevantRtpMap(rtpMap);
        return this;
    }

    private List<RtpCodec> getRelevantRtpMap(final List<RtpCodec> rtpMap) {
        return rtpMap.stream().filter(v -> codecNumbers.contains(v.getPayloadType())).collect(Collectors.toList());
    }
}
