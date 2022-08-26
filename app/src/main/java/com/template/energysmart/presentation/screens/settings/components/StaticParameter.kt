package com.template.energysmart.presentation.screens.settings.components


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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.template.energysmart.R
import com.template.energysmart.presentation.theme.MainGrayColor

@Composable

fun drawStaticParameter(name:String,
                        mean:String){
    Column(
        Modifier
            .background(Color.Transparent)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
             ){
        Row(
            Modifier
                .padding(top = 10.dp, bottom = 5.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text = name,

                fontSize = 16.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.4444443881511688.sp,
                lineHeight = 24.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .offset(y = 1.dp)


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
            Text(
                text = mean,
                textAlign = TextAlign.End,
                fontSize = 18.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.4444443881511688.sp,
                lineHeight = 24.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier



                    //.height(24.dp)

                    .alpha(1f),
                color = Color(red = 0f, green = 0f, blue = 0f, alpha = 1f),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
            )

        }
        Divider()
    }
    
}
@Composable
@Preview
fun test(){
    drawStaticParameter(name = "Мой телефон", mean ="79258223946" )
}




@Composable
fun Divider(){
    Box(
        modifier = Modifier
            .fillMaxWidth()

            .padding(top = 8.dp)
    ){
        Image(imageVector = ImageVector.vectorResource(id = R.drawable.line_border), contentDescription ="", modifier = Modifier.fillMaxWidth() )
    }
}