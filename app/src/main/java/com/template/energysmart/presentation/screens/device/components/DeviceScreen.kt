package com.template.energysmart.presentation.screens.device.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.template.energysmart.data.remote.api.model.response.Device
import com.template.energysmart.presentation.screens.device.DeviceViewEvent
import com.template.energysmart.presentation.screens.device.DeviceViewModel
import com.template.energysmart.presentation.theme.Green
import com.template.energysmart.presentation.theme.GreenAlpha
import com.template.energysmart.presentation.theme.MainGrayColor
import kotlinx.coroutines.launch


@Composable
fun DeviceScreen(navController: NavController){
  BindDeviceContent( navController = navController)

}
@Composable

fun ListDevicesScreen(list:List<Device> = listOf(Device(comment ="ДомСтоп"),Device(comment="ДомРще"),Device(comment="Гот"))){

    LazyColumn(content = {
      items(list){item ->
          DeviceItem(device = item, navController = rememberNavController() )
      }
    }, modifier = Modifier.fillMaxWidth())
    Button(onClick = { /*TODO*/ }) {
        Text(text = "Добавить новое устрйоство")
    }
}
@Composable
fun BindDeviceScreen(){
  //  BindDeviceContent(navController = rememberNavController())
}

@Composable
fun BindDeviceContent(navController: NavController,viewModel: DeviceViewModel= hiltViewModel()) {
          LaunchedEffect(true ){
              viewModel.navigation.collect{ isNavigate->
                  if(isNavigate) navController.navigate("settings")

              }
          }
       val state by viewModel.state.collectAsState()
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
            .background(MainGrayColor)

            .padding(start = 0.dp, top = 55.dp, end = 0.dp, bottom = 55.dp)

            .alpha(1f)
            .fillMaxHeight()
            .fillMaxWidth()


    ) {
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .height(75.dp)) {


            Text(
                text = "Добавить устройства",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,

                overflow = TextOverflow.Ellipsis,
                modifier = Modifier


                    //.height(28.dp)
                    .align(Alignment.TopCenter)
                    .alpha(1f),
                color = Color(
                    red = 0.06499999761581421f,
                    green = 0.10400000214576721f,
                    blue = 0.20000000298023224f,
                    alpha = 1f
                ),
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
            )
            Text(
                text = "Чтобы добавить новое устройство,введите его данные.",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,
                lineHeight = 17.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier

                    .width(335.dp)
                    .align(Alignment.BottomCenter)
                    //.height(17.dp)

                    .alpha(1f),
                color = Color(
                    red = 0.08444444835186005f,
                    green = 0.1370864361524582f,
                    blue = 0.2666666805744171f,
                    alpha = 0.550000011920929f
                ),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )
        }



        Column(
            Modifier
                .align(Alignment.Center)
                .height(105.dp), verticalArrangement = Arrangement.SpaceBetween) {



            TextField(value = state.uid, onValueChange = { viewModel.reduceEvent(DeviceViewEvent.ChangeTextUIDEvent(it)) },
                modifier = Modifier
                    .height(50.dp)
                    .width(333.dp),

                placeholder = {


                    Text(
                        text = "UID",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.sp,

                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier

                            .width(128.dp)

                            //.height(19.dp)

                            .alpha(1f),
                        color = Color(red = 0.08444444835186005f, green = 0.1370864361524582f, blue = 0.2666666805744171f, alpha = 0.550000011920929f),
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    )


                }
                , colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
                , shape =  RoundedCornerShape(8.dp)
            )
            TextField(value = state.password, onValueChange = {viewModel.reduceEvent(DeviceViewEvent.ChangeTextPasswordEvent(it))},
                modifier = Modifier
                    .height(50.dp)
                    .width(335.dp),
                placeholder = {


                    Text(
                        text = "Пароль",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.sp,

                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier

                            .width(57.dp)

                            //.height(19.dp)

                            .alpha(1f),
                        color = Color(red = 0.08444444835186005f, green = 0.1370864361524582f, blue = 0.2666666805744171f, alpha = 0.550000011920929f),
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    )

                }
                , colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
                , shape =  RoundedCornerShape(8.dp)
            )
        }

        Column(Modifier.align(Alignment.BottomCenter)) {
            val context= LocalContext.current

            Button(onClick = {
           viewModel.reduceEvent(DeviceViewEvent.BindDeviceEvent)
                             }, modifier = Modifier
                .width(335.dp)
                .height(50.dp), enabled =state.enabled, colors = ButtonDefaults.buttonColors(
                backgroundColor = Green,
                disabledBackgroundColor = GreenAlpha

            ), content = { Text(text = "Добавить") },
                shape = RoundedCornerShape(8.dp)


            )

        }


    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeviceItem(device: Device,navController: NavController){
    Card(onClick={

        navController.navigate("main")

    }, modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 2.dp)
        .width(80.dp)
        .height(20.dp),
        backgroundColor = Color.Transparent, shape = RoundedCornerShape(50.dp)) {
        Text(text = device.comment, fontSize = 8.sp, color = Color.White, modifier = Modifier.padding(top=5.dp, start = 10.dp))
    }
}