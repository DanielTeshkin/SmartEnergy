package com.template.energysmart.presentation.screens.settings
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.template.energysmart.R
import com.template.energysmart.presentation.screens.settings.components.*
import com.template.energysmart.presentation.theme.MainGrayColor

@Composable
@Preview
fun SettingsScreen(navHostController: NavHostController= rememberNavController(),
                   viewModel: SettingsViewModel= hiltViewModel(),scrollState: ScrollState = rememberScrollState()) {

    Column(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            )
            .background(MainGrayColor)
            .fillMaxWidth()
            .verticalScroll(scrollState)




    ) {
        val state by viewModel.ui.collectAsState()

        var odometerState =state.odometr_before_change_oil
        Box(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Настройки",
                textAlign = TextAlign.Start,
                fontSize = 48.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,
                lineHeight = 64.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier

                    .width(245.dp)

                    //.height(64.dp)

                    .alpha(1f),
                color = Color(red = 0f, green = 0f, blue = 0f, alpha = 1f),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )

        }
        Box(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            TitleText(text = "Основные параметры")
        }
        drawStaticParameter(name = "Версия прошивки", mean = state.generalSettingsData.version)
        drawStaticParameter(name = "Мой телефон", mean = state.generalSettingsData.phone)
        drawStaticParameter(name = "Баланс", mean = state.generalSettingsData.balance)
        if(state.preventiveMode) {
            drawStaticParameter(
                name = "Профилактический \nзапуск",
                mean = "Через ${state.time_before_start_preventive}  дней"
            )
        }
        drawStaticParameter(name = "Уровень топлива", mean = state.generalSettingsData.level_oil)
        drawStaticParameter(name = "Заряд аккумулятора", mean = state.generalSettingsData.energy+" " + "v")
        drawStaticParameter(name = "Температура генератора", mean = "${state.generalSettingsData.temperatureGenerator}°")
        drawStaticParameter(name = "Температура воздуха", mean = "${state.generalSettingsData.temperatureAir}°")
        drawStaticParameter(name = "Сумарный одометр", mean = "${state.general_odometr} час")
        drawStaticParameter(name = "Одометр до замены \nмасла", mean = odometerState)
        Box(Modifier.padding(start = 16.dp, end = 16.dp,top=20.dp)){
            IconButton(onClick = { viewModel.handleEvent(SettingsViewEvent.ResetOdometrToChangeOilEvent)}) {
                Image(imageVector = ImageVector.vectorResource(id = R.drawable.reset_odometr), contentDescription = "",
                )
            }
        }


        Box(
          modifier= Modifier
              .fillMaxWidth()
              .padding(top = 30.dp),
             contentAlignment = Alignment.Center
            ) {
           TitleText(text = "Настройки системы")
        }
        Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp,top=30.dp)) {
            SwitchItem(title = "Котроль по трём фазам",state.phaseControl,viewModel,
                ParameterType.PHASE_CONTROL
            ){
                TitleSwitchMedium(it)
            }
        }
        Box(Modifier.padding(start = 16.dp,end=16.dp,top=10.dp)) {
            PanelPhaseControl(!state.phaseControl,viewModel)
        }

        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .fillMaxWidth()) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.line_all_screen_border),
                contentDescription = "", modifier = Modifier.fillMaxWidth()
            )
        }
        Box(Modifier.padding(start = 16.dp,end=16.dp,top=21.dp)) {
            SwitchItem(title = "Включить контроль по \nнапряжению",state.voltageControl,
                viewModel,
                ParameterType.VOLTAGE_CONTROL){
                TitleSwitchMedium(it)
            }

        }
        Box(Modifier.padding(start = 15.dp,end=19.dp,top=13.dp)) {
            SliderParameter(range = 150..270, title = "Нижний порог городского напряжения", prefix ="v",state.voltage_low ,viewModel,ParameterValueType.VOLTAGE_LOW) {
                TitleSliderBold(text = it)

            }

        }
        Box(Modifier.padding(start = 15.dp,end=19.dp,top=12.dp)) {
            SliderParameter(range = 0..600, title = "Время срабатывания", prefix ="с" ,state.time_low,viewModel,ParameterValueType.TIME_LOW) {
                TitleSliderMedium(text = it)

            }

        }
        Box(Modifier.padding(start = 15.dp,end=19.dp,top=26.dp)) {
            SliderParameter(range = 150..270, title = "Верхний порог городского напряжения", prefix ="v",state.voltage_high,viewModel,ParameterValueType.VOLTAGE_HIGH) {
                TitleSliderBold(text = it)

            }

        }
        Box(Modifier.padding(start = 15.dp,end=19.dp,top=12.dp)) {
            SliderParameter(range = 0..600, title = "Время срабатывания", prefix ="с",state.time_high,viewModel,ParameterValueType.TIME_HIGH ) {
                TitleSliderMedium(text = it)

            }

        }
        Box(Modifier.padding(start = 16.dp,end=16.dp,top=22.dp)) {
            SwitchItem(title = "Включить уведомления \nпо напряжению",state.notifyEnabled,viewModel,ParameterType.NOTIFY_ENABLED){
                TitleSwitchMedium(it)
            }

        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 22.dp)
                .fillMaxWidth()) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.line_all_screen_border),
                contentDescription = "", modifier = Modifier.fillMaxWidth()
            )
        }

        Box(Modifier.padding(start = 16.dp,end=16.dp,top=24.dp)) {
            SwitchItem(title = "Эко режим",state.ecoEnable,viewModel,ParameterType.ECO){
                TitleSwitchBold(text = it)
            }
        }
        Box(Modifier.padding(start = 15.dp,end=19.dp,top=32.dp)) {
            SliderParameter(range = 0..600, title = "Пауза до запуска генератора", prefix ="с",state.time_pause,viewModel,ParameterValueType.TIME_PAUSE ) {
                TitleSliderMedium(text = it)

            }

        }
        Box(Modifier.padding(start = 15.dp,end=19.dp,top=12.dp)) {
            SliderParameter(range = 0..600, title = "Время работы генератора", prefix ="с",state.time_work,viewModel,ParameterValueType.TIME_WORK ) {
                TitleSliderMedium(text = it)

            }

        }
        Box(Modifier.padding(start = 15.dp,end=19.dp,top=12.dp)) {
            SliderParameter(range = 0..600, title = "Время простоя", prefix ="с",state.time_stop,viewModel,ParameterValueType.TIME_STOP) {
                TitleSliderMedium(text = it)

            }

        }
        Box(Modifier.padding(start = 16.dp, end = 16.dp,top=20.dp)){
            IconButton(onClick = { viewModel.handleEvent(SettingsViewEvent.ResetOdometrEvent) }) {
                Image(imageVector = ImageVector.vectorResource(id = R.drawable.reset_odometr), contentDescription = "",
                )
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .fillMaxWidth()) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.line_all_screen_border),
                contentDescription = "", modifier = Modifier.fillMaxWidth()
            )
        }
        Box(Modifier.padding(start = 16.dp,end=16.dp,top=24.dp)) {
            SwitchItem(title = "Профилактический \nзапуск",state.preventiveMode,viewModel,ParameterType.PREVENTIVE){
                TitleSwitchBold(text = it)
            }
        }
        Box(Modifier.padding(start = 15.dp,end=19.dp,top=32.dp)) {
            SliderParameter(range = 0..600, title = "Время до запуска ", prefix ="с" ,state.time_before_start_preventive,viewModel,ParameterValueType.TIME_BEFORE_START) {
                TitleSliderMedium(text = it)

            }

        }
        Box(Modifier.padding(start = 15.dp,end=19.dp,top=12.dp)) {
            SliderParameter(range = 0..600, title = "Время работы генератора", prefix ="с",state.time_workPreventive,viewModel,ParameterValueType.TIME_PREVENTIVE_WORK ) {
                TitleSliderMedium(text = it)

            }

        }
        Box(Modifier.padding(start = 80.dp, end = 80.dp,top=20.dp, bottom = 40.dp)){
            IconButton(onClick = { viewModel.update() }) {
                Image(imageVector = ImageVector.vectorResource(id = R.drawable.ic_group_142), contentDescription = "",
                )
            }
        }






    }
}

@Composable
fun TitleText(text:String){

    Text(
        text = text,
        textAlign = TextAlign.Start,
        fontSize = 26.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.sp,
        lineHeight = 32.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier



            //.height(32.dp)

            .alpha(1f),
        color = Color(red = 0f, green = 0f, blue = 0f, alpha = 1f),
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
    )
}