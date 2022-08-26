package com.template.energysmart.presentation.screens.main


import android.widget.ImageView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devs.vectorchildfinder.VectorChildFinder
import com.template.energysmart.R
import com.template.energysmart.presentation.screens.main.components.Temperature
import com.template.energysmart.presentation.screens.main.components.drawParameterGeneratorText
import com.template.energysmart.presentation.theme.Green
import com.template.energysmart.presentation.theme.MainGrayColor


@Composable
fun ModeControlButton(){
    Row(modifier = Modifier

        .background(Color.Transparent)

        ) {
        val autoState = remember { mutableStateOf(true) }
        val buttonAutoActive = remember {
            mutableStateOf(R.drawable.auto_button)
        }
        val buttonHandActive = remember {
            mutableStateOf(R.drawable.hand_button_gray)
        }
        val handle = remember { mutableStateOf(false) }
        IconToggleButton(checked = autoState.value, onCheckedChange = {
            autoState.value = it
            when (it) {
                true -> {
                    buttonAutoActive.value = R.drawable.auto_button
                    buttonHandActive.value = R.drawable.hand_button_gray
                    handle.value = false
                }
                false -> {
                    buttonAutoActive.value = R.drawable.auto_button_gray
                    buttonHandActive.value = R.drawable.hand_button_green
                    handle.value = false
                }
            }
        }) {
            Image(
              painterResource(id = buttonAutoActive.value),
                contentDescription = ""
            )
        }

        IconToggleButton(checked = handle.value, onCheckedChange = {
            handle.value = it
            when (it) {
                true -> {
                    buttonHandActive.value = R.drawable.hand_button_green
                    buttonAutoActive.value = R.drawable.auto_button_gray
                    autoState.value = false
                }
                false -> {
                    buttonHandActive.value = R.drawable.hand_button_green
                    buttonAutoActive.value = R.drawable.auto_button
                    autoState.value = true
                }
            }
        }) {


            Image(
              ImageVector.vectorResource(buttonHandActive.value),
                contentDescription = "",
            )
        }


    }
}

@Composable
@Preview
fun FirstBlock() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(258.dp),


        ) {
        Column(
            Modifier
                .align(Alignment.TopStart)


            ) {
            Box{
            Box(
                modifier = Modifier
                    .width(118.dp)
                    .height(115.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    )


                    .border(
                        1.dp,
                        Color(red = 0f, green = 0f, blue = 0f, alpha = 0.1599999964237213f),
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    )
                    .background(MainGrayColor),

                ) {

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.line_electro_city_green),
                    contentDescription = "image",
                    modifier = Modifier.align(Alignment.Center)

                )

            }
                Box(modifier = Modifier.align(Alignment.BottomCenter)
                    .offset(y=7.dp)
                         ){
                    TestPointNetwork()
                }
            }
            //y=-8.dp
            //x=25.dp
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding( top = 20.dp)
                ) {

                Image(
                    ImageVector.vectorResource(R.drawable.phases_generator_green),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 38.dp)
                )

                Column(
                    Modifier
                        .height(70.dp)
                        .offset(x = 4.dp, y = 2.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "0", textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.10000000149011612.sp,
                        lineHeight = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier



                            //.height(14.dp)

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
                    Text(
                        text = "0", textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.10000000149011612.sp,
                        lineHeight = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier


                            //.height(14.dp)

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
                    Text(
                        text = "0", textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.10000000149011612.sp,
                        lineHeight = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier



                            //.height(14.dp)

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

            }
            //RectangleTop()

        }

        Column(modifier = Modifier.align(Alignment.TopEnd)) {
            LazyRow(
                content =
                {
                    item {
                        Image(
                            ImageVector.vectorResource(id = R.drawable.frozen),
                            contentDescription = "",
                            Modifier.padding(top = 4.dp)
                        )
                    }
                    item {
                        Temperature(temperature = "-15")
                    }
                    item {
                        Image(
                            ImageVector.vectorResource(id = R.drawable.sv),
                            contentDescription = "",
                            Modifier.offset(y = 6.dp, x = 25.dp)
                        )
                    }

                }, modifier = Modifier
                    .padding(start = 30.dp, end = 18.dp)
                    .width(118.dp)

            )

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_home_green),
                contentDescription = "",
                Modifier.padding(top = 21.dp)
            )
        }

    }


}


@Composable

fun SecondBlock(navController: NavHostController= rememberNavController()) {
Box(
    Modifier
        .fillMaxHeight()
        .fillMaxWidth()) {

    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 19.dp,
                    topEnd = 19.dp,
                    bottomStart = 19.dp,
                    bottomEnd = 19.dp
                )
            )

            .border(
                1.dp,
                Color(red = 0f, green = 0f, blue = 0f, alpha = 0.1599999964237213f),
                RoundedCornerShape(
                    topStart = 19.dp,
                    topEnd = 19.dp,
                    bottomStart = 19.dp,
                    bottomEnd = 19.dp
                )
            )
            .background(MainGrayColor)
            .fillMaxWidth()
            .fillMaxHeight()

            //.height(1.dp)
    )

    Rectangle()
        Column {
            Box(Modifier.padding(start = 33.dp, end = 33.dp, top = 40.dp)) {
                GeneratorBlock()
            }
            Box(modifier = Modifier.padding(start = 49.dp, end = 52.dp, top = 40.dp)) {
                CommandAndNavigationPanel(navController)
            }
        }



}
    }



