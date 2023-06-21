package com.example.pm2e1_0007_0084.Settings;

public class Transactions {
    public static final String name_database="Exa1";

    //tabla contactos
    public static final String table_contacts="contacts";
    public static final String id_country="id_country";
    public static final String id="id";
    public static final String name="name";
    public static final String phone="phone";
    public static final String note="note";
    public static final String photo="photo";

    public static final String create_table_contacts="CREATE TABLE contacts(id_contact INTEGER PRIMARY KEY AUTOINCREMENT, id_country INT, name TEXT, phone INTEGER, note TEXT, image BLOB, FOREIGN KEY(id_country) REFERENCES countries(id_contry))";
    public static final String drop_table_contacts="DROP TABLE IF EXISTS contacts";
    public static final String select_table_contacts="SELECT * FROM "+Transactions.table_contacts;

    //tabla paises
    public static final String table_countries="countries";
    public static final String country="country";

    public static final String create_table_countries="CREATE TABLE countries(id_country INTEGER PRIMARY KEY AUTOINCREMENT, country)";
    public static final String drop_table_countries="DROP TABLE IF EXISTS countries";
    public static final String insert_table_countries="INSERT INTO countries(country) VALUES('Honduras (504)'), ('Costa Rica (506)'), ('Guatemala (502)'), ('El Salvador (503)')";
    public static final String select_table_countries="SELECT * FROM "+Transactions.table_countries;
}
