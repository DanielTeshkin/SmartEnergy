package com.template.energysmart.presentation.screens.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow



import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.template.energysmart.R
import com.template.energysmart.presentation.screens.main.MainViewEvent
import com.template.energysmart.presentation.screens.main.MainViewModel

@Composable
fun drawNotificationsSmall(viewModel: MainViewModel, alertShow: AlertNotificationState) {

    Box(


        modifier = Modifier

            .width(328.dp)
            .height(102.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                )
            )
            .background(alertShow.background)

            .padding(end = 16.dp)

            .alpha(1f)


    ) {

        Box(
            modifier = Modifier
                .width(9.dp)
                .height(102.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )


                .background(alertShow.lineColor)
        )

        Box(Modifier.fillMaxHeight()) {
            Image(ImageVector.vectorResource(alertShow.imageClose), contentDescription ="" ,Modifier.offset(x = 16.dp,y=10.dp))

            Text(
                text = alertShow.title,
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.25.sp,
                lineHeight = 20.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .offset(y = 10.dp, x = 50.dp)
                    //.height(20.dp)

                    .alpha(1f),
                color = Color(
                    red = 0.11372549086809158f,
                    green = 0.12156862765550613f,
                    blue = 0.16078431904315948f,
                    alpha = 1f
                ),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )
            Image(ImageVector.vectorResource(R.drawable.ic_icon_1), contentDescription ="" ,
                Modifier
                    .offset( y = 12.dp,x=150.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                       viewModel.handleEvent(MainViewEvent.CloseAlertEvent(alertShow.id))
                    })
        }


        Text(
            text = alertShow.description,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.15000000596046448.sp,
            lineHeight = 28.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.TopStart)
                .width(253.dp)

                //.height(28.dp)
                .offset(x = 50.dp, y = 30.dp)
                .alpha(1f),
            color = Color(red = 0.11372549086809158f, green = 0.12156862765550613f, blue = 0.16078431904315948f, alpha = 1f),
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
        )


}
}
@Composable
@Preview
fun testSmallSize(){

}