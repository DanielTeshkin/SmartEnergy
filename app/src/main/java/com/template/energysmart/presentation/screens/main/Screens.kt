package com.template.energysmart.presentation.screens.main


import android.util.Log
import android.widget.Toast
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
import com.template.energysmart.presentation.data.NotificationsResource
import com.template.energysmart.presentation.screens.main.components.Temperature
import com.template.energysmart.presentation.screens.main.components.drawParameterGeneratorText
import com.template.energysmart.presentation.screens.main.models.MainViewState
import com.template.energysmart.presentation.screens.notifications.NotificationViewState
import com.template.energysmart.presentation.screens.notifications.drawNotificationsSmall
import com.template.energysmart.presentation.theme.*


@Composable
fun ModeControlButton(viewModel: MainViewModel, state: State<MainViewState>){
    Row(modifier = Modifier

        .background(Color.Transparent)

        ) {

        val autoState =state.value.autoButtonIsEnabled
        val buttonAutoActive = remember {
            mutableStateOf(state.value.autoButton)
        }
        val buttonHandActive = remember {
            mutableStateOf(state.value.manualButton)
        }
        val handle = remember { mutableStateOf(state.value.handButtonIsEnabled) }
        IconToggleButton(checked = autoState, onCheckedChange = {
            when (it) {
                true -> {
                    viewModel.handleEvent(MainViewEvent.AutoModeEvent)
                    buttonAutoActive.value = R.drawable.auto_button
                    buttonHandActive.value = R.drawable.hand_button_gray
                    handle.value = false
                }
                false -> {
                    viewModel.handleEvent(MainViewEvent.ManualModeEvent)
                    buttonAutoActive.value = R.drawable.auto_button_gray
                    buttonHandActive.value = R.drawable.hand_button_green
                    handle.value = false
                }
            }
        }) {
            Image(
             ImageVector.vectorResource(id = state.value.autoButton),
                contentDescription = ""
            )
        }

        IconToggleButton(checked = handle.value, onCheckedChange = {
            handle.value = it
            when (it) {
                true -> {
                    viewModel.handleEvent(MainViewEvent.ManualModeEvent)

                }
                false -> {
                    viewModel.handleEvent(MainViewEvent.AutoModeEvent)

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
                    modifier = Modifier.align(Alignment.Center)

                )

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
                     when(state.value.commandButtonIsEnabled){
                         true -> TestPhaseNetwork(state)
                         false -> TestPhaseGenerator(state = state)
                     }

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
                imageVector = ImageVector.vectorResource(state.value.homeImage),
                contentDescription = "",
                Modifier.padding(top = 21.dp)
            )
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

    Rectangle()
        Column {
            val image=state.value.commandButtonImage
            Box(Modifier.padding(start = 33.dp, end = 33.dp, top = 40.dp)) {
                GeneratorBlock(state)
            }
            Box(modifier = Modifier.padding(start = 49.dp, end = 52.dp, top = 40.dp)) {
                CommandAndNavigationPanel(navController,viewModel,image,state.value.commandButtonIsEnabled)
            }
        }



}
    }



@Composable
fun GeneratorBlock(state: State<MainViewState>) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Log.i("go","dddf")
        Image(
            imageVector = ImageVector.vectorResource(state.value.generatorImage),
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
                    drawParameterGeneratorText(meanParameter = state.value.stationText+" "+"%")

                }
                Row(modifier = Modifier.padding(top = 14.dp)) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_union),
                        contentDescription = ""
                    )
                    drawParameterGeneratorText(meanParameter = state.value.timeText)
                }
                Row(modifier = Modifier.padding(top = 14.dp)) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_vector),
                        contentDescription = ""
                    )
                    drawParameterGeneratorText(meanParameter = state.value.oilText)
                }

            }
        }
    }
}

@Composable

fun CommandAndNavigationPanel(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel,
    image: Int,
    checked: Boolean,

) {
    var imageButton=image
    val checkedState = remember { mutableStateOf(checked) }
    Log.i("go","dfsd")
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { navController.navigate("settings") }) {
            Image(
                ImageVector.vectorResource(id = R.drawable.ic_settings_test),
                contentDescription = "",
            )
        }
        IconToggleButton(checked = checkedState.value, onCheckedChange = {
            checkedState.value = it
            when(it) {
             true->  {
               viewModel.handleEvent(MainViewEvent.StopGeneratorEvent)
                 imageButton =  R.drawable.ic_start_test
             }
                else -> {
                    viewModel.handleEvent(MainViewEvent.StartGeneratorEvent)
                    imageButton= R.drawable.ic_stop_test
                }
            }
        }, Modifier.offset(y = (-27).dp)) {
            Image(
                ImageVector.vectorResource(id = imageButton),
                contentDescription = "",

                )
        }
        Log.i("go","dot")

        IconButton(onClick = {

        }) {
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
   val alertShow by viewModel.alertDialog.collectAsState()
    //if (alertShow.id.isNotEmpty()){
    val resource= NotificationsResource("tgg")


    if (alertShow.id.isNotEmpty()) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            drawNotificationsSmall(viewModel, alertShow)
        }
    }
        //  }
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

   // val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
         //if (loading) Loader()
    if (error.isNotEmpty()) Toast.makeText(LocalContext.current,error, Toast.LENGTH_LONG).show()
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
        .width(360.dp)
      
        ) {
        Box(Modifier.padding( top = 48.dp)) {
            Log.i("go","gon")
            ModeControlButton(viewModel,state)
        }
        Box(Modifier.padding(start = 16.dp, end = 16.dp,top=32.dp)){
            FirstBlock(viewModel,state)
        }

        SecondBlock(navController,viewModel,state)
    }
    Log.i("go","dfdsdd")
}


@Composable
fun Rectangle(){
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
    Row(){
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