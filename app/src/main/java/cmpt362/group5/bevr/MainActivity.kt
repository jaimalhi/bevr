package cmpt362.group5.bevr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import cmpt362.group5.bevr.ui.drinkentry.DrinkEntryScreen
import cmpt362.group5.bevr.ui.drinkselect.DrinkSelectDialog
import cmpt362.group5.bevr.ui.drinklog.DrinkLogScreen
import cmpt362.group5.bevr.ui.locations.LocationsScreen
import cmpt362.group5.bevr.ui.settings.SettingsScreen
import cmpt362.group5.bevr.ui.theme.AppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Content()
        }
    }
}

interface Route {
    @Serializable
    object DrinkLog : Route

    @Serializable
    object Locations : Route

    @Serializable
    object DrinkEntry : Route

    @Serializable
    object DrinkSelect : Route

    @Serializable
    object Settings : Route
}


enum class NavItem(
    val route: Route, val label: String, val icon: ImageVector, val description: String
) {
    DRINK_LOG(Route.DrinkLog, "Drink Log", Icons.Default.History, "Drink Log"),
    LOCATIONS(Route.Locations, "Locations", Icons.Default.Map, "Locations"),
    DRINK_ENTRY(Route.DrinkEntry, "Add", Icons.Default.AddCircle, "Add New Drink Record"),
    DRINK_SELECT(Route.DrinkSelect, "Drinks", Icons.Default.Coffee, "Drinks Filter"),
    SETTINGS(Route.Settings, "Settings", Icons.Default.AccountCircle, "Settings And Profile"),
}

@Preview
@Composable
fun Content() {
    val navController = rememberNavController()
    val startNavItem = NavItem.DRINK_LOG
    var selectedNavItem by rememberSaveable { mutableIntStateOf(startNavItem.ordinal) }
    AppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(), bottomBar = {
                NavigationBar {
                    NavItem.entries.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedNavItem == index,
                            onClick = {
                                navController.navigate(item.route)
                                selectedNavItem = index
                            },
                            icon = { Icon(item.icon, item.description) },
                            label = { Text(item.label) },
                        )
                    }
                }
            }) { innerPadding ->
            NavHost(
                navController,
                startDestination = Route.DrinkEntry,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<Route.DrinkLog> { DrinkLogScreen() }
                composable<Route.Locations> { LocationsScreen() }
                composable<Route.DrinkEntry> { DrinkEntryScreen() }
                dialog<Route.DrinkSelect> { DrinkSelectDialog() }
                composable<Route.Settings> { SettingsScreen() }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}