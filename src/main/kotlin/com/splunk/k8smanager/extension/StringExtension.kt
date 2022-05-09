package com.splunk.k8smanager.extension

import io.kubernetes.client.custom.V1Patch

fun String.toV1Patch(): V1Patch = V1Patch(this)
