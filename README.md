# Caluladora para dividir as depessas no restaurante
Pequena calculadora para divider as despesas , pode inserir o valor total, a taxa que deu e quantidade de pessaos que ira dividir


## Feature
- Aprendi a manipular campos de input
- Apliquei uma logica para melhorar experiencia do usuario , usando opção de fechar o teclado assim que inserir o valor 
- Em keyboardAction tenho acesso ao input
- Para usar icones precisamos instalar a dependencia androidx.compose.material:material-icons-extended:$compose_ui_version
- ImeAction e o botão do teclado canto inferior esquerdo

```kotlin
// controller que ira garantir fechar o teclado

val keyboardController = LocalSoftwareKeyboardController.current


 InputField(
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



// componente InputField


fun InputField(
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
        leadingIcon = { Icon(imageVector = Icons.Rounded.AttachMoney, contentDescription = "Vector Money")},
        label = { Text(text = labelId)},
        keyboardOptions = KeyboardOptions(keyboardType= keyboardType, imeAction =  imeAction),
        keyboardActions = keyboardAction,
        singleLine =  isSingleLine,
    )

}

```

- Para criar um botão redondo usamos um hacker com componente card




```kotlin
fun RoundedButton(
    modifier: Modifier = Modifier,
    tint: Color = Color.Black.copy(alpha = 0.7f),
    onClicked: () -> Unit,
    backgroundColor: Color = MaterialTheme.colors.background,
    elevation: Dp = 4.dp,
    imageVector: ImageVector =  Icons.Rounded.Add
) {
    Card(
        //fazendo tipo animação quando clicar no botão,icon ira subir 44
        modifier = modifier.padding(4.dp).clickable { onClicked.invoke()}.then(IconButtonSizeModifier),
        backgroundColor = backgroundColor,
        shape = CircleShape,
        elevation = elevation
    ) {

       Icon(
           imageVector = imageVector,
           contentDescription = "Image pluss",
           tint = tint,
           )
    }
}


```



