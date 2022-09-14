package idk.bluecross.fhooker.events

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.util.get
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

    @JvmStatic
    fun start() {
        delaySlider.valueProperty().addListener { _, _, newValue ->
            delayIndicator.text = ceil(newValue as Double).toString().removeSuffix(".0")
            Globals.spamDelay = delayIndicator.text.toInt()
        }
        spamWithGitButton.selectedProperty().addListener { _, _, newVal ->
            Globals.useGitLink = newVal
        }
    }

    fun disableFields(x: Boolean) {
        delaySlider.isDisable = x
        hookField.isDisable = x
        spamWithGitButton.isDisable = x
        spamTextField.isDisable = x
    }

    val github = ""
    val discord = "https://discord.gg/frzjeHxKeX"
    fun setText() {
        var result = ""
        if (spamWithDiscordButton.isSelected) {
            result += discord + " --- "
        }
        result += spamTextField.text.replace("\n","  ........ ")
        if (spamWithGitButton.isSelected) {
            result += " --- " + github
        }
        Globals.spamText = result
    }
}