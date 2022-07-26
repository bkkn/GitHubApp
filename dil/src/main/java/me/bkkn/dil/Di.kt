package me.bkkn.dil

import java.lang.IllegalStateException
import kotlin.reflect.KClass

object Di {
    private val dependenciesHolder = HashMap<Qualifier, DependencyFactory<*>>()
    fun <T : Any> get(qualifier: Qualifier): T {
        val dependencyFactory = dependenciesHolder[qualifier]
        if (dependencyFactory != null) {
            return dependencyFactory.get() as T
        } else throw IllegalAccessException("no such Key in map")
    }

    fun <T : Any> add(qualifier: Qualifier, dependencyFactory: DependencyFactory<T>) {
        if(dependenciesHolder.containsKey(qualifier)){
            throw IllegalStateException("duplicate!")
        }
        dependenciesHolder[qualifier] = dependencyFactory
    }

    inline fun <reified T : Any> add(dependencyFactory: DependencyFactory<T>) {
        add(Qualifier(T::class), dependencyFactory)
    }
}

data class Qualifier(
    private val clazz: KClass<*>,
    private val name: String = "default class name"

)

inline fun <reified T : Any> get(): T {
    return Di.get(Qualifier(T::class))
}

inline fun <reified T : Any> get(name: String): T {
    return Di.get(Qualifier(T::class, name))
}

inline fun <reified T : Any> inject() = lazy {
    get<T>()
}

inline fun <reified T : Any> inject(name:String) = lazy {
    get<T>(name)
}

abstract class DependencyFactory<T : Any>(protected val creator: () -> Any) {
    abstract fun get(): Any
}

class Singleton<T : Any>(creator: () -> Any) : DependencyFactory<T>(creator) {
    private val dependency: Any by lazy { creator.invoke() }
    override fun get(): Any = dependency
}

class Factory<T : Any>(creator: () -> Any) : DependencyFactory<T>(creator) {
    override fun get(): Any = creator()
}

inline fun <reified T : Any> singleton(noinline creator: () -> T): DependencyFactory<T> {
    return Singleton<T>(creator)
}

inline fun <reified T : Any> factory(noinline creator: () -> T): DependencyFactory<T> {
    return Factory<T>(creator)
}

class Module(private val block: Module.() -> Unit) {

    fun install() {
        block()
    }

    inline fun <reified T : Any> singleton(noinline creator: () -> T) {
        Di.add(Singleton<T>(creator))
    }

    inline fun <reified T : Any> singleton(name: String, noinline creator: () -> T) {
        Di.add(Qualifier(T::class, name), Singleton<T>(creator))
    }

    inline fun <reified T : Any> factory(noinline creator: () -> T) {
        Di.add(Factory<T>(creator))
    }

    inline fun <reified T : Any> factory(name: String, noinline creator: () -> T) {
        Di.add(Qualifier(T::class, name), Factory<T>(creator))
    }
}