package co.edu.uptc.aj.os.logic

interface QueueListener {
    fun updateTime()
    fun onAddedToFirstQueue(process: Process, index: Int)
    fun onAddedToSecondQueue(process: Process, index: Int)
    fun onAddedToThirdQueue(process: Process, index: Int)
    fun onRemovedFromFirstQueue(process: Process)
    fun onRemovedFromSecondQueue(process: Process)
    fun onRemovedFromThirdQueue(process: Process)
    fun onProcessFinished(process: Process)
}