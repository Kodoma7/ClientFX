package com.kodoma.parser.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.Lists;
import com.kodoma.parser.values.SdpValue;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class.
 * Created on 13.10.2018.
 * @author Kodoma.
 */
public class Util {

    private static final Logger LOG = Logger.getLogger(Util.class);
    public static final ObjectWriter MAPPER = new ObjectMapper().writerWithDefaultPrettyPrinter();

    public static String getAttribute(final String sdp, final Pattern p) {
        final Matcher m = p.matcher(sdp);
        Exception exception = null;

        if (m.find()) {
            try {
                return m.group(1);
            } catch (IllegalStateException | IndexOutOfBoundsException e) {
                exception = e;
            }
        }
        LOG.error("Cannot find attribute " + (exception != null ? exception : ""));
        return null;
    }

    public static <V> V getAttribute(final String sdp, final Pattern p, final SdpValue value) {
        final Matcher m = p.matcher(sdp);
        Exception exception = null;

        if (m.find()) {
            try {
                return (V)value.fill(m.group(1));
            } catch (IllegalStateException | IndexOutOfBoundsException e) {
                exception = e;
            }
        }
        LOG.error("Cannot find attribute " + (exception != null ? exception : ""));
        return null;
    }

    public static <V> List<V> getAttributes(final String sdp, final Pattern p, final SdpValue value) {
        final Matcher m = p.matcher(sdp);
        final List<SdpValue> values = Lists.newArrayList();

        while (m.find()) {
            try {
                values.add(value.clone().fill(m.group(2)));
            } catch (IllegalStateException | IndexOutOfBoundsException | CloneNotSupportedException e) {
                LOG.error("Cannot find attribute " + e);
            }
        }
        return (List<V>)values;
    }

    public static List<String> getAttributes(final String sdp, final Pattern p) {
        return Arrays.asList(Objects.requireNonNull(getAttribute(sdp, p)).split(" "));
    }
}
