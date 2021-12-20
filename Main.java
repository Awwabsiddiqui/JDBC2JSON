package com.company;

import java.lang.*;
import java.io.*;
import java.sql.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;


class Main {
    public static void main(String args[]) {

//        //DATABASE 2 JSON
//        try {
//            //Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mainer", "root", "16200913");
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("select * from empdata");
//
//            ResultSetMetaData rsMetaData = rs.getMetaData();
//            int count = rsMetaData.getColumnCount();
//            String[] ttl = new String[count + 1];
//
//            for (int i = 1; i <=count; i++) {
//                ttl[i] = (String) rsMetaData.getColumnName(i);
//                System.out.print(ttl[i] + " ");
//            }
//            System.out.println();
//
//        //WRITING TO JSON
//        JSONObject jsonObject = new JSONObject();
//        JSONArray array = new JSONArray();
//
//        while (rs.next()) {
//            JSONObject record = new JSONObject();
//            for(int j=1 ; j<ttl.length ; j++){
//                System.out.print(rs.getString(j) + "  ");
//                record.put(ttl[j], rs.getString(ttl[j]));
//            }
//            System.out.println();
//            array.add(record);
//        }
//        jsonObject.put("DATAFRAME", array);
//        try {
//            FileWriter file = new FileWriter("db2json.json");
//            file.write(jsonObject.toJSONString());
//            file.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        conn.close();
//        System.out.println(" JSON FILE CREATED SUCCESSFULLY !!!");
//    }
//        catch(Exception e){System.out.println(e);}



//        //JSON SIMPLE
//        JSONParser parser = new JSONParser();
//        try {
//            Object obj = parser.parse(new FileReader("crunchify.json"));
//            JSONObject jsonObject = (JSONObject) obj;
//
//            JSONArray o = (JSONArray) jsonObject.get("players_data");
//            System.out.println(o);
//            System.out.println();
//            Iterator<JSONObject> iterator2 = o.iterator();
//            while (iterator2.hasNext()) {
//                System.out.println(iterator2.next());
//            }
//
//        }
//         catch (Exception e) {
//            e.printStackTrace();
//        }

//       //JACKSON
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//
//            // JSON file to Java object
//            Object staff = mapper.readValue(new FileReader("db2json.json"), Object.class);
//
//            // JSON string to Java object
//            //String jsonInString = "{\"name\":\"mkyong\",\"age\":37,\"skills\":[\"java\",\"python\"]}";
//            //Object staff2 = mapper.readValue(jsonInString, Object.class);
//
//            // compact print
//            System.out.println(staff);
//
//            // pretty print
//            String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff);
//
//            System.out.println(prettyStaff1);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

            //JACKSON2

        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON file to map
            Map<?, ?> map = mapper.readValue(new FileReader("db2json.json"), Map.class);

//            for (Map.Entry<?, ?> entry : map.entrySet()) {
//                System.out.println(entry.getKey() + "=" + entry.getValue());
//            }
            System.out.println(map.get("DATAFRAME").getClass());
             //ArrayList arr =new ArrayList(map.get("DATAFRAME"));

        }
            catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}



