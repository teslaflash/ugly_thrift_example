@file:JvmName("PushMsget")

package com.teslaflash.myapplication

import com.microsoft.thrifty.Struct
import com.microsoft.thrifty.TType
import com.microsoft.thrifty.ThriftField
import com.microsoft.thrifty.kotlin.Adapter
import com.microsoft.thrifty.protocol.Protocol
import com.microsoft.thrifty.util.ProtocolUtil
import kotlin.Boolean
import kotlin.String
import kotlin.Unit
import kotlin.jvm.JvmField
import kotlin.jvm.JvmName

public data class PushMsget(
  @JvmField
  @ThriftField(fieldId = 1)
  public val context: Contextual?,
  @JvmField
  @ThriftField(fieldId = 2)
  public val timestamp: String?,
  @JvmField
  @ThriftField(fieldId = 3)
  public val isShowabl: Boolean?
) : Struct {
  public override fun write(protocol: Protocol): Unit {
    ADAPTER.write(protocol, this)
  }

  private class PushMsgetAdapter : Adapter<PushMsget> {
    public override fun read(protocol: Protocol): PushMsget {
      var _local_context: Contextual? = null
      var _local_timestamp: String? = null
      var _local_isShowabl: Boolean? = null
      protocol.readStructBegin()
      while (true) {
        val fieldMeta = protocol.readFieldBegin()
        if (fieldMeta.typeId == TType.STOP) {
          break
        }
        when (fieldMeta.fieldId.toInt()) {
          1 -> {
            if (fieldMeta.typeId == TType.STRUCT) {
              val context = Contextual.ADAPTER.read(protocol)
              _local_context = context
            } else {
              ProtocolUtil.skip(protocol, fieldMeta.typeId)
            }
          }
          2 -> {
            if (fieldMeta.typeId == TType.STRING) {
              val timestamp = protocol.readString()
              _local_timestamp = timestamp
            } else {
              ProtocolUtil.skip(protocol, fieldMeta.typeId)
            }
          }
          3 -> {
            if (fieldMeta.typeId == TType.BOOL) {
              val isShowabl = protocol.readBool()
              _local_isShowabl = isShowabl
            } else {
              ProtocolUtil.skip(protocol, fieldMeta.typeId)
            }
          }
          else -> ProtocolUtil.skip(protocol, fieldMeta.typeId)
        }
        protocol.readFieldEnd()
      }
      protocol.readStructEnd()
      return PushMsget(
          context = _local_context,
          timestamp = _local_timestamp,
          isShowabl = _local_isShowabl)
    }

    public override fun write(protocol: Protocol, struct: PushMsget): Unit {
      protocol.writeStructBegin("PushMsget")
      if (struct.context != null) {
        protocol.writeFieldBegin("context", 1, TType.STRUCT)
        Contextual.ADAPTER.write(protocol, struct.context)
        protocol.writeFieldEnd()
      }
      if (struct.timestamp != null) {
        protocol.writeFieldBegin("timestamp", 2, TType.STRING)
        protocol.writeString(struct.timestamp)
        protocol.writeFieldEnd()
      }
      if (struct.isShowabl != null) {
        protocol.writeFieldBegin("isShowabl", 3, TType.BOOL)
        protocol.writeBool(struct.isShowabl)
        protocol.writeFieldEnd()
      }
      protocol.writeFieldStop()
      protocol.writeStructEnd()
    }
  }

  public companion object {
    @JvmField
    public val ADAPTER: Adapter<PushMsget> = PushMsgetAdapter()
  }
}
