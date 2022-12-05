package com.template.energysmart.presentation.screens.settings.components
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.slider.ColorfulSlider
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import com.template.energysmart.R
import com.template.energysmart.presentation.screens.settings.ParameterType
import com.template.energysmart.presentation.screens.settings.ParameterValueType
import com.template.energysmart.presentation.screens.settings.SettingsViewEvent
import com.template.energysmart.presentation.screens.settings.SettingsViewModel
import com.template.energysmart.presentation.theme.DarkGrayColor
import com.template.energysmart.presentation.theme.Green
import com.template.energysmart.presentation.theme.MainGrayColor

@Composable

fun SliderParameter(
    range: IntRange,
    title:String,
    prefix: String,
    mean: Int,
    enabled: Boolean,
    viewModel: SettingsViewModel,
    parameterValueType: ParameterValueType,
    TitleDraw:@Composable (String)->Unit,

    ){

    Column(
        Modifier
            .background(MainGrayColor)
            .padding(top = 13.dp)
            .width(331.dp)) {
       TitleDraw(title)
       Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
           .width(240.dp)
           .padding(top = 20.dp), verticalAlignment = Alignment.Bottom) {
           TextRange(range = range, prefix =prefix )

       }

        Row {

            val positionSlider = remember{ mutableStateOf(0f)}
            Log.i("sliderT",positionSlider.toString())

            Box(Modifier.width(250 .dp)) {
                if(enabled)
                ColorfulSlider(
                    value = sliderState(range,mean.toString()),
                    enabled = true,
                    thumbRadius = 15.dp,
                    trackHeight = 20.dp,
                    onValueChange = { it ->
                       positionSlider.value=it

                        viewModel.handleEvent(SettingsViewEvent.ValueChangerEvent(
                                 textFieldState(range,it),parameterValueType))
                                    },
                    colors = MaterialSliderDefaults.materialColors(
                        inactiveTrackColor = SliderBrushColor(color = DarkGrayColor),
                        activeTrackColor = SliderBrushColor(Green),
                        thumbColor = SliderBrushColor(Color.White),
                        disabledActiveTrackColor = SliderBrushColor(color = DarkGrayColor)
                    ),
                    valueRange = 0f..1f
                    )
                else
                    ColorfulSlider(
                        value = sliderState(range,mean.toString()),
                        enabled = false,
                        thumbRadius = 15.dp,
                        trackHeight = 20.dp,
                        onValueChange = { it ->
                            positionSlider.value=it

                            viewModel.handleEvent(SettingsViewEvent.ValueChangerEvent(
                                textFieldState(range,it),parameterValueType))
                        },
                        colors = MaterialSliderDefaults.materialColors(
                            inactiveTrackColor = SliderBrushColor(color = DarkGrayColor),
                            activeTrackColor = SliderBrushColor(Green),
                            thumbColor = SliderBrushColor(Color.White),
                            disabledActiveTrackColor = SliderBrushColor(color = DarkGrayColor)
                        ),
                        valueRange = 0f..1f
                    )
            }
           val color=if (enabled)Color.Black else Color.Gray
            TextField(
                    value = mean.toString(),
                    onValueChange = {
                        if(it.isNotEmpty()&&it.length<4) {

                            viewModel.handleEvent(SettingsViewEvent.ValueChangerEvent(it,parameterValueType))
                        }
                    },
                     modifier= Modifier
                         .offset(x = 22.dp, y = (-5).dp)
                         .width(60.dp)
                         .height(50.dp)
                         ,
                     enabled=enabled,
                     textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize =14.sp,
                        textDecoration = TextDecoration.None,
                        lineHeight = 5.sp,
                        color = color,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,

                    )

                )





        }

       Divider()

    } }






@Composable
fun PanelPhaseControl(
    enabled: Boolean = false, viewModel: SettingsViewModel, value: State<Int>
){
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
        Text(
            text = "Контроль по фазе",
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.4444443881511688.sp,
            lineHeight = 26.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier

                .width(148.dp)
                .width(30.dp)

                //.height(26.dp)

                .alpha(0.699999988079071f),
            color = Color(red = 0.2252604216337204f, green = 0.2252604216337204f, blue = 0.2252604216337204f, alpha = 1f),
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        )
        if(enabled)   Text(
            text = "(Неактивно)",
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.4444443881511688.sp,
            lineHeight = 26.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier

                .width(98.dp)

                //.height(26.dp)

                .alpha(0.699999988079071f),
            color = Color(red = 0.2252604216337204f, green = 0.2252604216337204f, blue = 0.2252604216337204f, alpha = 1f),
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        )
        else
            Box(Modifier.offset(y= (-10).dp)) {
                PanelPhase(viewModel,value)
            }


    }
}
@Composable
fun PanelPhase(viewModel: SettingsViewModel, count: State<Int>) {
    val checked_first = (count.value==1)
    val image_first =if (count.value==1) R.drawable.round_green else R.drawable.ic_group_16
    val checked_second = count.value==2
    val image_second  =if (count.value==2) R.drawable.round_green else R.drawable.ic_group_16
    val checked_third =count.value==3
    val image_third =if (count.value==3) R.drawable.round_green else R.drawable.ic_group_16
    Row {
        IconToggleButton(checked = checked_first, onCheckedChange ={

            viewModel.handleEvent(SettingsViewEvent.СhoicePhaseEvent(it,1))

        } ) {
                 Box{
                     Image(imageVector = ImageVector.vectorResource(id = image_first), contentDescription ="" )
                      Box(Modifier.align(Alignment.Center)) {
                          TextPhase(number = "1")
                      }
                 }
        }
        IconToggleButton(checked = checked_second, onCheckedChange ={

            viewModel.handleEvent(SettingsViewEvent.СhoicePhaseEvent(it,2))

        } ) {
            Box{
                Image(imageVector = ImageVector.vectorResource(id = image_second), contentDescription ="" )
                Box(Modifier.align(Alignment.Center)) {
                    TextPhase(number = "2")
                }
            }
        }
        IconToggleButton(checked = checked_third, onCheckedChange ={

            viewModel.handleEvent(SettingsViewEvent.СhoicePhaseEvent(it,3))

        } ) {
            Box{
                Image(imageVector = ImageVector.vectorResource(id = image_third), contentDescription ="" )
                Box(Modifier.align(Alignment.Center)) {
                    TextPhase(number = "3")
                }
            }
        }
    }
}


