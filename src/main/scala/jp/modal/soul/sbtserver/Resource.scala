package jp.modal.soul.sbtserver

import jp.modal.soul.sbtserver.util._
import jp.modal.soul.sbtserver.model.entity.Mock

import scala.collection.mutable.{Map => MutableMap}

/**
 * Created by imae on 2015/06/11.
 */
object Resource {
  private[this] final val DEFAULT_BASE_DIR = "."
  private[this] final val CHAR_SET = "UTF-8"
  private[this] var baseDir = DEFAULT_BASE_DIR
  private[this] val registered = MutableMap.empty[String, String => String]
  private[this] final val path = (target:String) => {
    if(target.startsWith("./")) target
    else s"./$target"
  }

  def setBaseDir(arg:String): Unit = baseDir = arg
  def addMock(mock:Mock):Unit = registered(mock.path) = mock.resource(_)

  def get(target:String) = {
    registered.get(target).fold {
      readFile(path(target), CHAR_SET)
    }{
      _(CHAR_SET)
    }
  }
}
