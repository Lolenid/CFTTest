package com.example.cfttest;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    //Don't use namespace
    private static final String ns = null;

    public List parse(InputStream input) throws XmlPullParserException, IOException {

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(input, null);
            parser.nextTag();
            return readCurValute(parser);
        } finally {
            input.close();
        }
    }

    public List readCurValute(XmlPullParser parser) throws IOException, XmlPullParserException {
        List valutes = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "ValCurs");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();

            if (tagName.equals("Valute")) {
                valutes.add(readValute(parser));
            } else {
                skip(parser);
            }
        }
        return valutes;
    }

    private Valute readValute(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(parser.START_TAG, ns, "Valute");
        String id = parser.getAttributeValue(0);
        String numCode = null;
        String charCode = null;
        int nominal = 0;
        String name = null;
        double value = 0.0;

        while (parser.next() != parser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            switch (tagName) {
                case "NumCode":
                    numCode = readNumCode(parser);
                    break;
                case "CharCode":
                    charCode = readCharCode(parser);
                    break;
                case "Name":
                    name = readName(parser);
                    break;
                case "Nominal":
                    nominal = readNominal(parser);
                    break;
                case "Value":
                    value = readValue(parser);
                    break;
                default:
                    skip(parser);
            }
        }

        return new Valute(id, numCode, charCode, nominal, name, value);
    }

    private String readNumCode(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(parser.START_TAG, ns, "NumCode");
        String numCode = readText(parser);
        parser.require(parser.END_TAG, ns, "NumCode");
        return numCode;
    }

    private String readCharCode(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "CharCode");
        String charCode = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "CharCode");
        return charCode;
    }

    private String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Name");
        String name = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Name");
        return name;
    }

    private int readNominal(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Nominal");
        int nominal = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG, ns, "Nominal");
        return nominal;
    }

    private double readValue(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Value");
        double value = Double.parseDouble(readText(parser).replace(',','.'));
        parser.require(XmlPullParser.END_TAG, ns, "Value");
        return value;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = null;
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}

