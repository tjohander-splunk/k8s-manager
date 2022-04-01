package com.splunk.k8smanager.extension

import com.splunk.k8smanager.model.Deployment
import com.splunk.k8smanager.model.DeploymentSpec
import io.kubernetes.client.openapi.models.V1Deployment
import io.kubernetes.client.openapi.models.V1DeploymentSpec

fun V1DeploymentSpec.toDeploymentSpec() = DeploymentSpec(
    replicas = this.replicas ?: 1,
    selector = this.selector
)

fun V1Deployment.toDeployment() = Deployment(
    name = this.metadata?.name!!,
    spec = this.spec?.toDeploymentSpec()
)