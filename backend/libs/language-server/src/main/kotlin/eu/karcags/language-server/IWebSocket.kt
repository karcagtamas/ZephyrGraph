package eu.karcags.`language-server`

interface IWebSocket {

    suspend fun send(content: String)
    fun onMessage(cb: (String) -> Unit)
    fun onError(cb: (Throwable) -> Unit)
    suspend fun onClose(cb: () -> Unit)
    suspend fun dispose()
}