package co.edu.uptc.aj.os.logic

data class Process(val name: String, val length: Int, val priority: Int) {
    private var currentlyExecuted: Int = 0
    
    val isFinished
        get() = currentlyExecuted >= length
    
    var arrival = -1
    val wait: Int
        get() {
            val cal = start - arrival
            if (cal <= 0) return 0
            return cal
        }
    var start = -1
    var end = -1
    
    fun reportExecuted(time: Int) {
        currentlyExecuted += time
    }
    
    override fun toString(): String {
        return "Process $name [Length: $length, Priority: $priority, Arrived at: ${arrival}s, Started at: ${start}s, Finished at: ${end}s], Waited: ${wait}s"
    }
}