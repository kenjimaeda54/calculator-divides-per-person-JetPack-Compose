package com.example.calculatorapp.componentes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun InputFiled(
      modifier: Modifier = Modifier,
      isSingleLine: Boolean,
      valueState: MutableState<String>,
      keyboardType: KeyboardType = KeyboardType.Number,
      keyboardAction: KeyboardActions = KeyboardActions.Default,
      imeAction: ImeAction = ImeAction.Next, // botão de retorno do teclado canto inferior direito
      enabled: Boolean,
      labelId: String
) {

    OutlinedTextField(
        value = valueState.value,
        onValueChange = {valueState.value = it},
        modifier = modifier.padding(bottom = 10.dp, start = 10.dp,end = 10.dp),
        enabled = enabled,
        //adicoinar no build.gradle dependencias implementation "androidx.compose.material:material-icons-extended:$compose_ui_version"
        // leading icone a esquerda
        leadingIcon = { Icon(imageVector = Icons.Rounded.AttachMoney, contentDescription = "Vector Money")},
        label = { Text(text = labelId)},
        keyboardOptions = KeyboardOptions(keyboardType= keyboardType, imeAction =  imeAction),
        keyboardActions = keyboardAction,
        singleLine =  isSingleLine,
    )

}