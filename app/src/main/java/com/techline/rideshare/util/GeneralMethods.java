package com.techline.rideshare.util;

public class GeneralMethods {


    public String toCommaAmount(String strTotal) {
        int strLen = strTotal.length();
        StringBuilder builder;
        String newamt = "";
        // Initialize StringBuilder with this value.
        builder = new StringBuilder(strTotal);
        if (strTotal.contains(".")) {
            strLen = strLen - 3;
            if (strLen < 4) {
                newamt = strTotal;
            }

            if (strLen == 4) {

                builder.insert(1, ",");
                newamt = builder.toString();
            }
            if (strLen == 5) {

                builder.insert(2, ",");
                newamt = builder.toString();
            }

            if (strLen == 6) {
                builder.insert(3, ",");
                newamt = builder.toString();
            }

            if (strLen == 7) {
                builder.insert(4, ",");
                newamt = builder.toString();
            }
            if (strLen == 8) {
                builder.insert(2, ",");
                builder.insert(6, ",");
                newamt = builder.toString();
            }
            if (strLen == 9) {
                builder.insert(3, ",");
                builder.insert(7, ",");
                newamt = builder.toString();
            }

            if (strLen == 10) {
                builder.insert(4, ",");
                builder.insert(8, ",");
                newamt = builder.toString();
            }
            if (strLen == 11) {
                builder.insert(2, ",");
                builder.insert(6, ",");
                builder.insert(9, ",");
                newamt = builder.toString();
            }
        } else {
            if (strLen < 4) {
                newamt = strTotal;
            }

            if (strLen == 4) {

                builder.insert(1, ",");
                newamt = builder.toString();
            }
            if (strLen == 5) {

                builder.insert(2, ",");
                newamt = builder.toString();
            }

            if (strLen == 6) {
                builder.insert(3, ",");
                newamt = builder.toString();
            }

            if (strLen == 7) {
                builder.insert(4, ",");
                newamt = builder.toString();
            }
            if (strLen == 8) {
                builder.insert(2, ",");
                builder.insert(6, ",");
                newamt = builder.toString();
            }
            if (strLen == 9) {
                builder.insert(3, ",");
                builder.insert(7, ",");
                newamt = builder.toString();
            }

            if (strLen == 10) {
                builder.insert(4, ",");
                builder.insert(8, ",");
                newamt = builder.toString();
            }
            if (strLen == 11) {
                builder.insert(2, ",");
                builder.insert(6, ",");
                builder.insert(9, ",");
                newamt = builder.toString();
            }
        }


        return newamt;
    }


}