@Composable
fun TextPhase(number:String){

     Box {
         Text(
             text = number,
             textAlign = TextAlign.Start,
             fontSize = 10.sp,
             textDecoration = TextDecoration.None,
             letterSpacing = 0.4444443881511688.sp,

             overflow = TextOverflow.Ellipsis,
             modifier = Modifier

                 .width(7.dp)
                 .align(Alignment.Center)

                 //.height(14.dp)

                 .alpha(1f),
             color = Color(red = 0.37109375f, green = 0.37109375f, blue = 0.37109375f, alpha = 1f),
             fontWeight = FontWeight.Bold,
             fontStyle = FontStyle.Normal,
         )
     }

}

@Composable
fun SwitchWindow(state:Boolean,viewModel: SettingsViewModel,parameterType: ParameterType){
    Row{
        val textState = when(state){
            true->"ON"
            false->"OFF" }


        val offsetState = when(state) {true-> 15.dp
                false->52.dp
            }


        Text(
            text =textState ,
            textAlign = TextAlign.Justify,
            fontSize = 10.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.4444443881511688.sp,
            lineHeight = 16.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                //.height(16.dp)
                .offset(x = offsetState, y = 17.dp)
                .alpha(1f),
            color = Color(red = 0.37109375f, green = 0.37109375f, blue = 0.37109375f, alpha = 1f),
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
        )

        Switch(
            checked =state , onCheckedChange = {
                viewModel.handleEvent(SettingsViewEvent.SwitchStateChangeEvent(it,parameterType))
                },
            Modifier.scale(2f),
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Green,
                uncheckedTrackColor = DarkGrayColor
            )

        )
    }


}

@Composable
fun SwitchItem(title: String,
               state:Boolean,
               viewModel:SettingsViewModel,
               parameterType: ParameterType,
               titleDraw:@Composable (String)->Unit){
    Column(
        Modifier
            .background(Color.Transparent)

    ){
        Row(
            Modifier
                .fillMaxWidth()
                , horizontalArrangement = Arrangement.SpaceBetween){

            titleDraw(title)

            Box(modifier = Modifier.offset(y= (-5).dp,x= (-8).dp)) {
                SwitchWindow(state,viewModel,parameterType)
            }

        }
    }

}

@Composable
fun TitleSwitchMedium(text:String){
    Text(
        text = text,

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
}
@Composable
fun TitleSwitchBold(text:String){
    Text(
        text = text,
        textAlign = TextAlign.Start,
        fontSize = 22.sp,
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
@Composable
fun TitleSliderMedium(text: String){

    Text(
        text = text,
        textAlign = TextAlign.Start,
        fontSize = 16.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.4444443881511688.sp,
        lineHeight = 26.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier

            .width(273.dp)

            //.height(26.dp)

            .alpha(1f),
        color = Color(red = 0.2252604216337204f, green = 0.2252604216337204f, blue = 0.2252604216337204f, alpha = 1f),
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
    )
}


@Composable
fun TitleSliderBold(text:String){
    Text(
        text = text,
        textAlign = TextAlign.Start,
        fontSize = 16.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.4444443881511688.sp,
        lineHeight = 26.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier



            //.height(26.dp)

            .alpha(1f),
        color = Color(red = 0.2252604216337204f, green = 0.2252604216337204f, blue = 0.2252604216337204f, alpha = 1f),
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
    )
}
@Composable
fun TextRange(range: IntRange ,prefix:String){
    Text(
        text = range.first.toString()+prefix,
        textAlign = TextAlign.Start,
        fontSize = 12.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.4444443881511688.sp,
        lineHeight = 32.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .offset(x = 5.dp, y = 5.dp)


            //.height(32.dp)

            .alpha(1f),
        color = Color(red = 0.40442708134651184f, green = 0.40442708134651184f, blue = 0.40442708134651184f, alpha = 1f),
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
    )
    Text(
        text = range.last.toString()+prefix,
        textAlign = TextAlign.Start,
        fontSize = 12.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.4444443881511688.sp,
        lineHeight = 32.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .offset(x = (5).dp, y = 5.dp)


            //.height(32.dp)

            .alpha(1f),
        color = Color(red = 0.40442708134651184f, green = 0.40442708134651184f, blue = 0.40442708134651184f, alpha = 1f),
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
    )
}

fun sliderState(range:IntRange,mean:String): Float {
    val number=mean.toFloat()
    val difference= range.run {endInclusive-start}
    return (number-range.first)/difference
}
fun textFieldState(range: IntRange,position:Float):String {
   val difference= range.run {endInclusive-start}
    val current=(position*difference+range.first).toInt()
    return current.toString()
}
   



