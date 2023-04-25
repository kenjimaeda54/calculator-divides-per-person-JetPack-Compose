package com.example.calculatorapp.widget
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val IconButtonSizeModifier = Modifier.size(30.dp)

@Composable
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