package com.splunk.k8smanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sManagerApplication

fun main(args: Array<String>) {
    runApplication<K8sManagerApplication>(*args)
}
