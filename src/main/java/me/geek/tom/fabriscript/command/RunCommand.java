package me.geek.tom.fabriscript.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.geek.tom.fabriscript.script.ScriptLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public class RunCommand {

    private static final RunCommand CMD = new RunCommand();

    public static ArgumentBuilder<ServerCommandSource, ?> register(CommandDispatcher<ServerCommandSource> dispatcher) {
        return CommandManager.literal("run")
                .requires(s->s.hasPermissionLevel(4))
                .then(CommandManager.argument("script", StringArgumentType.string())
                        .executes(ctx -> CMD.run(ctx, ""))
                        .then(CommandManager.argument("args", StringArgumentType.greedyString())
                            .executes(ctx -> CMD.run(ctx, getString(ctx, "args")))));
    }

    public int run(CommandContext<ServerCommandSource> ctx, String args) throws CommandSyntaxException {
        String scriptName = getString(ctx, "script");
        long time = ScriptLoader.loadAndExecScript(scriptName, args, ctx);
        if (time != -1L)
            ctx.getSource().sendFeedback(new TranslatableText("fabriscript.script.executed", scriptName, time)
                    .styled(s->s.withColor(Formatting.GREEN)), false);
        return 0;
    }
}
