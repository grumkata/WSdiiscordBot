package com.grumkata.JDAWarScribe.DataManagers;

import com.fasterxml.jackson.annotation.JsonGetter;
import net.dv8tion.jda.api.entities.Guild;

import java.util.ArrayList;
import java.util.List;

public class sUser {
    //server/basic data
    public String id;
    public String name;
    public String nickname;
    public String avatar;

    //rp data
    public int curChar = 0;
    private ArrayList<persona> charecters = new ArrayList<persona>();

    public sUser(String id, Guild fromServer) {
        this.id = id;
        name = fromServer.getMemberById(id).getUser().getName();
        avatar = fromServer.getMemberById(id).getUser().getAvatarUrl();
        nickname = fromServer.getMemberById(id).getUser().getEffectiveName();
    }

    public sUser() {
    }

    public void addCharecter(String name, String avatar) {
        charecters.add(new persona(name, avatar));
    }

    public void removeCharecter(int id) {
        if (id >= charecters.size() || id < 0) {
            return;
        }
        charecters.remove(id);
    }

    public void addPersona(persona tempPers) {
        charecters.add(tempPers);
    }

    public int personLength() {
        return charecters.size();
    }

    public persona[] getCharecters() {
        return charecters.toArray(new persona[charecters.size()]);
    }

    public persona getActiveCharecter() {
        if (curChar == 0) {
            return new persona(this.nickname, this.avatar);
        }
        return charecters.get(curChar - 1);
    }

    public void getPersona(String id) {
        for (persona charecter : charecters) {
            if (charecter.name.equals(id)) {
                curChar = charecters.indexOf(charecter);
            }
        }
    }


}
