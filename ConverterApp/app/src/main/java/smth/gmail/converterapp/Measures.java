package smth.gmail.converterapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Measures {
    private Map<String, Double> distance = new HashMap<String, Double>();
    private Map<String, Double> time = new HashMap<String, Double>();
    private Map<String, Double> weight = new HashMap<String, Double>();

    public Measures(){
        distance.put("Meters", 1.);
        distance.put("Centimeters", 100.);
        distance.put("Millimeters", 1000.);

        time.put("Hours", 1./3600);
        time.put("Minutes", 1./60);
        time.put("Seconds", 1.);

        weight.put("Tons", 1./1000);
        weight.put("Kilograms", 1.);
        weight.put("Grams", 1000.);
    }

    public ArrayList<String> getMeasuresNames(String category){
        ArrayList<String> names;
        switch (category){
            case "Distance":
                names = new ArrayList<String>(distance.keySet());
                break;
            case "Time":
                names =  new ArrayList<String>(time.keySet());
                break;
            case "Weight":
                names =  new ArrayList<String>(weight.keySet());
                break;
            default:
                names  = new ArrayList<String>();
        }
        return names;
    }

    public double getCf(String cat, String categoryName){
        double result = 1.;
        switch (cat)
        {
            case "Distance":
                result = distance.get(categoryName);
                break;
            case "Time":
                result = time.get(categoryName);
                break;
            case "Weight":
                result = weight.get(categoryName);
                break;
        }
        return result;
    }

    public String Convert(String data, double Cf1, double Cf2){
        double result;
        if (data.equals("")){ return "0"; }
        try {
            result = Double.parseDouble(data);
        }
        catch(Exception exp){
            return "Error";
        }
        result = result * Cf2 / Cf1;
        return ""+result;
    }
}
