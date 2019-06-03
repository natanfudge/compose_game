package de.jensklingenberg.jetpackcomposeplayground


import ProgressButton
import androidx.compose.*
import androidx.ui.core.*
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.borders.*
import androidx.ui.material.surface.*

class Dialog(var active: Boolean = false, @Children var children: @Composable() () -> Unit = {})


@Model
class AppState(private val dialog: Dialog) {
    fun showDialog(dismissible: Boolean = false, @Children children: @Composable() () -> Unit) {
        dialog.children = {
            Padding(left = 24.dp, right = 24.dp, bottom = 28.dp, top = 28.dp) {
                children()
            }
            if (dismissible) {
                Padding(10.dp) {
                    BottomRight {
                        Button(text = "OK", onClick = this::hideDialog)
                    }
                }
            }
        }
        dialog.active = true
    }

    fun dialogIsActive() = dialog.active

    val getDialogChildren get() = dialog.children

    fun hideDialog() {
        dialog.active = false
    }
}


@Composable
fun MyComposeApp() {
    val state = AppState(Dialog())

    CraneWrapper {
        MaterialTheme {
            Stack {
                //TODO: allow this unconditionally when stack crash is fixed
                if (!state.dialogIsActive()) {
                    ProgressButton(text = "asdf", onClick = {
                        state.showDialog {
                            BodyText("asdf")
                        }
                    })
                }
//                if (!state.dialogIsActive()) {
////                    StartingPage(state)
//                    ProgressButton(
////                        progress = Math.min(1f, bones.count / bonesForDogAppearing.toFloat()),
//                        progress = 0.5f,
//                        text = "Dig for bones",
//                        onClick = {
//                            state.showDialog {
//                                BodyText("asdf")
//                            }
//                            //                            bones.increment()
////                            if (bones.count == bonesForDogAppearing) {
//
////                            }
//                        }
//                    )
//                }


                if (state.dialogIsActive()) {

                    Card(color = (+themeColor { onSurface }).withOpacity(32)){BodyText("")}


                    Center {
                        ConstrainedBox(constraints = DpConstraints.tightConstraintsForWidth(280.dp)) {
                            Card(shape = RoundedRectangleBorder(borderRadius = BorderRadius.circular(16f))) {
                                Column(mainAxisSize = MainAxisSize.Min) {
                                    state.getDialogChildren()
                                }
                            }
                        }

                    }
                }
            }


        }


    }

}







