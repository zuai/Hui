Hui
===

LinkedTransferQueueFix.java: 这是为JDK中的LinkedTransferQueue增加了补丁之后的文件，修正了因节点的取消而丧失信号的问题。
StampedLock.java：这是为JDK中的StampedLock增加了保存/恢复中断机制之后的文件，修正了原来的CPU占有问题，如果要测试，请注意其中使用的LockSupport.nextSecondarySeed等方法是包控制域。

