package com.template.energysmart.presentation.screens.authorization.registration

import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.template.energysmart.presentation.screens.authorization.components.Loader
import com.template.energysmart.presentation.theme.Green
import com.template.energysmart.presentation.theme.GreenAlpha
import com.template.energysmart.presentation.theme.MainGrayColor

@Composable
fun ConfirmNumberScreen(viewModel: ConfirmNumberViewModel = hiltViewModel(),navController: NavHostController,
                        text:String,type:String?){
    val phone by viewModel.phone.collectAsState()
    val enabled by viewModel.enabled.collectAsState()
    LaunchedEffect(true){
        viewModel.navigation.collect{
            if(it)
                when(type) {
                    "reset"-> navController.navigate("confirm_code_reset/${phone}")
                    else->navController.navigate("confirm_code/${phone}")
                }
        }
    }
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    if(loading) Loader()
    if(error.isNotEmpty()) Toast.makeText(LocalContext.current,error,Toast.LENGTH_LONG).show()
    val focusManager= LocalFocusManager.current
    var buttonPadding by remember { mutableStateOf(0.dp)}
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

            .padding(start = 16.dp, top = 55.dp, end = 16.dp, bottom = 75.dp)

            .alpha(1f)
            .fillMaxHeight()
            .fillMaxWidth()


    ) {

        Box(
            Modifier
                .align(Alignment.TopCenter)


        ) {


            Text(
                text = text,
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
        }



        Column(
            Modifier
                .align(Alignment.Center)
                , verticalArrangement = Arrangement.SpaceBetween
        ) {


            TextField(value = phone, onValueChange = {
                viewModel.checkFieldsState(it)
            }, modifier = Modifier
                .width(333.dp)
                .onFocusEvent {
                    buttonPadding = when (it.isFocused) {
                        true -> 250.dp
                        false -> 0.dp

                    }

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
                placeholder = {


                    Text(
                        text = "Номер телефона",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.sp,

                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier

                            .width(128.dp)

                            //.height(19.dp)

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


                }, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ), shape = RoundedCornerShape(8.dp)
            )

        }

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = buttonPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {viewModel.confirmNumber(type) }, modifier = Modifier
                    .width(335.dp)
                    .height(50.dp), enabled = enabled, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Green,
                    disabledBackgroundColor = GreenAlpha

                ), content = { Text(text = "Продолжить") },
                shape = RoundedCornerShape(8.dp)


            )


        }
    }
}

@Composable

fun ConfirmCodeScreen(phone:String="",navHostController: NavHostController= rememberNavController(

),viewModel: ConfirmCodeViewModel = hiltViewModel(),text: String,type: String?){
    var seconds:Long by remember{ mutableStateOf(60)}
    var textReset by remember{ mutableStateOf("Отправить код повторно можно 60с")}
    var enabledReset =remember{ mutableStateOf(false)}
    val timerFlag=remember{ mutableStateOf(true)}

    val timer= object  : CountDownTimer(60000,1000){
        override fun onTick(time: Long) {
            seconds=time/1000
            textReset= "Отправить код повторно можно ${seconds}с"
        }

        override fun onFinish() {
              textReset="Отправить код повторно"
            timerFlag.value=false
            enabledReset.value=true
        }

    }
    if (timerFlag.value) timer.start()

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

            .padding(start = 16.dp, top = 55.dp, end = 16.dp, bottom = 75.dp)

            .alpha(1f)
            .fillMaxHeight()
            .fillMaxWidth()


    ) {


        val buttonEnabled by viewModel.enabled.collectAsState()
        val code by viewModel.code.collectAsState()
        LaunchedEffect(true){
            viewModel.navigation.collect{
                if(it) {
                    if (type == "reset") navHostController.navigate("reset_password/${phone}")
                    else navHostController.navigate("create_password/${phone}")
                }
            }
        }
        val loading by viewModel.loading.collectAsState()
        val error by viewModel.error.collectAsState()
        if(loading) Loader()
        if(error.isNotEmpty()) Toast.makeText(LocalContext.current,error,Toast.LENGTH_LONG).show()
        val focusManager= LocalFocusManager.current
        var buttonPadding by remember { mutableStateOf(0.dp)}
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .height(75.dp)

        ) {


            Text(
                text = text,
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
                text = "Мы отправили код подтверждения в смс на ваш номер",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,
                lineHeight = 17.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier

                    .width(335.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y=25.dp)
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
                , verticalArrangement = Arrangement.SpaceBetween
        ) {


            TextField(value =code , onValueChange = {
               viewModel.checkFieldsState(it)

            }, modifier = Modifier
                .height(50.dp)
                .width(200.dp)
                .onFocusEvent {
                    buttonPadding = when (it.isFocused) {
                        false -> 0.dp
                        true -> 180.dp

                    }

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
                    ), keyboardActions = KeyboardActions(
                    onDone = {focusManager.clearFocus()}
                    ),
                placeholder = {


                    Text(
                        text = "Код",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.sp,

                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier

                            .width(128.dp)

                            //.height(19.dp)

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


                }, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ), shape = RoundedCornerShape(8.dp)
            )

        }

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = buttonPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    viewModel.disableButton()
                    buttonPadding=0.dp
                    viewModel.confirmCode(phone,type)

                }, modifier = Modifier
                    .width(335.dp)
                    .height(50.dp), enabled = buttonEnabled, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Green,
                    disabledBackgroundColor = GreenAlpha

                ), content = { Text(text = "Продолжить") },

                shape = RoundedCornerShape(8.dp)


            )
            TextButton(onClick = {
                viewModel.sendNewCode(phone,type)
                enabledReset.value=false
                timerFlag.value=true
            }, enabled = enabledReset.value, modifier = Modifier

                .padding(5.dp)
                //.height(17.dp)

                .alpha(1f)) {


                Text(
                    text = textReset,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.None,
                    letterSpacing = 0.sp,
                    lineHeight = 17.sp,
                    overflow = TextOverflow.Ellipsis,

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
        }


    }
}

