package me.geek.tom.fabriscript.script;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.geek.tom.fabriscript.script.api.MethodConstants;
import me.geek.tom.fabriscript.script.impl.ContextImpl;
import me.geek.tom.fabriscript.script.timer.ScriptTimerGroup;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.javascript.Context;
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

    public static ScriptTimerGroup loadAndExecScript(String name, String args, ServerCommandSource sender) throws CommandSyntaxException {
        // Initialise some paths.
        File scriptDir = new File(sender.getMinecraftServer().getRunDirectory(), "fabriscript");
        String scriptFileName = name.endsWith(".js") ? name : name + ".js";
        File script  = new File(scriptDir, scriptFileName);

        // Only allow scripts to be run from the correct dir, prevent some security issues on the server.
        if (!scriptDir.equals(script.getAbsoluteFile().getParentFile())) {
            String first = "     USER: " + sender.getName() + " ATTEMPTED TO LOAD A SCRIPT FROM OUTSIDE THE fabriscript DIRECTORY!     ";
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

        // Setup the timers.
        timers.startSetup();

        // Initialise the engine.
        Context cx = Context.enter();
        ScriptableObject scope = setup(cx, sender.getPlayer());

        ScriptableObject.putProperty(scope, "args", args);

        timers.startEval();
        // Read and attempt to execute the script.
        try (FileReader reader = new FileReader(script)) {
            // Execute the script
            cx.evaluateReader(scope, reader, scriptFileName, 1, null);

            timers.complete();
            return timers;
        } catch (FileNotFoundException e) {
            sender.sendError(new TranslatableText("fabriscript.script.notfound", name));
        } catch (IOException e) {
            sender.sendError(new TranslatableText("fabriscript.script.loaderror", e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            sender.sendError(new TranslatableText("fabriscript.script.loaderror", e.getMessage()));
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

            // Initialise the engine.
            Context cx = Context.enter();
            ScriptableObject scope = setup(cx, ctx.getSource().getPlayer());

            timers.startEval();

            // Execute the script
            Object ret = cx.evaluateString(scope, script, "<cmd>", 1, null);
            timers.complete();

            ctx.getSource().sendFeedback(new TranslatableText("fabriscript.script.evalres", Context.toString(ret)), false);
            return timers;
        } catch (Exception e) {
            ctx.getSource().sendError(new TranslatableText("fabriscript.script.loaderror", e.getMessage()));
            return null;
        } finally {
            Context.exit();
        }
    }

    /**
     * Configures the class shutter and standard objects.
     *
     * @param cx The context
     * @return The scope
     */
    private static ScriptableObject setup(Context cx, ServerPlayerEntity player) {
        cx.setClassShutter(s ->
                ALLOWED_CLASSES.stream().anyMatch(s::startsWith));
        ScriptableObject scope = cx.initStandardObjects();

        // Initialise js constants
        ScriptableObject.putProperty(scope, "context", new ContextImpl(player));
        scope.defineFunctionProperties(new String[]{

                "sin", "cos", "tan", // Maths functions.

                "perlin2d", "perlin3d", // Noise gen.
                "perlin2d_seeded", "perlin3d_seeded", // Seeded noise gen

        }, MethodConstants.class, ScriptableObject.DONTENUM);

        return scope;
    }
}
