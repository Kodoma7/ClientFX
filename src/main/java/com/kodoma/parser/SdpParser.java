package com.kodoma.parser;

import com.kodoma.parser.pattern.Patterns;
import com.kodoma.parser.values.MediaValue;
import com.kodoma.parser.values.RtpCodec;

import static com.kodoma.parser.util.Util.getAttribute;
import static com.kodoma.parser.util.Util.getAttributes;

/**
 * SdpParser class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public class SdpParser {

    public static SdpEntity parse(final String sdp) {
        return new SdpEntity().setUserName(getAttribute(sdp, Patterns.USER_NAME_PATTERN))
                              .setSessionId(getAttribute(sdp, Patterns.SESSION_ID_PATTERN))
                              .setVersion(getAttribute(sdp, Patterns.VERSION))
                              .setNetworkType(getAttribute(sdp, Patterns.NETWORK_TYPE_PATTERN))
                              .setIpType(getAttribute(sdp, Patterns.IP_TYPE_PATTERN))
                              .setUserAddress(getAttribute(sdp, Patterns.USER_ADDRESS_PATTERN))
                              .setAudio(getAttribute(sdp, Patterns.AUDIO_PATTERN, new MediaValue("audio")))
                              .setAudioRtpMap(getAttributes(sdp, Patterns.RTP_PAYLOAD_TYPE_PATTERN, new RtpCodec()))
                              .setVideo(getAttribute(sdp, Patterns.VIDEO_PATTERN, new MediaValue("video")))
                              .setVideoRtpMap(getAttributes(sdp, Patterns.RTP_PAYLOAD_TYPE_PATTERN, new RtpCodec()));
    }
}
