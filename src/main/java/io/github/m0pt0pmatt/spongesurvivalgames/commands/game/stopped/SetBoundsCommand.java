/*
 * This file is part of SpongeSurvivalGamesPlugin, licensed under the MIT License (MIT).
 *
 * Copyright (c) Matthew Broomfield <m0pt0pmatt17@gmail.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.m0pt0pmatt.spongesurvivalgames.commands.game.stopped;

import io.github.m0pt0pmatt.spongesurvivalgames.BukkitSurvivalGamesPlugin;
import io.github.m0pt0pmatt.spongesurvivalgames.commands.CommandArgs;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * Command to set the bounds of a game
 */
public class SetBoundsCommand extends StoppedCommand {

    @Override
    public boolean execute(CommandSender sender, Map<String, String> arguments) {

        if (!super.execute(sender, arguments)) {
            return false;
        }

        if (
                !arguments.containsKey(CommandArgs.XMIN) ||
                        !arguments.containsKey(CommandArgs.XMAX) ||
                        !arguments.containsKey(CommandArgs.ZMIN) ||
                        !arguments.containsKey(CommandArgs.ZMAX)
                ) {
            sender.sendMessage("Missing one or more bounds.");
            return false;
        }

        String xMinString = arguments.get(CommandArgs.XMIN);
        String zMinString = arguments.get(CommandArgs.ZMIN);
        String xMaxString = arguments.get(CommandArgs.XMAX);
        String zMaxString = arguments.get(CommandArgs.ZMAX);

        int xMin, xMax, zMin, zMax;
        try {
            xMin = Integer.parseInt(xMinString);
            zMin = Integer.parseInt(zMinString);
            xMax = Integer.parseInt(xMaxString);
            zMax = Integer.parseInt(zMaxString);
        } catch (NumberFormatException e) {
            sender.sendMessage("Unable to convert from String to Integer");
            return false;
        }

        BukkitSurvivalGamesPlugin.survivalGameMap.get(id).setBounds(xMin, xMax, zMin, zMax);
        sender.sendMessage("Bounds set for game \"" + id + "\".");

        return true;
    }
}
