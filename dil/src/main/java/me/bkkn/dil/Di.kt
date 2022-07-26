package me.bkkn.dil

import kotlin.reflect.KClass

object Di {
    private val dependenciesHolder = HashMap<KClass<*>, DependencyFactory>()
    fun <T : Any> get(clazz: KClass<T>): T {
        val dependencyFactory = dependenciesHolder[clazz]
        if (dependencyFactory != null) {
            return dependencyFactory.get() as T
        } else throw IllegalAccessException("no such Key in map")
    }

    fun <T : Any> add(clazz: KClass<T>, dependencyFactory: DependencyFactory) {
        dependenciesHolder[clazz] = dependencyFactory
    }
}

inline fun <reified T : Any> get(): T {
    return Di.get(T::class)
}

inline fun <reified T : Any> inject() = lazy {
    get<T>()
}

abstract class DependencyFactory(protected val creator: () -> Any) {
    abstract fun get(): Any
}

class Singleton(creator: () -> Any) : DependencyFactory(creator) {
    private val dependency: Any by lazy { creator.invoke() }
    override fun get(): Any = dependency
}

class Factory(creator: () -> Any) : DependencyFactory(creator) {
    override fun get(): Any = creator()
}