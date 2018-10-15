package com.kodoma.parser;

import com.google.common.collect.Lists;
import com.kodoma.parser.values.MediaValue;
import com.kodoma.parser.values.RtpCodec;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kodoma.parser.util.Util.getAttribute;
import static com.kodoma.parser.util.Util.getAttributes;

/**
 * SdpParser class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public class SdpParser {
    private static final Logger LOG = Logger.getLogger(SdpParser.class);

    private static final Pattern USER_NAME_PATTERN = Pattern.compile("o=(.*?)\\s");
    private static final Pattern SESSION_ID_PATTERN = Pattern.compile("o=.*?\\s(.*?)\\s");
    private static final Pattern SDP_SESSION_TIMES_PATTERN = Pattern.compile("o=.*?\\s.*?\\s(.*?)\\s");
    private static final Pattern NETWORK_TYPE_PATTERN = Pattern.compile("o=.*?\\s.*?\\s.*?\\s(.*?)\\s");
    private static final Pattern IP_TYPE_PATTERN = Pattern.compile("o=.*?\\s.*?\\s.*?\\s.*?\\s(.*?)\\s");
    private static final Pattern USER_ADDRESS_PATTERN = Pattern.compile("o=.*?\\s.*?\\s.*?\\s.*?\\s.*?\\s(.*?)\\s");

    private static final Pattern M_AUDIO_PATTERN = Pattern.compile("m=audio\\s(.*)");

    private static final Pattern RTP_PAYLOAD_TYPE_PATTERN = Pattern.compile("(a=rtpmap:)(.*)");

    public static SdpEntity parse(final String sdp) {
        return new SdpEntity().setUserName(getAttribute(sdp, USER_NAME_PATTERN))
                              .setSessionId(getAttribute(sdp, SESSION_ID_PATTERN))
                              .setSdpSessionTimes(getAttribute(sdp, SDP_SESSION_TIMES_PATTERN))
                              .setNetworkType(getAttribute(sdp, NETWORK_TYPE_PATTERN))
                              .setIpType(getAttribute(sdp, IP_TYPE_PATTERN))
                              .setUserAddress(getAttribute(sdp, USER_ADDRESS_PATTERN))
                              .setmAudio(getAttribute(sdp, M_AUDIO_PATTERN, new MediaValue("audio")))
                              .setAudioRtpMap(getAttributes(sdp, RTP_PAYLOAD_TYPE_PATTERN, new RtpCodec()));
    }
}
