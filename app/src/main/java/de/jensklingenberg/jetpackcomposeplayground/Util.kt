package de.jensklingenberg.jetpackcomposeplayground

import androidx.compose.Children
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.*
import androidx.ui.engine.text.TextAlign
import androidx.ui.engine.text.TextDirection
import androidx.ui.graphics.Color
import androidx.ui.layout.Align
import androidx.ui.layout.Alignment
import androidx.ui.material.themeTextStyle
import androidx.ui.painting.TextStyle
import androidx.ui.rendering.paragraph.TextOverflow

private val DefaultTextAlign: TextAlign = TextAlign.Start
private val DefaultTextDirection: TextDirection = TextDirection.Ltr
private val DefaultSoftWrap: Boolean = true
private val DefaultOverflow: TextOverflow = TextOverflow.Clip
private val DefaultMaxLines: Int? = null

@Composable
fun BodyText(text: String,
             style: TextStyle? = +themeTextStyle { body1 },
             textAlign: TextAlign = DefaultTextAlign,
             textDirection: TextDirection = DefaultTextDirection,
             softWrap: Boolean = DefaultSoftWrap,
             overflow: TextOverflow = DefaultOverflow,
             maxLines: Int? = DefaultMaxLines
){
    Text(text,style,textAlign,textDirection,softWrap,overflow, maxLines)
}
fun Color.withOpacity(opacity: Int) = this.copy(alpha = opacity / 100f * 256)
@Composable
fun BottomLeft(@Children children : @Composable() () -> Unit){
    Align(Alignment.BottomLeft){
        children()
    }
}
@Composable
fun BottomRight(@Children children : @Composable() () -> Unit){
    Align(Alignment.BottomRight){
        children()
    }
}

@Composable
fun TopLeft(@Children children : @Composable() () -> Unit){
    Align(Alignment.TopLeft){
        children()
    }
}