package com.kodoma.parser.values;

import java.util.regex.Pattern;

import static com.kodoma.parser.util.Util.getAttribute;

/**
 * RtpCodec class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public class RtpCodec extends SdpValue {

    private static final Pattern ENCODING_NAME_PATTERN = Pattern.compile(".*?\\s(.*?)/\\d");
    private static final Pattern CLOCK_RATE_PATTERN = Pattern.compile(".*?\\s.*?/(.*?)$");

    private String payloadType;

    private String encodingName;

    private String clockRate;

    @Override
    public SdpValue fill(final String raw) {
        this.payloadType = getAttribute(raw, FIRST_VALUE_PATTERN);
        this.encodingName = getAttribute(raw, ENCODING_NAME_PATTERN);
        this.clockRate = getAttribute(raw, CLOCK_RATE_PATTERN);
        return this;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public RtpCodec setPayloadType(String payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    public String getEncodingName() {
        return encodingName;
    }

    public RtpCodec setEncodingName(String encodingName) {
        this.encodingName = encodingName;
        return this;
    }

    public String getClockRate() {
        return clockRate;
    }

    public RtpCodec setClockRate(String clockRate) {
        this.clockRate = clockRate;
        return this;
    }
}
