package com.example.calculatorapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculatorapp.componentes.InputFiled
import com.example.calculatorapp.ui.theme.CalculatorAppTheme
import com.example.calculatorapp.widget.RoundedButton
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MyApp {
               MainContent()
           }
        }
    }
}

// cuidado com o background pode gerar impressão errada,
// com backgorund não percebia as bordas
@Preview
@Composable
fun TopHeader(totalPerson: Double = 120.0)  {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 20.dp)
        .height(150.dp)
        .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color =  Color(0xFFE9D7F7)
          ) {
        val value = "%.2f".format(totalPerson)
         Column(modifier =  Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
              Text(text = "Total Per Person", style = MaterialTheme.typography.h5)
              Text(text = "$$value", style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)
         }
     }
}

@Composable
fun MyApp(content:  @Composable () -> Unit)  {
    CalculatorAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }

}
@Preview
@Composable
fun MainContent() {
      FormBill() { it ->
         Log.d("Debug",it)
      }
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormBill(modifier: Modifier = Modifier,valChange: (value: String) -> Unit)  {
    val valueBillState = remember {
       mutableStateOf(value = "")
    }

    //ele ira ficar monitorando o valor do valueBillState, neste caso não preciso de um mutableStateOf
    val validateState = remember(valueBillState.value) {
        valueBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current


    val sliderPositionValue = remember {
        mutableStateOf(value = 0f)
    }

    val totalPercentage = sliderPositionValue.value * 100

    val quantityPerson = remember {
        mutableStateOf(value = 1)
    }

    val totalTip = remember {
        mutableStateOf(value = 0.0)
    }

    val totalPerson = remember {
        mutableStateOf(value = 0.0)
    }

    fun handleCalcTotalPerson(): Double {
        if(valueBillState.value.isNotEmpty()) {
            val totalValue = valueBillState.value.toDouble() + totalTip.value
            return  totalValue / quantityPerson.value
        }
        return 0.0
    }

    fun handleRemove( ) {
        if (quantityPerson.value == 1) return
        quantityPerson.value -= 1
        handleCalcTotalPerson()
    }

    fun handleAdd()  {
        if(quantityPerson.value == 20) return
        quantityPerson.value += 1
        handleCalcTotalPerson()
    }

    fun handlePercentage(value: Float) {
        sliderPositionValue.value = value

        if(valueBillState.value.isNotEmpty() && value > 0.01) {
             totalTip.value = valueBillState.value.toDouble() * value
        }else {
            totalTip.value = 0.0
        }
        handleCalcTotalPerson()
    }



    Column {

        TopHeader(totalPerson = handleCalcTotalPerson())
        Surface(modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 7.dp)
            .fillMaxWidth(),
            shape =  RoundedCornerShape(corner = CornerSize(12.dp)),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                InputFiled(
                    modifier =  Modifier.fillMaxWidth(),
                    isSingleLine = true,
                    valueState = valueBillState,
                    enabled = true,
                    labelId = "Enter Bil",
                    keyboardAction = KeyboardActions {
                        if(!validateState) return@KeyboardActions
                        valChange(valueBillState.value)
                        keyboardController?.hide()
                    }
                )

                Row( modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 15.dp)
                    .fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,) {
                    Text(text = "Split")

                    Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {

                        RoundedButton(onClicked = {  handleRemove() }, imageVector = Icons.Rounded.Remove)
                        // quando e mais de uma palavra precisa usar o {}
                        Text(text = "${quantityPerson.value}" ,modifier = Modifier.padding(end = 4.dp, start = 4.dp), style = MaterialTheme.typography.h5 )

                        RoundedButton(onClicked = { handleAdd()  })
                    }

                }

                Row( modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 15.dp)
                    .fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Tip")


                    Text(text = "R$ ${BigDecimal(totalTip.value).setScale(2, RoundingMode.HALF_EVEN)}",modifier = Modifier.padding(end = 10.dp), style = MaterialTheme.typography.h6)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = "${totalPercentage.toInt()}  %",style = MaterialTheme.typography.h5)

                    Slider(value = sliderPositionValue.value, onValueChange = { handlePercentage(it)},
                        steps = 5,
                        modifier = Modifier.padding(horizontal = 10.dp),

                    )
                }

            }

        }
    }
}



@Preview
@Composable
fun DefaultPreview() {
    CalculatorAppTheme {
        MyApp {
            TopHeader()
        }
    }
}