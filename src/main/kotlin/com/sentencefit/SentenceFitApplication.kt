package com.sentencefit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SentenceFitApplication

fun main(args: Array<String>) {
	runApplication<SentenceFitApplication>(*args)
}
