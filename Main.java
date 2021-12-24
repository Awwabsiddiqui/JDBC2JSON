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
        try{
            System.out.println("1 for db2json    ///    2 for json 2 db");
            Scanner obj = new Scanner(System.in);
            //int x=obj.nextInt();
            int x=2;
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


public static Connection DBconn() throws Exception {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mainer", "root", "16200913");
        return conn;
    }

public static void dbtojson(){
        //DATABASE 2 JSON
        try {
            Scanner obj = new Scanner(System.in);
            Connection conn = DBconn();
            Statement stmt = conn.createStatement();
            System.out.print("Enter name of databse : ");
            String dbase = obj.nextLine();
            ResultSet rs = stmt.executeQuery("select * from "+dbase);

            ResultSetMetaData rsMetaData = rs.getMetaData();
            int count = rsMetaData.getColumnCount();
            String[] ttl = new String[count + 1];

            for (int i = 1; i <=count; i++) {
                ttl[i] = (String) rsMetaData.getColumnName(i);
                System.out.print(ttl[i] + " ");
            }
            System.out.println();

        //WRITING TO JSON
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        while (rs.next()) {
            JSONObject record = new JSONObject();
            for(int j=1 ; j<ttl.length ; j++){
                System.out.print(rs.getString(j) + "  ");
                record.put(ttl[j], rs.getString(ttl[j]));
            }
            System.out.println();
            array.add(record);
        }
        jsonObject.put("DATAFRAME", array);
        try {
            FileWriter file = new FileWriter(dbase+".json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.close();
        System.out.println(" JSON FILE CREATED SUCCESSFULLY !!!");
    }
        catch(Exception e){System.out.println(e);}
    }

public static void jsontodb(){
        try{

            Connection conn = DBconn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from empdata");
            ResultSetMetaData rsMetaData = rs.getMetaData();

            JSONParser jsonParser = new JSONParser();

            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("empdata.json"));

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

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO empdata values (?, ?, ?)");

            for(Object object : jsonArray) {
                JSONObject record = (JSONObject) object;

//                for(int k=0 ; k<colen ; k++){
//                    //System.out.println(record.get(arr.get(k)));
//                }
                for(int z=0 ; z<colen ; z++){
                    pstmt.setString(rs.findColumn((String)arr.get(z)),(String)record.get((String)arr.get(z)));
                }
//                pstmt.setString(1,(String) record.get("id"));
//                pstmt.setString(2,(String) record.get("name"));
//                pstmt.setString(3,(String) record.get("role"));

                pstmt.executeUpdate();

            }
            System.out.println("VALUES INSERTED TO DATABASE SUCCESSFULLY");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
