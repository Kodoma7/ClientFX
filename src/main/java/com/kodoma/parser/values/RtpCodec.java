package com.kodoma.parser.values;

import com.kodoma.parser.pattern.Patterns;

import static com.kodoma.parser.util.Util.getAttribute;

/**
 * RtpCodec class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public class RtpCodec extends SdpValue {

    private String payloadType;

    private String encodingName;

    private String clockRate;

    @Override
    public SdpValue fill(final String raw) {
        this.payloadType = getAttribute(raw, Patterns.PAYLOAD_TYPE_PATTERN);
        this.encodingName = getAttribute(raw, Patterns.ENCODING_NAME_PATTERN);
        this.clockRate = getAttribute(raw, Patterns.CLOCK_RATE_PATTERN);
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
