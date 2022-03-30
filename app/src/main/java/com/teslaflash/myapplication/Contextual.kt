@file:JvmName("Contextual")

package com.teslaflash.myapplication

import com.microsoft.thrifty.Struct
import com.microsoft.thrifty.TType
import com.microsoft.thrifty.kotlin.Adapter
import com.microsoft.thrifty.protocol.Protocol
import com.microsoft.thrifty.util.ProtocolUtil
import kotlin.String
import kotlin.Unit
import kotlin.jvm.JvmField
import kotlin.jvm.JvmName

public sealed class Contextual : Struct {
  public override fun write(protocol: Protocol): Unit {
    ADAPTER.write(protocol, this)
  }

  public data class Text(
    public val `value`: PlainText
  ) : Contextual() {
    public override fun toString(): String = "Contextual(text=$value)"
  }

  public data class Task(
    public val `value`: TaskUuid
  ) : Contextual() {
    public override fun toString(): String = "Contextual(task=$value)"
  }

  private class ContextualAdapter : Adapter<Contextual> {
    public override fun read(protocol: Protocol): Contextual {
      protocol.readStructBegin()
      var result : Contextual? = null
      while (true) {
        val fieldMeta = protocol.readFieldBegin()
        if (fieldMeta.typeId == TType.STOP) {
          break
        }
        when (fieldMeta.fieldId.toInt()) {
          1 -> {
            if (fieldMeta.typeId == TType.STRUCT) {
              val text = PlainText.ADAPTER.read(protocol)
              result = Text(text)
            } else {
              ProtocolUtil.skip(protocol, fieldMeta.typeId)
            }
          }
          2 -> {
            if (fieldMeta.typeId == TType.STRUCT) {
              val task = TaskUuid.ADAPTER.read(protocol)
              result = Task(task)
            } else {
              ProtocolUtil.skip(protocol, fieldMeta.typeId)
            }
          }
          else -> ProtocolUtil.skip(protocol, fieldMeta.typeId)
        }
        protocol.readFieldEnd()
      }
      protocol.readStructEnd()
      return result ?: error("unreadable")
    }

    public override fun write(protocol: Protocol, struct: Contextual): Unit {
      protocol.writeStructBegin("Contextual")
      when (struct) {
        is Text -> {
          protocol.writeFieldBegin("text", 1, TType.STRUCT)
          PlainText.ADAPTER.write(protocol, struct.value)
          protocol.writeFieldEnd()
        }
        is Task -> {
          protocol.writeFieldBegin("task", 2, TType.STRUCT)
          TaskUuid.ADAPTER.write(protocol, struct.value)
          protocol.writeFieldEnd()
        }
      }
      protocol.writeFieldStop()
      protocol.writeStructEnd()
    }
  }

  public companion object {
    @JvmField
    public val ADAPTER: Adapter<Contextual> = ContextualAdapter()
  }
}
