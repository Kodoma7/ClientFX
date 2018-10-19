package com.kodoma;

import com.kodoma.parser.SdpParser;
import org.testng.annotations.Test;

import javax.sdp.SdpException;

/**
 * SipParserTest class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public class SipParserTest {

    private static final String SDP = "v=0\n"
                                      + "o=- 0 0 IN IP4 192.168.106.63\n"
                                      + "s=session\n"
                                      + "c=IN IP4 192.168.106.63\n"
                                      + "b=CT:99980\n"
                                      + "t=0 0\n"
                                      + "a=x-devicecaps:audio:send,recv;video:send,recv\n"
                                      + "m=audio 10406 RTP/AVP 104 114 9 112 111 0 8 103 116 115 97 13 118 119 101\n"
                                      + "a=x-ssrc-range:362803970-362803970\n"
                                      + "a=rtcp-fb:*\n"
                                      + " x-message app send:dsh recv:dsh\n"
                                      + "a=rtcp-rsize\n"
                                      + "a=label:main-audio\n"
                                      + "a=x-source:main-audio\n"
                                      + "a=ice-ufrag:/4N5\n"
                                      + "a=ice-pwd:+3l2Yir4ryApcueo0Fqq4gex\n"
                                      + "a=candidate:1 1 UDP 2130706431 192.168.106.63 10406 typ host\n"
                                      + "a=candidate:1 2 UDP 2130705918 192.168.106.63 10407 typ host\n"
                                      + "a=x-candidate-ipv6:2 1 UDP 2130705919 2001:67c:2344:106:e415:e4d7:dfb1:a540 26920 typ host\n"
                                      + "a=x-candidate-ipv6:2 2 UDP 2130705406 2001:67c:2344:106:e415:e4d7:dfb1:a540 26921 typ host\n"
                                      + "a=candidate:3 1 TCP-ACT 1684798463 192.168.106.63 10406 typ srflx raddr 192.168.106.63 rport 10406\n"
                                      + "a=candidate:3 2 TCP-ACT 1684797950 192.168.106.63 10406 typ srflx raddr 192.168.106.63 rport 10406\n"
                                      + "a=maxptime:200\n"
                                      + "a=rtcp:10407\n"
                                      + "a=rtpmap:104 SILK/16000\n"
                                      + "a=fmtp:104 useinbandfec=1; usedtx=0\n"
                                      + "a=rtpmap:114 x-msrta/16000\n"
                                      + "a=fmtp:114 bitrate=29000\n"
                                      + "a=rtpmap:9 G722/8000\n"
                                      + "a=rtpmap:112 G7221/16000\n"
                                      + "a=fmtp:112 bitrate=24000\n"
                                      + "a=rtpmap:111 SIREN/16000\n"
                                      + "a=fmtp:111 bitrate=16000\n"
                                      + "a=rtpmap:0 PCMU/8000\n"
                                      + "a=rtpmap:8 PCMA/8000\n"
                                      + "a=rtpmap:103 SILK/8000\n"
                                      + "a=fmtp:103 useinbandfec=1; usedtx=0\n"
                                      + "a=rtpmap:116 AAL2-G726-32/8000\n"
                                      + "a=rtpmap:115 x-msrta/8000\n"
                                      + "a=fmtp:115 bitrate=11800\n"
                                      + "a=rtpmap:97 RED/8000\n"
                                      + "a=rtpmap:13 CN/8000\n"
                                      + "a=rtpmap:118 CN/16000\n"
                                      + "a=rtpmap:119 CN/24000\n"
                                      + "a=rtpmap:101 telephone-event/8000\n"
                                      + "a=fmtp:101 0-16\n"
                                      + "a=rtcp-mux\n"
                                      + "a=x-bypassid:be38f190-a24a-4a72-b66c-b09fd0909ebc\n"
                                      + "a=ptime:20\n"
                                      + "a=extmap:1 http:\\\\www.webrtc.org\\experiments\\rtp-hdrext\\abs-send-time\n"
                                      + "a=extmap:3 http:\\\\skype.com\\experiments\\rtp-hdrext\\fast_bandwidth_feedback\n"
                                      + "a=x-bwealgorithm:bwc packetpair";

    @Test
    public void test() throws SdpException {
        System.out.println(SdpParser.parse(SDP));
    }
}
