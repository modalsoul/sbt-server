package jp.modal.soul.sbtserver

import jp.modal.soul.sbtserver.model.entity.Mock
import jp.modal.soul.sbtserver.util._

import scala.collection.mutable.{Map => MutableMap}

/**
 * Created by imae on 2015/06/11.
 */
object Resource {
  private[this] final val DEFAULT_BASE_DIR = "."
  private[this] var baseDir = DEFAULT_BASE_DIR
  private[this] val mocks = MutableMap.empty[String, Mock]
  private[this] final val path = (target:String) => if(target.startsWith("./")) target else s"./$target"

  def setBaseDir(arg:String): Unit = baseDir = arg
  def addMock(mock:Mock):Unit = mocks(mock.path) = mock
  def get(target:String) = mocks.get(target).fold(readFile(path(target)))(_.resource)
  def mockList = mocks.toList.sortWith((m1,m2) => m1._1 > m2._1)
}
