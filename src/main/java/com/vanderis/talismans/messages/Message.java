package com.vanderis.talismans.messages;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

@UtilityClass
public class Message {

    // Message(s)
    public void sendMessages(CommandSender player, List<String> messages) {
        messages.forEach(message -> player.sendMessage(Color.color(message)));
    }

    // Message(s)
    public void sendMessagesToAll(List<String> messages) {
        messages.forEach(Message::sendMessageToAll);
    }

    // Message
    public void sendMessage(CommandSender player, String message) {
        player.sendMessage(Color.color(message));
    }

    // Message
    public void sendMessageToAll(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> sendMessage(player, message));
    }

    // Message
    public void sendMessage(Player player, String message) {
        player.sendMessage(Color.color(message));
    }

    // Message(s) - placeholders replacer
    public void sendMessages(CommandSender player, List<String> messages, Map<String, String> placeholderAndValue) {
        messages.forEach(message -> player.sendMessage(Color.color(Placeholder.replacePlaceholders(message, placeholderAndValue))));
    }

    // Message(s) - placeholders replacer
    public void sendMessagesToAll(List<String> messages, Map<String, String> placeholderAndValue) {
        messages.forEach(message -> sendMessageToAll(message, placeholderAndValue));
    }

    // Message - placeholders replacer
    public void sendMessage(CommandSender player, String message, Map<String, String> placeholderAndValue) {
        player.sendMessage(Color.color(Placeholder.replacePlaceholders(message, placeholderAndValue)));
    }

    // Message - placeholders replacer
    public void sendMessageToAll(String message, Map<String, String> placeholderAndValue) {
        Bukkit.getOnlinePlayers().forEach(player -> sendMessage(player, message, placeholderAndValue));
    }

    // Message - placeholders replacer
    public void sendMessage(Player player, String message, Map<String, String> placeholderAndValue) {
        player.sendMessage(Color.color(Placeholder.replacePlaceholders(message, placeholderAndValue)));
    }


}