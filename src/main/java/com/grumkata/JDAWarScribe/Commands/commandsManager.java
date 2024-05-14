package com.grumkata.JDAWarScribe.Commands;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.WebhookClientBuilder;
import com.grumkata.JDAWarScribe.DataManagers.persona;
import com.grumkata.JDAWarScribe.DataManagers.sUser;
import com.grumkata.JDAWarScribe.WarScribe;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.lang.Math;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class commandsManager extends ListenerAdapter {
    WebhookClient client = WarScribe.GetWebhook();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        switch (command) {
            case "roll":
                boolean defer = true;
                if (event.getOption("descreit") != null) {
                    defer = event.getOption("descreit").getAsBoolean();
                }
                event.deferReply(defer).queue();
                String rollmessage = "";
                int DiceTotal = 0;
                for (int i = 0; i < event.getOption("number").getAsInt(); i++) {
                    int dRollTemp = WarScribe.rollDice(event.getOption("value").getAsInt(), 1);
                    rollmessage += "[" + dRollTemp + "]\n";
                    DiceTotal += dRollTemp;
                }
                rollmessage += "your dice total is " + DiceTotal + "!";
                event.getHook().sendMessage(rollmessage).queue();
                break;
            case "connect":
                event.deferReply(true).queue();
                for (sUser person : WarScribe.ServerUsers) {
                    if (person.id.equals(event.getUser().getId())) {
                        event.getHook().sendMessage("you  already exist in the user list").queue();
                        return;
                    }
                }
                WarScribe.ServerUsers.add(new sUser(event.getUser().getId(), event.getGuild()));
                event.getHook().sendMessage("you have now been added to the the user list").queue();
                break;
            case "charecters":
                boolean charUserDefined = false;
                sUser currentUser = null;
                for (sUser person : WarScribe.ServerUsers) {
                    if (person.id.equals(event.getUser().getId())) {
                        charUserDefined = true;
                        currentUser = person;
                    }
                }
                if (!charUserDefined) {
                    event.getHook().sendMessage("you have connected to the server user list yet. please use the /connect command then try again").queue();
                    return;
                }
                switch (event.getOption("command").getAsString()) {
                    case "new":
                        if (currentUser.getCharecters().length >= 24) {
                            event.reply("you currently are at the maximum allowed charecters.").setEphemeral(true).queue();
                            return;
                        }
                        TextInput personaName = TextInput.create("name", "Name", TextInputStyle.SHORT)
                                .setPlaceholder("name of your new charecter")
                                .setMinLength(1)
                                .setMaxLength(40) // or setRequiredRange(10, 100)
                                .build();
                        TextInput personaAvatar = TextInput.create("avatar", "Avatar url", TextInputStyle.PARAGRAPH)
                                .setPlaceholder("avatar url of your new charecter.\nignore if you wish to use your current discord avatar. ")
                                .setMinLength(1)
                                .setMaxLength(2083)
                                .setPlaceholder(event.getUser().getAvatarUrl())
                                .build();
                        Modal personaCreationModal = Modal.create("permakemodal", "Make Your New Charecter")
                                .addComponents(ActionRow.of(personaName), ActionRow.of(personaAvatar))
                                .build();

                        event.replyModal(personaCreationModal).queue();
                        break;
                    case "help":
                        event.deferReply(true).queue();
                        event.getHook().sendMessage("the command HELP is currently W.I.P").queue();
                        break;
                    case "delete":
                        if (currentUser == null) {
                            return;
                        }
                        if (currentUser.personLength() == 0) {
                            event.reply("you have not made any charecters to delete").setEphemeral(true).queue();
                        }
                        ArrayList<SelectOption> charMenuOptionsdelete = new ArrayList<SelectOption>();
                        for (int i = 1; i < currentUser.personLength() + 1; i++) {
                            charMenuOptionsdelete.add(SelectOption.of((currentUser.getCharecters())[i - 1].name, "" + i));
                        }
                        event.reply("Choose the charecter you want to delete").setEphemeral(true)
                                .addActionRow(
                                        StringSelectMenu.create("delete-charecter").addOptions(charMenuOptionsdelete).build()
                                ).queue();
                        break;
                    case "switch":
                        if (currentUser == null) {
                            return;
                        }
                        ArrayList<SelectOption> charMenuOptions = new ArrayList<SelectOption>();
                        charMenuOptions.add(SelectOption.of(currentUser.nickname, "0"));
                        for (int i = 1; i < currentUser.personLength() + 1; i++) {
                            charMenuOptions.add(SelectOption.of((currentUser.getCharecters())[i - 1].name, "" + i));
                        }
                        event.reply("Choose the charecter you want to switch to").setEphemeral(true)
                                .addActionRow(
                                        StringSelectMenu.create("switch-charecter").addOptions(charMenuOptions).build()
                                ).queue();
                        break;
                    case "change name":
                        event.deferReply(true).queue();
                        event.getHook().sendMessage("the command CHANGE NAME is currently W.I.P").queue();
                        break;
                    case "change avatar":
                        event.deferReply(true).queue();
                        event.getHook().sendMessage("the command CHANGE AVATAR is currently W.I.P").queue();
                        break;
                    default:
                        event.deferReply(true).queue();
                        event.getHook().sendMessage("the command you put in was invalid use help to get a full list of valid commands").queue();
                        break;
                }
                break;
            case "say":
                event.deferReply(true).queue();
                charUserDefined = false;
                currentUser = null;
                for (sUser person : WarScribe.ServerUsers) {
                    if (person.id.equals(event.getUser().getId())) {
                        charUserDefined = true;
                        currentUser = person;
                    }
                }
                if (charUserDefined && currentUser != null) {
                    Webhook whPlaceHolder;
                    List<Webhook>  WHT = event.getChannel().asTextChannel().retrieveWebhooks().complete();
                    if(WHT.size()>=1){
                        whPlaceHolder = WHT.get(0);
                    }else{
                        whPlaceHolder = (Webhook) (event.getChannel().asTextChannel().createWebhook(event.getChannel().getName()+"-hook").complete());
                    }

                    WebhookClientBuilder builder = new WebhookClientBuilder(whPlaceHolder.getUrl());
                    builder.setThreadFactory((job) -> {
                        Thread thread = new Thread(job);
                        thread.setName("RP BOT");
                        thread.setDaemon(true);
                        return thread;
                    });
                    builder.setWait(true);
                    WebhookClient client = builder.build();

                    WebhookMessage message = new WebhookMessageBuilder()
                            .setUsername(currentUser.getActiveCharecter().name) // use this username
                            .setAvatarUrl(currentUser.getActiveCharecter().avatar) // use this avatar
                            .setContent(event.getOption("message").getAsString())
                            .build();
                    client.send(message);

                }
                event.getHook().sendMessage("message sent").complete();
                event.getHook().deleteOriginal().queue();
                break;
            case "combat":
                event.deferReply(true).queue();
                if(event.getChannel().getName().equals("combat-channel")) {
                    int A1num = event.getOption("army1num").getAsInt();
                    int A2num = event.getOption("army2num").getAsInt();
                    int A1val = 0;
                    int A2val = 0;
                    int constAdv = 0;
                    double multAdv = 1;
                    String A1name = "imperial";
                    String A2name = "caliphate";
                    if (event.getOption("a1dif") != null) {
                        constAdv += event.getOption("a1dif").getAsInt();
                        A1val = event.getOption("a1dif").getAsInt();
                    }
                    if (event.getOption("a2dif") != null) {
                        constAdv += event.getOption("a2dif").getAsInt();
                        A2val = event.getOption("a2dif").getAsInt();
                    }
                    if (event.getOption("a1name") != null) {
                        A1name = event.getOption("a1name").getAsString();
                    }
                    if (event.getOption("a2name") != null) {
                        A2name = event.getOption("a2name").getAsString();
                    }
                    A1val += RollDie(event.getOption("army1num").getAsInt(), 100);
                    A2val += RollDie(event.getOption("army2num").getAsInt(), 100);
                    if (event.getOption("multa1dif") != null) {
                        multAdv *= event.getOption("multa1dif").getAsDouble();
                        A1val *= event.getOption("multa1dif").getAsDouble();
                    }
                    if (event.getOption("multa2dif") != null) {
                        multAdv /= event.getOption("multa2dif").getAsDouble();
                        A2val *= event.getOption("multa2dif").getAsDouble();
                    }
                    if (A1val > A2val) {
                        event.getHook().sendMessage("The " + A1name + " has won over the " + A2name + " with a " + (A1val - A2val) + " point advantage.").queue();

                    } else {
                        event.getHook().sendMessage("The " + A1name + " has lost to the " + A2name + " with a " + (A1val - A2val) + " point disadvantage.").queue();
                    }
                    event.getGuild().getChannelById( TextChannel.class, "1237552118986965032").sendMessage("\n ```LOG:  user " + event.getUser().getName() +
                            " went into combat as the " + A1name + " against the " + A2name + " " + A1num + " on " + A2num +
                            ".\nthe battle point total was " + (A1val - A2val) + " with a total constant advantage of " + constAdv +
                            " and a total mulitplicative advantage of " + multAdv + "```").queue();
                }else{
                    event.getHook().sendMessage("you cant use this command in this channel").queue();
                }
                break;
            case "test":
                event.deferReply(true).queue();

                event.getHook().sendMessage("").queue();
                event.getHook().deleteOriginal().queue();
                break;
            default:
                break;
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<CommandData>();
        commandData.add(Commands.slash("roll", "a simple dice roller")
                .addOption(OptionType.INTEGER, "number", "number of dice you will roll", true)
                .addOption(OptionType.INTEGER, "value", "the type of dice you want to roll", true)
                .addOption(OptionType.BOOLEAN, "descreit", "do you want other to see this message", false)
        );
        commandData.add(Commands.slash("connect", "if not already added adds you to the server user list"));
        commandData.add(Commands.slash("charecters", "these commands are for using charecter avatars")
                .addOption(OptionType.STRING, "command", "here you put in charceter commands use help to get the list", true, true)
        );
        commandData.add(Commands.slash("say", "say something in your current active charecter in Genreal")
                .addOption(OptionType.STRING, "message", "this i what you are going to say", true));

        commandData.add(Commands.slash("combat","decide who wins in combat between army 1 and army 2")
                .addOption(OptionType.INTEGER, "army1num", "the number of soldiers in army 1",true)
                .addOption(OptionType.INTEGER, "army2num", "the number of soldiers in army 1",true)
                .addOption(OptionType.INTEGER, "a1dif", "the overall advantage + or disadvantage - of army1",false)
                .addOption(OptionType.INTEGER, "a2dif", "the overall advantage + or disadvantage - of army2",false)
                .addOption(OptionType.NUMBER, "multa1dif", "the overall advantage + or disadvantage - of army1 multiplied",false)
                .addOption(OptionType.NUMBER, "multa2dif", "the overall advantage + or disadvantage - of army2 multiplied",false)
                .addOption(OptionType.STRING, "a1name", "this is the name your army")
                .addOption(OptionType.STRING, "a2name", "this is the name of your enemy")

        );
        commandData.add(Commands.slash("test", "this command is purely for testing purposes"));

                //imports commands here
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    private String[] slash_words = new String[]{"help", "new", "delete", "switch", "change avatar", "change name"};

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if (event.getName().equals("charecters") && event.getFocusedOption().getName().equals("command")) {
            List<Command.Choice> options = Stream.of(slash_words)
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue())) // only display words that start with the user's current input
                    .map(word -> new Command.Choice(word, word)) // map the words to choices
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        switch (event.getModalId()) {
            case "permakemodal":
                String avatar = event.getUser().getAvatarUrl();
                Image image = null;
                try {
                    image = ImageIO.read(new URL(event.getValue("avatar").getAsString()));
                } catch (IOException e) {

                }
                if (image != null) {
                    avatar = event.getValue("avatar").getAsString();
                }
                for (sUser person : WarScribe.ServerUsers) {
                    if (person.id.equals(event.getUser().getId())) {
                        person.addCharecter(event.getValue("name").getAsString(), avatar);
                        person.curChar++;
                    }
                }
                event.reply("you have made the new charecter " + event.getValue("name").getAsString()).setEphemeral(true).queue();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("switch-charecter")) {
            for (sUser person : WarScribe.ServerUsers) {
                if (person.id.equals(event.getUser().getId())) {
                    if (event.getValues().get(0).equals("0")) {
                        person.curChar = 0;
                        event.reply("You have switched too " + person.getActiveCharecter().name).setEphemeral(true).queue();
                        event.getMessage().delete().queue();
                        return;
                    }
                    for (int i = 1; i < person.personLength() + 1; i++) {
                        if (event.getValues().get(0).equals("" + i)) {
                            person.curChar = i;
                            event.reply("You have switched too " + person.getActiveCharecter().name).setEphemeral(true).queue();
                            event.getMessage().delete().queue();
                            return;
                        }
                    }
                }
            }
        }
        if (event.getComponentId().equals("delete-charecter")) {
            for (sUser person : WarScribe.ServerUsers) {
                if (person.id.equals(event.getUser().getId())) {
                    for (int i = 1; i < person.personLength() + 1; i++) {
                        if (event.getValues().get(0).equals("" + i)) {
                            if (person.curChar == i) {
                                person.curChar = 0;
                            }
                            String temp = person.getCharecters()[i - 1].name;
                            person.removeCharecter(i - 1);
                            event.reply("You have deleted " + temp).setEphemeral(true).queue();
                            event.getMessage().delete().queue();
                            return;
                        }
                    }
                }
            }
        }

    }

    public int RollDie(int DiceNum, int Val )
    {
        int value =0;
        for(int i=0;i<DiceNum;i++){value +=(int)(Math.random()*Val+1);}
        return value;
    }

}
