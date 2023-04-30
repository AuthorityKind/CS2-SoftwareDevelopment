package code.example.code;

public class Controller {

    //retrieves data from the database out from the query values it was given
    //used to retrieve data associated with specific data points, such as specific dates or locations
    //runs the retrieveData method from the Model class, passing it's own parameters on through the getQuery method
    public static String[] getData(QueryValue[] queryValues) {
        return Model.retrieveData(getQuery(queryValues));
    }

    //gets the query that "books" a shipment for a customer
    //to interface between the user and database
    //runs the getBookingQuery method from the Model class
    public static String getBookingQuery(QueryValue[] queryValues) {
        return Model.getBookingQuery(getQuery(queryValues), queryValues);
    }

    //runs the runQuery method from the Model class
    public static void runQuery(String query) {
        Model.runQuery(query);
    }

    //parses the date from the database to add slashes between the day, month and year
    //meant to make dates more readable for the user
    //knowing every two digits are paired up, the method goes through the characters and add a "/" between each pair
    public static String parseDate(String date) {
        int count = 0;
        for (int i = 0; i < date.length(); i++) {
            count++;

            if (count == 2 && i != date.length() - 1) {
                StringBuilder start = new StringBuilder();
                StringBuilder end = new StringBuilder();

                for (int i2 = 0; i2 <= i; i2++) start.append(date.charAt(i2));
                for (int i3 = i + 1; i3 < date.length(); i3++) end.append(date.charAt(i3));
                date = start + "/" + end;
                count = -1;
            }
        }
        return date;
    }

    //"unparses" the readable version of the dates back to it's original format
    //meant to make dates able to interact with queries and the database again after being parsed
    //simply removes all insteaces of the "/" in the string
    public static String unparseDate(String date) {
        if (date != null) {
            return date.replaceAll("/", "");
        } else {
            return "";
        }
    }

    //gets a list of all the entries in a given column in the database
    //to show the user all available options when they are booking their shipment
    //runs the retrieveData method from the Model class, with the argument as a query to get every unique entry in a column
    public static String[] getList(String list) {
        return Model.retrieveData("select distinct " + list + " from voyage order by " + list + " asc");
    }

    //builds the query for the getData and getBookingQuery methods
    //as the code was needed more than once, it was made into a method
    //it goes through each selected query value, given as it's parameters, and builds the query from that
    private static String getQuery(QueryValue[] queryValues) {
        StringBuilder query = new StringBuilder("SELECT * from voyage ");
        boolean firstCounted = false;

        for (QueryValue qval : queryValues) {
            if (!firstCounted) {
                query.append("WHERE ");
                firstCounted = true;
            } else {
                query.append(" AND ");
            }
            if (qval.type.equals("curCap")) {
                query.append("maxCap > ").append(qval.val).append(" + curCap");
            } else {
                query.append(qval.type).append(" = ").append(qval.val);
            }
        }
        return query.toString();
    }

    //checks the capacity and availability of space of every shipment in the database
    //to check wether or not they shipments are available or not
    //goes through a for loop of each row in the database and prints the result
    public static void checkCapacity() {
        String[] capacityList = Model.retrieveData("SELECT * from voyage where maxCap < curCap;");
        System.out.println(capacityList.length + " ship(s) exceeds their capacity");
    }

    //checks if two or more shipments are identical
    //to ensure that there isn't any mistakes or managerial hiccups
    //counts the number of instances of each row with and returns that number for each row
    public static void checkOverlappingVoyages() {
        String[] capacityList = Model.retrieveData("SELECT *, COUNT(*) AS \"Count\" from voyage group by departDate, arrivalDate, companyName, departLocal, arrivalLocal, maxCap, curCap;", "Count");
        for(String s : capacityList)
            if(!s.equals("1")){
                System.out.println("A voyage is overlapping");
                return;
            }
        System.out.println("No voyage overlaps");
    }
}
