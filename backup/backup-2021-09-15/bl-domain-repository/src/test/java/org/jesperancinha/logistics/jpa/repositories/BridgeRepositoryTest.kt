package org.jesperancinha.logistics.jpa.repositories

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.spring.SpringListener
import org.jesperancinha.logistics.jpa.model.Bridge
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class BridgeRepositoryTest(
    private val bridgeRepository: BridgeRepository,
) : FunSpec({

    listeners(SpringListener)

    test("adding a bridge should put it on the database") {
        val savedBridge =
            bridgeRepository.save(Bridge.builder().name("25 de Abril").address("Lx").postCode("2700").build())

        savedBridge.id.shouldNotBeNull()
        savedBridge.name shouldBe "25 de Abril"
        savedBridge.address shouldBe "Lx"
        savedBridge.postCode shouldBe "2700"
    }
})
