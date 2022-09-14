package idk.bluecross.fhooker.util

import idk.bluecross.fhooker.Globals
import javafx.scene.Node
import javafx.scene.control.TextField

fun get(e:String) : Node{
    return Globals.scene.lookup("#$e")
}