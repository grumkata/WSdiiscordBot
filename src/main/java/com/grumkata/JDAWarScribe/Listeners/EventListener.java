package com.grumkata.JDAWarScribe.Listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.grumkata.JDAWarScribe.DataManagers.sUser;
import com.grumkata.JDAWarScribe.WarScribe;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.Widget;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.UpdateEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.*;

public class EventListener extends ListenerAdapter {
    WebhookClient client = WarScribe.GetWebhook();

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //String textChannel = e.getMessage().getChannel().getName();
        if (e.getChannel().getType() == ChannelType.GUILD_PUBLIC_THREAD) {
            String PchannelName = e.getChannel().asThreadChannel().getParentChannel().getName();
            System.out.println(e.getChannel().getType().name());
            boolean charUserDefined = false;
            sUser currentUser = null;
            switch (PchannelName) {
                case ("alliance-roleplay-threads"):
                    charUserDefined = false;
                    currentUser = null;
                    for (sUser person : WarScribe.ServerUsers) {
                        if (person.id.equals(e.getAuthor().getId())) {
                            charUserDefined = true;
                            currentUser = person;
                        }
                    }
                    if (!e.getMessage().isWebhookMessage() && charUserDefined && currentUser != null) {
                        System.out.println(e.getChannel().asThreadChannel().getIdLong());
                        client = WarScribe.GetWebhookultra(1, e.getChannel().asThreadChannel().getIdLong());
                        e.getMessage().delete().queue();
                        WebhookMessage message = new WebhookMessageBuilder()
                                .setUsername(currentUser.getActiveCharecter().name) // use this username
                                .setAvatarUrl(currentUser.getActiveCharecter().avatar) // use this avatar
                                .setContent(e.getMessage().getContentDisplay())
                                .build();
                        client.send(message);

                    }
                    break;
                case ("empire-roleplay-threads"):
                    charUserDefined = false;
                    currentUser = null;
                    for (sUser person : WarScribe.ServerUsers) {
                        if (person.id.equals(e.getAuthor().getId())) {
                            charUserDefined = true;
                            currentUser = person;
                        }
                    }
                    if (!e.getMessage().isWebhookMessage() && charUserDefined && currentUser != null) {
                        client = WarScribe.GetWebhookultra(5, Long.valueOf(e.getChannel().asThreadChannel().getIdLong()));
                        e.getMessage().delete().queue();
                        WebhookMessage message = new WebhookMessageBuilder()
                                .setUsername(currentUser.getActiveCharecter().name) // use this username
                                .setAvatarUrl(currentUser.getActiveCharecter().avatar) // use this avatar
                                .setContent(e.getMessage().getContentDisplay())
                                .build();
                        client.send(message);

                    }
                    break;
                case "dynasty-roleplay-threads":
                    charUserDefined = false;
                    currentUser = null;
                    for (sUser person : WarScribe.ServerUsers) {
                        if (person.id.equals(e.getAuthor().getId())) {
                            charUserDefined = true;
                            currentUser = person;
                        }
                    }
                    if (!e.getMessage().isWebhookMessage() && charUserDefined && currentUser != null) {
                        client = WarScribe.GetWebhookultra(4, e.getChannel().asThreadChannel().getIdLong());
                        e.getMessage().delete().queue();
                        WebhookMessage message = new WebhookMessageBuilder()
                                .setUsername(currentUser.getActiveCharecter().name) // use this username
                                .setAvatarUrl(currentUser.getActiveCharecter().avatar) // use this avatar
                                .setContent(e.getMessage().getContentDisplay())
                                .build();
                        client.send(message);

                    }
                    break;
                case "federation-roleplay-thread":
                    charUserDefined = false;
                    currentUser = null;
                    for (sUser person : WarScribe.ServerUsers) {
                        if (person.id.equals(e.getAuthor().getId())) {
                            charUserDefined = true;
                            currentUser = person;
                        }
                    }
                    if (!e.getMessage().isWebhookMessage() && charUserDefined && currentUser != null) {
                        client = WarScribe.GetWebhookultra(6, e.getChannel().asThreadChannel().getIdLong());
                        e.getMessage().delete().queue();
                        WebhookMessage message = new WebhookMessageBuilder()
                                .setUsername(currentUser.getActiveCharecter().name) // use this username
                                .setAvatarUrl(currentUser.getActiveCharecter().avatar) // use this avatar
                                .setContent(e.getMessage().getContentDisplay())
                                .build();
                        client.send(message);

                    }
                    break;
                case "caliphate-roleplay-threads":
                    charUserDefined = false;
                    currentUser = null;
                    for (sUser person : WarScribe.ServerUsers) {
                        if (person.id.equals(e.getAuthor().getId())) {
                            charUserDefined = true;
                            currentUser = person;
                        }
                    }
                    if (!e.getMessage().isWebhookMessage() && charUserDefined && currentUser != null) {
                        client = WarScribe.GetWebhookultra(3, e.getChannel().asThreadChannel().getIdLong());
                        e.getMessage().delete().queue();
                        WebhookMessage message = new WebhookMessageBuilder()
                                .setUsername(currentUser.getActiveCharecter().name) // use this username
                                .setAvatarUrl(currentUser.getActiveCharecter().avatar) // use this avatar
                                .setContent(e.getMessage().getContentDisplay())
                                .build();
                        client.send(message);

                    }
                    break;
                case "brotherhood-roleplay-threads":
                    charUserDefined = false;
                    currentUser = null;
                    for (sUser person : WarScribe.ServerUsers) {
                        if (person.id.equals(e.getAuthor().getId())) {
                            charUserDefined = true;
                            currentUser = person;
                        }
                    }
                    if (!e.getMessage().isWebhookMessage() && charUserDefined && currentUser != null) {
                        client = WarScribe.GetWebhookultra(2, e.getChannel().asThreadChannel().getIdLong());
                        e.getMessage().delete().queue();
                        WebhookMessage message = new WebhookMessageBuilder()
                                .setUsername(currentUser.getActiveCharecter().name) // use this username
                                .setAvatarUrl(currentUser.getActiveCharecter().avatar) // use this avatar
                                .setContent(e.getMessage().getContentDisplay())
                                .build();
                        client.send(message);

                    }
                    break;
                case "planetary-rp-threads":
                    charUserDefined = false;
                    currentUser = null;
                    for (sUser person : WarScribe.ServerUsers) {
                        if (person.id.equals(e.getAuthor().getId())) {
                            charUserDefined = true;
                            currentUser = person;
                        }
                    }
                    if (!e.getMessage().isWebhookMessage() && charUserDefined && currentUser != null) {
                        client = WarScribe.GetWebhookultra(7, e.getChannel().asThreadChannel().getIdLong());
                        e.getMessage().delete().queue();
                        WebhookMessage message = new WebhookMessageBuilder()
                                .setUsername(currentUser.getActiveCharecter().name) // use this username
                                .setAvatarUrl(currentUser.getActiveCharecter().avatar) // use this avatar
                                .setContent(e.getMessage().getContentDisplay())
                                .build();
                        client.send(message);

                    }
                    break;
                case "space-rp-threads":
                    charUserDefined = false;
                    currentUser = null;
                    for (sUser person : WarScribe.ServerUsers) {
                        if (person.id.equals(e.getAuthor().getId())) {
                            charUserDefined = true;
                            currentUser = person;
                        }
                    }
                    if (!e.getMessage().isWebhookMessage() && charUserDefined && currentUser != null) {
                        client = WarScribe.GetWebhookultra(8, e.getChannel().asThreadChannel().getIdLong());
                        e.getMessage().delete().queue();
                        WebhookMessage message = new WebhookMessageBuilder()
                                .setUsername(currentUser.getActiveCharecter().name) // use this username
                                .setAvatarUrl(currentUser.getActiveCharecter().avatar) // use this avatar
                                .setContent(e.getMessage().getContentDisplay())
                                .build();
                        client.send(message);

                    }
                    break;
                default:
                    break;

            }
            client.close();
            ;

        } else {

        }
    }

    @Override
    public void onGenericUpdate(UpdateEvent<?, ?> event) {
        try {
            WarScribe.UpdateAllUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
