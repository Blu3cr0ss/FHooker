package idk.bluecross.fhooker.util

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.events.FieldChangedEvent
import idk.bluecross.fhooker.events.SubmitAttackEvent
import idk.bluecross.fhooker.events.SubmitAttackEvent.submitButton
import javafx.scene.text.Text
import java.io.File
import java.net.InetSocketAddress
import java.net.Proxy

private var currentProxy = 0
private var thisProxyUsedTimes = 0

private var proxy: Proxy? = Proxy.NO_PROXY
private val proxyFile = File("FHooker", "ProxyList.txt")
val errorProxy = get("invalidProxyError") as Text

fun startProxyService() {
    log("started ProxyService")
}

fun resetProxy() {
    try {
        if (errorText.textProperty().get() == "Proxy error") {
            showError(true, "")
        }
        if (Globals.useProxy) {
            val proxyRegex =
                Regex("\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b:\\d{2,4}")
            if (proxy != null) {
                if ((Globals.proxy.removePrefix("HTTP ").removePrefix("SOCKS ")
                        .matches(proxyRegex) && (Globals.proxy.startsWith("HTTP") || Globals.proxy.startsWith(
                        "SOCKS"
                    )))
                ) {
                    errorProxy.isVisible = false
                    if (FieldChangedEvent.validHook) {
                        submitButton.isDisable = false
                        SubmitAttackEvent.deleteButton.isDisable = false
                    }
                    if (Globals.proxy.startsWith("HTTP ")) {
                        proxy = Proxy(
                            Proxy.Type.HTTP,
                            InetSocketAddress(
                                Globals.proxy.split(" ")[1].split(":")[0],
                                Globals.proxy.split(" ")[1].split(":")[1].toInt()
                            )
                        )
                    } else if (Globals.proxy.startsWith("SOCKS ")) {
                        proxy = Proxy(
                            Proxy.Type.SOCKS,
                            InetSocketAddress(
                                Globals.proxy.split(" ")[1].split(":")[0],
                                Globals.proxy.split(" ")[1].split(":")[1].toInt()
                            )
                        )
                    }
                } else {
                    errorProxy.isVisible = true
                    submitButton.isDisable = true
                    SubmitAttackEvent.deleteButton.isDisable = true
                }
            }
        } else {
            proxy = Proxy.NO_PROXY
            errorProxy.isVisible = false
            if (FieldChangedEvent.validHook) {
                submitButton.isDisable = false
                SubmitAttackEvent.deleteButton.isDisable = false
            }
        }
    } catch (e: Exception) {
        showError(true, "Proxy error")
        e.printStackTrace()
    }
}

fun getProxy(): Proxy? {
    return proxy
}

fun File.createIfNotExist() {
    if (!this.exists()) {
        File(this.parent).mkdirs()
        this.createNewFile()
    }
}