/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("FunctionName")

import androidx.annotation.CheckResult
import androidx.annotation.FloatRange
import androidx.compose.*
import androidx.ui.baseui.DeterminateProgressIndicator
import androidx.ui.core.*
import androidx.ui.engine.geometry.Offset
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.borders.ShapeBorder
import androidx.ui.material.clip.ClipPath
import androidx.ui.material.clip.ShapeBorderClipper
import androidx.ui.painting.*

/**
 *  Draws a linear progress indicator around the [children] with the specified [shape].
 *
 *  To increase the overall area of the progress indicator, increase the size of the [children] by giving them padding for example.
 *
 *  @param progress a value from 0 to 1 that determines how full the progress indicator will be. 1 is Completely full.
 *  @param color the color of the 'filled' area of the progress indicator. If progress = 1 the indicator will only be this color.
 *  The 'unfilled' area will be a more transparent version of this color.
 *  To customize the color of the unfilled area, see [ConfigurableProgressIndicator]
 */
@Composable
fun ConfigurableProgressIndicator(
    @FloatRange(from = 0.0, to = 1.0) progress: Float = 0f,
    color: Color? = null,
    shape: ShapeBorder? = null,
    @Children children: @Composable() () -> Unit
) {
    ConfigurableProgressIndicator(
        progress = progress,
        activeColor = color,
        inactiveColor = color?.copy(alpha = BackgroundOpacity),
        shape = +shape.orFromTheme { button },
        children = children
    )
}

/**
 * //TODO: update 'T.orFromTheme' to the newer 'optionalParam = +theme' format
 *  Draws a linear progress indicator around the [children] with the specified [shape].
 *
 *  To increase the overall area of the progress indicator, increase the size of the [children] by giving them padding for example.
 *
 *  @param progress a value from 0 to 1 that determines how full the progress indicator will be. 1 is Completely full.
 *  @param activeColor the color of the 'filled' area of the progress indicator. If progress = 1 the indicator will only be this color.
 *  @param inactiveColor the color of the 'unfilled' area of the progress indicator. If progress = 0 the indicator will only be this color.
 */
@Composable
fun ConfigurableProgressIndicator(
    @FloatRange(from = 0.0, to = 1.0) progress: Float = 0f,
    activeColor: Color? = null,
    inactiveColor: Color? = null,
    shape: ShapeBorder? = null,
    @Children children: @Composable() () -> Unit
) {
    DeterminateProgressIndicator(progress = progress) {
        Wrap {
            val primaryColor = +activeColor.orFromTheme { primary }
            val paint = +paint(primaryColor)
            val backgroundPaint = +paint(
                inactiveColor ?: primaryColor.copy(alpha = BackgroundOpacity)
            )
            ClipPath(
                clipper = ShapeBorderClipper(
                    +shape.orFromTheme { button }
                )
            ) {
                Draw { canvas, parentSize ->
                    paint.strokeWidth = parentSize.height.value
                    backgroundPaint.strokeWidth = parentSize.height.value

                    drawCustomLinearIndicatorBackground(canvas, parentSize, backgroundPaint)
                    drawCustomLinearIndicator(canvas, parentSize, progress, paint)
                }
                children()
            }

//            }
        }
    }
}


private fun drawCustomLinearIndicator(
    canvas: Canvas,
    parentSize: PxSize,
    endFraction: Float,
    paint: Paint
) {
    val width = parentSize.width.value
    val height = parentSize.height.value
    // Start drawing from the vertical center of the stroke
    val yOffset = height / 2

    val barStart = 0f
    val barEnd = endFraction * width

    canvas.drawLine(Offset(barStart, yOffset), Offset(barEnd, yOffset), paint)

}

private fun drawCustomLinearIndicatorBackground(
    canvas: Canvas,
    parentSize: PxSize,
    paint: Paint
) = drawCustomLinearIndicator(canvas, parentSize, 1f, paint)


// The opacity applied to the primary color to create the background color
private const val BackgroundOpacity = 0.24f


@CheckResult(suggest = "+")
private fun paint(color: Color) = effectOf<Paint> {
    val basePaint = withDensity(+ambientDensity()) {
        +memo {
            Paint().apply {
                isAntiAlias = true
                style = PaintingStyle.stroke
//                this.strokeWidth = height.value
            }
        }
    }
    basePaint.color = color
    basePaint.strokeCap = StrokeCap.butt
    basePaint
}


/**
 * //TODO: update 'T.orFromTheme' to the newer 'optionalParam = +theme' format
 *  Draws a button with the specified [shape] that can fill up with progress.
 *
 * To make a [ProgressButton] clickable, you must provide an [onClick]. Not providing it will
 * also make this [ProgressButton] to be displayed as a disabled one.
 * You can specify a [shape] of the surface, it's background [color] and [elevation].
 *
 * @param text The text to display.
 * @param textStyle The optional text style to apply for the text.
 * @param onClick Will be called when user clicked on the button. The button will be disabled
 *  when it is null.
 * @param shape Defines the Button's shape as well its shadow.
 * @param elevation The z-coordinate at which to place this button. This controls the size
 *  of the shadow below the button.
 *  @param progress a value from 0 to 1 that determines how full the progress indicator will be. 1 is Completely full.
 *  @param activeColor the color of the 'filled' area of the progress indicator. If progress = 1 the indicator will only be this color.
 *  @param inactiveColor the color of the 'unfilled' area of the progress indicator. If progress = 0 the indicator will only be this color.
 */
@Composable
fun ProgressButton(
    @FloatRange(from = 0.0, to = 1.0) progress: Float = 0f,
    textStyle: TextStyle = (+themeTextStyle { button }).copy(color = +themeColor { surface }),
    text: String,
    onClick: (() -> Unit)? = null,
    shape: ShapeBorder? = null,
    elevation: Dp = 0.dp,
    activeColor: Color? = +themeColor { primary },
    inactiveColor: Color? = (+themeColor { primary }).copy(alpha = 150f)
) {
    ConfigurableProgressIndicator(
        progress = progress,
        shape = shape,
        activeColor = activeColor,
        inactiveColor = inactiveColor
    ) {
        Button(
            text,
            textStyle,
            onClick,
            shape,
            Color.Transparent,
                    elevation
        )
    }

}