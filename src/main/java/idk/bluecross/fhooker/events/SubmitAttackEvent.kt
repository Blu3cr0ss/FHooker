package idk.bluecross.fhooker.events

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.logic.startAttack
import idk.bluecross.fhooker.logic.stopAttack
import idk.bluecross.fhooker.util.get
import idk.bluecross.fhooker.util.log
import idk.bluecross.fhooker.util.setText
import idk.bluecross.fhooker.util.showError
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.text.Text

object SubmitAttackEvent {
    val submitButton = get("submitAttackButton") as Button
    val isRunningIndicator = get("isRunningText") as Text
    var attackingRn = false;

    @JvmStatic
    fun start() {
        submitButton.setOnAction {
            attackingRn = !attackingRn
            if (attackingRn) {
                Globals.webhook = FieldChangedEvent.hookField.text
                setText()
                startAttack()
                submitButton.text = "Stop attack"
                isRunningIndicator.text = "Attack is in progress..."
                disableFields(true)
            } else {
                stopAttack()
                submitButton.text = "Attack"
                isRunningIndicator.text = ""
                disableFields(false)
                showError(false, "")
            }
        }
    }

    fun disableFields(x: Boolean) {
        FieldChangedEvent.delaySlider.isDisable = x
        FieldChangedEvent.hookField.isDisable = x
        FieldChangedEvent.spamWithGitButton.isDisable = x
        FieldChangedEvent.spamTextField.isDisable = x
        FieldChangedEvent.spamWithDiscordButton.isDisable = x
        FieldChangedEvent.proxyButton.isDisable = x
    }

}