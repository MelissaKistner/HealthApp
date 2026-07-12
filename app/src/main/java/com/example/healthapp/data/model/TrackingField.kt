package com.example.healthapp.data.model

sealed interface TrackingField {
    data object Effectiveness: TrackingField
    data object Medicine: TrackingField
    data object Scale: TrackingField
    data object  FreeText: TrackingField
    data object Trigger: TrackingField
    data object YesNo: TrackingField
    data object FunctionalImpact: TrackingField
    data object Symptoms: TrackingField
}