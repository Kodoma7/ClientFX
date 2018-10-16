package com.kodoma;

import com.kodoma.parser.SdpParser;
import org.testng.annotations.Test;

/**
 * SipParserTest class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public class SipParserTest {

    private static final String SDP = "v=0\n"
                                      + "o=alice 2890844526 2890844526 IN IP4 host.atlanta.example.com\n"
                                      + "s=\n"
                                      + "c=IN IP4 host.atlanta.example.com\n"
                                      + "t=0 0\n"
                                      + "m=audio 49170 RTP/AVP 0 8 97\n"
                                      + "a=rtpmap:0 PCMU/8000\n"
                                      + "a=rtpmap:8 PCMA/8000\n"
                                      + "a=rtpmap:97 iLBC/8000\n"
                                      + "m=video 51372 RTP/AVP 31 32\n"
                                      + "a=rtpmap:31 H261/90000\n"
                                      + "a=rtpmap:32 MPV/90000";

    @Test
    public void test() {
        System.out.println(SdpParser.parse(SDP));
    }
}
