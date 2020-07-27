/*
 * FabriScript
 * Copyright (C) 2020 Tom_The_Geek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.geek.tom.fabriscript.ws;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.geek.tom.fabriscript.script.ScriptExecutor;
import me.geek.tom.fabriscript.script.timer.ScriptTimerGroup;
import me.geek.tom.fabriscript.server.ApiPlayer;
import me.geek.tom.fabriscript.server.IFabriScript;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FabriScriptImpl implements IFabriScript {

    private static final Logger LOGGER = LogManager.getLogger();

    private final MinecraftServer server;
    private final File scriptDir;

    public FabriScriptImpl(MinecraftServer server) {
        this.server = server;
        scriptDir = new File(server.getRunDirectory(), "fabriscript");
    }

    @Override
    public boolean receiveScript(String name, String content) {
        File script = new File(scriptDir, name);

        if (!scriptDir.equals(script.getAbsoluteFile().getParentFile())) { // No exploits pls.
            String warning1 = "     SOMEBODY ATTEMPTED TO SAVE A SCRIPT FILE OUTSIDE OF THE SCRIPTS DIR     ";
            String warning2 = "     THEY ATTEMPTED TO WRITE TO:" + script + "     ";
            int count = Math.max(warning1.length(), warning2.length());
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < count; i++) {
                stars.append("*");
            }
            LOGGER.warn(stars);
            LOGGER.warn(warning1);
            LOGGER.warn(warning2);
            LOGGER.warn(stars);
            return false;
        }

        if (!script.exists()) {
            try {
                if (!script.createNewFile())
                    return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(script))) {
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean runScript(String name) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        if (players.size() == 0)
            return false;
        ServerPlayerEntity player = players.get(0);
        ServerCommandSource source = player.getCommandSource();
        try {
            ScriptTimerGroup timers = ScriptExecutor.loadAndExecScript(name, "", source);
            if (timers != null)
                source.sendFeedback(new TranslatableText("fabriscript.script.executed",
                        name, timers.getTotalTime(), timers.getSetupTime(), timers.getEvalTime())
                        .styled(s->s.withColor(Formatting.GREEN)), false);
        } catch (CommandSyntaxException e) {
            source.sendError(Texts.toText(e.getRawMessage()));
            if (e.getInput() != null && e.getCursor() >= 0) {
                int i = Math.min(e.getInput().length(), e.getCursor());
                MutableText msg = (new LiteralText("")).formatted(Formatting.GRAY);
                if (i > 10) {
                    msg.append("...");
                }

                msg.append(e.getInput().substring(Math.max(0, i - 10), i));
                if (i < e.getInput().length()) {
                    Text text = (new LiteralText(e.getInput().substring(i))).formatted(Formatting.RED, Formatting.UNDERLINE);
                    msg.append(text);
                }

                msg.append((new TranslatableText("command.context.here")).formatted(Formatting.RED, Formatting.ITALIC));
                source.sendError(msg);
            }
            return false;
        }

        return true;
    }

    @Override
    public List<ApiPlayer> getPlayerList() {
        return server.getPlayerManager().getPlayerList().stream()
                .map(player -> new ApiPlayer(player.getUuid(), player.getName().asString())).collect(Collectors.toList());
    }
}
