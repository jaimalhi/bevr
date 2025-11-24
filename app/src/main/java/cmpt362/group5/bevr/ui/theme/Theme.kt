package cmpt362.group5.bevr.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import cmpt362.group5.bevr.data.usersettings.UserSettings
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepository

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
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


// Coffee theme
private val coffeeLightColorScheme = lightColorScheme(
    primary = Color(0xFF6F4E37),          // coffee brown
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCC6B6),
    onPrimaryContainer = Color(0xFF3B2F2F),
    secondary = Color(0xFF8B6D5C),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF0E2DA),
    onSecondaryContainer = Color(0xFF3B2F2F),
    tertiary = Color(0xFFBFA6A0),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFF6E7E1),
    onTertiaryContainer = Color(0xFF3B2F2F),
    background = Color(0xFFFFFBF5),
    onBackground = Color(0xFF3B2F2F),
    surface = Color(0xFFFFFBF5),
    onSurface = Color(0xFF3B2F2F),
    surfaceVariant = Color(0xFFEDE0D9),
    onSurfaceVariant = Color(0xFF3B2F2F),
    error = Color(0xFFB00020),
    onError = Color.White
)

private val coffeeDarkColorScheme = darkColorScheme(
    primary = Color(0xFFDCC6B6),
    onPrimary = Color(0xFF3B2F2F),
    primaryContainer = Color(0xFF6F4E37),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFE0CFC2),
    onSecondary = Color(0xFF3B2F2F),
    secondaryContainer = Color(0xFF8B6D5C),
    onSecondaryContainer = Color.White,
    tertiary = Color(0xFFD1BFB9),
    onTertiary = Color(0xFF3B2F2F),
    tertiaryContainer = Color(0xFFBFA6A0),
    onTertiaryContainer = Color.White,
    background = Color(0xFF3B2F2F),
    onBackground = Color(0xFFFFF6ED),
    surface = Color(0xFF3B2F2F),
    onSurface = Color(0xFFFFF6ED),
    surfaceVariant = Color(0xFF554438),
    onSurfaceVariant = Color(0xFFEDE0D9),
    error = Color(0xFFCF6679),
    onError = Color.Black
)

// Tea theme
private val teaLightColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50),          // green tea
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC8E6C9),
    onPrimaryContainer = Color(0xFF1B5E20),
    secondary = Color(0xFF81C784),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8F5E9),
    onSecondaryContainer = Color(0xFF1B5E20),
    tertiary = Color(0xFFA5D6A7),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFE0F2F1),
    onTertiaryContainer = Color(0xFF1B5E20),
    background = Color(0xFFE8F5E9),
    onBackground = Color(0xFF1B5E20),
    surface = Color(0xFFE8F5E9),
    onSurface = Color(0xFF1B5E20)
)

private val teaDarkColorScheme = darkColorScheme(
    primary = Color(0xFFC8E6C9),
    onPrimary = Color(0xFF1B5E20),
    primaryContainer = Color(0xFF4CAF50),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFA5D6A7),
    onSecondary = Color(0xFF1B5E20),
    secondaryContainer = Color(0xFF81C784),
    onSecondaryContainer = Color.White,
    background = Color(0xFF1B5E20),
    onBackground = Color(0xFFE8F5E9),
    surface = Color(0xFF1B5E20),
    onSurface = Color(0xFFE8F5E9)
)

// Juice theme
private val juiceLightColorScheme = lightColorScheme(
    primary = Color(0xFFFF9800),          // orange
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE0B2),
    onPrimaryContainer = Color(0xFF5D4037),
    secondary = Color(0xFFFFB74D),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFFF3E0),
    onSecondaryContainer = Color(0xFF5D4037),
    background = Color(0xFFFFF8E1),
    onBackground = Color(0xFF5D4037),
    surface = Color(0xFFFFF8E1),
    onSurface = Color(0xFF5D4037)
)

private val juiceDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFE0B2),
    onPrimary = Color(0xFF5D4037),
    primaryContainer = Color(0xFFFF9800),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFFFCC80),
    onSecondary = Color(0xFF5D4037),
    background = Color(0xFF5D4037),
    onBackground = Color(0xFFFFF3E0),
    surface = Color(0xFF5D4037),
    onSurface = Color(0xFFFFF3E0)
)

