package com.lcmobile.kotlinbestpractices.demo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {

    private val dispacher = TestCoroutineDispatcher()
    private val scope = TestCoroutineScope(dispacher)

    override fun apply(base: Statement?, description: Description?) = object : Statement() {
        override fun evaluate() {
//            Dispatchers.setMain(dispacher)
            Dispatchers.setMain(Dispatchers.Unconfined)

            base?.evaluate()

            Dispatchers.resetMain()
            scope.cleanupTestCoroutines()
        }
    }

    fun runBlocking(block: suspend TestCoroutineScope.() -> Unit) {
        scope.runBlockingTest { block() }
    }
}

