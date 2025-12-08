package com.sentencefit.adapter.out.mail

import com.sentencefit.application.auth.port.out.MailSendPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class MailSenderAdapter(
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.username}") private val from: String
) : MailSendPort {

    override fun send(to: String, subject: String, text: String) {
        val message = SimpleMailMessage().apply {
            setFrom(from)
            setTo(to)
            setSubject(subject)
            setText(text)
        }
        javaMailSender.send(message)
    }
}