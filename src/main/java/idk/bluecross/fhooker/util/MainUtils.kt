package idk.bluecross.fhooker.util

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.events.FieldChangedEvent

val github = "https://github.com/Bluecross-anarchy/FHooker"
val discord = "https://discord.gg/frzjeHxKeX"
fun setText() {
    var result = ""
    if (FieldChangedEvent.spamWithDiscordButton.isSelected) {
        result += discord + "\n------------------------------------------------------------------------------\n"
    }
    result += FieldChangedEvent.spamTextField.text
    if (FieldChangedEvent.spamWithGitButton.isSelected) {
        result += "\n------------------------------------------------------------------------------\n" + github
    }
    result = result.replace("\n", "\\n")
    Globals.spamText = result
}