package me.geek.tom.fabriscript.script;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import me.geek.tom.fabriscript.script.impl.ContextImpl;
import me.geek.tom.fabriscript.script.impl.WorldInterfaceImpl;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ScriptLoader {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final List<String> ALLOWED_CLASSES = Arrays.asList(
            ""
    );

    public static long loadAndExecScript(String name, String args, CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        File scriptDir = new File(ctx.getSource().getMinecraftServer().getRunDirectory(), "fabriscript");
        File script  = new File(scriptDir, name.endsWith(".js") ? name : name + ".js");

        // Only allow scripts to be run from the correct dir, prevent some security issues on the server.
        if (!scriptDir.equals(script.getParentFile())) {
            String first = "     USER: " + ctx.getSource().getName() + " ATTEMPTED TO LOAD A SCRIPT FROM OUTSIDE THE fabriscript DIRECTORY!     ";
            String second = "     THIS IS NOT NORMAL BEHAVIOR, THE FILE THEY ATTEMPTED TO ACCESS WAS: " + script + "     ";
            int len = Math.max(first.length(), second.length());
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < len; i++) {
                stars.append("*");
            }
            LOGGER.warn(stars);
            LOGGER.warn(first);
            LOGGER.warn(second);
            LOGGER.warn(stars);
            return 1;
        }

        final NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        final NashornScriptEngine engine = (NashornScriptEngine) factory.getScriptEngine(s ->
                ALLOWED_CLASSES.stream().anyMatch(s::startsWith));
        try (FileReader reader = new FileReader(script)) {
            ScriptTimer timer = new ScriptTimer();
            timer.start();
            engine.eval(reader);
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            engine.invokeFunction("main", new WorldInterfaceImpl(player, player.getEntityWorld()), new ContextImpl(player), args);
            return timer.stop();
        } catch (FileNotFoundException e) {
            ctx.getSource().sendError(new TranslatableText("fabriscript.script.notfound", name));
        } catch (IOException | ScriptException e) {
            ctx.getSource().sendError(new TranslatableText("fabriscript.script.loaderror", e.getMessage()));
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            ctx.getSource().sendError(new TranslatableText("fabriscript.script.mainfail", e.getMessage()));
            e.printStackTrace();
        }
        return -1;
    }
}