private val liquorLightColorScheme = lightColorScheme(
    primary = Color(0xFF6F1D1B),           // deep red wine
    onPrimary = Color(0xFFFFF8F5),         // off-white for contrast
    primaryContainer = Color(0xFFFADADD),  // soft pink container
    onPrimaryContainer = Color(0xFF6F1D1B),
    secondary = Color(0xFFB35C44),         // amber
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFE3DA),
    onSecondaryContainer = Color(0xFFB35C44),
    tertiary = Color(0xFF8A5A44),          // brownish accent
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFE8D6),
    onTertiaryContainer = Color(0xFF8A5A44),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    background = Color(0xFFFFFBFA),
    onBackground = Color(0xFF201A19),
    surface = Color(0xFFFFFBFA),
    onSurface = Color(0xFF201A19),
    surfaceVariant = Color(0xFFF5DDDA),
    onSurfaceVariant = Color(0xFF534341),
    outline = Color(0xFF857370),
    inverseSurface = Color(0xFF362F2E),
    inverseOnSurface = Color(0xFFFBEEEC),
    inversePrimary = Color(0xFFF7BDC0)
)

private val liquorDarkColorScheme = darkColorScheme(
    primary = Color(0xFFF7BDC0),           // lighter red for dark theme
    onPrimary = Color(0xFF4C0F0E),
    primaryContainer = Color(0xFF6F1D1B),
    onPrimaryContainer = Color(0xFFFFDADA),
    secondary = Color(0xFFFFB59C),
    onSecondary = Color(0xFF5B1F12),
    secondaryContainer = Color(0xFF8C3A25),
    onSecondaryContainer = Color(0xFFFFDAD0),
    tertiary = Color(0xFFD3A78A),
    onTertiary = Color(0xFF422E21),
    tertiaryContainer = Color(0xFF603D2C),
    onTertiaryContainer = Color(0xFFFFDCC5),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    background = Color(0xFF201A19),
    onBackground = Color(0xFFEDE0DD),
    surface = Color(0xFF201A19),
    onSurface = Color(0xFFEDE0DD),
    surfaceVariant = Color(0xFF534341),
    onSurfaceVariant = Color(0xFFD7C1BE),
    outline = Color(0xFFA08C88),
    inverseSurface = Color(0xFFEDE0DD),
    inverseOnSurface = Color(0xFF201A19),
    inversePrimary = Color(0xFF6F1D1B)
)


// Boba theme
private val bobaLightColorScheme = lightColorScheme(
    primary = Color(0xFF8D6E63),          // milk tea brown
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD7CCC8),
    onPrimaryContainer = Color(0xFF3E2723),
    secondary = Color(0xFFA1887F),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFF5F5F5),
    onSecondaryContainer = Color(0xFF3E2723),
    background = Color(0xFFFFF8F4),
    onBackground = Color(0xFF3E2723),
    surface = Color(0xFFFFF8F4),
    onSurface = Color(0xFF3E2723)
)

private val bobaDarkColorScheme = darkColorScheme(
    primary = Color(0xFFD7CCC8),
    onPrimary = Color(0xFF3E2723),
    primaryContainer = Color(0xFF8D6E63),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFBCAAA4),
    onSecondary = Color(0xFF3E2723),
    background = Color(0xFF3E2723),
    onBackground = Color(0xFFFFF8F4),
    surface = Color(0xFF3E2723),
    onSurface = Color(0xFFFFF8F4)
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
    userSettingsRepository: UserSettingsRepository,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Observe the UserSettings as Compose state
    val userSettings by userSettingsRepository.getUserSettings().collectAsState(
        initial = UserSettings(
            displayName = "Guest",
            avatarId = 0,
            activeBeverages = emptySet(),
            selectedTheme = "default" // Set to default theme initially
        )
    )

    // For debugging
//    LaunchedEffect(userSettings.selectedTheme) {
//        Log.d("AppTheme", "Selected theme: ${userSettings.selectedTheme}")
//    }

    // Map drink names to color schemes
    val drinkThemes = mapOf(
        "default" to mapOf(
            true to mediumContrastDarkColorScheme,
            false to mediumContrastLightColorScheme
        ),
        "coffee" to mapOf(
            true to coffeeDarkColorScheme,
            false to coffeeLightColorScheme
        ),
        "tea" to mapOf(
            true to teaDarkColorScheme,
            false to teaLightColorScheme
        ),
        "juice" to mapOf(
            true to juiceDarkColorScheme,
            false to juiceLightColorScheme
        ),
        "liquor" to mapOf(
            true to liquorDarkColorScheme,
            false to liquorLightColorScheme
        ),
        "boba" to mapOf(
            true to bobaDarkColorScheme,
            false to bobaLightColorScheme
        )
    )

    // Pick the color scheme based on the selected drink theme
    val colorScheme = drinkThemes[userSettings.selectedTheme]?.get(darkTheme) ?: mediumContrastDarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = shapes,
        content = content
    )
}



