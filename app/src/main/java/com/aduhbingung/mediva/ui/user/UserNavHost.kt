package com.aduhbingung.mediva.ui.user

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.aduhbingung.mediva.ui.user.model.EmergencyReportState
import com.aduhbingung.mediva.ui.user.model.UserRoute
import com.aduhbingung.mediva.ui.user.screens.CameraPermissionScreen
import com.aduhbingung.mediva.ui.user.screens.EmergencyReportScreen
import com.aduhbingung.mediva.ui.user.screens.LocationConfirmationScreen
import com.aduhbingung.mediva.ui.user.screens.OptionalAttachmentScreen
import com.aduhbingung.mediva.ui.user.screens.ReportReviewScreen
import com.aduhbingung.mediva.ui.user.screens.ReportSentSuccessScreen
import com.aduhbingung.mediva.ui.user.screens.UserHomeScreen
import com.aduhbingung.mediva.ui.user.screens.WaitingDriverScreen

@Composable
fun UserNavHost(
    onLogoutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val backStack = remember { mutableStateListOf<UserRoute>(UserRoute.Home) }
    val currentRoute = backStack.lastOrNull() ?: UserRoute.Home

    var reportState by remember { mutableStateOf(EmergencyReportState()) }

    fun navigateTo(route: UserRoute) {
        backStack.add(route)
    }

    fun popBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
        }
    }

    fun resetTo(route: UserRoute) {
        backStack.clear()
        backStack.add(route)
    }

    // System Back Press Handler
    BackHandler(enabled = backStack.size > 1) {
        popBack()
    }

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = currentRoute,
            transitionSpec = {
                (slideInHorizontally { width -> width / 3 } + fadeIn()) togetherWith
                        (slideOutHorizontally { width -> -width / 3 } + fadeOut())
            },
            label = "user_flow_transition"
        ) { route ->
            when (route) {
                is UserRoute.Home -> {
                    UserHomeScreen(
                        userName = "Narendra",
                        onEmergencyCallClick = {
                            navigateTo(UserRoute.EmergencyReport)
                        },
                        onViewHistoryClick = {
                            /* Open History */
                        },
                        onTabSelected = { tab ->
                            if (tab == "Lapor") {
                                navigateTo(UserRoute.EmergencyReport)
                            } else if (tab == "Akun") {
                                onLogoutClick()
                            }
                        }
                    )
                }

                is UserRoute.EmergencyReport -> {
                    EmergencyReportScreen(
                        initialState = reportState,
                        onReviewReportClick = { updatedState ->
                            reportState = updatedState
                            navigateTo(UserRoute.LocationConfirmation)
                        },
                        onAddPhotoClick = {
                            navigateTo(UserRoute.CameraPermission)
                        },
                        onChangeLocationClick = {
                            navigateTo(UserRoute.LocationConfirmation)
                        },
                        onBackClick = {
                            popBack()
                        },
                        onTabSelected = { tab ->
                            if (tab == "Beranda") {
                                resetTo(UserRoute.Home)
                            } else if (tab == "Akun") {
                                onLogoutClick()
                            }
                        }
                    )
                }

                is UserRoute.CameraPermission -> {
                    CameraPermissionScreen(
                        onGrantCameraClick = {
                            navigateTo(UserRoute.OptionalAttachment)
                        },
                        onPickGalleryClick = {
                            navigateTo(UserRoute.OptionalAttachment)
                        },
                        onContinueWithoutPhotoClick = {
                            reportState = reportState.copy(hasPhotoAttachment = false)
                            popBack()
                        },
                        onBackClick = {
                            popBack()
                        }
                    )
                }

                is UserRoute.OptionalAttachment -> {
                    OptionalAttachmentScreen(
                        onUsePhotoClick = {
                            reportState = reportState.copy(hasPhotoAttachment = true)
                            popBack()
                        },
                        onChangePhotoClick = {
                            /* Choose different photo */
                        },
                        onBackClick = {
                            popBack()
                        },
                        onTabSelected = { tab ->
                            if (tab == "Beranda") resetTo(UserRoute.Home)
                        }
                    )
                }

                is UserRoute.LocationConfirmation -> {
                    LocationConfirmationScreen(
                        locationAddress = reportState.locationAddress,
                        locationAccuracy = reportState.locationAccuracy,
                        onConfirmLocationClick = {
                            navigateTo(UserRoute.ReportReview)
                        },
                        onChangeLocationClick = {
                            /* Edit location pin */
                        },
                        onBackClick = {
                            popBack()
                        },
                        onTabSelected = { tab ->
                            if (tab == "Beranda") resetTo(UserRoute.Home)
                        }
                    )
                }

                is UserRoute.ReportReview -> {
                    ReportReviewScreen(
                        reportState = reportState,
                        onSlideConfirm = {
                            navigateTo(UserRoute.ReportSentSuccess)
                        },
                        onEditReportClick = {
                            popBack()
                        },
                        onBackClick = {
                            popBack()
                        },
                        onTabSelected = { tab ->
                            if (tab == "Beranda") resetTo(UserRoute.Home)
                        }
                    )
                }

                is UserRoute.ReportSentSuccess -> {
                    ReportSentSuccessScreen(
                        reportState = reportState,
                        onTrackStatusClick = {
                            navigateTo(UserRoute.WaitingDriver)
                        },
                        onViewDetailClick = {
                            navigateTo(UserRoute.WaitingDriver)
                        },
                        onTabSelected = { tab ->
                            if (tab == "Beranda") resetTo(UserRoute.Home)
                        }
                    )
                }

                is UserRoute.WaitingDriver -> {
                    WaitingDriverScreen(
                        locationAddress = reportState.locationAddress,
                        onStayOnPageClick = {
                            /* Stay */
                        },
                        onBackToHomeClick = {
                            resetTo(UserRoute.Home)
                        },
                        onBackClick = {
                            popBack()
                        },
                        onTabSelected = { tab ->
                            if (tab == "Beranda") resetTo(UserRoute.Home)
                        }
                    )
                }
            }
        }
    }
}
