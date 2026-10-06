package com.aduhbingung.mediva.ui.user.model

sealed interface UserRoute {
    data object Home : UserRoute
    data object EmergencyReport : UserRoute
    data object CameraPermission : UserRoute
    data object OptionalAttachment : UserRoute
    data object LocationConfirmation : UserRoute
    data object ReportReview : UserRoute
    data object ReportSentSuccess : UserRoute
    data object WaitingDriver : UserRoute
}
