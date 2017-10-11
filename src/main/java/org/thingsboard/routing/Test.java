package org.thingsboard.routing;

import com.graphhopper.*;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.*;
import com.graphhopper.util.shapes.GHPoint;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

        GraphHopper hopper = new GraphHopperOSM().forServer();
        hopper.setCHEnabled(false);
        String osmFile = "ukraine-latest.osm.pbf";
        hopper.setDataReaderFile(osmFile);
        String graphFolder = "resources/graphFolder";
        hopper.setGraphHopperLocation(graphFolder);
        hopper.setEncodingManager(new EncodingManager("car, bike"));

        hopper.importOrLoad();

        GHRequest request = new GHRequest().
                addPoint(new GHPoint(50.468363, 30.510063)). //кінотеатр жовтень
                addPoint(new GHPoint(50.415239, 30.619649)). //здолбунівська 11а
                addPoint(new GHPoint(50.474047, 30.514935)). //турівська 31
                setVehicle("car").setAlgorithm(Parameters.Algorithms.ASTAR_BI).
                setWeighting("shortest").
                setLocale(Locale.US);
        GHResponse rsp = hopper.route(request);

        if(rsp.hasErrors()) {
            rsp.getErrors();
        }

        PathWrapper path = rsp.getBest();

        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();


        InstructionList il = path.getInstructions();

        for(Instruction instruction : il) {
            instruction.getDistance();

        }

        List<Map<String, Object>> iList = il.createJson();

        List<GPXEntry> list = il.createGPXList();

        for (Map<String, Object> aList : iList) {
            System.out. println(aList);
        }

    }
}
