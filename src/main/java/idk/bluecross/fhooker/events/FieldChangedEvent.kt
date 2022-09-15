package idk.bluecross.fhooker.events

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.util.get
import idk.bluecross.fhooker.util.log
import javafx.scene.control.CheckBox
import javafx.scene.control.Slider
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.text.Text
import kotlin.math.ceil

object FieldChangedEvent {
    val hookField = get("hookField") as TextField
    val delaySlider = get("delaySlider") as Slider
    val delayIndicator = get("delayIndicator") as Text
    val spamWithGitButton = get("spamWithGitButton") as CheckBox
    val spamTextField = get("spamTextField") as TextArea
    val spamWithDiscordButton = get("spamWithDiscordButton") as CheckBox
    val proxyButton = get("useProxyButton") as CheckBox

    @JvmStatic
    fun start() {
        delaySlider.valueProperty().addListener { _, _, newValue ->
            delayIndicator.text = ceil(newValue as Double).toString().removeSuffix(".0")
            Globals.spamDelay = delayIndicator.text.toInt()
        }
        spamWithGitButton.selectedProperty().addListener { _, _, newVal ->
            Globals.useGitLink = newVal
        }
        proxyButton.selectedProperty().addListener { _, _, newVal ->
            Globals.useProxy = newVal
        }
    }

    fun disableFields(x: Boolean) {
        delaySlider.isDisable = x
        hookField.isDisable = x
        spamWithGitButton.isDisable = x
        spamTextField.isDisable = x
        spamWithDiscordButton.isDisable = x
        proxyButton.isDisable = x
    }

    val github = "https://github.com/Bluecross-anarchy/FHooker"
    val discord = "https://discord.gg/frzjeHxKeX"
    fun setText() {
        var result = ""
        if (spamWithDiscordButton.isSelected) {
            result += discord + "\n------------------------------------------------------------------------------\n"
        }
        result += spamTextField.text
        if (spamWithGitButton.isSelected) {
            result += "\n------------------------------------------------------------------------------\n" + github
        }
        result = result.replace("\n", "\\n")
        Globals.spamText = result
    }
}