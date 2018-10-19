package com.kodoma.parser.patterns;

import java.util.regex.Pattern;

/**
 * Patterns class.
 * Created on 15.10.2018.
 * @author Kodoma.
 */
public enum Patterns {

    USER_NAME_PATTERN(Pattern.compile("o=(.*?)\\s"), "user_name"),
    SESSION_ID_PATTERN(Pattern.compile("o=.*?\\s(.*?)\\s"), "session_id"),
    VERSION(Pattern.compile("o=.*?\\s.*?\\s(.*?)\\s"), "version"),
    NETWORK_TYPE_PATTERN(Pattern.compile("o=.*?\\s.*?\\s.*?\\s(.*?)\\s"), "network_type"),
    IP_TYPE_PATTERN(Pattern.compile("o=.*?\\s.*?\\s.*?\\s.*?\\s(.*?)\\s"), "ip_type"),
    USER_ADDRESS_PATTERN(Pattern.compile("o=.*?\\s.*?\\s.*?\\s.*?\\s.*?\\s(.*?)\\s"), "user_address"),

    AUDIO_PATTERN(Pattern.compile("m=audio\\s(.*)"), "audio"),
    VIDEO_PATTERN(Pattern.compile("m=video\\s(.*)"), "video"),
    RTP_PAYLOAD_TYPE_PATTERN(Pattern.compile("(a=rtpmap:)(.*)"), "rtp_payload_type"),

    PAYLOAD_TYPE_PATTERN(Pattern.compile("(.*?)\\s"), "payload_type"),
    ENCODING_NAME_PATTERN(Pattern.compile(".*?\\s(.*?)/\\d"), "encoding_name"),
    CLOCK_RATE_PATTERN(Pattern.compile(".*?\\s.*?/(.*?)$"), "clock_rate"),
    PORT_PATTERN(Pattern.compile("(.*?)\\s"), "port"),
    PROTOCOL_PATTERN(Pattern.compile(".*?\\s(.*?)\\s"), "protocol"),
    CODEC_NUMBERS_PATTERN(Pattern.compile(".*?\\s.*?\\s(.*)"), "codec_numbers"),

    CANDIDATE_PATTERN(Pattern.compile("(a=candidate:|a=x-candidate-ipv6:)(.*)"), "candidate"),
    FOUNDATION_PATTERN(Pattern.compile("(.*?)\\s"), "foundation"),
    COMPONENT_ID_PATTERN(Pattern.compile(".*?\\s(.*?)\\s"), "component_id"),
    CANDIDATE_PROTOCOL_PATTERN(Pattern.compile(".*?\\s.*?\\s(.*?)\\s"), "candidate_protocol"),
    PRIORITY_PATTERN(Pattern.compile(".*?\\s.*?\\s.*?\\s(.*?)\\s"), "priority"),
    CANDIDATE_IP_PATTERN(Pattern.compile(".*?\\s.*?\\s.*?\\s.*?\\s(.*?)\\s"), "candidate_ip"),
    CANDIDATE_PORT_PATTERN(Pattern.compile(".*?\\s.*?\\s.*?\\s.*?\\s.*?\\s(.*?)\\s"), "candidate_port"),
    CANDIDATE_TYPE_PATTERN(Pattern.compile(".*?\\s.*?\\s.*?\\s.*?\\s.*?\\s.*?\\s(.*?\\s.*?)($|\\s)"), "candidate_type"),
    CANDIDATE_IP_PORT_PATTERN(Pattern.compile("typ\\s.*?\\s(.*?\\s.*?\\s.*?\\s.*?)$"), "candidate_id_port"),

    ATTRIBUTE_A_PATTERN(Pattern.compile("a=(.*?):"), "attribute_a");

    private Pattern pattern;

    private String name;

    Patterns(Pattern pattern, String name) {
        this.pattern = pattern;
        this.name = name;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getName() {
        return name;
    }
}
