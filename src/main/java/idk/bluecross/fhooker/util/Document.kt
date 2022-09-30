package idk.bluecross.fhooker.util

import idk.bluecross.fhooker.Globals
import idk.bluecross.fhooker.events.SubmitAttackEvent
import javafx.scene.Node
import javafx.scene.text.Text

fun get(e:String) : Node{
    return Globals.scene.lookup("#$e")
}

val errorText = get("errorText") as Text
fun showError(show:Boolean,err:String){
    errorText.text = err
    errorText.isVisible = show
    SubmitAttackEvent.isRunningIndicator.isVisible = !show
}