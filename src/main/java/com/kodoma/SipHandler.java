package com.kodoma;

import java.util.Arrays;

/**
 * Handler of a SIP messages.
 * Created on 30.09.2018.
 * @author Kodoma.
 */
public class SipHandler {

    private String userName;

    private String sessionId;

    private String versionNumber;

    private String networkType;

    private String ipType;

    private String userAddress;

    private String tagO;

    public static void main(String[] args) {
        final SipHandler handler = new SipHandler();
        final String sdp = "v=0\n"
                         + "o=alice 2890844526 2890844526 IN IP4 host.atlanta.example.com\n"
                         + "s=\n"
                         + "c=IN IP4 host.atlanta.example.com\n"
                         + "t=0 0\n"
                         + "m=audio 49170 RTP/AVP 97\n"
                         + "a=rtpmap:97 iLBC/8000\n"
                         + "m=video 51372 RTP/AVP 31\n"
                         + "a=rtpmap:31 H261/90000";

        handler.handle(sdp);
    }

    public void handle(final String sdp) {
        tagO = Arrays.stream(sdp.split("\n")).filter(s -> s.contains("o=")).findFirst().get();

        final String[] tagOSplit = tagO.split(" ");

        userName = tagOSplit[0].substring(2);
        sessionId = tagOSplit[1];
        versionNumber = tagOSplit[2];
        networkType = tagOSplit[3];
        ipType = tagOSplit[4];
        userAddress = tagOSplit[5];
    }

    @Override
    public String toString() {
        return "SipHandler{" +
               "userName='" + userName + '\'' +
               ", sessionId='" + sessionId + '\'' +
               ", versionNumber='" + versionNumber + '\'' +
               ", networkType='" + networkType + '\'' +
               ", ipType='" + ipType + '\'' +
               ", userAddress='" + userAddress + '\'' +
               ", tagO='" + tagO + '\'' +
               '}';
    }
}