@Composable
fun GeneratorBlock(){

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.generator_off),
            contentDescription = "",

            )
        Row {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.line),
                contentDescription = ""
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Row(Modifier.padding(top = 4.dp)) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_ev_station),
                        contentDescription = ""
                    )
                    drawParameterGeneratorText(meanParameter = "70 %")

                }
                Row(modifier = Modifier.padding(top = 14.dp)) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_union),
                        contentDescription = ""
                    )
                    drawParameterGeneratorText(meanParameter = "151")
                }
                Row(modifier = Modifier.padding(top = 14.dp)) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_oil),
                        contentDescription = ""
                    )
                    drawParameterGeneratorText(meanParameter = "3")
                }

            }
        }
    }
}

@Composable

fun CommandAndNavigationPanel(navController: NavHostController= rememberNavController()) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        val checkedState = remember { mutableStateOf(true) }
        val imageButton = remember { mutableStateOf(R.drawable.ic_start_test) }
        IconButton(onClick = { navController.navigate("settings") }) {
            Image(
                ImageVector.vectorResource(id = R.drawable.ic_settings_test),
                contentDescription = "",
            )
        }

        IconToggleButton(checked = checkedState.value, onCheckedChange = {
            checkedState.value = it
            imageButton.value = if (it) R.drawable.ic_start_test
            else R.drawable.ic_stop_test
        }, Modifier.offset(y = (-27).dp)) {
            Image(
                ImageVector.vectorResource(id = imageButton.value),
                contentDescription = "",

                )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Image(
                ImageVector.vectorResource(id = R.drawable.ic_instruction_test),
                contentDescription = "",
            )
        }
    }
}

@Composable

fun Test(
    navController: NavHostController = rememberNavController(),

    ){
    Column(modifier = Modifier
        .clip(
            RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = 20.dp,
                bottomEnd = 20.dp
            )
        )
        .background(MainGrayColor)

        .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

        .alpha(1f)
        .width(360.dp)
      
        ) {
        Box(Modifier.padding( top = 48.dp)) {
            ModeControlButton()
        }
        Box(Modifier.padding(start = 16.dp, end = 16.dp,top=32.dp)){
            FirstBlock()
        }

        SecondBlock(navController)


    }
}


@Composable
fun Rectangle(){
    Box(
        Modifier
            .padding(start = 54.dp)
            .offset(y = (-7).dp)) {
        Box(
            modifier = Modifier
                .width(34.dp)
                .height(14.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                )

                .border(
                    1.dp,
                    Color(red = 0f, green = 0f, blue = 0f, alpha = 0.1599999964237213f),
                    RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                )
                .background(
                    Color(
                        red = 0.12159723043441772f,
                        green = 0.8583333492279053f,
                        blue = 0.3518272638320923f,
                        alpha = 1f
                    )
                )
        )
    }
}

@Composable

fun TestPhaseNetwork(){


    Box{
     Image(ImageVector.vectorResource(id = R.drawable.phase_3_gray), contentDescription = "",Modifier.align(
         Alignment.TopStart) )
     Image(ImageVector.vectorResource(id = R.drawable.phase_2_gray), contentDescription = "",
         Modifier.offset(x=26.dp)


             )
     Image(ImageVector.vectorResource(id = R.drawable.phase_1_gray), contentDescription = "",
         Modifier.offset(x=66.dp)
     )

 }
}
@Composable

fun TestPhaseGenerator(){
    Box(){
        Image(ImageVector.vectorResource(id = R.drawable.phase_generator_3_green), contentDescription = "",
            Modifier
                .align(
                    Alignment.TopEnd
                )
                .offset(x = 20.dp))
        Image(ImageVector.vectorResource(id = R.drawable.phase_generator_2_green), contentDescription = "",
            Modifier
                .align(
                    Alignment.CenterEnd
                )
                .offset(x = 20.dp, y = 30.dp))
        Image(ImageVector.vectorResource(id = R.drawable.phase_generator_1_green), contentDescription = "",
            Modifier
                .align(
                    Alignment.BottomEnd
                )
                .offset(x = 20.dp, y = 10.dp))
    }
}

@Composable

fun TestPointNetwork(){
    Row(){
        Image(ImageVector.vectorResource(id = R.drawable.point_network_green), contentDescription = ""
            )
        Image(ImageVector.vectorResource(id = R.drawable.point_network_green), contentDescription = "", modifier = Modifier.padding(start=12.dp)
        )
        Image(ImageVector.vectorResource(id = R.drawable.point_network_green), contentDescription = "", modifier = Modifier.padding(start=12.dp)
        )
    }
}
@Composable

fun T(){
    Column {
        TestPointNetwork()
        TestPhaseNetwork()


    }
}