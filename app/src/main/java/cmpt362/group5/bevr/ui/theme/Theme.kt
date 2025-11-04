package cmpt362.group5.bevr.ui.theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val lightScheme = lightColorScheme(
    primary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryLight,
    onPrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryLight,
    primaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryContainerLight,
    onPrimaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryContainerLight,
    secondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryLight,
    onSecondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryLight,
    secondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryContainerLight,
    onSecondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryContainerLight,
    tertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryLight,
    onTertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryLight,
    tertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryContainerLight,
    onTertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryContainerLight,
    error = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorLight,
    onError = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorLight,
    errorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorContainerLight,
    onErrorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorContainerLight,
    background = _root_ide_package_.cmpt362.group5.bevr.ui.theme.backgroundLight,
    onBackground = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onBackgroundLight,
    surface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceLight,
    onSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceLight,
    surfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceVariantLight,
    onSurfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceVariantLight,
    outline = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineLight,
    outlineVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineVariantLight,
    scrim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.scrimLight,
    inverseSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inverseSurfaceLight,
    inverseOnSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inverseOnSurfaceLight,
    inversePrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inversePrimaryLight,
    surfaceDim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceDimLight,
    surfaceBright = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceBrightLight,
    surfaceContainerLowest = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLowestLight,
    surfaceContainerLow = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLowLight,
    surfaceContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLight,
    surfaceContainerHigh = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerHighLight,
    surfaceContainerHighest = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryDark,
    onPrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryDark,
    primaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryContainerDark,
    onPrimaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryContainerDark,
    secondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryDark,
    onSecondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryDark,
    secondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryContainerDark,
    onSecondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryContainerDark,
    tertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryDark,
    onTertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryDark,
    tertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryContainerDark,
    onTertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryContainerDark,
    error = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorDark,
    onError = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorDark,
    errorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorContainerDark,
    onErrorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorContainerDark,
    background = _root_ide_package_.cmpt362.group5.bevr.ui.theme.backgroundDark,
    onBackground = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onBackgroundDark,
    surface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceDark,
    onSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceDark,
    surfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceVariantDark,
    onSurfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceVariantDark,
    outline = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineDark,
    outlineVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineVariantDark,
    scrim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.scrimDark,
    inverseSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inverseSurfaceDark,
    inverseOnSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inverseOnSurfaceDark,
    inversePrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inversePrimaryDark,
    surfaceDim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceDimDark,
    surfaceBright = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceBrightDark,
    surfaceContainerLowest = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLowestDark,
    surfaceContainerLow = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLowDark,
    surfaceContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerDark,
    surfaceContainerHigh = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerHighDark,
    surfaceContainerHighest = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryLightMediumContrast,
    onPrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryLightMediumContrast,
    primaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryContainerLightMediumContrast,
    onPrimaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryContainerLightMediumContrast,
    secondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryLightMediumContrast,
    onSecondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryLightMediumContrast,
    secondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryContainerLightMediumContrast,
    onSecondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryContainerLightMediumContrast,
    tertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryLightMediumContrast,
    onTertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryLightMediumContrast,
    tertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryContainerLightMediumContrast,
    onTertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryContainerLightMediumContrast,
    error = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorLightMediumContrast,
    onError = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorLightMediumContrast,
    errorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorContainerLightMediumContrast,
    onErrorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorContainerLightMediumContrast,
    background = _root_ide_package_.cmpt362.group5.bevr.ui.theme.backgroundLightMediumContrast,
    onBackground = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onBackgroundLightMediumContrast,
    surface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceLightMediumContrast,
    onSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceLightMediumContrast,
    surfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceVariantLightMediumContrast,
    onSurfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceVariantLightMediumContrast,
    outline = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineLightMediumContrast,
    outlineVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineVariantLightMediumContrast,
    scrim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.scrimLightMediumContrast,
    inverseSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inverseSurfaceLightMediumContrast,
    inverseOnSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inverseOnSurfaceLightMediumContrast,
    inversePrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inversePrimaryLightMediumContrast,
    surfaceDim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceDimLightMediumContrast,
    surfaceBright = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceBrightLightMediumContrast,
    surfaceContainerLowest = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLowLightMediumContrast,
    surfaceContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLightMediumContrast,
    surfaceContainerHigh = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryLightHighContrast,
    onPrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryLightHighContrast,
    primaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryContainerLightHighContrast,
    onPrimaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryContainerLightHighContrast,
    secondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryLightHighContrast,
    onSecondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryLightHighContrast,
    secondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryContainerLightHighContrast,
    onSecondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryContainerLightHighContrast,
    tertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryLightHighContrast,
    onTertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryLightHighContrast,
    tertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryContainerLightHighContrast,
    onTertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryContainerLightHighContrast,
    error = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorLightHighContrast,
    onError = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorLightHighContrast,
    errorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorContainerLightHighContrast,
    onErrorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorContainerLightHighContrast,
    background = _root_ide_package_.cmpt362.group5.bevr.ui.theme.backgroundLightHighContrast,
    onBackground = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onBackgroundLightHighContrast,
    surface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceLightHighContrast,
    onSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceLightHighContrast,
    surfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceVariantLightHighContrast,
    onSurfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceVariantLightHighContrast,
    outline = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineLightHighContrast,
    outlineVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineVariantLightHighContrast,
    scrim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.scrimLightHighContrast,
    inverseSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inverseSurfaceLightHighContrast,
    inverseOnSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inverseOnSurfaceLightHighContrast,
    inversePrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inversePrimaryLightHighContrast,
    surfaceDim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceDimLightHighContrast,
    surfaceBright = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceBrightLightHighContrast,
    surfaceContainerLowest = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLowLightHighContrast,
    surfaceContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLightHighContrast,
    surfaceContainerHigh = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryDarkMediumContrast,
    onPrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryDarkMediumContrast,
    primaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryContainerDarkMediumContrast,
    onPrimaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryContainerDarkMediumContrast,
    secondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryDarkMediumContrast,
    onSecondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryDarkMediumContrast,
    secondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryContainerDarkMediumContrast,
    onSecondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryContainerDarkMediumContrast,
    tertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryDarkMediumContrast,
    onTertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryDarkMediumContrast,
    tertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryContainerDarkMediumContrast,
    error = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorDarkMediumContrast,
    onError = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorDarkMediumContrast,
    errorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorContainerDarkMediumContrast,
    onErrorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorContainerDarkMediumContrast,
    background = _root_ide_package_.cmpt362.group5.bevr.ui.theme.backgroundDarkMediumContrast,
    onBackground = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onBackgroundDarkMediumContrast,
    surface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceDarkMediumContrast,
    onSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceDarkMediumContrast,
    surfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceVariantDarkMediumContrast,
    onSurfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceVariantDarkMediumContrast,
    outline = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineDarkMediumContrast,
    outlineVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineVariantDarkMediumContrast,
    scrim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.scrimDarkMediumContrast,
    inverseSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inverseSurfaceDarkMediumContrast,
    inverseOnSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inverseOnSurfaceDarkMediumContrast,
    inversePrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.inversePrimaryDarkMediumContrast,
    surfaceDim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceDimDarkMediumContrast,
    surfaceBright = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerLowDarkMediumContrast,
    surfaceContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryDarkHighContrast,
    onPrimary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryDarkHighContrast,
    primaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.primaryContainerDarkHighContrast,
    onPrimaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onPrimaryContainerDarkHighContrast,
    secondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryDarkHighContrast,
    onSecondary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryDarkHighContrast,
    secondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.secondaryContainerDarkHighContrast,
    onSecondaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSecondaryContainerDarkHighContrast,
    tertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryDarkHighContrast,
    onTertiary = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryDarkHighContrast,
    tertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.tertiaryContainerDarkHighContrast,
    onTertiaryContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onTertiaryContainerDarkHighContrast,
    error = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorDarkHighContrast,
    onError = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorDarkHighContrast,
    errorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.errorContainerDarkHighContrast,
    onErrorContainer = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onErrorContainerDarkHighContrast,
    background = _root_ide_package_.cmpt362.group5.bevr.ui.theme.backgroundDarkHighContrast,
    onBackground = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onBackgroundDarkHighContrast,
    surface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceDarkHighContrast,
    onSurface = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceDarkHighContrast,
    surfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.surfaceVariantDarkHighContrast,
    onSurfaceVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.onSurfaceVariantDarkHighContrast,
    outline = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineDarkHighContrast,
    outlineVariant = _root_ide_package_.cmpt362.group5.bevr.ui.theme.outlineVariantDarkHighContrast,
    scrim = _root_ide_package_.cmpt362.group5.bevr.ui.theme.scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}

