package com.template.energysmart.presentation.screens

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

fun drawNotificationsFullScreen(
  color: Color,
  image:Int,
  imageButton:Int,
  imageSecond:Int,
  title:String,
  text:String,
  textColor: Color

){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(54.dp, Alignment.Bottom),
        modifier = Modifier

            .width(360.dp)
            .height(720.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                )
            )
            .background(color)
            .border(
                0.5.dp,
                Color(red = 0f, green = 0f, blue = 0f, alpha = 0f),
                RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                )
            )
            .padding(start = 16.dp, top = 36.dp, end = 16.dp, bottom = 50.dp)

            .alpha(1f)


    ) {

        Box(


            modifier = Modifier
                .weight(1f)
                .width(44.dp)

                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(Color.Transparent)

                .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                .alpha(1f)


        ) {
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(45.dp, Alignment.Top),
            modifier = Modifier

                .fillMaxWidth()
                .height(285.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(Color.Transparent)

                .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                .alpha(1f)


        ) {

            Box(


                modifier = Modifier

                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(Color.Transparent)

                    .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                    .alpha(1f)


            ) { Image(painterResource(id = image),"") }

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(9.dp, Alignment.Top),
                modifier = Modifier

                    .width(312.dp)
                    .height(77.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(Color.Transparent)

                    .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                    .alpha(1f)


            ) {


                Text(

                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.None,
                    letterSpacing = 0.sp,

                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier

                        .width(312.dp)

                        //.height(23.dp)

                        .alpha(1f),
                    color = textColor,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                    )


                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.None,
                    letterSpacing = 0.20000000298023224.sp,

                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier

                        .width(312.dp)

                        //.height(48.dp)

                        .alpha(1f),
                    color =textColor,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.5.dp, Alignment.CenterVertically),
            modifier = Modifier

                .width(196.dp)

                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(Color.Transparent)

                .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                .alpha(1f)


        ) {


            Text(
                text = "Запускаем  генератор?",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.20000000298023224.sp,

                overflow = TextOverflow.Ellipsis,
                modifier = Modifier

                    .fillMaxWidth()

                    //.height(16.dp)

                    .alpha(1f),
                color = Color(red = 0.3541666567325592f, green = 0.3541666567325592f, blue = 0.3541666567325592f, alpha = 1f),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )




                    Image(painter = painterResource(id = imageButton), contentDescription ="",Modifier.clickable(
                        onClick = {}
                    )

                     )




            Image(painter = painterResource(id = imageSecond), contentDescription ="",
                Modifier
                    .clickable(
                        onClick = {}
                    )
                    .offset(y = (-25).dp)

            )


                    }
                }
            }


@Composable
@Preview
fun test(){
    drawNotificationsFullScreen(RedLight,R.drawable.cold_error,R.drawable.error_button,R.drawable.cancel_error,"Холодно",
        "Генератор не завелся,\n"
                +"Низкая температура воздуха", ErrorText)
}