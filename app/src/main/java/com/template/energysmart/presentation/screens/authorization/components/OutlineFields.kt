package com.template.energysmart.presentation.screens.authorization.components


import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout


import androidx.navigation.NavController

import com.template.energysmart.presentation.screens.authorization.AuthorizationViewModel
import com.template.energysmart.presentation.theme.Green
import com.template.energysmart.presentation.theme.GreenAlpha
import com.template.energysmart.presentation.theme.MainGrayColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.ThreadLocalRandom.current


@OptIn(ExperimentalFoundationApi::class)
@Composable

fun OutlineCustom(navController: NavController, viewModel: AuthorizationViewModel) {

    LaunchedEffect(true){
          viewModel.navigation.collect{ if(it) navController.navigate("device")}
      }
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
    var buttonDp by remember{ mutableStateOf(450.dp)}
    val focusManager= LocalFocusManager.current

    val bring= BringIntoViewRequester()
    val enabled = viewModel.enabled.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()

    ) {
        val phone = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val buttonEnabled = remember { mutableStateOf(false) }
        val (mainContent, progressBar) = createRefs()
        Column(Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(MainGrayColor)

            .padding(start = 16.dp, top = 55.dp, end = 16.dp)
            .constrainAs(mainContent) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }

            .alpha(1f), horizontalAlignment = Alignment.CenterHorizontally


        ) {


            Text(
                text = "Вход в аккаунт",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,

                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
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
                text = "Введите ваши данные.",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,
                lineHeight = 17.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(335.dp)
                    .padding(top = 10.dp)

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







            TextField(
                phone.value, onValueChange = {
                    phone.value = it
                    viewModel.checkFieldsState(it)

                }, modifier = Modifier
                    .clearFocusOnKeyboardDismiss()
                    .height(50.dp)
                    .width(333.dp)
                    .offset(y = 180.dp)

                    .onFocusEvent {
                        focusFirst = it.isFocused
                        coroutineScope.launch {

                            delay(100)

                            buttonDp = when (it.isFocused) {
                                true -> 210.dp
                                false -> {
                                    when (focusSecond) {
                                        false -> 450.dp
                                        true -> 210.dp
                                    }
                                }
                            }
                        }

                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() },
                    onPrevious = { focusManager.clearFocus() }
                ),
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
            TextField(
                value = password.value, onValueChange = {
                    password.value = it
                    viewModel.checkFieldsState(it)
                }, modifier = Modifier
                    .offset(y = 190.dp)
                    .height(50.dp)
                    .width(335.dp)
                    .onFocusEvent {
                        focusSecond = it.isFocused
                        coroutineScope.launch {
                            delay(100)

                            buttonDp = when (it.isFocused) {
                                true -> 210.dp
                                false -> {
                                    when (focusFirst) {
                                        true -> 210.dp
                                        false -> 450.dp
                                    }
                                }
                            }
                        }

                    }, keyboardOptions = KeyboardOptions(

                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() },
                    onPrevious = { focusManager.clearFocus() }
                ),
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


            val context = LocalContext.current

            Button(
                onClick = {
                    buttonDp = 450.dp
                    viewModel.signIn(phone.value, password.value)


                },
                modifier = Modifier
                    .width(335.dp)
                    .height(50.dp)
                    .offset(y = buttonDp)
                    .bringIntoViewRequester(bring),
                enabled = enabled.value,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Green,
                    disabledBackgroundColor = GreenAlpha

                ),
                content = { Text(text = "Продолжить") },
                shape = RoundedCornerShape(8.dp)


            )

            TextButton(onClick = { navController.navigate("") },  modifier = Modifier

                .offset(y = buttonDp + 5.dp)
                //.height(17.dp)

                .alpha(1f)) {


                Text(
                    text = "Забыл пароль",
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
fun Loader() {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .background(White, shape = RoundedCornerShape(8.dp))
        ) {
            CircularProgressIndicator()
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
    if (isFocused) {
        val imeIsVisible = WindowInsets.isImeVisible
        val focusManager = LocalFocusManager.current
        LaunchedEffect(imeIsVisible) {
            if (imeIsVisible) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}