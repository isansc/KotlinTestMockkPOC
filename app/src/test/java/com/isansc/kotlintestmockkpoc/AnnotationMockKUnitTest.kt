package com.isansc.kotlintestmockkpoc

import com.isansc.kotlintestmockkpoc.tools.Foo
import com.isansc.kotlintestmockkpoc.tools.InjectTestService
import com.isansc.kotlintestmockkpoc.tools.TestableService
import com.isansc.kotlintestmockkpoc.tools.TestableSingletonService
import io.kotlintest.shouldBe
import io.kotlintest.specs.AbstractAnnotationSpec
import io.kotlintest.specs.AnnotationSpec
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK

class AnnotationMockKUnitTest: AnnotationSpec(){
    @MockK
    lateinit var service1: TestableService

    @MockK
    lateinit var service2: TestableService

    @InjectMockKs
    var objectUnderTest = InjectTestService()
    // This specifies an object where defined mocks should be injected. By default, it injects variables that are not assigned yet. We can use @OverrideMockKs to override fields that have a value already.

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)
    // MockK requires MockKAnnotations.init(…) to be called on an object declaring a variable with annotations.

    @Test
    fun testMockTestableService(){

        every { service1.getDataFromDb(any()) } returns "Mocked Output"
        every { service2.getDataFromDb(any()) } returns "Mocked Output 2"

        // when checking mocked method
        val result = objectUnderTest.invokeService1()

        // then
        result shouldBe "Mocked Output"

    }

    @Test
    fun givenServiceSpy_whenMockingOnlyOneMethod_thenOtherMethodsShouldBehaveAsOriginalObject() {
        // given
        val service = spyk<TestableService>()
        every { service.getDataFromDb(any()) } returns "Mocked Output"

        // when checking mocked method
        val firstResult = service.getDataFromDb("Any Param")

        // then
        firstResult shouldBe "Mocked Output"

        // when checking not mocked method
        val secondResult = service.doSomethingElse("Any Param")

        // then
        secondResult shouldBe "I don't want to!"
    }

    @Test
    fun givenRelaxedMock_whenCallingNotMockedMethod_thenReturnDefaultValue() {
        // given
        val service = mockk<TestableService>(relaxed = true) // This kind of mock provides default values for each function.

        // when
        val result = service.getDataFromDb("Any Param")

        // then
        result shouldBe ""
    }

    @Test
    fun givenObject_whenMockingIt_thenMockedMethodShouldReturnProperValue(){
        // given
        mockkObject(TestableSingletonService) // This allows to mock singleton objects

        // when calling not mocked method
        val service = TestableSingletonService
        val firstResult = service.getDataFromDb("Any Param")

        // then return real response
        firstResult shouldBe "something"
        // when calling mocked method
        every { service.getDataFromDb(any()) } returns "Mocked Output"
        val secondResult = service.getDataFromDb("Any Param")

        // then return mocked response
        secondResult shouldBe "Mocked Output"
    }

    @Test
    fun givenHierarchicalClass_whenMockingIt_thenReturnProperValue() {
        // given
        val foo = mockk<Foo> {
            every { name } returns "Karol"
            every { bar } returns mockk {
                every { nickname } returns "Tomato"
            }
        }

        // when
        val name = foo.name
        val nickname = foo.bar.nickname

        // then
        name shouldBe "Karol"
        nickname shouldBe "Tomato"
    }

    @Test
    fun givenMock_whenCapturingParamValue_thenProperValueShouldBeCaptured() {
        // given
        val service = mockk<TestableService>()
        val slot = slot<String>()
        every { service.getDataFromDb(capture(slot)) } returns "Expected Output"

        // when
        service.getDataFromDb("Expected Param")

        // then
        slot.captured shouldBe "Expected Param"
    }

    @Test
    fun givenMock_whenCapturingParamsValues_thenProperValuesShouldBeCaptured() {
        // given
        val service = mockk<TestableService>()
        val list = mutableListOf<String>()
        every { service.getDataFromDb(capture(list)) } returns "Expected Output"

        // when
        service.getDataFromDb("Expected Param 1")
        service.getDataFromDb("Expected Param 2")

        // then
        list.size shouldBe 2
        list[0] shouldBe "Expected Param 1"
        list[1] shouldBe "Expected Param 2"
    }
}