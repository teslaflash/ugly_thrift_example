@file:JvmName("PlainText")

package com.teslaflash.myapplication

import com.microsoft.thrifty.Struct
import com.microsoft.thrifty.TType
import com.microsoft.thrifty.ThriftField
import com.microsoft.thrifty.kotlin.Adapter
import com.microsoft.thrifty.protocol.Protocol
import com.microsoft.thrifty.util.ProtocolUtil
import kotlin.String
import kotlin.Unit
import kotlin.jvm.JvmField
import kotlin.jvm.JvmName

public data class PlainText(
  @JvmField
  @ThriftField(fieldId = 1)
  public val text: String?
) : Struct {
  public override fun write(protocol: Protocol): Unit {
    ADAPTER.write(protocol, this)
  }

  private class PlainTextAdapter : Adapter<PlainText> {
    public override fun read(protocol: Protocol): PlainText {
      var _local_text: String? = null
      protocol.readStructBegin()
      while (true) {
        val fieldMeta = protocol.readFieldBegin()
        if (fieldMeta.typeId == TType.STOP) {
          break
        }
        when (fieldMeta.fieldId.toInt()) {
          1 -> {
            if (fieldMeta.typeId == TType.STRING) {
              val text = protocol.readString()
              _local_text = text
            } else {
              ProtocolUtil.skip(protocol, fieldMeta.typeId)
            }
          }
          else -> ProtocolUtil.skip(protocol, fieldMeta.typeId)
        }
        protocol.readFieldEnd()
      }
      protocol.readStructEnd()
      return PlainText(text = _local_text)
    }

    public override fun write(protocol: Protocol, struct: PlainText): Unit {
      protocol.writeStructBegin("PlainText")
      if (struct.text != null) {
        protocol.writeFieldBegin("text", 1, TType.STRING)
        protocol.writeString(struct.text)
        protocol.writeFieldEnd()
      }
      protocol.writeFieldStop()
      protocol.writeStructEnd()
    }
  }

  public companion object {
    @JvmField
    public val ADAPTER: Adapter<PlainText> = PlainTextAdapter()
  }
}
