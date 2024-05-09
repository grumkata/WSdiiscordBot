package com.grumkata.JDAWarScribe;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.grumkata.JDAWarScribe.Commands.commandsManager;
import com.grumkata.JDAWarScribe.DataManagers.sUser;
import com.grumkata.JDAWarScribe.DataManagers.sUserDeserialize;
import com.grumkata.JDAWarScribe.DataManagers.sUserSerializer;
import com.grumkata.JDAWarScribe.Listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import kotlin.io.encoding.Base64;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WarScribe {

    private final Dotenv config;
    private final ShardManager shardManager;

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static List<sUser> ServerUsers = new ArrayList<sUser>();


    public WarScribe() throws LoginException, FileNotFoundException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_WEBHOOKS, GatewayIntent.GUILD_VOICE_STATES);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS);
        shardManager = builder.build();
        //register listeners
        shardManager.addEventListener(new EventListener());
        shardManager.addEventListener(new commandsManager());


    }


    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) throws IOException {
        SetupAllUsers();
        try {
            WarScribe bot = new WarScribe();
        } catch (LoginException e) {
            System.out.println("ERROR: provided bot token is invalid");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: could not find file from provided path");
        }


    }

    public static club.minnced.discord.webhook.WebhookClient GetWebhook() {
        WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/1237595484747599932/mtGIpUwrv1KfgYNACvBWB147foAZZ_sMul8mpBI_OfPwJ9YlFuBNKbWQEfUo0sdQaeeq"); // or id, token
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("RP BOT");
            thread.setDaemon(true);
            return thread;
        });
        builder.setWait(true);
        WebhookClient client = builder.build();
        return client;
    }

    public static club.minnced.discord.webhook.WebhookClient GetWebhookultra(int WHType, Long threadID) {
        WebhookClientBuilder builder;
        WebhookClient client;
        switch (WHType) {
            case 1:
                builder = new WebhookClientBuilder("https://discord.com/api/webhooks/1237563174241177610/RMqt_MHJeb0nedzYKWdfBxuZsa9Pjix75EeofSPDC2-XENP8isM3tuhGi96XP8VGiI02"); // or id, token
                builder.setThreadId(Long.valueOf(threadID));
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("RP BOT");
                    thread.setDaemon(true);
                    return thread;
                });
                builder.setWait(true);
                client = builder.build();
                return client;
            case 2:
                builder = new WebhookClientBuilder("https://discord.com/api/webhooks/1237563753189605396/ncMQ9_iV5OIG7KAfyxkcQVuOLaMgWAMkA1GQejsDt0SdeG236yKBSNl6dk2GnkUU6SwS");
                builder.setThreadId(Long.valueOf(threadID));
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("RP BOT");
                    thread.setDaemon(true);
                    return thread;
                });
                builder.setWait(true);
                client = builder.build();
                return client;
            case 3:
                builder = new WebhookClientBuilder("https://discord.com/api/webhooks/1237563617499545652/1nPeB9H7xT1R2ucdvNNKeOJLMmC5SL1nYZ_vMtrzzAPPPLZPvKsI4ODoTe9p9Y6W7Jhq"); // or id, token
                builder.setThreadId(Long.valueOf(threadID));
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("RP BOT");
                    thread.setDaemon(true);
                    return thread;
                });
                builder.setWait(true);
                client = builder.build();
                return client;
            case 4:
                builder = new WebhookClientBuilder("https://discord.com/api/webhooks/1237563396174643300/Teuic42y3-fQeaaxKXe4z3w4o6p-ceRgg8-I_osPZcarN_T1VGXiaW6jRzBxkoS0-Q9x"); // or id, token
                builder.setThreadId(Long.valueOf(threadID));
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("RP BOT");
                    thread.setDaemon(true);
                    return thread;
                });
                builder.setWait(true);
                client = builder.build();
                return client;
            case 5:
                builder = new WebhookClientBuilder("https://discord.com/api/webhooks/1237563292902363229/naXb61bFgg8-UVBGsWakf82PAnYW02yy6h0dKboW4DRxBdXiqvWMKEWKa6o8IfIAQDkU"); // or id, token
                builder.setThreadId(Long.valueOf(threadID));
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("RP BOT");
                    thread.setDaemon(true);
                    return thread;
                });
                builder.setWait(true);
                client = builder.build();
                return client;
            case 6:
                builder = new WebhookClientBuilder("https://discord.com/api/webhooks/1237563613661626458/gk6sru9io5Ve3LuvLJUN7OM8umkOdWU7x0tnMVVpn-7oZ9Evme1HMqW6yms65Gmgs8K3"); // or id, token
                builder.setThreadId(Long.valueOf(threadID));
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("RP BOT");
                    thread.setDaemon(true);
                    return thread;
                });
                builder.setWait(true);
                client = builder.build();
                return client;
            case 7:
                builder = new WebhookClientBuilder("https://discord.com/api/webhooks/1237603626977857557/zwV4MB0GfZiEK9jAGeh7uecxk7JzwCe3Fuyf4EPfsvjcKxEUf0izThJ9xg0tU7yYHQF9"); // or id, token
                builder.setThreadId(Long.valueOf(threadID));
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("RP BOT");
                    thread.setDaemon(true);
                    return thread;
                });
                builder.setWait(true);
                client = builder.build();
                return client;
            case 8:
                builder = new WebhookClientBuilder("https://discord.com/api/webhooks/1237603620115976192/sFSlrGEUJpa3KEoduMxDRhIt6CpObeyP0arFFBRQvmZpljQR0IdPSuGUta2jWOb3TYY-"); // or id, token
                builder.setThreadId(Long.valueOf(threadID));
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("RP BOT");
                    thread.setDaemon(true);
                    return thread;
                });
                builder.setWait(true);
                client = builder.build();
                return client;

        }
        return null;
    }

    public static int rollDice(int diceVal, int diceNum) {
        int sum = 0;
        for (int i = 0; i < diceNum; i++) {
            sum += (int) (Math.random() * diceVal + 1);
        }
        return sum;
    }

    public static double statRoll(double mean, double standardDeviation) {
        Random randVal = new Random();
        double GausVal = randVal.nextGaussian();
        return GausVal * standardDeviation + mean;
    }

    // user data location is "src/main/Users.json"

    public static void UpdateAllUsers() throws IOException {
        SimpleModule module = new SimpleModule("sUserSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(sUser.class, new sUserSerializer());
        objectMapper.registerModule(module);
        objectMapper.writeValue(new File("src/main/Users.json"), ServerUsers);
    }

    public static void SetupAllUsers() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule("sUserdeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(sUser.class, new sUserDeserialize());
        objectMapper.registerModule(module);
        ServerUsers = objectMapper.readValue(new File("src/main/Users.json"), new TypeReference<List<sUser>>() {
        });
    }

}
