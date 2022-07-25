package me.bkkn.githubapp.di

import kotlin.reflect.KClass

interface Di {
    fun <T : Any> get(clazz: KClass<T>): T
    fun <T : Any> add(clazz: KClass<T>, dependency: T)
    fun <T : Any> add( dependency: T)
}

class DiImpl : Di {
    private val dependenciesHolder = HashMap<KClass<*>, Any>()

    override fun <T : Any> get(clazz: KClass<T>): T {
        if (dependenciesHolder.containsKey(clazz))
            return dependenciesHolder[clazz] as T
        else throw IllegalAccessException("no such Key in map")
    }

    override fun <T : Any> add(clazz: KClass<T>, dependency: T) {
        dependenciesHolder[clazz] = dependency
    }
    override fun <T : Any> add( dependency: T) {
        dependenciesHolder[dependency::class] = dependency
    }
}