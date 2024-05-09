package com.grumkata.JDAWarScribe.DataManagers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class sUserDeserialize extends StdDeserializer<sUser> {
    public sUserDeserialize() {
        this(null);
    }

    public sUserDeserialize(Class<?> vc) {
        super(vc);
    }

    @Override
    public sUser deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        sUser USER = new sUser();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        // try catch block
        JsonNode idNode = node.get("id");
        JsonNode nameNode = node.get("name");
        JsonNode nicknameNode = node.get("nickname");
        JsonNode avatarNode = node.get("avatar");
        JsonNode curCharNode = node.get("curChar");
        JsonNode charecterRoot = node.path("charecters");

        USER.id = idNode.asText();
        USER.name = nameNode.asText();
        USER.avatar = avatarNode.asText();
        USER.nickname = nicknameNode.asText();
        USER.curChar = curCharNode.asInt();
        if (charecterRoot.isArray()) {
            for (JsonNode point : charecterRoot) {
                String persname = point.path("name").asText();
                String persavatar = point.path("avatar").asText();
                persona newChar = new persona(persname, persavatar);
                USER.addPersona(newChar);

            }
        }


        return USER;
    }
}
