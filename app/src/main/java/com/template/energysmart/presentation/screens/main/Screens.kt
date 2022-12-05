package com.template.energysmart.presentation.screens.main


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.template.energysmart.R
import com.template.energysmart.domain.model.FormatNotification
import com.template.energysmart.domain.model.ImageType
import com.template.energysmart.domain.model.PowerSource
import com.template.energysmart.presentation.base.Loader
import com.template.energysmart.presentation.data.NotificationsResource
import com.template.energysmart.presentation.screens.main.components.Temperature
import com.template.energysmart.presentation.screens.main.components.drawParameterGeneratorText
import com.template.energysmart.presentation.screens.main.models.MainViewState
import com.template.energysmart.presentation.screens.notifications.NotificationViewState
import com.template.energysmart.presentation.screens.notifications.drawNotificationsSmall
import com.template.energysmart.presentation.theme.*


@Composable
fun ModeControlButton(viewModel: MainViewModel){
    val autoModeImage=viewModel.uiController.autoModeImage.collectAsState()
    val handModeImage=viewModel.uiController.handModeImage.collectAsState()

    Row(modifier = Modifier

        .background(Color.Transparent)

        ) {

        val autoState = viewModel.uiController.autoButtonIsEnabled.collectAsState()

        val handle = viewModel.uiController.handButtonIsEnabled.collectAsState()
        IconToggleButton(checked = !autoState.value, onCheckedChange = {viewModel.handleEvent(MainViewEvent.ModeEvent)}, enabled = autoState.value) {
            Image(
             ImageVector.vectorResource(id = autoModeImage.value),
                contentDescription = ""
            )
        }

        IconToggleButton(checked = !handle.value, onCheckedChange = {viewModel.handleEvent(MainViewEvent.ModeEvent) },
            enabled = handle.value) {


            Image(
              ImageVector.vectorResource(handModeImage.value),
                contentDescription = "",
            )
        }


    }
}

@Composable

fun FirstBlock(viewModel: MainViewModel, state: State<MainViewState>) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(250.dp)

    ) {
        Column(
            Modifier
                .align(Alignment.TopStart)


            ) {
            Box{
                Log.i("go","dd")
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
                    imageVector = ImageVector.vectorResource(state.value.electricNetworkImage),
                    contentDescription = "image",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-10).dp)


                )

            }
                Box(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-5).dp)) {

                        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.width(80.dp)){
                            Text(text =state.value.network_1.toString(),
                                fontSize = 14.sp,textDecoration = TextDecoration.None,
                                letterSpacing = 0.10000000149011612.sp,
                                lineHeight = 14.sp,
                                overflow = TextOverflow.Ellipsis, color = Color(
                                    red = 0f,
                                    green = 0f,
                                    blue = 0f,
                                    alpha = 0.46000000834465027f
                                ),
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,)
                            Text(text = state.value.network_2.toString(), fontSize = 14.sp,
                                textDecoration = TextDecoration.None,
                                letterSpacing = 0.10000000149011612.sp,
                                lineHeight = 10.sp,
                                overflow = TextOverflow.Ellipsis,
                                modifier =Modifier.alpha(1f),
                                color = Color(
                                    red = 0f,
                                    green = 0f,
                                    blue = 0f,
                                    alpha = 0.46000000834465027f
                                ),
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,)
                            Text(text = state.value.network_3.toString(),
                                textDecoration = TextDecoration.None,
                                letterSpacing = 0.10000000149011612.sp,
                                lineHeight = 10.sp,
                                modifier =Modifier.alpha(1f),
                                overflow = TextOverflow.Ellipsis,fontSize = 14.sp,
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
                Box(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 7.dp)
                         ){
                    TestPointNetwork(state)
                }
            }
            //star=31.dp(network)

            Box(

                modifier = Modifier.padding( top = 7.dp)
                ) {
                 Box(Modifier.padding(start = 31.dp)) {
                     if (state.value.source==PowerSource.NETWORK) TestPhaseNetwork(state)
                     else if(state.value.source==PowerSource.GENERATOR) TestPhaseGenerator(state = state)

                 }
                Box(
                    Modifier
                        .padding(top = 17.dp)
                        .offset(x = 114.dp)) {
                    TestPhasePoint(state)


                }
                Column(
                    Modifier
                        .height(70.dp)
                        .offset(x = 136.dp, y = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = state.value.phase_vol_3.toString(), textAlign = TextAlign.Start,
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
                        text = state.value.phase_vol_2.toString(), textAlign = TextAlign.Start,
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
                        text = state.value.phase_vol_1.toString(), textAlign = TextAlign.Start,
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
                        if(state.value.cold)
                        Image(
                            ImageVector.vectorResource(id = R.drawable.frozen),
                            contentDescription = "",
                            Modifier.padding(top = 4.dp)
                        )
                    }
                    item {
                        Temperature(temperature = state.value.temperature.toString())
                    }
                    item {
                        Image(
                            ImageVector.vectorResource(id = state.value.batteryImage),
                            contentDescription = "",
                            Modifier.offset(y = 6.dp, x = 25.dp)
                        )
                    }

                }, modifier = Modifier
                    .padding(start = 30.dp, end = 18.dp)
                    .width(118.dp)

            )
            

                Image(
                    imageVector = ImageVector.vectorResource(state.value.homeImage),
                    contentDescription = "",
                    Modifier.padding(top = 21.dp)
                )
            if(state.value.general!=0f) {
                Text(
                    text = state.value.general.toString(),
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.None,
                    letterSpacing = 0.10000000149011612.sp,
                    lineHeight = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .offset(y = ((-140).dp), x = 67.dp)


                        //.height(19.dp)

                        .alpha(1f),
                    color = Color(red = 0f, green = 0f, blue = 0f, alpha = 0.46000000834465027f),
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                )
                Text(
                    text = "кВт",
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.None,
                    letterSpacing = 0.10000000149011612.sp,
                    lineHeight = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .offset(y = ((-145).dp), x = 65.dp)


                        //.height(19.dp)

                        .alpha(1f),
                    color = Color(red = 0f, green = 0f, blue = 0f, alpha = 0.46000000834465027f),
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                )
            }



           
        }

    }


}


