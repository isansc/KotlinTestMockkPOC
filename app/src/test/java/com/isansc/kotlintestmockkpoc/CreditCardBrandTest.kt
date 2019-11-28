package com.isansc.kotlintestmockkpoc

import br.com.moip.creditcard.Brands
import br.com.moip.validators.CreditCard
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.setMain

@UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class CreditCardBrandTest: FeatureSpec({
    feature("Card issuer recognition") {

        val cardData = mapOf(
            "5494275943345454" to Brands.MASTERCARD,
            "4716157914089748" to Brands.VISA,
            "344013826477034" to Brands.AMERICAN_EXPRESS,
            "379237058082588" to Brands.AMERICAN_EXPRESS,
            "30111122223331" to Brands.DINERS,
            "3841001111222233334" to Brands.HIPERCARD,
            "6370950000000005" to Brands.HIPER
        )

        cardData.forEach { cardNumber, issuer ->
            Dispatchers.setMain(Dispatchers.Default)
            GlobalScope.launch (Dispatchers.Main) {
                scenario("card number $cardNumber should be recognized as $issuer") {
                    CreditCard(cardNumber).brand.shouldBe(issuer)
                }
            }

        }
    }
})
