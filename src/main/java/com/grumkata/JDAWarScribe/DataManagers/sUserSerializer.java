package com.grumkata.JDAWarScribe.DataManagers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class sUserSerializer extends StdSerializer<sUser> {
    public sUserSerializer() {
        this(null);
    }

    public sUserSerializer(Class<sUser> t) {
        super(t);
    }

    @Override
    public void serialize(sUser sUser, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", sUser.id);
        jsonGenerator.writeStringField("name", sUser.name);
        jsonGenerator.writeStringField("nickname", sUser.nickname);
        jsonGenerator.writeStringField("avatar", sUser.avatar);
        jsonGenerator.writeNumberField("curChar", sUser.curChar);
        jsonGenerator.writeArrayFieldStart("charecters");
        for (persona X : sUser.getCharecters()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name", X.name);
            jsonGenerator.writeStringField("avatar", X.avatar);
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
