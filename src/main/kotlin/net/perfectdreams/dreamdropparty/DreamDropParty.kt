package net.perfectdreams.dreamdropparty

import net.perfectdreams.dreamcore.DreamCore
import net.perfectdreams.dreamcore.utils.DreamUtils
import net.perfectdreams.dreamcore.utils.KotlinPlugin
import net.perfectdreams.dreamdropparty.commands.DropPartyCommand
import net.perfectdreams.dreamdropparty.events.DropParty
import net.perfectdreams.dreamdropparty.utils.LocationWrapper
import org.bukkit.inventory.ItemStack
import java.io.File
import org.bukkit.Material


class DreamDropParty : KotlinPlugin() {

    companion object {
        const val PREFIX = "§8[§a§lD§b§lr§5§lo§c§lp§2§lP§3§la§d§lr§4§lt§6§ly§8]§e"
    }

    val items = mutableMapOf<ItemStack, Double>()

    lateinit var config: Config
    lateinit var dropParty: DropParty

    override fun softEnable() {
        super.softEnable()

        this.dataFolder.mkdirs()

        val file = File(this.dataFolder, "config.json")
        if (!file.exists()) {
            file.createNewFile()
            file.writeText(DreamUtils.gson.toJson(Config()))
        }

        config = DreamUtils.gson.fromJson(file.readText(), Config::class.java)
        dropParty = DropParty(this)

        registerCommand(DropPartyCommand(this))

        DreamCore.INSTANCE.dreamEventManager.events.add(dropParty)

        items[ItemStack(Material.PUMPKIN, 4)] = 4.0
        items[ItemStack(Material.MELON, 4)] = 4.0
        items[ItemStack(Material.EXPERIENCE_BOTTLE, 1)] = 2.0
        items[ItemStack(Material.GLOWSTONE, 5)] = 1.0
        items[ItemStack(Material.NAME_TAG, 5)] = 1.0
        items[ItemStack(Material.PACKED_ICE, 4)] = 2.0
        items[ItemStack(Material.CAKE, 4)] = 1.0
        items[ItemStack(Material.BROWN_MUSHROOM, 4)] = 1.0

        items[ItemStack(Material.WOODEN_SWORD)] = 3.0
        items[ItemStack(Material.WOODEN_PICKAXE)] = 3.0
        items[ItemStack(Material.WOODEN_AXE)] = 3.0
        items[ItemStack(Material.WOODEN_SHOVEL)] = 3.0

        items[ItemStack(Material.WOODEN_SWORD)] = 3.0
        items[ItemStack(Material.WOODEN_PICKAXE)] = 3.0
        items[ItemStack(Material.WOODEN_AXE)] = 3.0
        items[ItemStack(Material.WOODEN_SHOVEL)] = 3.0

        items[ItemStack(Material.STONE_SWORD)] = 2.5
        items[ItemStack(Material.STONE_PICKAXE)] = 2.5
        items[ItemStack(Material.STONE_AXE)] = 2.5
        items[ItemStack(Material.STONE_SHOVEL)] = 2.5

        items[ItemStack(Material.IRON_SWORD)] = 2.0
        items[ItemStack(Material.IRON_PICKAXE)] = 2.0
        items[ItemStack(Material.IRON_AXE)] = 2.0
        items[ItemStack(Material.IRON_SHOVEL)] = 2.0
        items[ItemStack(Material.IRON_SWORD)] = 2.0
        items[ItemStack(Material.IRON_PICKAXE)] = 2.0
        items[ItemStack(Material.IRON_AXE)] = 2.0
        items[ItemStack(Material.IRON_SHOVEL)] = 2.0
        items[ItemStack(Material.SHEARS)] = 2.0
        items[ItemStack(Material.IRON_INGOT)] = 2.0

        items[ItemStack(Material.GOLDEN_SWORD)] = 1.5
        items[ItemStack(Material.GOLDEN_PICKAXE)] = 1.55
        items[ItemStack(Material.GOLDEN_AXE)] = 1.5
        items[ItemStack(Material.GOLDEN_SHOVEL)] = 1.5
        items[ItemStack(Material.GOLDEN_HELMET)] = 1.5
        items[ItemStack(Material.GOLDEN_PICKAXE)] = 1.5
        items[ItemStack(Material.GOLDEN_AXE)] = 1.5
        items[ItemStack(Material.GOLDEN_SHOVEL)] = 1.5
        items[ItemStack(Material.GOLD_INGOT)] = 1.5

        items[ItemStack(Material.DIAMOND_SWORD)] = 1.0
        items[ItemStack(Material.DIAMOND_PICKAXE)] = 1.0
        items[ItemStack(Material.DIAMOND_AXE)] = 1.0
        items[ItemStack(Material.DIAMOND_SHOVEL)] = 1.0
        items[ItemStack(Material.DIAMOND_HELMET)] = 1.0
        items[ItemStack(Material.DIAMOND_PICKAXE)] = 1.0
        items[ItemStack(Material.DIAMOND_AXE)] = 1.0
        items[ItemStack(Material.DIAMOND_SHOVEL)] = 1.0
        items[ItemStack(Material.DIAMOND)] = 1.0

        items[ItemStack(Material.SPONGE)] = 1.0

        items[ItemStack(Material.EMERALD)] = 0.75
    }

    override fun softDisable() {
        super.softDisable()

        DreamCore.INSTANCE.dreamEventManager.events.remove(dropParty)
    }

    class Config(var spawn: LocationWrapper = LocationWrapper("world", 0.0, 0.0, 0.0, 0f, 0f))
}