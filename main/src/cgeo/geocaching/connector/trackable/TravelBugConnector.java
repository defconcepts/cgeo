package cgeo.geocaching.connector.trackable;

import cgeo.geocaching.Trackable;
import cgeo.geocaching.connector.gc.GCParser;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;

import java.util.regex.Pattern;

public class TravelBugConnector extends AbstractTrackableConnector {

    /**
     * TB codes really start with TB1, there is no padding or minimum length
     */
    private final static Pattern PATTERN_TB_CODE = Pattern.compile("(TB[0-9A-Z]+)|([0-9A-Z]{6})", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean canHandleTrackable(String geocode) {
        return TravelBugConnector.PATTERN_TB_CODE.matcher(geocode).matches();
    }

    @Override
    public String getUrl(Trackable trackable) {
        return "http://www.geocaching.com//track/details.aspx?tracker=" + trackable.getGeocode();
    }

    @Override
    public boolean isLoggable() {
        return true;
    }

    @Override
    public Trackable searchTrackable(String geocode, String guid, String id) {
        return GCParser.searchTrackable(geocode, guid, id);
    }

    /**
     * initialization on demand holder pattern
     */
    private static class Holder {
        private static final TravelBugConnector INSTANCE = new TravelBugConnector();
    }

    private TravelBugConnector() {
        // singleton
    }

    public static TravelBugConnector getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String getTrackableCodeFromUrl(@NonNull String url) {
        return StringUtils.substringAfterLast(url, "?tracker=");
    }
}