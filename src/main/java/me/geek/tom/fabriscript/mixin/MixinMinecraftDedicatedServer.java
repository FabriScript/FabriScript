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

package me.geek.tom.fabriscript.mixin;

import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftDedicatedServer.class)
public class MixinMinecraftDedicatedServer {

    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "setupServer",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    shift = At.Shift.AFTER, remap = false))
    private void onServerSetup(CallbackInfoReturnable<Boolean> cir) {
        LOGGER.warn("=================| ! WARNING ! |=================");
        LOGGER.warn("*****  This server is running FabriScript!  *****");
        LOGGER.warn("*****  This could allow players to execute  *****");
        LOGGER.warn("*****  any JavaScript files on this device. *****");
        LOGGER.warn("***** This mod has certain protections, but *****");
        LOGGER.warn("*****        they may not be perfect.       *****");
        LOGGER.warn("*****       BE CAREFUL WHO YOU TRUST,       *****");
        LOGGER.warn("*****         YOU HAVE BEEN WARNED!         *****");
        LOGGER.warn("=================================================");
    }
}