@Composable

fun SecondBlock(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel,
    state: State<MainViewState>

) {
Box(
    Modifier
        .fillMaxHeight()
        .fillMaxWidth()) {
    Log.i("go","s")

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

           Rectangle(state.value.eclipseColor)
    Log.i("go","top")
        Column {
            val image=state.value.commandButtonImage
            Box(Modifier.padding(start = 33.dp, end = 33.dp, top = 40.dp)) {
                
                GeneratorBlock(state)
                if (viewModel.uiController.timeShow.collectAsState().value) {
                    Row(Modifier.offset(y=40.dp,x=20.dp)) {
                        Image(
                            ImageVector.vectorResource(id = R.drawable.worker),
                            contentDescription = ""
                        )
                        Text(
                            text = "00:${viewModel.uiController.time.collectAsState().value}",
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            textDecoration = TextDecoration.None,
                            letterSpacing = 0.sp,
                            lineHeight = 32.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier

                                .width(64.dp)

                                //.height(32.dp)

                                .alpha(1f),
                            color = Color(
                                red = 0.10062497854232788f,
                                green = 0.6708333492279053f,
                                blue = 0.2788150906562805f,
                                alpha = 1f
                            ),
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                        )


                    }
                }
            }
            Box(modifier = Modifier.padding(start = 49.dp, end = 52.dp, top = 40.dp)) {
                CommandAndNavigationPanel(navController,viewModel)
            }
        }



}
    }



@Composable
fun GeneratorBlock(state: State<MainViewState>) {
    Log.i("go","trop")
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Image(
            imageVector = ImageVector.vectorResource(state.value.generatorImage),
            contentDescription = "",

            )
        Row(Modifier.offset(x= 10.dp)) {
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
                    drawParameterGeneratorText(meanParameter = state.value.stationText+" "+"%")

                }
                Row(modifier = Modifier.padding(top = 14.dp)) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_union),
                        contentDescription = ""
                    )
                    drawParameterGeneratorText(meanParameter = state.value.timeText)
                }
                Row(modifier = Modifier.padding(top = 18.dp)) {
                    Image(
                        imageVector = ImageVector.vectorResource(state.value.oilImage),
                        contentDescription = ""
                    )
                    Box(modifier =Modifier.offset(y= (-10).dp) ) {
                        drawParameterGeneratorText(meanParameter = state.value.oilText)
                    }
                }

            }
        }
    }
    Log.i("go","tro")
}

@Composable

