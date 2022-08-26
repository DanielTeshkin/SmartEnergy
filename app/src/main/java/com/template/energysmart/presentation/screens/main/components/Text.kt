package com.template.energysmart.presentation.screens.main.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
 fun drawVoltageText(voltage:String){

    Text(
        text = voltage,
        textAlign = TextAlign.Start,
        fontSize = 14.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.10000000149011612.sp,
        lineHeight = 20.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier

            .width(26.dp)

            //.height(14.dp)

            .alpha(1f),
        color = Color(red = 0f, green = 0f, blue = 0f, alpha = 0.46000000834465027f),
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
    )

}

@Composable
fun drawParameterGeneratorText(meanParameter:String){
    Text(
        text = meanParameter,
        textAlign = TextAlign.Start,
        fontSize = 24.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.sp,
        lineHeight = 32.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier



            //.height(32.dp)
            .padding(start = 10.dp)
            .alpha(1f),
        color = Color(
            red = 0f,
            green = 0f,
            blue = 0f,
            alpha = 0.46000000834465027f
        ),
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
    )
}
@Composable
fun Temperature(temperature:String){
    Text(
        text = "$temperature°",
        textAlign = TextAlign.Start,
        fontSize = 28.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.sp,
        lineHeight = 32.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.offset(x=10.dp)
            .alpha(1f),
        color = Color(red = 0f, green = 0f, blue = 0f, alpha = 0.46000000834465027f),
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
    )

}