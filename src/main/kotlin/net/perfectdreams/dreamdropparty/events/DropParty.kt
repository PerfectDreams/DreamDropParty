package net.perfectdreams.dreamdropparty.events

import com.okkero.skedule.SynchronizationContext
import com.okkero.skedule.schedule
import net.perfectdreams.dreamcore.DreamCore
import net.perfectdreams.dreamcore.eventmanager.ServerEvent
import net.perfectdreams.dreamcore.utils.DreamUtils
import net.perfectdreams.dreamcore.utils.scheduler
import net.perfectdreams.dreamdropparty.DreamDropParty
import net.perfectdreams.dreamdropparty.utils.toChatColor
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import net.perfectdreams.dreamcore.utils.chance
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

class DropParty(val m: DreamDropParty) : ServerEvent("Drop Party", "/dropparty") {

    init {
        this.requiredPlayers = 25
        this.discordAnnouncementRole = "539979402143072267"
    }

    override fun getWarmUpAnnouncementMessage(idx: Int): Any {
        return "${DreamDropParty.PREFIX} Se preparem que o evento Drop Party começará em $idx segundos! §6/dropparty"
    }

    override fun preStart() {
        running = true

        countdown()
    }

    override fun start() {
        val world = Bukkit.getWorld(m.config.spawn.world)

        Bukkit.broadcastMessage("${DreamDropParty.PREFIX} O evento Drop Party começou! §6/dropparty")

        val color = BarColor.values().random()
        val bossBar = Bukkit.createBossBar("${color.toChatColor()}§lDrop Party!", color, BarStyle.SEGMENTED_20)

        scheduler().schedule(m) {
            repeat(15) {
                val color = BarColor.values().random()
                bossBar.color = color
                bossBar.title = "${color.toChatColor()}§lDrop Party!"

                world.players.forEach {
                    bossBar.addPlayer(it)
                }

                val location = m.config.spawn.toLocation()
                val x = DreamUtils.random.nextDouble(-10.0, 11.0)
                val z = DreamUtils.random.nextDouble(-10.0, 11.0)
                location.add(x, 2.0, z)

                switchContext(SynchronizationContext.SYNC)
                m.items.forEach { item, chance ->
                    repeat(world.players.size) {
                        if (chance(chance)) {
                            location.world.dropItem(location, item)
                        }
                    }
                }
                switchContext(SynchronizationContext.ASYNC)

                bossBar.progress -= 0.06
                waitFor(20)
            }

            bossBar.removeAll()
            Bukkit.broadcastMessage("${DreamDropParty.PREFIX} Evento Drop Party acabou! Obrigado a todos que participaram!")

            running = false

            waitFor(100)

            switchContext(SynchronizationContext.SYNC)

            world.players.forEach {
                it.teleport(DreamCore.dreamConfig.spawn)
            }

            world.entities.filter { it.type == EntityType.DROPPED_ITEM }.forEach {
                it.remove()
            }
        }
    }
}