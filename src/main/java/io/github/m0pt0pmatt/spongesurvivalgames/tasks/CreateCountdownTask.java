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

package io.github.m0pt0pmatt.spongesurvivalgames.tasks;

import org.bukkit.Bukkit;

import io.github.m0pt0pmatt.spongesurvivalgames.BukkitSurvivalGamesPlugin;
import io.github.m0pt0pmatt.spongesurvivalgames.SurvivalGame;
import io.github.m0pt0pmatt.spongesurvivalgames.exceptions.TaskException;

/**
 * Task for counting down the starting time
 */
public class CreateCountdownTask implements SurvivalGameTask {
	
	private static final String playerToken = "[PLAYER]";
	
	private static final String timeToken = "[TIME]";
	
	private static final String colorToken = "[COLOR]";
	
	private static final String[] titleCommands = new String[]{
			"title " + playerToken + " times 10 0 10",
			"title dove_bren title {text:\"" + timeToken + "\",color:\"" + colorToken + "\"}"
	};
	
    @Override
    public void execute(SurvivalGame game) throws TaskException {

        BukkitSurvivalGamesPlugin.getPlayers(game.getPlayerUUIDs()).forEach(player -> {
            for (int i = game.getCountdownTime().get(); i > 0; i--) {
                final int j = i;

                Bukkit.getScheduler().scheduleSyncDelayedTask(
                        BukkitSurvivalGamesPlugin.plugin,
                        () -> {
                        	//player.sendMessage(Integer.toString(j)),
                        	for (String command : titleCommands) {
                        		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
                        				command.replace(playerToken, player.getName())
                        					.replace(timeToken, j + "")
                        					.replace(colorToken, j < 4 ? "dark_red" : "dark_green"));
                        	}
                        },
                        20L * (game.getCountdownTime().get() - i)
                );
            }

            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    BukkitSurvivalGamesPlugin.plugin,
                    () -> {
                    	//player.sendMessage(Integer.toString(j)),
                    	for (String command : titleCommands) {
                    		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
                    				command.replace(playerToken, player.getName())
                    					.replace(timeToken, "Go!")
                    					.replace(colorToken, "dark_red"));
                    	}
                    	game.checkWin();
                    },
                    20L * game.getCountdownTime().get()
            );
        });
    }
}
