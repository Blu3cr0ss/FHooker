package idk.bluecross.fhooker.events

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.util.errorProxy
import idk.bluecross.fhooker.util.get
import idk.bluecross.fhooker.util.log
import idk.bluecross.fhooker.util.resetProxy
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
    val invalidHookError = get("invalidHookError") as Text
    val proxyField = get("proxyIp") as TextField
    var validHook = false

    val hookValidatorRegex = Regex("^(https://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])*")

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
            proxyField.isDisable = !newVal
            errorProxy.isVisible = newVal
            resetProxy()
        }
        hookField.textProperty().addListener { _, _, newVal ->
            invalidHookError.isVisible = !newVal.replace("\\s", "").matches(hookValidatorRegex) && newVal.isNotEmpty() && !newVal.equals("bebra")
            validHook = !(!newVal.replace("\\s", "").matches(hookValidatorRegex)
                    || newVal.replace("\\s","").isBlank())
            SubmitAttackEvent.submitButton.isDisable = !validHook
            SubmitAttackEvent.deleteButton.isDisable = !validHook
        }
        proxyField.textProperty().addListener { _, _, newVal ->
            Globals.proxy = newVal
            resetProxy()
        }
    }
}