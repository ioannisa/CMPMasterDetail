package eu.anifantakis.cmpmasterdetail.app.global_state.domain

sealed class GlobalAction {
    data object ShowLoading: GlobalAction()
    data object HideLoading: GlobalAction()
}