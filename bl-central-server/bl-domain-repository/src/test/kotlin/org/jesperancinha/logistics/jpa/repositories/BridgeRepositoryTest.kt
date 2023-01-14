package org.jesperancinha.logistics.jpa.repositories

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jesperancinha.logistics.jpa.dao.Bridge
import org.jesperancinha.logistics.jpa.dao.BridgeRepository
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class BridgeRepositoryTest(
    private val bridgeRepository: BridgeRepository,
) : FunSpec({

    extensions(SpringExtension)

    test("adding a bridge should put it on the database") {
        val savedBridge =
            withContext(Dispatchers.IO) {
                bridgeRepository.save(
                    Bridge(
                        name = "25 de Abril",
                        address = "Lx",
                        postCode = "2700"
                    )
                )
            }

        savedBridge.id.shouldNotBeNull()
        savedBridge.name shouldBe "25 de Abril"
        savedBridge.address shouldBe "Lx"
        savedBridge.postCode shouldBe "2700"
    }
})
