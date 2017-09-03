package Modules;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vidhant on 19/08/2017.
 */
public class DirectionFinder {
    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyBcyLqGz-gAQojjQi4oxul1_9NvsJK8_4w";
    private DirectionFinderListener listener;
    private String origin;
    private String destination;
    String place;

    public DirectionFinder(DirectionFinderListener listener, String origin, String destination) {
        this.listener = listener;
        this.origin = origin;
        this.destination = destination;
    }

    public void execute() throws UnsupportedEncodingException {
        listener.onDirectionFinderStart();
        new DownloadRawData().execute(createUrl());
    }

    private String createUrl() throws UnsupportedEncodingException {
        String urlOrigin = URLEncoder.encode(origin, "utf-8");
        String urlDestination = URLEncoder.encode(destination, "utf-8");
        if (urlOrigin.equalsIgnoreCase("sims hospital") && urlDestination.equalsIgnoreCase("sri sairam")) {
            return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&waypoints=mgr+nagar+police+station,chennai%7Cdeepam+pallavaram+hospital,chennai%7Cs11+police+station,chennai&key=" + GOOGLE_API_KEY;
        }
        if (urlOrigin.equalsIgnoreCase("ambattur industrial") &&urlDestination.equalsIgnoreCase("adyar")) {
            return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&waypoints=police+booth,anna+nagar,chennai%7Csims+hospital,chennai%7Cguindy+Police+Station,chennai&key=" + GOOGLE_API_KEY;

        }
        if (urlOrigin.equalsIgnoreCase("ambattur OT bus station") && urlDestination.equalsIgnoreCase("porur junction")) {
            return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&waypoints=T6+Avadi+Police+Station,chennai%7CT5+Police+Station+Thiruverkadu,chennai%7CT15+Police+Station,chennai&key=" + GOOGLE_API_KEY;

        }
        if (urlOrigin.equalsIgnoreCase("siruseri") && urlDestination.equalsIgnoreCase("poonamallee")) {
            return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&waypoints=via:24hrs+cm+hospital,chennai%7Cvia:s10+pallikaranai+police+station,chennai%7Cvia:t+15+police+station&key=" + GOOGLE_API_KEY;
        }
        if (urlOrigin.equalsIgnoreCase("kollam,kerala") && urlDestination.equalsIgnoreCase("thiruvananthapuram,kerala")) {
            return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&waypoints=via:kollam east police station|via:paravur police station|via:ayiroor police station|via:attinga mini civil station|via:police station KTDC Rd&key=" + GOOGLE_API_KEY;


        }

        if (urlOrigin.equalsIgnoreCase("ernakulam town railway station") && urlDestination.equalsIgnoreCase("viswajyothi college of engineering and technology")) {
            return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&waypoints=via:ernakulam town north police station,kerala|via:ernakulam town south circle office,kerala|via:cochin mental health centre, kerala|via:rcm wellness and ayurveda hospital , kerala&key=" + GOOGLE_API_KEY;

        }

        if (urlOrigin.equalsIgnoreCase("aluva") && urlDestination.equalsIgnoreCase("lakshmi hospital,marine drive,kerala")) {
            return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&waypoints=via:binanipuram police station,kerala|via:assistant commmissioner office,kekkanad,kerala|via:crime branch office,anchumana temple road,kerala|via:ernakulam town,xavier arakkal road=" + GOOGLE_API_KEY;

        }

        if (urlOrigin.equalsIgnoreCase("viswajyothi college of engineering and technology ") && urlDestination.equalsIgnoreCase("mattathipara")) {

            return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&waypoints=vazhakulam%20police%20station%20rd|via:kaimkunnam%20police%20station&key=" + GOOGLE_API_KEY;

        }


        else {
            return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&key=" + GOOGLE_API_KEY;
        }
    }
    private class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return;

        List<Route> routes = new ArrayList<Route>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            Route route = new Route();

            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

            route.distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
            route.duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
            route.endAddress = jsonLeg.getString("end_address");
            route.startAddress = jsonLeg.getString("start_address");
            route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
            route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
            route.points = decodePolyLine(overview_polylineJson.getString("points"));

            routes.add(route);
        }

        listener.onDirectionFinderSuccess(routes);
    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }
}
