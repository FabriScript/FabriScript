package me.geek.tom.fabriscript.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class HelpCommand implements Command<ServerCommandSource> {

    public static final HelpCommand CMD = new HelpCommand();

    public static ArgumentBuilder<ServerCommandSource, ?> register(CommandDispatcher<ServerCommandSource> dispatcher) {
        return CommandManager.literal("help")
                .requires(sender->sender.hasPermissionLevel(0))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(new TranslatableText("fabriscript.command.help.feedback"), false);

        return 0;
    }
}
