package org.emanueldossantos.model

import java.time.LocalDateTime
import java.util.UUID

class Sessao(
    val token: String = UUID.randomUUID().toString(),
    val datahora: LocalDateTime = LocalDateTime.now()
)
