package com.template.energysmart.presentation.screens.notifications.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.template.energysmart.R
import com.template.energysmart.presentation.theme.*
@Composable
@Preview
fun textInBorder(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
        modifier = Modifier

            .width(229.dp)

            .clip(RoundedCornerShape(topStart = 19.dp, topEnd = 19.dp, bottomStart = 19.dp, bottomEnd = 19.dp))
            .background(Color.Transparent)
            .border(1.dp, Color(red = 0f, green = 0f, blue = 0f, alpha = 0.1599999964237213f), RoundedCornerShape(topStart = 19.dp, topEnd = 19.dp, bottomStart = 19.dp, bottomEnd = 19.dp))
            .padding(start = 14.dp, top = 10.dp, end = 14.dp, bottom = 10.dp)

            .alpha(1f)


    ) {


        Text(
            text = "1. Нажмите красную кнопку    на щите АВР\n" +
                    "2. Залейте топливо\n"+
                    "3. Поверните красную кнопку \nпо часосой стрелке",
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.20000000298023224.sp,
            lineHeight = 18.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier

                .width(201.dp)

                //.height(90.dp)

                .alpha(1f),
            color = Color(red = 0.4274509847164154f, green = 0.47058823704719543f, blue = 0.5215686559677124f, alpha = 1f),
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        )
    }
}