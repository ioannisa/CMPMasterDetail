package eu.anifantakis.cmpmasterdetail.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cmpmasterdetail.composeapp.generated.resources.Poppins_Bold
import cmpmasterdetail.composeapp.generated.resources.Poppins_Italic
import cmpmasterdetail.composeapp.generated.resources.Poppins_Regular
import cmpmasterdetail.composeapp.generated.resources.Poppins_SemiBold
import cmpmasterdetail.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

object AppColor {
    val White = Color(0xFFFFFFFF)
    val Whiten = Color(0xFFF5F5F5)
    val BrightGrey = Color(0xFFC5C5C5)
    val Grey = Color(0xFFC4C4C4)
    val MidGrey = Color(0xFF969696)
    val DarkGrey = Color(0xFF181818)
    val Yellow = Color(0xFFFFC000)
}

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    // Define your custom font family
    val myFontFamily = FontFamily(
        Font(resource = Res.font.Poppins_Bold, weight = FontWeight.Bold),
        Font(resource = Res.font.Poppins_SemiBold, weight = FontWeight.SemiBold),
        Font(resource = Res.font.Poppins_Regular, weight = FontWeight.Normal, style = FontStyle.Normal),
        Font(resource = Res.font.Poppins_Italic, weight = FontWeight.Normal, style = FontStyle.Italic)
    )

    // Create a Typography instance that uses your font family as default
    val typography = Typography(
        bodyLarge = TextStyle(
            fontFamily = myFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        titleLarge = TextStyle(
            fontSize = 20.sp,
            fontFamily = myFontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        bodyMedium = TextStyle(
            fontFamily = myFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        displaySmall = TextStyle(
            fontFamily = myFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    )

    val colorScheme = MaterialTheme.colorScheme.copy(
        primary = AppColor.BrightGrey,
        onPrimary = AppColor.DarkGrey,
        secondary = AppColor.Grey,
        onSecondary = AppColor.DarkGrey,
        tertiary = AppColor.Yellow,
        onTertiary = AppColor.DarkGrey,

        surface = AppColor.DarkGrey,
        onSurface = AppColor.BrightGrey,
        background = AppColor.DarkGrey,
        onBackground = AppColor.BrightGrey
    )

    MaterialTheme(
        typography = typography,
        colorScheme = colorScheme,
        content = content
    )
}