package ducttape.exec

import ducttape.workflow.TaskTemplate
import ducttape.util.Files

class Purger(dirs: DirectoryArchitect) extends PackedDagVisitor {
  override def visit(task: TaskTemplate) {
    val where = dirs.assignPackedDir(task.taskDef.name)
    println("Removing directory: %s".format(where.getAbsolutePath))
    if (where.exists) {
      Files.deleteDir(where)
    }
  }
}
