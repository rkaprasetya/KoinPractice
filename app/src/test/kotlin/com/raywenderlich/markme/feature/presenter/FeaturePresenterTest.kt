package com.raywenderlich.markme.feature.presenter

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.raywenderlich.markme.di.applicationModule
import com.raywenderlich.markme.feature.FeatureContract
import com.raywenderlich.markme.model.Student
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.koin.test.declareMock
import org.mockito.Mockito

class FeaturePresenterTest:KoinTest {
    private val view : FeatureContract.View<Student> = mock()
    private val repository:FeatureContract.Model<Student> by inject()
    private val presenter : FeatureContract.Presenter<Student> by inject { parametersOf(view) }

    @Before
    fun setup() {
        startKoin(listOf(applicationModule))
        declareMock<FeatureContract.Model<Student>>()
    }
    @After
    fun after(){
        stopKoin()
    }

    @Test
    fun `check that onSave2DbClick invokes a repository callback`(){
        //arrange
        val studentList = listOf(Student(0,"Pablo",true,8),
                Student(1,"Irene",false,10))
        val dummyCallback = argumentCaptor<(String) -> Unit>()
        //act
        presenter.onSave2DbClick(studentList)
        //assert
        Mockito.verify(repository).add2Db(data = eq(studentList),callback = dummyCallback.capture())
    }

}