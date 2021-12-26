package com.company;

import java.lang.*;
import java.io.*;
import java.sql.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.util.*;
//import com.fasterxml.jackson.databind.ObjectMapper;

class Main{

public static void main(String args[]) {
        try{
            System.out.println("1 for db2json___________________2 for json 2 db");
            Scanner obj = new Scanner(System.in);
            System.out.print("Enter option : ");
            int x=obj.nextInt();
            if (x==1){
                    dbtojson();
                    }
            else if (x==2){
                jsontodb();
            }
            else{
                System.out.println("Wrong");
            }
    }
        catch(Exception e){
            System.out.println(e);
        }
    }


public static Connection DBconn(String schema) throws Exception {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+schema , "root", "16200913");
        //establishing connection to MySQL using drivermanager - returns Connection datatype
        return conn;
    }

public static void dbtojson(){
        //DATABASE 2 JSON
        try {
            Scanner obj = new Scanner(System.in);
            System.out.print("Enter name of schema : ");
            String schema = obj.nextLine();
            System.out.print("Enter name of table : ");
            String dbase = obj.nextLine();

            Connection conn = DBconn(schema); // getting connection from dbconn method
            Statement stmt = conn.createStatement(); // the statement is used to send a SQL query to the database
            ResultSet rs = stmt.executeQuery("select * from "+dbase); // Returns one ResultSet object with the given query

            ResultSetMetaData rsMetaData = rs.getMetaData(); // to get column length and other metadata about the DB using the ReusltSET only
            int count = rsMetaData.getColumnCount(); // number of columns

            String[] ttl = new String[count + 1]; //intialise array to save said DB columns
            for (int i = 1; i <=count; i++) {
                ttl[i] = (String) rsMetaData.getColumnName(i); // append column names
                System.out.print(ttl[i] + " ");
            }
            System.out.println();

        //WRITING TO JSON
        JSONObject jsonObject = new JSONObject(); //intialise jsonObject
        JSONArray array = new JSONArray(); // initialise json array

        while (rs.next()) {
            JSONObject record = new JSONObject(); //json object to save key value pairs
            for(int j=1 ; j<ttl.length ; j++){
                System.out.print(rs.getString(j) + "  ");
                record.put(ttl[j], rs.getString(ttl[j])); // add to jsonobj based on column names
            }
            System.out.println();
            array.add(record); // add to json array
        }
        jsonObject.put("DATAFRAME", array); // write as dataframe named json

        try { // locally save json
            FileWriter file = new FileWriter(dbase+".json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.close(); //connection to DB closed
        System.out.println(" JSON FILE CREATED SUCCESSFULLY !!!");
    }
        catch(Exception e){System.out.println(e);}
    }

public static void jsontodb(){
        try{
            Scanner obj = new Scanner(System.in);
            System.out.print("Enter name of schema : ");
            String schema = obj.nextLine();
            System.out.print("Enter name of table : ");
            String dbase = obj.nextLine();

            Connection conn = DBconn(schema);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from "+dbase);

            JSONParser jsonParser = new JSONParser(); // intialise jsonparser object

            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(dbase+".json")); // set jsonobj values from .json file

            JSONArray jsonArray = (JSONArray) jsonObject.get("DATAFRAME");

            ArrayList<String> arr = new ArrayList<String>();


            int colen=0;
            for(Object object : jsonArray) {
                JSONObject record = (JSONObject) object;
                colen = record.keySet().toArray().length;
                break;
            }
            System.out.println("Length of the columns is : "+colen);

                for(Object object : jsonArray) {
                    JSONObject record = (JSONObject) object;
                    for (int i = 0; i < colen; i++){
                        //System.out.println(record.keySet().toArray()[i]);
                        arr.add((String)record.keySet().toArray()[i]);
                }
                    break;
                }
            System.out.println("Column names are : "+arr);


            int datalen = jsonArray.toArray().length;
            System.out.println("Number of Data Rows : "+datalen);
                  String nvl = String.join("", Collections.nCopies(colen-1, "?,"));
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO "+dbase+" values ("+nvl+"?)");

            for(Object object : jsonArray) {
                JSONObject record = (JSONObject) object;

//                for(int k=0 ; k<colen ; k++){
//                    //System.out.println(record.get(arr.get(k)));
//                }
                for(int z=0 ; z<colen ; z++){
                    pstmt.setString(rs.findColumn((String)arr.get(z)),(String)record.get((String)arr.get(z)));
                }

                pstmt.executeUpdate();

            }
            System.out.println("VALUES INSERTED TO DATABASE SUCCESSFULLY");
            conn.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
