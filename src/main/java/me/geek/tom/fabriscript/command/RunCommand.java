package me.geek.tom.fabriscript.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.geek.tom.fabriscript.script.ScriptExecutor;
import me.geek.tom.fabriscript.script.timer.ScriptTimerGroup;
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

    private int run(CommandContext<ServerCommandSource> ctx, String args) throws CommandSyntaxException {
        String scriptName = getString(ctx, "script");
        ScriptTimerGroup timers = ScriptExecutor.loadAndExecScript(scriptName, args, ctx.getSource());
        if (timers != null)
            ctx.getSource().sendFeedback(new TranslatableText("fabriscript.script.executed", scriptName, timers.getTotalTime(), timers.getSetupTime(), timers.getEvalTime())
                    .styled(s->s.withColor(Formatting.GREEN)), false);
        return 0;
    }
}
