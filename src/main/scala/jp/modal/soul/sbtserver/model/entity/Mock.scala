package jp.modal.soul.sbtserver.model.entity

import java.io.File

import jp.modal.soul.sbtserver.Resource

import scala.util.Try

/**
 * Created by imae on 2015/06/16.
 */
class Mock(val path:String, val resource:String){
  override def toString = s"$path:$resource"
}
object Mock {
  def apply(path:String, src:String):Mock = {
    val file = Try(new File(src)).toOption.filter(_.isFile)
    new Mock(path, file.fold(src)(f => Resource.get(src)))
  }
}
