package com.template.energysmart.presentation.screens.settings.components

import android.annotation.SuppressLint
import android.widget.SeekBar
import android.widget.Switch
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.versionedparcelable.ParcelField
import com.smarttoolfactory.slider.ColorfulSlider
import com.smarttoolfactory.slider.MaterialSliderColors
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import com.template.energysmart.R
import com.template.energysmart.presentation.screens.settings.SettingsViewModel
import com.template.energysmart.presentation.theme.DarkGrayColor
import com.template.energysmart.presentation.theme.Gray
import com.template.energysmart.presentation.theme.Green
import com.template.energysmart.presentation.theme.MainGrayColor
import kotlin.math.absoluteValue

@Composable

fun UserItem(
    phone: String = "79258223051",
    name: String = "Денис",
    id: String = "",
    viewModel: SettingsViewModel
){
    Column(Modifier.background(Color.Transparent).fillMaxWidth()) {
        Box(Modifier.fillMaxWidth()){
            IconButton(onClick = { viewModel.apply {
                unbind(id)
                removeUser(id)
            }}, Modifier.align(Alignment.TopEnd)) {
                Image(imageVector = ImageVector.vectorResource(id = R.drawable.delete_user), contentDescription = ""
                )
            }
            Text(
                text = name,

                fontSize = 20.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.4444443881511688.sp,
                lineHeight = 24.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .offset(y = 1.dp)
                    .align(Alignment.TopStart)
                    .padding(top = 12.dp, start = 15.dp)
                    //.height(24.dp)

                    .alpha(1f),
                color = Color(
                    red = 0.2252604216337204f,
                    green = 0.2252604216337204f,
                    blue = 0.2252604216337204f,
                    alpha = 1f
                ),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,


                )

        }
        Text(
            text = phone,
            textAlign = TextAlign.End,
            fontSize = 18.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.4444443881511688.sp,
            lineHeight = 24.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 15.dp)



                //.height(24.dp)

                .alpha(1f),
            color = Color(red = 0f, green = 0f, blue = 0f, alpha = 1f),
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
        )
        Divider()
    }
}