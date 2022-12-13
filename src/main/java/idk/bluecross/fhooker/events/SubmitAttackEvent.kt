package idk.bluecross.fhooker.events

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.logic.startAttack
import idk.bluecross.fhooker.logic.stopAttack
import idk.bluecross.fhooker.util.*
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.scene.text.Text
import java.net.HttpURLConnection
import java.net.URL

object SubmitAttackEvent {
    val submitButton = get("submitAttackButton") as Button
    val isRunningIndicator = get("isRunningText") as Text
    val deleteButton = get("deleteHook") as Button
    val testHook = get("testHook") as Button
    val testResult = get("testResult") as Text
    var testResIsVisible = false
    var isDead = false
    var attackingRn = false

    @JvmStatic
    fun start() {
        deleteButton.setOnAction {
            parseHooksToArray()
            try {
                Globals.webhooks.forEach {
                    testResult.text = "Checking hook..."
                    testResult.isVisible = false
                    isDead = false
                    testResult.fill = Color.BLACK
                    val connection = URL(it).openConnection(getProxy()) as HttpURLConnection
                    connection.connectTimeout = 5000
                    connection.requestMethod = "DELETE"
                    val responseCode = connection.responseCode
                    connection.disconnect()
                    log("tried to delete hook : $it")
                }
                FieldChangedEvent.hookField.text = ""
            } catch (e: Exception) {
            }
        }
        testHook.setOnAction {
            isDead = false
            testResIsVisible = false
            testResult.isVisible = testResIsVisible
            parseHooksToArray()
            try {
                Globals.webhooks.forEach {
                    val connection = URL(it).openConnection(getProxy()) as HttpURLConnection
                    connection.connectTimeout = 5000
                    connection.requestMethod = "GET"
                    val responseCode = connection.responseCode
                    connection.disconnect()
                    if (responseCode == 404) {
                        testResIsVisible = true
                        isDead = true
                        testResult.fill = Color.RED
                        testResult.text = "Webhook is dead"
                        testResult.isVisible = testResIsVisible
                        return@forEach
                    }
                }
                if (!isDead) {
                    testResIsVisible = true
                    isDead = false
                    testResult.fill = Color.GREEN
                    testResult.text = "Webhook is alive"
                    testResult.isVisible = testResIsVisible
                }
            } catch (e: Exception) {
            }
        }
        submitButton.setOnAction {
            isDead = false
            testResult.text = "Checking hook..."
            testResult.isVisible = false
            testResult.fill = Color.BLACK

            attackingRn = !attackingRn
            if (attackingRn) {
//                Globals.webhook = FieldChangedEvent.hookField.
                parseHooksToArray()
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

    fun parseHooksToArray() {
        Globals.webhooks.clear()
        Globals.webhooks.addAll(FieldChangedEvent.hookField.text.replace("\\s", "").split(";").distinct())
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