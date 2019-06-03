package de.jensklingenberg.jetpackcomposeplayground

import ProgressButton
import android.content.Context
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.Column
import androidx.ui.layout.MainAxisAlignment
import androidx.ui.layout.Padding
import androidx.ui.material.Button
import androidx.ui.material.themeTextStyle


const val bonesKey = "bones"


@Model
class Bones {

    var count = getPreferences(Context.MODE_PRIVATE).getInt(bonesKey, 0)
        set(value) {
            if (value >= 0) {
                field = value
                save()
            }
        }

    fun increment() = count++
    fun decrement() = count--


    private fun save() = getPreferences(Context.MODE_PRIVATE).edit().run {
        putInt(bonesKey, count)
        apply()
    }

}

@Composable
fun StartingPage(state: AppState) {
    val bones = Bones()
    Column(mainAxisAlignment = MainAxisAlignment.Center) {
        Text("Bones: ${bones.count}", style = +themeTextStyle { body1 })
        IncreaseBonesButton(bones, state)

    }
    BottomLeft {
        Button(text = "Reset Bones", onClick = { bones.count = 0 })
    }
    BottomRight {
        Button(text = "Reduce Nones", onClick = { bones.decrement() })
    }


}

const val bonesForDogAppearing = 10


@Composable
fun IncreaseBonesButton(bones: Bones, state: AppState) {
    //TODO: put glow around the button before the first time the player clicks
    ProgressButton(
        progress = Math.min(1f, bones.count / bonesForDogAppearing.toFloat()),
        text = "Dig for bones",
        onClick = {
            bones.increment()
            if (bones.count == bonesForDogAppearing) {
//                state.showDialog {
//                    BodyText("amar")
////                    Column{
////                        Padding(bottom = 20.dp){
////                            BodyText(text = "A dog appears out of the bushes. It appears he wants some of your bones.")
////                        }
////
////                        Padding(bottom = 10.dp){
////                            Button(text = "Give him a bone",onClick = {
////                                state.hideDialog()
////                                state.showDialog(dismissible = true) {
////                                    BodyText("The dog happily gnaws on the bones. It looks like you made a friend.")
////                                }
////                            })
////                        }
////
////                        Button(text = "Give him all the bones", onClick = {
////                            state.hideDialog()
////                            state.showDialog(dismissible = true) {
////                                BodyText("The dog happily gnaws on one of the bones and buries the rest. It looks like you made a friend.")
////                            }
////                        })
////
////                    }
//
//                }
            }
        }
    )

}