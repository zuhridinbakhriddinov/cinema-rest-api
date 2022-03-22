package uz.pdp.appcinemarest.projection;

import java.time.LocalTime;
import java.util.UUID;

public interface SessionTimeProjection {

    Integer getId();

    LocalTime getTime();

    Integer getSessionId();

}
