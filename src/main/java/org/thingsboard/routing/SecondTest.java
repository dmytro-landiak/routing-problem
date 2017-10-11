package org.thingsboard.routing;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.PathWrapper;
import com.graphhopper.api.GraphHopperWeb;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;
import okhttp3.OkHttpClient;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SecondTest {
    public static void main(String[] args) {
        GraphHopperWeb gh = new GraphHopperWeb();
        gh.setKey("YOUR_KEY");
        gh.setDownloader(new OkHttpClient.Builder().
                connectTimeout(5, TimeUnit.SECONDS).
                readTimeout(5, TimeUnit.SECONDS).build());

        GHRequest req = new GHRequest().
                addPoint(new GHPoint(49.6724, 11.3494)).
                addPoint(new GHPoint(49.6550, 11.4180));
        req.setVehicle("bike");
        req.getHints().put("elevation", false);
        req.getHints().put("instructions", true);
        req.getHints().put("calc_points", true);
        req.setLocale(Locale.GERMAN);

        GHResponse fullRes = gh.route(req);

        if(fullRes.hasErrors()) {
            // handle or throw exceptions res.getErrors()
            return;
        }

        PathWrapper res = fullRes.getBest();
        PointList pl = res.getPoints();
        double distance = res.getDistance();
        long millis = res.getTime();
        InstructionList il = res.getInstructions();

    }

}