@Composable
fun CreatePasswordScreen(phone: String = "", navController: NavHostController,
                         viewModel: CreatePasswordViewModel= hiltViewModel(),type: String?=""){
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

            .padding(start = 16.dp, top = 55.dp, end = 16.dp, bottom = 75.dp)

            .alpha(1f)
            .fillMaxHeight()
            .fillMaxWidth()


    ) {
        val loading by viewModel.loading.collectAsState()
        val error by viewModel.error.collectAsState()
        var focusFirst by remember {
            mutableStateOf(false)
        }
        var focusSecond by remember {
            mutableStateOf(false)
        }
        if (loading) Loader()
        if (error.isNotEmpty()) Toast.makeText(LocalContext.current,error,Toast.LENGTH_LONG).show()

        val coroutineScope= rememberCoroutineScope()
        var buttonDp by remember{ mutableStateOf(0.dp)}
        val focusManager= LocalFocusManager.current
        val enabled = viewModel.enabled.collectAsState()
        val password = remember { mutableStateOf("") }
        val repeat_password = remember{ mutableStateOf("")}
        val buttonEnabled by viewModel.enabled.collectAsState()
        LaunchedEffect(true){
            viewModel.navigation.collect{
                if(it) {
                    when(type){
                        "reset"->navController.navigate("sign-in")
                        else-> navController.navigate("user_info")
                    }

                }
            }
        }
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .height(75.dp)
        ) {


            Text(
                text = "Почти готово",
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
                text = "Отлично! Придумайте пароль.",
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
                .height(120.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {


            TextField(value = password.value, onValueChange = {
                password.value=it
                viewModel.updatePassword(it)
            }, modifier = Modifier
                .height(50.dp)
                .width(333.dp)
                .onFocusChanged {
                    focusFirst = it.isFocused
                    when (focusFirst) {
                        true -> buttonDp = 220.dp
                        false -> {
                            when (focusSecond) {
                                true -> buttonDp = 220.dp
                                false -> 0.dp
                            }
                        }
                    }
                },

                placeholder = {


                    Text(
                        text = "Пароль",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.sp,

                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier

                            .width(128.dp)

                            //.height(19.dp)

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


                }, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ), shape = RoundedCornerShape(8.dp)
            )
            TextField(value = repeat_password.value, onValueChange = {
                repeat_password.value = it
                viewModel.updateRepeatPassword(it)
            }, modifier = Modifier
                .height(50.dp)
                .width(335.dp)
                .onFocusChanged {
                    focusSecond = it.isFocused
                    when (focusSecond) {
                        true -> buttonDp = 220.dp
                        false -> {
                            when (focusFirst) {
                                true -> buttonDp = 220.dp
                                false -> 0.dp
                            }
                        }
                    }
                },
                placeholder = {


                    Text(
                        text = "Повторите пароль",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.sp,

                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier



                            //.height(19.dp)

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

                }, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ), shape = RoundedCornerShape(8.dp)
            )
        }

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = buttonDp), horizontalAlignment = Alignment.CenterHorizontally) {
            val context = LocalContext.current

            Button(
                onClick = {
                    viewModel.disableButton()
                    when(type){
                        "reset"->viewModel.resetPassword(phone)
                        else->   viewModel.createPassword(phone)
                    }


                }, modifier = Modifier
                    .width(335.dp)
                    .height(50.dp), enabled = buttonEnabled, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Green,
                    disabledBackgroundColor = GreenAlpha

                ), content = { Text(text = "Продолжить") },
                shape = RoundedCornerShape(8.dp)


            )




        }


    }
}