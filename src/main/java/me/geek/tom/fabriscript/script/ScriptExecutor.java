package me.geek.tom.fabriscript.script;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.geek.tom.fabriscript.script.impl.ContextImpl;
import me.geek.tom.fabriscript.script.timer.ScriptTimerGroup;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ScriptExecutor {

    private static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument") // Plan to add a whitelist at some point lol.
    private static final List<String> ALLOWED_CLASSES = Arrays.asList(
            ""
    );

    public static ScriptTimerGroup loadAndExecScript(String name, String args, CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        // Initialise some paths.
        File scriptDir = new File(ctx.getSource().getMinecraftServer().getRunDirectory(), "fabriscript");
        String scriptFileName = name.endsWith(".js") ? name : name + ".js";
        File script  = new File(scriptDir, scriptFileName);

        // Only allow scripts to be run from the correct dir, prevent some security issues on the server.
        if (!scriptDir.equals(script.getAbsoluteFile().getParentFile())) {
            String first = "     USER: " + ctx.getSource().getName() + " ATTEMPTED TO LOAD A SCRIPT FROM OUTSIDE THE fabriscript DIRECTORY!     ";
            String second = "     THIS IS NOT NORMAL BEHAVIOR! THE FILE THEY ATTEMPTED TO ACCESS WAS: " + script.getAbsolutePath() + "     ";
            int len = Math.max(first.length(), second.length());
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < len; i++) {
                stars.append("*");
            }
            LOGGER.warn(stars);
            LOGGER.warn(first);
            LOGGER.warn(second);
            LOGGER.warn(stars);
            return null;
        }

        // Initialise a set of timers.
        ScriptTimerGroup timers = new ScriptTimerGroup();

        // Setup the script engine.
        timers.startSetup();
        Context cx = Context.enter();
        cx.setClassShutter(s ->
                ALLOWED_CLASSES.stream().anyMatch(s::startsWith));
        Scriptable scope = cx.initStandardObjects();

        // Configure the context.
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        ScriptableObject.putProperty(scope, "context", new ContextImpl(player));
        ScriptableObject.putProperty(scope, "args", args);

        timers.startEval();
        // Read and attempt to execute the script.
        try (FileReader reader = new FileReader(script)) {
            // Execute the script
            cx.evaluateReader(scope, reader, scriptFileName, 0, null);

            timers.complete();
            return timers;
        } catch (FileNotFoundException e) {
            ctx.getSource().sendError(new TranslatableText("fabriscript.script.notfound", name));
        } catch (IOException e) {
            ctx.getSource().sendError(new TranslatableText("fabriscript.script.loaderror", e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            ctx.getSource().sendError(new TranslatableText("fabriscript.script.loaderror", e.getMessage()));
            return null;
        } finally {
            Context.exit();
        }
        return null;
    }

    public static ScriptTimerGroup evalString(String script, CommandContext<ServerCommandSource> ctx) {
        try {
            // Initialise a set of timers.
            ScriptTimerGroup timers = new ScriptTimerGroup();

            timers.startSetup();

            // Initialise the context.
            Context cx = Context.enter();
            cx.setClassShutter(s ->
                    ALLOWED_CLASSES.stream().anyMatch(s::startsWith));
            Scriptable scope = cx.initStandardObjects();

            // Configure the context.
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            ScriptableObject.putProperty(scope, "context", new ContextImpl(player));

            timers.startEval();

            // Execute the script
            Object ret = cx.evaluateString(scope, script, "<cmd>", 0, null);
            timers.complete();

            ctx.getSource().sendFeedback(new TranslatableText("fabriscript.script.evalres", Context.toString(ret)), false);
            return timers;
        } catch (Exception e) {
            return null;
        } finally {
            Context.exit();
        }
    }
}
