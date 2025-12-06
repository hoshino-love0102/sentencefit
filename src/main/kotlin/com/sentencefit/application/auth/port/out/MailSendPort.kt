package com.sentencefit.application.auth.port.out

interface MailSendPort {
    fun send(to: String, subject: String, text: String)
}