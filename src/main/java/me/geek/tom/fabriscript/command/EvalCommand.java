package me.geek.tom.fabriscript.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.geek.tom.fabriscript.script.ScriptExecutor;
import me.geek.tom.fabriscript.script.timer.ScriptTimerGroup;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public class EvalCommand {

    private static final EvalCommand CMD = new EvalCommand();

    public static ArgumentBuilder<ServerCommandSource, ?> register(CommandDispatcher<ServerCommandSource> dispatcher) {
        return CommandManager.literal("eval")
                .requires(sender -> sender.hasPermissionLevel(4))
                .then(CommandManager.argument("script", StringArgumentType.greedyString())
                        .executes(CMD::execute)
                );
    }

    private int execute(CommandContext<ServerCommandSource> ctx) {
        String script = getString(ctx, "script");
        ScriptTimerGroup timers = ScriptExecutor.evalString(script, ctx);
        if (timers != null)
            ctx.getSource().sendFeedback(new TranslatableText("fabriscript.script.executed", "<cmd>", timers.getTotalTime(), timers.getSetupTime(), timers.getEvalTime())
                    .styled(s->s.withColor(Formatting.GREEN)), false);
        return 0;
    }
}
