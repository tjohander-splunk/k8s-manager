package com.splunk.k8smanager.model

import io.kubernetes.client.openapi.models.V1LabelSelector

class Deployment(
    val name: String,
    val spec: DeploymentSpec? = null
)

data class DeploymentSpec(
    val replicas: Int,
    val selector: V1LabelSelector
)