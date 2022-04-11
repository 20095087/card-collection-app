package persistence

interface Serializer {
    @Throws(Exception::class)
    fun write(obj: Any?)

    @Throws(Exception::class)
    fun read(): Any?
    abstract fun JettisonMappedXmlDriver(): Any
    abstract fun XStream(jettisonMappedXmlDriver: Any): Any
}