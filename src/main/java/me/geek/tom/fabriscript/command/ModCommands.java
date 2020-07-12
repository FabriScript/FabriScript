package me.geek.tom.fabriscript.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralCommandNode<ServerCommandSource> node = dispatcher.register(CommandManager.literal("fabriscript")
                    .then(HelpCommand.register(dispatcher))
                    .then(RunCommand.register(dispatcher))
                    .executes(HelpCommand.CMD)
            );
            dispatcher.register(CommandManager.literal("script").redirect(node)); // Alias '/script' to '/fabriscript'
        });
    }
}
