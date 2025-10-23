package com.calyrsoft.ucbp1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.calyrsoft.ucbp1.navigation.AppNavigation
import com.calyrsoft.ucbp1.navigation.NavigationDrawer
import com.calyrsoft.ucbp1.navigation.NavigationViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val navigationViewModel: NavigationViewModel by viewModels()
    private var currentIntent: Intent? = null

    // Solicitud de permiso POST_NOTIFICATIONS (Android 13+)
    private val requestPostNotifications =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Log.d("FIREBASE", "POST_NOTIFICATIONS granted? $granted")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentIntent = intent

        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                Log.d("MainActivity", "onCreate - Procesando intent inicial")
                navigationViewModel.handleDeepLink(currentIntent)
            }
            LaunchedEffect(Unit) {
                snapshotFlow { currentIntent }
                    .distinctUntilChanged()
                    .collect { intent ->
                        Log.d("MainActivity", "Nuevo intent recibido: ${intent?.action}")
                        navigationViewModel.handleDeepLink(intent)
                    }
            }
            MainApp(navigationViewModel)
        }

        // Pedir permiso de notificaciones en Android 13+
        if (Build.VERSION.SDK_INT >= 33) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            val hasPermission =
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                requestPostNotifications.launch(permission)
            }
        }

        // Obtener y loguear el token FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FIREBASE", "Fetching FCM token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FIREBASE", "FCM Token: $token")
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("MainActivity", "onNewIntent llamado")
        this.intent = intent
        currentIntent = intent
        navigationViewModel.handleDeepLink(intent)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NavigationDrawerHost(
        coroutineScope: CoroutineScope,
        drawerState: DrawerState,
        navigationViewModel: NavigationViewModel,
        navController: NavHostController
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    modifier = Modifier.statusBarsPadding(),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            AppNavigation(
                navController = navController,
                navigationViewModel = navigationViewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainApp(navigationViewModel: NavigationViewModel) {
        val navController: NavHostController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val navigationDrawerItems = listOf(
            NavigationDrawer.Profile,
            NavigationDrawer.Dollar,
            NavigationDrawer.Movie,
            NavigationDrawer.Github
        )

        val drawerState = rememberDrawerState(
            initialValue = androidx.compose.material3.DrawerValue.Closed
        )
        val coroutineScope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier.width(256.dp)) {
                    Box(
                        modifier = Modifier.width(256.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.width(120.dp),
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Logo"
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Logo",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    navigationDrawerItems.forEach { item ->
                        val isSelected = currentDestination?.route == item.route
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }
                }
            }
        ) {
            NavigationDrawerHost(coroutineScope, drawerState, navigationViewModel, navController)
        }
    }
}
