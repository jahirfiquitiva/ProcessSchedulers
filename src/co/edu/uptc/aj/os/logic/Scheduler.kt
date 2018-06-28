package co.edu.uptc.aj.os.logic

abstract class Scheduler {
    
    abstract val type: SchedulerType
    
    var quantum: Int = 0
    var onStatusChanged: (Process, Int) -> Unit = { _, _ -> }
    
    private val processes: ArrayList<Process> = ArrayList()
    private var currentProcess: Process? = null
    private var lastTimeStamp: Int = 0
    
    init {
        processes.clear()
    }
    
    val number: Int
        get() = when (quantum) {
            2 -> 1
            4 -> 2
            else -> 3
        }
    
    val isFinished: Boolean
        get() = processes.isEmpty()
    
    fun addProcess(new: Process, received: Int, whenAdded: (Process, index: Int) -> Unit) {
        if (!new.isFinished) {
            if (new.arrival < 0)
                new.arrival = received
            processes.add(new)
            if (type == SchedulerType.SJF) {
                processes.sortBy { it.length }
            }
            val ind = processes.indexOfFirst { it.name.equals(new.name, true) }
            if (ind >= 0) whenAdded(new, ind)
        }
    }
    
    fun removeProcess(to: Process, whenRemoved: (Process) -> Unit) {
        try {
            currentProcess?.let {
                if (it.name.equals(to.name, true)) currentProcess = null
            }
            processes.removeAt(processes.indexOfFirst { it.name.equals(to.name, true) })
            whenRemoved(to)
        } catch (e: Exception) {
        }
    }
    
    fun checkStatus(time: Int) {
        currentProcess?.let {
            lastTimeStamp += 1
            it.reportExecuted(1)
            if (it.start < 0)
                it.start = time
            if (lastTimeStamp >= quantum || lastTimeStamp >= it.length) {
                it.end = time
                onStatusChanged(it, time)
                pickNext()
            }
        } ?: pickNext()
    }
    
    private fun pickNext() {
        currentProcess = processes.firstOrNull()
        lastTimeStamp = 0
    }
}

enum class SchedulerType(val title: String) {
    FIFO("FiFo"),
    ROUND_ROBIN("Round-Robin"),
    SJF("Shortest Job First");
}

class RoundRobin : Scheduler() {
    override val type: SchedulerType =
        SchedulerType.ROUND_ROBIN
}

class FiFo : Scheduler() {
    override val type: SchedulerType =
        SchedulerType.FIFO
}

class SJF : Scheduler() {
    override val type: SchedulerType =
        SchedulerType.SJF
}