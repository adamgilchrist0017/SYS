package Modules;

import java.util.List;

/**
 * Created by vidhant on 19/08/2017.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
