package jp.modal.soul.sbtserver.model.entity

import java.io.File

import jp.modal.soul.sbtserver.util._

import scala.util.Try

/**
 * Created by imae on 2015/06/16.
 */
case class Mock(path:String, src:String) {
  def resource = Try(new File(src)).toOption.filter(_.isFile).fold(src){f => readFile(src)}
}