fun CommandAndNavigationPanel(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel,



) {
  val enabled=viewModel.uiController.generatorButtonEnabled.collectAsState()
    val image=viewModel.uiController.generatorButtonImage.collectAsState()

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { navController.navigate("settings") }) {
            Image(
                ImageVector.vectorResource(id = R.drawable.ic_settings_test),
                contentDescription = "",
            )
        }
        IconButton (onClick = {

            viewModel.handleEvent(MainViewEvent.GeneratorCommandEvent)
        },  Modifier.offset(y = (-27).dp),enabled =enabled.value) {
            Image(
                ImageVector.vectorResource(id = image.value),
                contentDescription = "",

                )
        }
        Log.i("go","dot")

        IconButton(onClick = { navController.navigate("instruction") }) {
            Image(
                ImageVector.vectorResource(id = R.drawable.ic_instruction_test),
                contentDescription = "",
            )
        }
        Log.i("go","dob")
    }
    Log.i("go","dfsdd")
}

@Composable

fun Test(
    navController: NavHostController = rememberNavController(),
     viewModel: MainViewModel = hiltViewModel()
    ){
   val alertShow by viewModel.uiController.alertDialog.collectAsState()
    if (alertShow.id.isNotEmpty()) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            drawNotificationsSmall(viewModel, alertShow)
        }
    }
    LaunchedEffect(true) {
            viewModel.notification.collect {
                if (it.title.isNotEmpty()) {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        value = it,
                       key = "state"
                    )
                    navController.navigate("notification")

                }
            }
        }

   val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
         //if (loading) Loader()
    if (error.isNotEmpty()){
        Toast.makeText(LocalContext.current,error, Toast.LENGTH_LONG).show()
    }
    val state=viewModel.configuration.collectAsState()

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
        .fillMaxWidth()
      
        ) {
        Box(Modifier.padding( top = 48.dp)) {
            Log.i("go","gon")
            ModeControlButton(viewModel)
        }
        Box(Modifier.padding(start = 16.dp, end = 16.dp,top=32.dp)){
            FirstBlock(viewModel,state)
        }

        SecondBlock(navController,viewModel,state)
    }
    Log.i("go","dfdsdd")
}


@Composable
fun Rectangle(color: Color){
    Box(
        Modifier
            .padding(start = 58.dp)
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
                .background(color)
        )
    }
}

@Composable

fun TestPhaseNetwork(state: State<MainViewState>) {


    Box{
     Image(ImageVector.vectorResource(state.value.phaseFirstImage), contentDescription = "" )
     Image(ImageVector.vectorResource(state.value.phaseImageSecond), contentDescription = "",
         Modifier.offset(x= 26.dp)


             )
     Image(ImageVector.vectorResource(state.value.phaseImageThird), contentDescription = "",
         Modifier.offset(x= 53.dp)
     )

 }
}
@Composable

fun TestPhasePoint(state: State<MainViewState>) {


    Column( modifier = Modifier.height(75.dp)){
        Image(ImageVector.vectorResource(state.value.phasePointThird), contentDescription = "" )
        Image(ImageVector.vectorResource(state.value.phasePointSecond), contentDescription = "",Modifier.padding(top=8.dp)
            )
        Image(ImageVector.vectorResource(state.value.phasePointFirst), contentDescription = "",
            Modifier.padding(top=8.dp)
        )

    }
}
@Composable

fun TestPhaseGenerator(state: State<MainViewState>){
    Box(){
        Image(ImageVector.vectorResource(state.value.phaseFirstImage), contentDescription = "",
            Modifier

                .offset(x=10.dp,y=12.dp))
        Image(ImageVector.vectorResource(state.value.phaseImageSecond), contentDescription = "",
            Modifier

                .offset(x = 17.dp, y = 40.dp))
        Image(ImageVector.vectorResource(state.value.phaseImageThird), contentDescription = "",
            Modifier

                .offset(x = 24.dp, y = 67.dp))
    }
}

@Composable

fun TestPointNetwork(state: State<MainViewState>) {
    Row{
        Image(ImageVector.vectorResource(state.value.pNetworkImageFirst), contentDescription = ""
            )
        Image(ImageVector.vectorResource(state.value.pNetworkImageSecond), contentDescription = "", modifier = Modifier.padding(start=12.dp)
        )
        Image(ImageVector.vectorResource(state.value.pNetworkImageThird), contentDescription = "", modifier = Modifier.padding(start=12.dp)
        )
    }
}
@Composable

fun T(){
    Column {
     //   TestPointNetwork(state)
       // TestPhaseNetwork(state)


    }
}