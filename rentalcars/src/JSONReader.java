/**
 * Created by sc15ig on 02/11/17.
 */
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;
import java.net.*;
import jdk.nashorn.api.scripting.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.sound.midi.SysexMessage;

public class JSONReader{

    // Initializing the list variables and the table needed to compute the details of a vehicles
    private String sipp;
    private String name;
    private double price;
    private String supplier;
    private double rating;
    private String typeOfCar;
    private String[][] carType = {{"M","Mini"},{"E","Economy"},{"C","Compact"},{"I","Intermediante"},{"S","Standard"},{"F","Full size"},{"P","Premium"},{"L","Luxury"},{"X","Special"}};
    private String[][] doors = {{"B","2 doors"},{"C","4 doors"},{"D","5 doors"},{"W","Estate"},{"T","Convertible"},{"F","SUV"},{"P","Pick up"},{"V","Passenger Van"}};
    private String[][] transmission = {{"M","Manual"},{"A","Automatic"}};
    private String[][] fuel = {{"N","Petrol/no AC"},{"R","Petrol/AC"}};
    private String[][] highSup = new String[9][4];
    List<Object> highSupp = new ArrayList<Object>();

    public  JSONReader(String name, String typeOfCar, String supplier, double rating) {
     this.name = name;
     this.typeOfCar = typeOfCar;
     this.supplier = supplier;
     this.rating = rating;
    }

    public double getRating() {
        return rating;
    }
    public String getName() {
        return name;
    }
    public String getCarType() {
        return typeOfCar;
    }
    public String getSupplier() {
        return supplier;
    }
    // From the chosen operation the function computes the data
    public void getData(String file, int mode) {
        JSONParser parser = new JSONParser();
        JSONObject car = null;

        try {
            Object obj = parser.parse(new FileReader("src/" + file));

            JSONObject jsonObject = (JSONObject) obj;
            JSONObject list = (JSONObject) jsonObject.get("Search");
            JSONArray vehicleList = (JSONArray) list.get("VehicleList");


            System.out.println("\nCar:");
            Iterator<List> iterator = vehicleList.iterator();

            // Iterate through the list of vehicles and extract data
            while (iterator.hasNext()) {
                car = (JSONObject) iterator.next();
                sipp = (String) car.get("sipp");
                name = (String) car.get("name");
                price = (double) car.get("price");
                supplier = (String) car.get("supplier");
                rating = (double) car.get("rating");

                if (mode == 1){
                    System.out.print("{ " + name + " } - ");
                    System.out.println("{ " + price + " }");

                }
                else if (mode == 2){
                    System.out.print("{ " + name + " } - ");
                    System.out.print("{ " + sipp + " } - ");

                    for (int i = 0; i < 9; i++){
                        if (sipp.substring(0,1).equals(carType[i][0])){
                            System.out.print("{ " + carType[i][1] + " } - ");
                        }
                    }
                    for (int j = 0; j < 8; j++){
                        if (sipp.substring(1,2).equals(doors[j][0])){
                            System.out.print("{ " + doors[j][1] + " } - ");
                        }
                    }
                    for (int k = 0; k < 2; k++){
                        if (sipp.substring(2,3).equals(transmission[k][0])){
                            System.out.print("{ " + transmission[k][1] + " } - ");
                        }
                    }
                    for (int l = 0; l < 2; l++){
                        if (sipp.substring(3,4).equals(fuel[l][0])){
                            System.out.print("{ " + fuel[l][1]+ " }");
                        }
                    }
                    System.out.println();
                }
                else if (mode == 3){
                    for (int i = 0; i < 9; i++){
                        if (sipp.substring(0,1).equals(carType[i][0])){
                            JSONReader newcar = new JSONReader(name,carType[i][1],supplier, rating);
                            //highSupp.add(newcar);
                            System.out.println("Cartype " +newcar.getCarType()+ "Rating" + (newcar.getRating()));
                        }
                    }
//                    Collections.sort(highSupp);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the json file + '.json': ");
        //String file = scanner.nextLine();
        System.out.println("Type 1 for list of vehicles and prices: ");
        System.out.println("Type 2 for list of specification based on SIPP: ");
        String mode = scanner.nextLine();
        Integer x = Integer.valueOf(mode);

        JSONReader cars = new JSONReader(null,null,null, 0);

        cars.getData("vehicles.json",x);


    }
}