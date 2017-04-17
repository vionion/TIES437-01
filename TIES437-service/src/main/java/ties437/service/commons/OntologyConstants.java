package ties437.service.commons;

/**
 * Created by chinhnk on 4/4/2017.
 */
public class OntologyConstants {
    public static final String PREFIX_CB = "http://users.jyu.fi/~chnguyen/TIES437/cottage-booking.owl#";
    public static final String PREFIX_CBD = "http://localhost:8080/resources/rdf/cottage-booking-data.rdf#";

    // Property URIs
    public static final String PROP_HAS_BED_ROOM = PREFIX_CB + "hasBedroom";
    public static final String PROP_HAS_DISTANCE_TO_LAKE = PREFIX_CB + "hasDistanceToLake";
    public static final String PROP_HAS_IMAGE_URL = PREFIX_CB + "hasImageURL";
    public static final String PROP_HAS_LATITUDE = PREFIX_CB + "hasLatitude";
    public static final String PROP_HAS_LONGITUDE = PREFIX_CB + "hasLongitude";
    public static final String PROP_HAS_NAME = PREFIX_CB + "hasName";

    public static final String PROP_HAS_PLACE_COUNT = PREFIX_CB + "hasPlaceCount";

    public static final String PROP_HAS_BOOKING = PREFIX_CB + "hasBooking";
    public static final String PROP_HAS_BOOKER_NAME = PREFIX_CB + "hasBookerName";
    public static final String PROP_HAS_END_DATE = PREFIX_CB + "hasEndDate";
    public static final String PROP_HAS_START_DATE = PREFIX_CB + "hasStartDate";
}
